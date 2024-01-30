package com.bucketbingo.api.application.port.`in`.command

import com.bucketbingo.api.adapter.out.repository.BoardRepository
import com.bucketbingo.api.application.port.`in`.PutBoardUseCase
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
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
@EnableAutoConfiguration(
    exclude = [MongoAutoConfiguration::class]
)
class PutBoardCommandTest: BehaviorSpec() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    private lateinit var putUseCase: PutBoardUseCase

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

        this.Given("유효한 빙고칸이 주어졌을 때") {
            val squares = (1..25).map {
                Square(
                    order = it,
                    objective = Objective(
                        content = "write test code."
                    ),
                    status = SquareStatus.DRAFT,
                    updatedAt = LocalDateTime.now(),
                )
            }

            When("존재하지 않는 빙고판에 대한 수정 요청의 경우") {
                every {
                    getBoardPort.findOne(any())
                } returns null

                val request = PutBoardUseCase.Request(
                    boardId = TEST_BOARD_ID,
                    squares = squares,
                    name = TEST_BOARD_NAME,
                    description = "description for test",
                    targetCount = TEST_BOARD_SIZE,
                    endDate = LocalDateTime.now(),
                )
                val exception = shouldThrow<RuntimeException> {
                    putUseCase.execute(TEST_USER, request)
                }

                Then("예외가 발생한다.") {

                    exception.message shouldBe "cannot find board($TEST_BOARD_ID)"
                }
            }

            When("DRAFT 상태가 아닌 빙고판에 대한 수정 요청의 경우") {
                every {
                    getBoardPort.findOne(any())
                } returns Board(
                    status = BoardStatus.ACTIVE,
                    id = TEST_BOARD_ID,
                    name = TEST_BOARD_NAME,
                    description = "",
                    size = TEST_BOARD_SIZE,
                    squares = getSquaresMockBy(size = TEST_BOARD_SIZE),
                    targetCount = TEST_BOARD_SIZE,
                    startDate = null,
                    endDate = null,
                    createdById = TEST_USER_ID,
                    updatedById = TEST_USER_ID,
                )

                val request = PutBoardUseCase.Request(
                    boardId = TEST_BOARD_ID,
                    squares = squares,
                    name = TEST_BOARD_NAME,
                    description = "description for test",
                    endDate = LocalDateTime.now(),
                    targetCount = TEST_BOARD_SIZE,
                )
                val exception = shouldThrow<RuntimeException> {
                    putUseCase.execute(TEST_USER, request)
                }

                Then("예외가 발생한다.") {

                    exception.message shouldBe "cannot update board"
                }
            }

            When("요청된 빙고칸의 크기와 수정 대상 빙고판의 크기가 맞지 않는 경우") {
                val persistedBoardSize = TEST_BOARD_SIZE + 1
                every {
                    getBoardPort.findOne(any())
                } returns Board(
                    size = persistedBoardSize,

                    id = TEST_BOARD_ID,
                    name = TEST_BOARD_NAME,
                    status = BoardStatus.DRAFT,
                    description = "",
                    squares = getSquaresMockBy(size = TEST_BOARD_SIZE),
                    targetCount = TEST_BOARD_SIZE,
                    startDate = null,
                    endDate = null,
                    createdById = TEST_USER_ID,
                    updatedById = TEST_USER_ID,
                )

                val request = PutBoardUseCase.Request(
                    boardId = TEST_BOARD_ID,
                    squares = squares,
                    name = TEST_BOARD_NAME,
                    description = "description for test",
                    targetCount = TEST_BOARD_SIZE,
                    endDate = LocalDateTime.now(),
                )
                val exception = shouldThrow<RuntimeException> {
                    putUseCase.execute(TEST_USER, request)
                }

                Then("예외가 발생한다.") {

                    val expectedSize = persistedBoardSize * persistedBoardSize
                    exception.message shouldBe "squares size must be $expectedSize"
                }
            }

            When("요청된 빙고칸에 TODO 상태가 아닌 빙고칸이 하나라도 포함된 경우") {
                every {
                    getBoardPort.findOne(any())
                } returns Board(
                    id = TEST_BOARD_ID,
                    name = TEST_BOARD_NAME,
                    size = TEST_BOARD_SIZE,
                    status = BoardStatus.DRAFT,
                    description = "",
                    squares = getSquaresMockBy(size = TEST_BOARD_SIZE),
                    targetCount = TEST_BOARD_SIZE,
                    startDate = null,
                    endDate = null,
                    createdById = TEST_USER_ID,
                    updatedById = TEST_USER_ID,
                )

                val request = PutBoardUseCase.Request(
                    boardId = TEST_BOARD_ID,
                    squares = squares.mapIndexed { index, square ->
                        square.copy(
                            status = if(index == 0) {
                                SquareStatus.IN_PROGRESS
                            } else {
                                SquareStatus.DRAFT
                            }
                        )
                    },
                    name = TEST_BOARD_NAME,
                    description = "description for test",
                    targetCount = TEST_BOARD_SIZE,
                    endDate = LocalDateTime.now(),
                )
                val exception = shouldThrow<RuntimeException> {
                    putUseCase.execute(TEST_USER, request)
                }

                Then("예외가 발생한다.") {

                    exception.message shouldBe "all board squares status must be DRAFT"
                }
            }

            When("수정 요청에 어떠한 이상도 없는 경우") {
                val persistedBoard = Board(
                    id = TEST_BOARD_ID,
                    name = TEST_BOARD_NAME,
                    size = TEST_BOARD_SIZE ,
                    status = BoardStatus.DRAFT,
                    description = "",
                    squares = getSquaresMockBy(size = TEST_BOARD_SIZE),
                    targetCount = TEST_BOARD_SIZE,
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

                val endDate = LocalDateTime.now()
                val request = PutBoardUseCase.Request(
                    boardId = TEST_BOARD_ID,
                    squares = squares,
                    name = TEST_BOARD_NAME,
                    description = "description for test",
                    targetCount = TEST_BOARD_SIZE,
                    endDate = endDate,
                )

                putUseCase.execute(TEST_USER, request)

                Then("updateBoardPort에 적절한 데이터가 전달된다.") {
                    val capturedBoard = boardSlot.captured

                    capturedBoard shouldBe persistedBoard.copy(
                        squares = squares,
                        name = TEST_BOARD_NAME,
                        description = "description for test",
                        endDate = endDate,
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