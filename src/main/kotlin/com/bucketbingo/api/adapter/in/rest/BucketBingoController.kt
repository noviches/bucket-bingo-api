package com.bucketbingo.api.adapter.`in`.rest

import com.bucketbingo.api.adapter.`in`.rest.models.*
import com.bucketbingo.api.adapter.`in`.rest.operations.BucketBingoApi
import com.bucketbingo.api.application.port.`in`.*
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

        return super.deleteBoard(boardId, authorization)
    }

    override fun getBoard(boardId: String, authorization: String?): ResponseEntity<BingoBoard> {
        require(authorization != null) { "authorization cannot be null" }

        return super.getBoard(boardId, authorization)
    }

    override fun listBoards(
        authorization: String?,
        pageSize: Int,
        pageOffset: Int
    ): ResponseEntity<ListBoards200Response> {
        require(authorization != null) { "authorization cannot be null" }

        return super.listBoards(authorization, pageSize, pageOffset)
    }

    override fun putBoard(
        boardId: String,
        authorization: String?,
        putBoardRequest: PutBoardRequest?
    ): ResponseEntity<Unit> {
        require(authorization != null) { "authorization cannot be null" }
        require(putBoardRequest != null) { "request cannot be null" }

        return super.putBoard(boardId, authorization, putBoardRequest)
    }

    override fun startBingo(boardId: String, authorization: String?): ResponseEntity<Unit> {
        require(authorization != null) { "authorization cannot be null" }

        return super.startBingo(boardId, authorization)
    }

    override fun updateSquare(
        boardId: String,
        squareId: String,
        authorization: String?,
        updateSquareRequest: UpdateSquareRequest?
    ): ResponseEntity<Unit> {
        require(authorization != null) { "authorization cannot be null" }
        require(updateSquareRequest != null) { "request cannot be null" }

        return super.updateSquare(boardId, squareId, authorization, updateSquareRequest)
    }

}