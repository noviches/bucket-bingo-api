package com.bucketbingo.api.application.port.`in`.query

import com.bucketbingo.api.application.port.`in`.GetBoardUseCase
import com.bucketbingo.api.application.port.out.persistence.GetBoardPort
import com.bucketbingo.api.domain.Board
import com.bucketbingo.api.domain.User
import org.springframework.stereotype.Service

@Service
class GetBoardQuery(
    private val port: GetBoardPort,
) : GetBoardUseCase {

    override fun execute(user: User, data: GetBoardUseCase.Request): Board {
        return port.findOne(data.id) ?: throw NoSuchElementException("board(${data.id}) not found")
    }
}
