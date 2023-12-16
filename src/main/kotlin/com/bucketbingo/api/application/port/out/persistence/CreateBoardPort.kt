package com.bucketbingo.api.application.port.out.persistence

import com.bucketbingo.api.domain.Board

interface CreateBoardPort {

    fun create(board: Board): String
}
