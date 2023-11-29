package com.bucketbingo.api.adapter.`in`.rest.models

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
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
 * 최초에 BingoSquare가 생성된 경우, objective가 설정되지 않았을 것이므로 nullable임
 * @param content 
 * @param totalCount 
 * @param currentCount 
 */
data class BingoObjective(

    @get:JsonProperty("content", required = true) val content: kotlin.String,

    @get:Min(1)
    @get:JsonProperty("totalCount", required = true) val totalCount: kotlin.Int,

    @get:Min(1)
    @get:JsonProperty("currentCount", required = true) val currentCount: kotlin.Int
) {

}

