package com.bucketbingo.api.application.port.`in`

import java.time.LocalDateTime

interface CreateBoardUseCase : UseCase<CreateBoardUseCase.Request, String> {

    data class Request(

        val name: String,

        val size: Int,

        val description: String?,

        val targetCount: Int,

        val endDate: LocalDateTime?

    )
}
