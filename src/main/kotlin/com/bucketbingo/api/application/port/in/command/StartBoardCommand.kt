package com.bucketbingo.api.application.port.`in`.command

import com.bucketbingo.api.application.exception.NotFoundResourceException
import com.bucketbingo.api.application.port.`in`.StartBoardUseCase
import com.bucketbingo.api.application.port.out.persistence.GetBoardPort
import com.bucketbingo.api.application.port.out.persistence.UpdateBoardPort
import com.bucketbingo.api.domain.BoardStatus
import com.bucketbingo.api.domain.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class StartBoardCommand(
    private val getPort: GetBoardPort,
    private val updatePort: UpdateBoardPort,
) : StartBoardUseCase {

    companion object {
        private val logger = LoggerFactory.getLogger(StartBoardCommand::class.java)
    }

    override fun execute(user: User, data: StartBoardUseCase.Request) {
        val (boardId) = data

        val board = getPort.findOne(boardId)
            ?: throw NotFoundResourceException("cannot find board($boardId)")

        // TODO: board.createdById != user.id인 경우 예외를 던지도록 개선
        board.endDate?.also { endDate ->
            if (endDate > LocalDateTime.now().plusYears(100L)) {
                throw IllegalStateException("100년까지 밖에 안되요")
            }
        } ?: throw IllegalStateException("please set end date")

        if(board.status != BoardStatus.DRAFT) {
            throw IllegalStateException("board status must be DRAFT")
        }
        if(board.squares.any { it.objective?.content.isNullOrBlank() }) {
            throw IllegalArgumentException("objective content cannot be empty string")
        }

        updatePort.update(
            board = board.copy(
                status = BoardStatus.ACTIVE
            )
        )
    }
}
