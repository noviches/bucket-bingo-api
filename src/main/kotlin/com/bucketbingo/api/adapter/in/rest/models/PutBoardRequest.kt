package com.bucketbingo.api.adapter.`in`.rest.models

import java.util.Objects
import com.bucketbingo.api.adapter.`in`.rest.models.PutBoardRequestSquaresInner
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
 * @param name 
 * @param targetCount 
 * @param squares 
 * @param description 
 * @param endDate 
 */
data class PutBoardRequest(

    @get:JsonProperty("name", required = true) val name: kotlin.String,

    @get:Min(1)
    @get:Max(22)
    @get:JsonProperty("targetCount", required = true) val targetCount: kotlin.Int,

    @field:Valid
    @get:Size(min=25,max=100) 
    @get:JsonProperty("squares", required = true) val squares: kotlin.collections.List<PutBoardRequestSquaresInner>,

    @get:JsonProperty("description") val description: kotlin.String? = null,

    @get:JsonProperty("endDate") val endDate: java.time.OffsetDateTime? = null
) {

}

