package com.bucketbingo.api.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "board")
data class Board(

    @Id
    var id: String? = null,

    val name: String,

    val description: String,

    val size: Int,

    val squares: List<Square> = emptyList(),

    val status: BoardStatus = BoardStatus.DRAFT,

    val startDate: LocalDateTime?,

    val endDate: LocalDateTime?,

    @CreatedDate
    var createdAt: LocalDateTime? = null,

    val createdById: String,

    @Transient
    val creator: User? = null,

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,

    val updatedById: String,

    @Transient
    val updater: User? = null

) {

    companion object {

        fun getSquareLength(boardSize: Int): Int {
            return boardSize * boardSize
        }

    }

}
