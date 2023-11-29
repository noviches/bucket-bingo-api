package com.bucketbingo.api.application.port.`in`.command

import com.bucketbingo.api.application.port.`in`.CreateBoardUseCase
import com.bucketbingo.api.domain.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CreateBoardCommand : CreateBoardUseCase {
    companion object {
        private val logger = LoggerFactory.getLogger(CreateBoardCommand::class.java)
    }

    override fun execute(user: User, data: CreateBoardUseCase.Request): CreateBoardUseCase.Response {
        TODO("Not yet implemented")
    }
}