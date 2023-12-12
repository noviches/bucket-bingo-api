package com.bucketbingo.api.adapter.out

import com.bucketbingo.api.application.port.`in`.ListBoardsUseCase
import com.bucketbingo.api.application.port.out.persistence.CreateBoardPort
import com.bucketbingo.api.application.port.out.persistence.DeleteBoardPort
import com.bucketbingo.api.application.port.out.persistence.GetBoardPort
import com.bucketbingo.api.application.port.out.persistence.ListBoardsPort
import com.bucketbingo.api.common.annotation.Adapter
import com.bucketbingo.api.domain.Board
import com.bucketbingo.api.domain.Pagination

@Adapter
class BucketBingoAdapter : CreateBoardPort, ListBoardsPort, GetBoardPort, DeleteBoardPort {

    private var id: Long = 1
    private val repository: MutableList<Board> = mutableListOf()

    override fun create(board: Board): Long {
        val entity = board.copy(
            id = id++
        )

        repository.add(entity)

        return entity.id!!
    }

    override fun findAll(data: ListBoardsUseCase.Request): Pagination<Board> {
        return Pagination(
            items = repository,
            totalCount = repository.size,
            pageSize = 42,
            pageOffset = 1,
            totalPageCount = 42,
        )
    }

    override fun findOne(id: Long): Board? {

        return repository.find { it.id == id }
    }

    override fun delete(id: Long): Int {

        return 1
    }
}