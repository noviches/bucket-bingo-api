package com.bucketbingo.api.adapter.out

import com.bucketbingo.api.adapter.out.repository.BoardRepository
import com.bucketbingo.api.application.port.`in`.ListBoardsUseCase
import com.bucketbingo.api.application.port.out.persistence.*
import com.bucketbingo.api.common.annotation.Adapter
import com.bucketbingo.api.domain.Board
import com.bucketbingo.api.domain.Pagination
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import kotlin.jvm.optionals.getOrNull

@Adapter
class BucketBingoAdapter(
    private val repository: BoardRepository
) : CreateBoardPort, ListBoardsPort, GetBoardPort, DeleteBoardPort, UpdateBoardPort {

    override fun create(board: Board): String {

        try {
            repository.save(board)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        return board.id!!
    }

    override fun findAll(data: ListBoardsUseCase.Request): Pagination<Board> {

        val direction = Sort.Direction.valueOf(data.sort.direction.name)
        val sort = Sort.by(direction, data.sort.by.value)
        val pageRequest = PageRequest.of(data.pageSize, data.pageOffset, sort)

        val boards = repository.findAll(pageRequest)

        return Pagination(
            items = boards.content,
            totalCount = boards.numberOfElements,
            pageSize = boards.size,
            pageOffset = boards.number,
            totalPageCount = boards.totalPages,
        )
    }

    override fun findOne(id: String): Board? {

        return repository.findById(id).getOrNull()
    }

    override fun delete(id: String) {

        repository.deleteById(id)
    }

    override fun update(board: Board) {

        repository.save(board)
    }
}
