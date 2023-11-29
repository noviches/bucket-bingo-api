package com.bucketbingo.api.domain.dto

data class Objective(
    val content: String,

    val totalCount: Int = 1,

    val currentCount: Int = 0,
)
