package com.bucketbingo.api.application.port.`in`.command

import com.bucketbingo.api.application.port.`in`.PutBoardUseCase
import com.bucketbingo.api.application.port.out.persistence.GetBoardPort
import com.bucketbingo.api.application.port.out.persistence.UpdateBoardPort
import com.bucketbingo.api.domain.Board
import com.bucketbingo.api.domain.BoardStatus
import com.bucketbingo.api.domain.SquareStatus
import com.bucketbingo.api.domain.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PutBoardCommand(
    private val getPort: GetBoardPort,
    private val updatePort: UpdateBoardPort,
) : PutBoardUseCase {

    companion object {
        private val logger = LoggerFactory.getLogger(PutBoardCommand::class.java)
    }

    override fun execute(user: User, data: PutBoardUseCase.Request) {
        val (boardId, squares) = data

        val board = getPort.findOne(boardId)
            ?: throw RuntimeException("cannot find board($boardId)")

        // TODO: board.createdById != user.id인 경우 예외를 던지도록 개선

        if(board.status != BoardStatus.DRAFT) {
            throw RuntimeException("cannot update board")
        }

        val expectedSize = Board.getSquareLength(board.size)
        if(squares.size != expectedSize) {
            throw IllegalArgumentException("squares size must be $expectedSize")
        }
        if(squares.any { it.status != SquareStatus.TODO }) {
            throw IllegalArgumentException("all board squares status must be TODO")
        }

        updatePort.update(
            board = board.copy(
                squares = squares,
                name = data.name,
                description = data.description,
                targetCount = data.targetCount,
                endDate = data.endDate,
            )
        )
    }
}
