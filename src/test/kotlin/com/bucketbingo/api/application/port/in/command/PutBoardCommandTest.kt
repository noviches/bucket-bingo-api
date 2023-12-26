package com.bucketbingo.api.application.port.`in`.command

import com.bucketbingo.api.application.port.`in`.PutBoardUseCase
import com.bucketbingo.api.application.port.out.persistence.UpdateBoardPort
import com.bucketbingo.api.domain.Square
import com.bucketbingo.api.domain.SquareStatus
import com.bucketbingo.api.domain.User
import com.ninjasquad.springmockk.SpykBean
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldNotBe
import io.mockk.unmockkObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class PutBoardCommandTest: BehaviorSpec() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    private lateinit var command: PutBoardUseCase

    @SpykBean
    private lateinit var outgoingPort: UpdateBoardPort

    init {
        afterEach { unmockkObject(outgoingPort) }

        this.Given("테스트") {
            val user = User("tester")
            val squares = (1..25).map {
                Square(
                    order = it,
                    objective = null,
                    status = SquareStatus.TODO,
                    updatedAt = LocalDateTime.now(),
                )
            }
            val request = PutBoardUseCase.Request(
                boardId = "658aa5487fbfca00ecb0bc64",
                squares = squares,
                name = "업데이트했지롱",
                description = "늘보는 고개만 끄덕이고 있었다",
                endDate = LocalDateTime.now(),
            )

            When("둘을 더하면") {
                val result = command.execute(user, request)

                Then("3이 나온다") {
                    result shouldNotBe null
                }
            }
        }
    }
}