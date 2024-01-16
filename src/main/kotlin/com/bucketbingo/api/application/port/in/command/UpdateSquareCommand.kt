package com.bucketbingo.api.application.port.`in`.command

import com.bucketbingo.api.application.exception.NotFoundResourceException
import com.bucketbingo.api.application.port.`in`.UpdateSquareUseCase
import com.bucketbingo.api.application.port.out.persistence.GetBoardPort
import com.bucketbingo.api.application.port.out.persistence.UpdateBoardPort
import com.bucketbingo.api.domain.Board
import com.bucketbingo.api.domain.BoardStatus
import com.bucketbingo.api.domain.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UpdateSquareCommand(
    private val getPort: GetBoardPort,
    private val updatePort: UpdateBoardPort,
) : UpdateSquareUseCase {

    companion object {
        private val logger = LoggerFactory.getLogger(UpdateSquareCommand::class.java)
    }

    override fun execute(user: User, data: UpdateSquareUseCase.Request) {
        val (boardId, squareId, status) = data

        val board = getPort.findOne(boardId)
            ?: throw NotFoundResourceException("cannot find board($boardId)")

        // TODO: board.createdById != user.id인 경우 예외를 던지도록 개선

        if (board.status != BoardStatus.ACTIVE) {
            throw IllegalStateException("cannot update square")
        }

        val maxSquareId = Board.getSquareLength(board.size)
        if (squareId > maxSquareId) {
            throw IllegalArgumentException("maximum square id is $maxSquareId")
        }

        val squares = board.squares.map { square ->
            if (square.order == squareId) square.copy(status = status) else square.copy()
        }

        updatePort.update(
            board = board.copy(
                squares = squares
            )
        )
    }
}
