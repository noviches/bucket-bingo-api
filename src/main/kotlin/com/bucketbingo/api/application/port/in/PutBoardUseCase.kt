package com.bucketbingo.api.application.port.`in`

import com.bucketbingo.api.domain.Square
import java.time.LocalDateTime

interface PutBoardUseCase : UseCase<PutBoardUseCase.Request, Unit> {

    data class Request(

        val boardId: String,

        val squares: List<Square>,

        val name: String,

        val description: String,

        val targetCount: Int,

        val endDate: LocalDateTime?

    )
}
