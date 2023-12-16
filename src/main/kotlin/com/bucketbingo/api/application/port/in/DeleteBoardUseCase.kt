package com.bucketbingo.api.application.port.`in`

interface DeleteBoardUseCase : UseCase<DeleteBoardUseCase.Request, Unit> {

    data class Request(

        val id: String,

        )
}
