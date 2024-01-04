package com.bucketbingo.api.application.port.`in`.command

import com.bucketbingo.api.adapter.out.repository.BoardRepository
import com.bucketbingo.api.application.port.`in`.CreateBoardUseCase
import com.bucketbingo.api.application.port.out.persistence.CreateBoardPort
import com.bucketbingo.api.domain.Board
import com.bucketbingo.api.domain.User
import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
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

@SpringBootTest
@EnableAutoConfiguration(
    exclude = [MongoAutoConfiguration::class]
)
class CreateBoardCommandTest : BehaviorSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    private lateinit var createUseCase: CreateBoardUseCase

    @MockkBean
    private lateinit var repository: BoardRepository

    @SpykBean
    private lateinit var outgoingPort: CreateBoardPort

    companion object {
        private const val TEST_BOARD_ID = "65816b425a4b2a7f0b52b964"
        private const val TEST_BOARD_NAME = "test-board"
        private const val TEST_BOARD_SIZE = 5

        private const val TEST_USER_ID = "tester"

        private val TEST_USER = User(TEST_USER_ID)
    }

    init {
        afterEach { unmockkObject(outgoingPort) }

        this.Given("빙고판 생성 요청에 대해") {
            val request = CreateBoardUseCase.Request(
                name = TEST_BOARD_NAME,
                size = TEST_BOARD_SIZE,
                description = null,
                targetCount = TEST_BOARD_SIZE,
                endDate = null,
            )

            val boardSlot = slot<Board>()

            every {
                outgoingPort.create(
                    board = capture(boardSlot)
                )
            } returns TEST_BOARD_ID

            When("빙고판 생성 유즈케이스를 실행할 경우") {
                val result = createUseCase.execute(TEST_USER, request)

                Then("어댑터에 전달된 빙고판에는 size의 제곱만큼의 크기를 갖는 squares가 포함된다.") {
                    val capturedBoard = boardSlot.captured
                    val expectedSquaresLength = TEST_BOARD_SIZE * TEST_BOARD_SIZE

                    capturedBoard.squares.size shouldBe expectedSquaresLength
                    capturedBoard.squares.map { it.order } shouldBe (1..expectedSquaresLength).map { it }
                }

                Then("처리가 완료된 후에는 생성된 빙고판 식별자가 반환된다.") {
                    result shouldBe TEST_BOARD_ID
                }
            }
        }
    }
}
