package com.bucketbingo.api.adapter.in.rest.models

import java.util.Objects
import com.bucketbingo.api.adapter.in.rest.models.PutBoardRequestSquaresInner
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
 * @param description 
 * @param endDate 
 * @param squares 
 */
data class PutBoardRequest(

    @get:JsonProperty("name", required = true) val name: kotlin.String,

    @get:JsonProperty("description", required = true) val description: kotlin.String,

    @get:JsonProperty("endDate", required = true) val endDate: java.time.OffsetDateTime,

    @field:Valid
    @get:Size(min=25,max=100) 
    @get:JsonProperty("squares", required = true) val squares: kotlin.collections.List<PutBoardRequestSquaresInner>
) {

}

