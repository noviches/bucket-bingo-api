package com.bucketbingo.api.domain

import java.time.LocalDateTime

data class UserContext(

    val at: LocalDateTime,

    val by: By

) {

    data class By(

        val id: String

    )
}
