package com.bucketbingo.api.adapter.in.rest.models

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
 * @param name 
 * @param propertySize 
 * @param description 
 * @param endDate (정책) 최소 7일, 최대 300년
 */
data class CreateBoardRequest(

    @get:JsonProperty("name", required = true) val name: kotlin.String,

    @get:Min(5)
    @get:Max(10)
    @get:JsonProperty("size", required = true) val propertySize: kotlin.Int,

    @get:JsonProperty("description") val description: kotlin.String? = null,

    @get:JsonProperty("endDate") val endDate: java.time.OffsetDateTime? = null
) {

}

