package com.bucketbingo.api.application.port.`in`.command

import com.bucketbingo.api.adapter.out.repository.BoardRepository
import com.bucketbingo.api.application.port.`in`.UpdateSquareUseCase
import com.bucketbingo.api.application.port.out.persistence.GetBoardPort
import com.bucketbingo.api.application.port.out.persistence.UpdateBoardPort
import com.bucketbingo.api.domain.*
import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.slot
import io.mockk.unmockkObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class UpdateSquareCommandTest : BehaviorSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    private lateinit var command: UpdateSquareUseCase

    @MockkBean
    private lateinit var repository: BoardRepository

    @SpykBean
    private lateinit var getBoardPort: GetBoardPort

    @SpykBean
    private lateinit var updateBoardPort: UpdateBoardPort

    companion object {
        private const val TEST_BOARD_ID = "65816b425a4b2a7f0b52b964"
        private const val TEST_BOARD_NAME = "test-board"
        private const val TEST_BOARD_SIZE = 5

        private const val TEST_USER_ID = "tester"

        private val TEST_USER = User(TEST_USER_ID)
    }

    init {
        afterEach {
            unmockkObject(getBoardPort)
            unmockkObject(updateBoardPort)
        }

        this.Given("임의의 빙고칸에 대한 수정요청이 주어졌을 때") {
            val squareId = 25
            val squareStatus = SquareStatus.DONE
            val request = UpdateSquareUseCase.Request(
                boardId = TEST_BOARD_ID,
                squareId = squareId,
                status = squareStatus
            )

            When("빙고판이 존재하지 않는 경우") {
                every {
                    getBoardPort.findOne(any())
                } returns null

                val exception = shouldThrow<RuntimeException> {
                    command.execute(TEST_USER, request)
                }

                Then("예외가 발생한다.") {
                    println(exception)
                    exception.message shouldBe "cannot find board($TEST_BOARD_ID)"
                }
            }

            When("ACTIVE가 아닌 빙고판에 대한 요청인 경우") {
                every {
                    getBoardPort.findOne(any())
                } returns Board(


                    status = BoardStatus.DRAFT,
                    id = TEST_BOARD_ID,
                    name = TEST_BOARD_NAME,
                    description = "",
                    squares = getSquaresMockBy(size = TEST_BOARD_SIZE),
                    size = TEST_BOARD_SIZE,
                    startDate = null,
                    endDate = null,
                    createdById = TEST_USER_ID,
                    updatedById = TEST_USER_ID,
                )

                val exception = shouldThrow<RuntimeException> {
                    command.execute(TEST_USER, request)
                }

                Then("예외가 발생한다.") {
                    exception.message shouldBe "cannot update square"
                }
            }

            When("빙고칸 아이디가 빙고판에 포함된 빙고칸 개수보다 큰 경우") {
                every {
                    getBoardPort.findOne(any())
                } returns Board(
                    size = TEST_BOARD_SIZE - 1,

                    status = BoardStatus.ACTIVE,
                    id = TEST_BOARD_ID,
                    name = TEST_BOARD_NAME,
                    description = "",
                    squares = getSquaresMockBy(size = TEST_BOARD_SIZE - 1),
                    startDate = null,
                    endDate = null,
                    createdById = TEST_USER_ID,
                    updatedById = TEST_USER_ID,
                )

                val exception = shouldThrow<RuntimeException> {
                    command.execute(TEST_USER, request)
                }

                Then("예외가 발생한다.") {
                    exception.message shouldBe "maximum square id is 16"
                }
            }

            When("수정 요청에 어떠한 이상도 없는 경우") {
                val persistedBoard = Board(
                    id = TEST_BOARD_ID,
                    name = TEST_BOARD_NAME,
                    size = TEST_BOARD_SIZE,
                    status = BoardStatus.ACTIVE,
                    description = "",
                    squares = getSquaresMockBy(size = TEST_BOARD_SIZE),
                    startDate = null,
                    endDate = null,
                    createdById = TEST_USER_ID,
                    updatedById = TEST_USER_ID,
                )
                every {
                    getBoardPort.findOne(any())
                } returns persistedBoard

                val boardSlot = slot<Board>()
                every {
                    updateBoardPort.update(
                        board = capture(boardSlot)
                    )
                } returns Unit

                command.execute(TEST_USER, request)

                Then("updateBoardPort에 적절한 데이터가 전달된다.") {
                    val capturedBoard = boardSlot.captured

                    capturedBoard shouldBe persistedBoard.copy(
                        squares = persistedBoard.squares.map { square ->
                            if (square.order == squareId) square.copy(status = squareStatus) else square.copy()
                        },
                    )
                }
            }
        }
    }

    private fun getSquaresMockBy(size: Int): List<Square> {
        val expectedSize = size * size
        return (1..expectedSize).map {
            Square(
                order = it,
                objective = null,
                updatedAt = LocalDateTime.now(),
            )
        }
    }

}
