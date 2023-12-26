package com.bucketbingo.api.application.port.out.persistence

import com.bucketbingo.api.domain.Board

interface UpdateBoardPort {

    fun update(board: Board)
}
