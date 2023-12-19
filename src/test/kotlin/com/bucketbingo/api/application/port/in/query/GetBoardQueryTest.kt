package com.bucketbingo.api.application.port.`in`.query

import com.bucketbingo.api.application.port.`in`.GetBoardUseCase
import com.bucketbingo.api.application.port.out.persistence.GetBoardPort
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
class GetBoardQueryTest : BehaviorSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    private lateinit var query: GetBoardUseCase

    @SpykBean
    private lateinit var outgoingPort: GetBoardPort

    init {
        afterEach { unmockkObject(outgoingPort) }

        this.Given("테스트") {
            val user = User("tester")
            val request = GetBoardUseCase.Request(
                id = "65816b425a4b2a7f0b52b964",
            )

            When("65816b425a4b2a7f0b52b964 ID로 Board를 가져오면") {
                val result = query.execute(user, request)

                Then("Board 객체를 가져온다") {
                    println(result)
                    result shouldNotBe null
                }
            }
        }

        // TODO: outgoingPort에 적합한 객체가 전달되었는지 검증해야 함!
    }


}
