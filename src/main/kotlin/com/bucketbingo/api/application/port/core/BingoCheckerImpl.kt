package com.bucketbingo.api.application.port.core

import com.bucketbingo.api.domain.Bingo
import com.bucketbingo.api.domain.Board
import com.bucketbingo.api.domain.Square

class BingoCheckerImpl(private val board: Board) : BingoChecker {
    override val size: Int
        get() = board.size
    override val squares: List<Square>
        get() = board.squares

    override fun getBingoCount(): Int {
        check(squares.size == (size * size)) { "board size doesn't match the length of squares" }

        val allBingo = getAllBingoFromColumns() + getAllBingoFromRows() + getAllDiagonalBingo()

        return allBingo.count { bingo -> bingo.isDone }
    }

    private fun getAllBingoFromColumns(): List<Bingo> {
        val orderOfColumnHeaders = (1..size)

        return orderOfColumnHeaders.map { order ->
            generateSequence(seed = order) { it + size }
                .take(size)
                .toList()
                .map { squares[it.convertOrderToIndex()] }
        }.map {
            Bingo(it)
        }
    }

    private fun Int.convertOrderToIndex(): Int {
        return this - 1
    }

    private fun getAllBingoFromRows(): List<Bingo> {
        val orderOfRowHeaders = generateSequence(seed = 1) { it + size }
            .take(size)
            .toList()

        return orderOfRowHeaders.map { rowHeader ->
                val from = rowHeader.convertOrderToIndex()
                val to = (from + size).convertOrderToIndex()

                squares.slice(from..to)
            }
            .map {
                Bingo(it)
            }
    }

    private fun getAllDiagonalBingo(): List<Bingo> {
        // 우하단 방향 빙고칸
        val distanceOfEachSquaresForDownwardToRight = size + 1
        val squaresFromDownwardToRight = generateSequence(1) { it + distanceOfEachSquaresForDownwardToRight }
            .take(size)
            .toList()
            .map { squares[it.convertOrderToIndex()] }

        // 좌하단 방향 빙고칸
        val distanceOfEachSquaresForDownwardToLeft = size - 1
        val squaresFromDownwardToLeft = generateSequence(size) { it + distanceOfEachSquaresForDownwardToLeft }
            .take(size)
            .toList()
            .map { squares[it.convertOrderToIndex()] }

        return listOf(
            Bingo(squaresFromDownwardToRight),
            Bingo(squaresFromDownwardToLeft)
        )
    }
}