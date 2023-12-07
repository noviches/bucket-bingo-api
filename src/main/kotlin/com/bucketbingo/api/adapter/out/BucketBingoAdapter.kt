package com.bucketbingo.api.adapter.out

import com.bucketbingo.api.application.port.out.persistence.CreateBoardPort
import com.bucketbingo.api.common.annotation.Adapter
import com.bucketbingo.api.domain.Board

@Adapter
class BucketBingoAdapter : CreateBoardPort {

    private var id: Long = 1
    private val repository: MutableList<Board> = mutableListOf()

    override fun create(board: Board): Long {
        val entity = board.copy(
            id = id++
        )

        repository.add(entity)

        return entity.id!!
    }
}