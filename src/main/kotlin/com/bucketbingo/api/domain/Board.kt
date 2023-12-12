package com.bucketbingo.api.domain

import java.time.LocalDateTime

data class Board(

    val id: Long? = null,

    val name: String,

    val description: String,

    val size: Int,

    val squares: List<Square> = emptyList(),

    val status: BoardStatus = BoardStatus.DRAFT,

    val startDate: LocalDateTime?,

    val endDate: LocalDateTime?,

    val createdAt: LocalDateTime,

    val createdById: String,

    val creator: User? = null,

    val updatedById: String,

    val updater: User? = null
)
