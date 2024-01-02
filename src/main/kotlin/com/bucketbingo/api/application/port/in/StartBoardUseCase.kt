package com.bucketbingo.api.application.port.`in`

interface StartBoardUseCase : UseCase<StartBoardUseCase.Request, Unit> {

    data class Request(

        val boardId: String

    )
}
