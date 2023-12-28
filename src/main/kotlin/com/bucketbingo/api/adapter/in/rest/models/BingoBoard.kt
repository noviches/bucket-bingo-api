package com.bucketbingo.api.adapter.`in`.rest.models

import java.util.Objects
import com.bucketbingo.api.adapter.`in`.rest.models.BingoBoardStatus
import com.bucketbingo.api.adapter.`in`.rest.models.BingoSquare
import com.bucketbingo.api.adapter.`in`.rest.models.UserContext
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
 * @param id 
 * @param name 
 * @param propertySize 
 * @param squares 
 * @param status 
 * @param created 
 * @param updated 
 * @param description 
 * @param startDate 
 * @param endDate 
 */
data class BingoBoard(

    @get:JsonProperty("id", required = true) val id: kotlin.String,

    @get:JsonProperty("name", required = true) val name: kotlin.String,

    @get:Min(5)
    @get:Max(10)
    @get:JsonProperty("size", required = true) val propertySize: kotlin.Int,

    @field:Valid
    @get:Size(min=25,max=100) 
    @get:JsonProperty("squares", required = true) val squares: kotlin.collections.List<BingoSquare>,

    @field:Valid
    @get:JsonProperty("status", required = true) val status: BingoBoardStatus,

    @field:Valid
    @get:JsonProperty("created", required = true) val created: UserContext,

    @field:Valid
    @get:JsonProperty("updated", required = true) val updated: UserContext,

    @get:JsonProperty("description") val description: kotlin.String? = null,

    @get:JsonProperty("startDate") val startDate: java.time.OffsetDateTime? = null,

    @get:JsonProperty("endDate") val endDate: java.time.OffsetDateTime? = null
) {

}

