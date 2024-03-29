package com.bucketbingo.api.application.port.`in`.command

import com.bucketbingo.api.application.exception.NotFoundResourceException
import com.bucketbingo.api.application.port.`in`.DeleteBoardUseCase
import com.bucketbingo.api.application.port.out.persistence.DeleteBoardPort
import com.bucketbingo.api.application.port.out.persistence.GetBoardPort
import com.bucketbingo.api.domain.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteBoardCommand(
    private val getBoardPort: GetBoardPort,
    private val deleteBoardPort: DeleteBoardPort,
) : DeleteBoardUseCase {

    companion object {
        private val logger = LoggerFactory.getLogger(DeleteBoardCommand::class.java)
    }

    @Transactional
    override fun execute(user: User, data: DeleteBoardUseCase.Request) {

        val (boardId) = data

        getBoardPort.findOne(boardId) ?: throw NotFoundResourceException("board(${data.id}) not found")

        deleteBoardPort.delete(boardId)
    }
}
