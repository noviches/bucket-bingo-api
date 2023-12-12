package com.bucketbingo.api.application.port.out.persistence

import com.bucketbingo.api.application.port.`in`.ListBoardsUseCase
import com.bucketbingo.api.domain.Board
import com.bucketbingo.api.domain.Pagination

interface ListBoardsPort {

    fun findAll(data: ListBoardsUseCase.Request): Pagination<Board>
}