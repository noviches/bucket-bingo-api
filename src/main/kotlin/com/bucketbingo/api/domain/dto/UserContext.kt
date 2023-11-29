package com.bucketbingo.api.domain.dto

import java.time.LocalDateTime

data class UserContext(
    val at: LocalDateTime,
    val by: By,
) {
    data class By(
        val id: String,
    )
}
