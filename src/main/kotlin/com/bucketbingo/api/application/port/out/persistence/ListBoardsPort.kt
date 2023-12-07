package com.bucketbingo.api.application.port.out.persistence

import com.bucketbingo.api.application.port.`in`.ListBoardsUseCase

interface ListBoardsPort {

    fun find(data: ListBoardsUseCase.Request): ListBoardsUseCase.Response
}