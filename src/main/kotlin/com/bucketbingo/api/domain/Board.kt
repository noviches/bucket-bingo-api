package com.bucketbingo.api.domain

import com.bucketbingo.api.application.port.core.BingoCheckerImpl
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

    val description: String?,

    val size: Int,

    val squares: List<Square> = emptyList(),

    val status: BoardStatus = BoardStatus.DRAFT,

    val targetCount: Int,

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
    val currentBingoCount: Int
        get() = BingoCheckerImpl(this).getBingoCount()

    companion object {

        fun getSquareLength(boardSize: Int): Int {
            return boardSize * boardSize
        }

    }

}
