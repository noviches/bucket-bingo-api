package com.bucketbingo.api.adapter.in.rest.models

import java.util.Objects
import com.bucketbingo.api.adapter.in.rest.models.User
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
 * @param at 
 * @param by 
 */
data class UserContext(

    @get:JsonProperty("at") val at: java.time.OffsetDateTime? = null,

    @field:Valid
    @get:JsonProperty("by") val by: User? = null
) {

}

