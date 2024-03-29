package com.bucketbingo.api.domain

import java.time.LocalDateTime

data class Square(

    val order: Int,

    val objective: Objective?,

    val status: SquareStatus = SquareStatus.DRAFT,

    val updatedAt: LocalDateTime

)
