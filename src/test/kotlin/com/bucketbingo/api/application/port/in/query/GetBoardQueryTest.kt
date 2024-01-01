package com.bucketbingo.api.application.port.`in`.query

import com.bucketbingo.api.adapter.out.repository.BoardRepository
import com.bucketbingo.api.application.port.`in`.GetBoardUseCase
import com.bucketbingo.api.application.port.out.persistence.GetBoardPort
import com.bucketbingo.api.domain.Board
import com.bucketbingo.api.domain.Square
import com.bucketbingo.api.domain.User
import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
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
class GetBoardQueryTest : BehaviorSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    private lateinit var getUseCase: GetBoardUseCase

    @MockkBean
    private lateinit var repository: BoardRepository

    @SpykBean
    private lateinit var outgoingPort: GetBoardPort

    // TODO: 모든 테스트 코드에서 사용하는 공통 정보는 object로 추출할 것
    companion object {
        private const val TEST_BOARD_ID = "65816b425a4b2a7f0b52b964"
        private const val TEST_BOARD_NAME = "test-board"
        private const val TEST_BOARD_SIZE = 5

        private const val TEST_USER_ID = "tester"

        private val TEST_USER = User(TEST_USER_ID)
    }

    init {
        afterEach { unmockkObject(outgoingPort) }

        this.Given("임의의 빙고판 조회 요청에 대해") {
            val request = GetBoardUseCase.Request(
                id = TEST_BOARD_ID,
            )

            When("존재하는 빙고판에 대해 조회 유즈케이스를 실행할 경우") {
                every {
                    outgoingPort.findOne(any())
                } returns Board(
                    id = TEST_BOARD_ID,
                    name = TEST_BOARD_NAME,
                    description = "",
                    size = TEST_BOARD_SIZE,
                    squares = getSquaresMockBy(size = TEST_BOARD_SIZE),
                    startDate = null,
                    endDate = null,
                    createdById = TEST_USER_ID,
                    updatedById = TEST_USER_ID,
                )
                val result = getUseCase.execute(TEST_USER, request)

                Then("Board 인스턴스가 반환된다.") {

                    result shouldNotBe null
                    result.id shouldNotBe null
                    result.squares shouldHaveSize (TEST_BOARD_SIZE * TEST_BOARD_SIZE)
                }
            }

            And("그러나 존재하지 않는 빙고판의 경우") {
                every {
                    outgoingPort.findOne(any())
                } returns null

                val exception = shouldThrow<RuntimeException> {
                    getUseCase.execute(TEST_USER, request)
                }

                Then("예외가 발생한다.") {

                    exception.message shouldBe "board(${TEST_BOARD_ID}) not found"
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
