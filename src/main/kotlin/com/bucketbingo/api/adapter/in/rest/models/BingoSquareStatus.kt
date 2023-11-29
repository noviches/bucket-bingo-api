package com.bucketbingo.api.adapter.`in`.rest.models

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonValue
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
* Values: TODO,IN_PROGRESS,DONE
*/
enum class BingoSquareStatus(val value: kotlin.String) {

    @JsonProperty("TODO") TODO("TODO"),
    @JsonProperty("IN_PROGRESS") IN_PROGRESS("IN_PROGRESS"),
    @JsonProperty("DONE") DONE("DONE")
}

