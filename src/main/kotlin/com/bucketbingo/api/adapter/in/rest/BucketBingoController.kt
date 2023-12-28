package com.bucketbingo.api.adapter.`in`.rest

import com.bucketbingo.api.adapter.core.extension.convertToBingoBoard
import com.bucketbingo.api.adapter.core.extension.convertToSquare
import com.bucketbingo.api.adapter.`in`.rest.models.*
import com.bucketbingo.api.adapter.`in`.rest.operations.BucketBingoApi
import com.bucketbingo.api.application.port.`in`.*
import com.bucketbingo.api.domain.SquareStatus
import com.bucketbingo.api.domain.User
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class BucketBingoController(
    private val createBoard: CreateBoardUseCase,
    private val deleteBoard: DeleteBoardUseCase,
    private val getBoard: GetBoardUseCase,
    private val listBoards: ListBoardsUseCase,
    private val putBoard: PutBoardUseCase,
    private val startBoard: StartBoardUseCase,
    private val updateSquare: UpdateSquareUseCase
): BucketBingoApi {

    companion object {
        private val logger = LoggerFactory.getLogger(BucketBingoController::class.java)

        // TODO: 추후 인증 추가 필요
        private val tester = User("tester")
    }

    override fun createBoard(authorization: String?, createBoardRequest: CreateBoardRequest?): ResponseEntity<Unit> {
        require(authorization != null) { "authorization cannot be null" }
        require(createBoardRequest != null) { "request cannot be null" }

        val boardId = createBoard.execute(tester, CreateBoardUseCase.Request(
            name = createBoardRequest.name,
            size = createBoardRequest.propertySize,
            description = createBoardRequest.description,
            endDate = createBoardRequest.endDate?.toLocalDateTime(),
        ))

        return ResponseEntity
            .created(URI.create("/boards/$boardId"))
            .build()
    }

    override fun deleteBoard(boardId: String, authorization: String?): ResponseEntity<Unit> {
        require(authorization != null) { "authorization cannot be null" }

        deleteBoard.execute(
            tester, DeleteBoardUseCase.Request(
                id = boardId
            )
        )

        return ResponseEntity
            .noContent()
            .build()
    }

    override fun getBoard(boardId: String, authorization: String?): ResponseEntity<BingoBoard> {
        require(authorization != null) { "authorization cannot be null" }

        val result = getBoard.execute(
            tester, GetBoardUseCase.Request(
                id = boardId
            )
        )

        return ResponseEntity.ok(
            result.convertToBingoBoard()
        )
    }

    override fun listBoards(
        authorization: String?,
        pageSize: Int,
        pageOffset: Int
    ): ResponseEntity<ListBoards200Response> {
        require(authorization != null) { "authorization cannot be null" }

        val result = listBoards.execute(
            tester, ListBoardsUseCase.Request(
                pageSize = pageSize,
                pageOffset = pageOffset,
            )
        )

        return ResponseEntity.ok(
            ListBoards200Response(
                items = result.items.map { it.convertToBingoBoard() },
                totalCount = result.totalCount.toLong(),
                pageSize = result.pageSize.toLong(),
                pageOffset = result.pageOffset.toLong(),
                totalPageCount = result.totalPageCount.toLong(),
            )
        )
    }

    override fun putBoard(
        boardId: String,
        authorization: String?,
        putBoardRequest: PutBoardRequest?
    ): ResponseEntity<Unit> {
        require(authorization != null) { "authorization cannot be null" }
        require(putBoardRequest != null) { "request cannot be null" }

        putBoard.execute(
            tester, PutBoardUseCase.Request(
                boardId = boardId,
                name = putBoardRequest.name,
                description = putBoardRequest.description,
                endDate = putBoardRequest.endDate.toLocalDateTime(),
                squares = putBoardRequest.squares.convertToSquare()
            )
        )

        return ResponseEntity
            .noContent()
            .build()
    }

    override fun startBingo(boardId: String, authorization: String?): ResponseEntity<Unit> {
        require(authorization != null) { "authorization cannot be null" }

        startBoard.execute(
            tester, StartBoardUseCase.Request(
                boardId = boardId
            )
        )

        return ResponseEntity
            .noContent()
            .build()
    }

    override fun updateSquare(
        boardId: String,
        squareId: Int,
        authorization: String?,
        updateSquareRequest: UpdateSquareRequest?
    ): ResponseEntity<Unit> {
        require(authorization != null) { "authorization cannot be null" }
        require(updateSquareRequest != null) { "request cannot be null" }

        //TODO : 추후 고도화될 경우 아래 조건은 제거되어야함
        require(updateSquareRequest.status != null) { "square status cannot be null" }

        updateSquare.execute(
            tester, UpdateSquareUseCase.Request(
                boardId = boardId,
                squareId = squareId,
                status = SquareStatus.valueOf(updateSquareRequest.status.value)
            )
        )

        return ResponseEntity
            .noContent()
            .build()
    }
}