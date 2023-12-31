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
 * @param targetCount 
 * @param currentCount 
 */
data class Bingo(

    @get:Min(1)
    @get:Max(22)
    @get:JsonProperty("targetCount", required = true) val targetCount: kotlin.Int,

    @get:Min(0)
    @get:Max(22)
    @get:JsonProperty("currentCount", required = true) val currentCount: kotlin.Int
) {

}

