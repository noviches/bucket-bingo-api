package com.bucketbingo.api.application.port.`in`.command

import com.bucketbingo.api.application.port.`in`.CreateBoardUseCase
import com.bucketbingo.api.application.port.out.persistence.CreateBoardPort
import com.bucketbingo.api.domain.User
import com.ninjasquad.springmockk.SpykBean
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldNotBe
import io.mockk.unmockkObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
//@ActiveProfiles("test")
class CreateBoardCommandTest : BehaviorSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    private lateinit var command: CreateBoardUseCase

    @SpykBean
    private lateinit var outgoingPort: CreateBoardPort

    init {
        afterEach { unmockkObject(outgoingPort) }

        this.Given("테스트") {
            val user = User("tester")
            val request = CreateBoardUseCase.Request(
                name = "test-board-33",
                size = 5,
                description = null,
                endDate = null,
            )

            When("둘을 더하면") {
                val result = command.execute(user, request)
                Then("3이 나온다") {
                    result shouldNotBe null
                }
            }
        }

        // TODO: outgoingPort에 적합한 객체가 전달되었는지 검증해야 함!
    }


}
