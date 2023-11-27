package com.bucketbingo.api.adapter.in.rest.models

import java.util.Objects
import com.bucketbingo.api.adapter.in.rest.models.BingoObjective
import com.bucketbingo.api.adapter.in.rest.models.BingoSquareStatus
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import jakarta.validation.Valid

/**
 * 
 * @param order 각 빙고 칸의 order는 빙고 판마다 유일하므로 사실상 식별자 역할을 대체함
 * @param status 
 * @param updatedAt 
 * @param objective 
 */
data class BingoSquare(

    @get:JsonProperty("order", required = true) val order: kotlin.Int,

    @field:Valid
    @get:JsonProperty("status", required = true) val status: BingoSquareStatus,

    @get:JsonProperty("updatedAt", required = true) val updatedAt: java.time.OffsetDateTime,

    @field:Valid
    @get:JsonProperty("objective") val objective: BingoObjective? = null
) {

}

