package com.bucketbingo.api.adapter.`in`.rest

import com.bucketbingo.api.application.exception.ExceptionResponse
import com.bucketbingo.api.application.exception.NotFoundResourceException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorHandler {

    companion object {
        private val logger = LoggerFactory.getLogger(ErrorHandler::class.java);
    }

    @ExceptionHandler(Exception::class)
    fun handleCommonException(ex: Exception): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            code = "UNCAUGHT_EXCEPTION",
            message = ex.message ?: ""
        )
        logger.error("UNCAUGHT_EXCEPTION occurred: ${ex.message}")

        return ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(value = [IllegalArgumentException::class, IllegalStateException::class])
    fun handleBadRequestException(ex: Exception): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            code = "ILLEGAL_ARGUMENT_EXCEPTION",
            message = ex.message ?: ""
        )
        logger.error("ILLEGAL_ARGUMENT_EXCEPTION occurred: ${ex.message}")

        return ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NotFoundResourceException::class)
    fun handleNotFoundResourceException(ex: Exception): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            code = "NOT_FOUND_RESOURCE",
            message = ex.message ?: ""
        )
        logger.error("NOT_FOUND_RESOURCE occurred: ${ex.message}")

        return ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND)
    }
}