package com.bucketbingo.api.adapter.out

import com.bucketbingo.api.application.port.`in`.ListBoardsUseCase
import com.bucketbingo.api.application.port.out.persistence.CreateBoardPort
import com.bucketbingo.api.application.port.out.persistence.ListBoardsPort
import com.bucketbingo.api.common.annotation.Adapter
import com.bucketbingo.api.domain.Board

@Adapter
class BucketBingoAdapter : CreateBoardPort, ListBoardsPort {

    private var id: Long = 1
    private val repository: MutableList<Board> = mutableListOf()

    override fun create(board: Board): Long {
        val entity = board.copy(
            id = id++
        )

        repository.add(entity)

        return entity.id!!
    }

    override fun find(data: ListBoardsUseCase.Request): ListBoardsUseCase.Response {
        TODO("Not yet implemented")
    }
}