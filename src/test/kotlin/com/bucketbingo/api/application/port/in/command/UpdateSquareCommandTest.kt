package com.bucketbingo.api.application.port.`in`.command

import com.bucketbingo.api.application.port.`in`.UpdateSquareUseCase
import com.bucketbingo.api.domain.SquareStatus
import com.bucketbingo.api.domain.User
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UpdateSquareCommandTest : BehaviorSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    private lateinit var command: UpdateSquareUseCase

    init {
        this.Given("테스트") {
            val user = User("tester")
            val request = UpdateSquareUseCase.Request(
                boardId = "658d4d1ef385c32cf90d8cf5",
                squareId = 1,
                status = SquareStatus.DONE
            )

            When("658d4d1ef385c32cf90d8cf5 ID로 Board를 가져오면") {
                val result = command.execute(user, request)

                Then("Board 객체를 가져온다") {
                    println(result)
                    result shouldNotBe null
                }
            }
        }

        // TODO: outgoingPort에 적합한 객체가 전달되었는지 검증해야 함!
    }


}
