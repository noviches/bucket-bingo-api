package com.bucketbingo.api.application.port.`in`

import com.bucketbingo.api.domain.Board
import com.bucketbingo.api.domain.Pagination

interface ListBoardsUseCase : UseCase<ListBoardsUseCase.Request, Pagination<Board>> {

    data class Request(
        val pageSize: Int = 20,
    )
}
