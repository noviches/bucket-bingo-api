package com.bucketbingo.api.application.port.`in`

import java.time.LocalDateTime

interface CreateBoardUseCase : UseCase<CreateBoardUseCase.Request, CreateBoardUseCase.Response> {

    data class Request(
        val name: String,

        val size: Int,

        val description: String?,

        val endDate: LocalDateTime?,
    )

    data class Response(
        val id: Int,
    )
}
