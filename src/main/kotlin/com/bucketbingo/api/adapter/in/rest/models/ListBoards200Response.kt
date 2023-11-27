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
 * @param items 
 * @param totalCount 전체 항목 개수
 * @param pageSize 현재 페이지에서 보여주는 목록의 크기
 * @param pageOffset 현재 페이지의 번호
 * @param totalPageCount 전체 페이지의 개수
 */
data class ListBoards200Response(

    @get:JsonProperty("items", required = true) val items: kotlin.String,

    @get:Min(0L)
    @get:JsonProperty("totalCount", required = true) val totalCount: kotlin.Long,

    @get:Min(1L)
    @get:JsonProperty("pageSize", required = true) val pageSize: kotlin.Long = 20L,

    @get:Min(0L)
    @get:JsonProperty("pageOffset", required = true) val pageOffset: kotlin.Long,

    @get:Min(0L)
    @get:JsonProperty("totalPageCount", required = true) val totalPageCount: kotlin.Long
) {

}

