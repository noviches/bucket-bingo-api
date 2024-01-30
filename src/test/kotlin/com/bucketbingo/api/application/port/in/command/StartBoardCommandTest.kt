package com.bucketbingo.api.application.port.`in`.command

import com.bucketbingo.api.adapter.out.repository.BoardRepository
import com.bucketbingo.api.application.port.`in`.StartBoardUseCase
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
class StartBoardCommandTest : BehaviorSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    private lateinit var startUseCase: StartBoardUseCase

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

        this.Given("임의의 빙고판에 대한 시작 요청이 주어졌을 때") {
            val request = StartBoardUseCase.Request(
                boardId = TEST_BOARD_ID,
            )

            When("요청된 빙고판이 존재하지 않는 경우") {
                every {
                    getBoardPort.findOne(any())
                } returns null

                val exception = shouldThrow<RuntimeException> {
                    startUseCase.execute(TEST_USER, request)
                }

                Then("예외가 발생한다.") {

                    exception.message shouldBe "cannot find board(${TEST_BOARD_ID})"
                }
            }

            When("100년 이후의 값을 갖는 endDate를 포함한 빙고판에 대한 시작 요청의 경우") {
                every {
                    getBoardPort.findOne(any())
                } returns Board(
                    endDate = LocalDateTime.now().plusYears(101L),

                    id = TEST_BOARD_ID,
                    name = TEST_BOARD_NAME,
                    status = BoardStatus.DRAFT,
                    description = "",
                    size = TEST_BOARD_SIZE,
                    squares = getSquaresMockBy(size = TEST_BOARD_SIZE),
                    targetCount = TEST_BOARD_SIZE,
                    startDate = null,
                    createdById = TEST_USER_ID,
                    updatedById = TEST_USER_ID,
                )

                val exception = shouldThrow<RuntimeException> {
                    startUseCase.execute(TEST_USER, request)
                }

                Then("예외가 발생한다.") {

                    exception.message shouldBe "100년까지 밖에 안되요"
                }
            }

            When("endDate를 포함하지 않는 빙고판에 대한 시작 요청의 경우") {
                every {
                    getBoardPort.findOne(any())
                } returns Board(
                    endDate = null,

                    id = TEST_BOARD_ID,
                    name = TEST_BOARD_NAME,
                    status = BoardStatus.DRAFT,
                    description = "",
                    size = TEST_BOARD_SIZE,
                    squares = getSquaresMockBy(size = TEST_BOARD_SIZE),
                    targetCount = TEST_BOARD_SIZE,
                    startDate = null,
                    createdById = TEST_USER_ID,
                    updatedById = TEST_USER_ID,
                )

                val exception = shouldThrow<RuntimeException> {
                    startUseCase.execute(TEST_USER, request)
                }

                Then("예외가 발생한다.") {

                    exception.message shouldBe "please set end date"
                }
            }

            When("DRAFT 상태가 아닌 빙고판에 대한 시작 요청의 경우") {
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
                    endDate = LocalDateTime.now().plusYears(1L),
                    createdById = TEST_USER_ID,
                    updatedById = TEST_USER_ID,
                )

                val exception = shouldThrow<RuntimeException> {
                    startUseCase.execute(TEST_USER, request)
                }

                Then("예외가 발생한다.") {

                    exception.message shouldBe "board status must be DRAFT"
                }
            }

            When("목표가 설정되지 않은 빙고칸을 하나라도 갖는 빙고판에 대한 시작 요청의 경우") {
                every {
                    getBoardPort.findOne(any())
                } returns Board(
                    squares = getSquaresMockBy(size = TEST_BOARD_SIZE),

                    id = TEST_BOARD_ID,
                    name = TEST_BOARD_NAME,
                    status = BoardStatus.DRAFT,
                    description = "",
                    size = TEST_BOARD_SIZE,
                    targetCount = TEST_BOARD_SIZE,
                    startDate = null,
                    endDate = LocalDateTime.now().plusYears(1L),
                    createdById = TEST_USER_ID,
                    updatedById = TEST_USER_ID,
                )

                val exception = shouldThrow<RuntimeException> {
                    startUseCase.execute(TEST_USER, request)
                }

                Then("예외가 발생한다.") {

                    exception.message shouldBe "objective content cannot be empty string"
                }
            }

            When("시작 요청에 어떠한 이상도 없는 경우") {
                val persistedBoard = Board(
                    id = TEST_BOARD_ID,
                    name = TEST_BOARD_NAME,
                    status = BoardStatus.DRAFT,
                    description = "",
                    size = TEST_BOARD_SIZE,
                    squares = getSquaresMockBy(size = TEST_BOARD_SIZE).map { it.copy(status = SquareStatus.TODO, objective = Objective(content = "do something")) },
                    targetCount = TEST_BOARD_SIZE,
                    startDate = null,
                    endDate = LocalDateTime.now().plusYears(1L),
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

                startUseCase.execute(TEST_USER, request)

                Then("updateBoardPort에 적절한 데이터가 전달된다.") {
                    val capturedBoard = boardSlot.captured

                    capturedBoard shouldBe persistedBoard.copy(
                        status = BoardStatus.ACTIVE
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
