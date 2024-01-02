package com.bucketbingo.api.application.port.`in`

import com.bucketbingo.api.domain.SquareStatus

interface UpdateSquareUseCase : UseCase<UpdateSquareUseCase.Request, Unit> {

    data class Request(

        val boardId: String,

        val squareId: Int,

        val status: SquareStatus

    )
}
