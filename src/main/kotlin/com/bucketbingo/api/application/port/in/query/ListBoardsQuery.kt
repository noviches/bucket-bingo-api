package com.bucketbingo.api.application.port.`in`.query

import com.bucketbingo.api.application.port.`in`.ListBoardsUseCase
import com.bucketbingo.api.application.port.out.persistence.ListBoardsPort
import com.bucketbingo.api.domain.Board
import com.bucketbingo.api.domain.Pagination
import com.bucketbingo.api.domain.User
import org.springframework.stereotype.Service

@Service
class ListBoardsQuery(
    private val port: ListBoardsPort,
) : ListBoardsUseCase {

    override fun execute(user: User, data: ListBoardsUseCase.Request): Pagination<Board> {
        // TODO: board.squares가 잘 파싱되는지 확인하기
        return port.findAll(data)
    }
}