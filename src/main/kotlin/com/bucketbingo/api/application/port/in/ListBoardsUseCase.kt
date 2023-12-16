package com.bucketbingo.api.application.port.`in`

import com.bucketbingo.api.domain.Board
import com.bucketbingo.api.domain.Pagination

interface ListBoardsUseCase : UseCase<ListBoardsUseCase.Request, Pagination<Board>> {

    data class Request(

        val pageSize: Int = 20,

        val pageOffset: Int = 0,

        val sort: Sort = Sort(),

        )

    data class Sort(

        val by: By = By.CREATED_AT,

        val direction: Direction = Direction.DESC,

        ) {

        enum class Direction {
            ASC,
            DESC,
        }

        enum class By(val value: String) {
            CREATED_AT("createdAt"),
        }
    }
}
