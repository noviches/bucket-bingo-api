package com.bucketbingo.api.application.port.`in`.query

import com.bucketbingo.api.application.port.`in`.ListBoardsUseCase
import com.bucketbingo.api.application.port.out.persistence.ListBoardsPort
import com.bucketbingo.api.domain.User
import org.springframework.stereotype.Service

@Service
class ListBoardsQuery(
    private val port: ListBoardsPort,
) : ListBoardsUseCase {

    override fun execute(user: User, data: ListBoardsUseCase.Request): ListBoardsUseCase.Response {
        return port.find(data)
    }
}