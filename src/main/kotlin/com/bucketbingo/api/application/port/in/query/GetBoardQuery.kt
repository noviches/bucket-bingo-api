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
        // TODO: board.squares가 잘 파싱되는지 확인하기
        return port.findOne(data.id)
    }
}