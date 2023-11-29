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
 * 
 * @param content 
 * @param totalCount 
 */
data class PutBoardRequestSquaresInner(

    @get:JsonProperty("content", required = true) val content: kotlin.String,

    @get:JsonProperty("totalCount", required = true) val totalCount: kotlin.String
) {

}

