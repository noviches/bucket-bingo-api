package com.bucketbingo.api.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "board")
data class Board(

    @Id
    val id: String? = null,

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

    val updater: User? = null,

    )
