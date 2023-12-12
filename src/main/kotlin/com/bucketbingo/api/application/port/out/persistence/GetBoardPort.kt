package com.bucketbingo.api.application.port.out.persistence

import com.bucketbingo.api.domain.Board

interface GetBoardPort {

    fun findOne(id: Long): Board
}