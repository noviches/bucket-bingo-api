package com.bucketbingo.api.adapter.in.rest.models

import java.util.Objects
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
 * @param status 
 */
data class UpdateSquareRequest(

    @get:JsonProperty("status") val status: UpdateSquareRequest.Status? = null
) {

    /**
    * 
    * Values: IN_PROGRESS,DONE
    */
    enum class Status(val value: kotlin.String) {

        @JsonProperty("IN_PROGRESS") IN_PROGRESS("IN_PROGRESS"),
        @JsonProperty("DONE") DONE("DONE")
    }

}

