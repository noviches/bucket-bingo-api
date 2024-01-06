package com.bucketbingo.api.application.port.`in`.command

import com.bucketbingo.api.application.port.`in`.CreateBoardUseCase
import com.bucketbingo.api.application.port.out.persistence.CreateBoardPort
import com.bucketbingo.api.domain.Board
import com.bucketbingo.api.domain.Square
import com.bucketbingo.api.domain.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CreateBoardCommand(
    private val port: CreateBoardPort
) : CreateBoardUseCase {

    companion object {
        private val logger = LoggerFactory.getLogger(CreateBoardCommand::class.java)
    }

    override fun execute(user: User, data: CreateBoardUseCase.Request): String {

        val squaresLength = Board.getSquareLength(data.size)
        logger.info("squaresLength: $squaresLength")

        val squares = (1..squaresLength).map { order ->
            Square(
                order = order,
                objective = null,
                updatedAt = LocalDateTime.now()
            )
        }

        val board = Board(
            name = data.name,
            description = data.description,
            size = data.size,
            squares = squares,
            targetCount = data.targetCount,
            startDate = null,
            endDate = data.endDate,
            createdById = user.id,
            creator = null,
            updatedById = user.id,
            updater = null
        )

        return port.create(board)
    }
}
