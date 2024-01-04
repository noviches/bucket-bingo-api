package com.bucketbingo.api.application.port.core

import com.bucketbingo.api.domain.Board
import com.bucketbingo.api.domain.Square
import com.bucketbingo.api.domain.SquareStatus
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class BingoCheckerImplTest : BehaviorSpec() {

    init {
        Given("어떠한 이유로 잘못된 크기를 갖는 빙고판에 대해 ") {
            val expectedBoardSize = 5
            val squares = createSquares(size = expectedBoardSize)
            val board = createBoardBySquare(squares, size = (expectedBoardSize - 1))

            When("체커를 활용하여 검증을 시도할 경우") {
                val checker = BingoCheckerImpl(board)
                val exception = shouldThrow<IllegalStateException> {
                    checker.getBingoCount()
                }

                Then("예외가 발생한다.") {

                    exception.message shouldBe "board size doesn't match the length of squares"
                }
            }
        }

        Given("어떠한 빙고칸도 성공하지 못한 임의의 빙고판에 대해") {
            val squares = createSquares(size = 5)
            val board = createBoardBySquare(squares)

            When("체커를 활용하여 검증할 경우") {
                val checker = BingoCheckerImpl(board)
                val result = checker.getBingoCount()

                Then("결과로는 0이 반환된다.") {
                    result shouldBe 0
                }
            }
        }

        Given("세로 첫 줄의 빙고가 성공한 임의의 빙고판에 대해") {
            val squares = createSquares(size = 5, 1, 6, 11, 16, 21)
            val board = createBoardBySquare(squares)

            When("체커를 활용하여 검증할 경우") {
                val checker = BingoCheckerImpl(board)
                val result = checker.getBingoCount()

                Then("빙고 개수는 1이 반환된다.") {
                    result shouldBe 1
                }
            }
        }

        Given("세로 첫 줄, 가로 첫 줄, 우하단 방향 대각 한 줄의 빙고가 성공한 임의의 빙고판에 대해") {
            val squares = createSquares(size = 5, 1, 6, 11, 16, 21, 2, 3, 4, 5, 7, 13, 19, 25)
            val board = createBoardBySquare(squares)

            When("체커를 활용하여 검증할 경우") {
                val checker = BingoCheckerImpl(board)
                val result = checker.getBingoCount()

                Then("빙고 개수는 3이 반환된다.") {
                    result shouldBe 3
                }
            }
        }

        Given("모든 빙고가 성공한 임의의 빙고 판에 대해") {
            val size = 5
            val squareLength = size * size
            val allOrders = (1..squareLength).toList().toIntArray()
            val squares = createSquares(size = size, *allOrders)
            val board = createBoardBySquare(squares)

            When("체커를 활용하여 검증할 경우") {
                val checker = BingoCheckerImpl(board)
                val result = checker.getBingoCount()

                Then("빙고 개수는 size * 2 + 2 와 같다.") {
                    result shouldBe size * 2 + 2
                }
            }
        }
    }

    private fun createSquares(size: Int, vararg order: Int): List<Square> {
        val set = order.toSet()
        val updatedAt = LocalDateTime.now()
        val squaresLength = size * size

        return (1..squaresLength).map {
            Square(
                order = it,
                objective = null,
                updatedAt = updatedAt,

                status = if (set.contains(it)) {
                    SquareStatus.DONE
                } else {
                    SquareStatus.TODO
                }
            )
        }
    }

    private fun createBoardBySquare(square: List<Square>, size: Int = 5): Board {
        return Board(
            id = "test-board-id",
            name = "test-board-name",
            description = "this is test board",
            size = size,
            squares = square,
            startDate = null,
            endDate = null,
            createdById = "tester",
            updatedById = "tester"
        )
    }
}
