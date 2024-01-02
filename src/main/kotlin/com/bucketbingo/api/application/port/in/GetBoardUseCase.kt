package com.bucketbingo.api.application.port.`in`

import com.bucketbingo.api.domain.Board

interface GetBoardUseCase : UseCase<GetBoardUseCase.Request, Board> {

    data class Request(

        val id: String

    )
}
