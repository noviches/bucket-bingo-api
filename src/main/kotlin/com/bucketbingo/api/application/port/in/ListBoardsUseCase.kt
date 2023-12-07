package com.bucketbingo.api.application.port.`in`

import com.bucketbingo.api.domain.Board

interface ListBoardsUseCase : UseCase<ListBoardsUseCase.Request, ListBoardsUseCase.Response> {

    data class Request(
        val pageSize: Int = 20,
    )

    data class Response(

        val items: List<Board>,

        val totalCount: Int,

        val pageSize: Int,

        val pageOffset: Int,

        val totalPageCount: Int,

        )
}
