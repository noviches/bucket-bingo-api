package com.bucketbingo.api.application.port.`in`.command

import com.bucketbingo.api.application.port.`in`.StartBoardUseCase
import com.bucketbingo.api.domain.User
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class StartBoardCommandTest : BehaviorSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    private lateinit var command: StartBoardUseCase

    init {
        this.Given("테스트") {
            val user = User("tester")

            val request = StartBoardUseCase.Request(
                boardId = "658aa5487fbfca00ecb0bc64",
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
