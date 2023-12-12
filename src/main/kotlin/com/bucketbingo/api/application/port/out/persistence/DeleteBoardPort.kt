package com.bucketbingo.api.application.port.out.persistence

interface DeleteBoardPort {

    fun delete(id: Long): Int
}