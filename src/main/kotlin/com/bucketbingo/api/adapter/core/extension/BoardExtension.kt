package com.bucketbingo.api.adapter.core.extension

import com.bucketbingo.api.adapter.`in`.rest.models.*
import com.bucketbingo.api.domain.Board
import com.bucketbingo.api.domain.Objective
import com.bucketbingo.api.domain.Square
import com.bucketbingo.api.domain.SquareStatus
import java.time.LocalDateTime

internal fun Board.convertToBingoBoard(): BingoBoard {
    return BingoBoard(
        id = id!!,
        name = name,
        propertySize = size,
        squares = squares.map { it.convertToBingoSquare() },
        status = BingoBoardStatus.valueOf(status.name),
        description = description,
        startDate = startDate?.toOffsetDateTime(),
        endDate = endDate?.toOffsetDateTime(),
        created = UserContext(
            at = createdAt!!.toOffsetDateTime(),
            by = User(
                id = createdById,
            )
        ),
        bingo = Bingo(
            targetCount = targetCount,
            currentCount = currentBingoCount,
        ),
        updated = UserContext(
            at = updatedAt!!.toOffsetDateTime(),
            by = User(
                id = updatedById,
            )
        ),
    )
}

internal fun Square.convertToBingoSquare(): BingoSquare {
    return BingoSquare(
        order = order,
        status = BingoSquareStatus.valueOf(status.name),
        updatedAt = updatedAt.toOffsetDateTime(),
        objective = objective?.convertToBingoObjective()
    )
}

internal fun Objective.convertToBingoObjective(): BingoObjective {
    return BingoObjective(
        content = content,
        totalCount = totalCount,
        currentCount = currentCount,
    )
}

// TODO: 파일 분리하기
internal fun List<PutBoardRequestSquaresInner>.convertToSquare(): List<Square> {
    return mapIndexed { index, it ->
        Square(
            order = index + 1,
            objective = Objective(
                content = it.content,
            ),
            status = SquareStatus.TODO,
            updatedAt = LocalDateTime.now(),
        )
    }
}

internal fun BingoObjective.convertToObjective(): Objective {
    return Objective(
        content = content,
        totalCount = totalCount,
        currentCount = currentCount
    )
}