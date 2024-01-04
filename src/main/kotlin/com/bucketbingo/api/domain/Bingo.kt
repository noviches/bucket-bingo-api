package com.bucketbingo.api.domain

data class Bingo(
    val squares: List<Square>
) {
    val isDone: Boolean
        get() = squares.all { it.status == SquareStatus.DONE }
}