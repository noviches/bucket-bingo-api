package com.bucketbingo.api.application.port.`in`.command

import com.bucketbingo.api.application.port.`in`.StartBoardUseCase
import com.bucketbingo.api.application.port.out.persistence.GetBoardPort
import com.bucketbingo.api.application.port.out.persistence.UpdateBoardPort
import com.bucketbingo.api.domain.*
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
            ?: throw RuntimeException("cannot find board($boardId)")

        // TODO: board.createdById != user.id인 경우 예외를 던지도록 개선
        board.endDate?.also { endDate ->
            if(endDate > LocalDateTime.now().plusYears(100L)) {
                throw RuntimeException("100년까지 밖에 안되요")
            }
        } ?: throw RuntimeException("please set end date")

        if(board.status != BoardStatus.DRAFT) {
            throw RuntimeException("board status must be DRAFT")
        }
        if(board.squares.any { it.objective?.content.isNullOrBlank() }) {
            throw RuntimeException("objective content cannot be empty string")
        }

        updatePort.update(
            board = board.copy(
                status = BoardStatus.ACTIVE
            )
        )
    }
}
