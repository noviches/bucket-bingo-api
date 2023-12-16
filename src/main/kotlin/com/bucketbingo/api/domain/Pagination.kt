package com.bucketbingo.api.domain

data class Pagination<out T>(

    val items: List<T>,

    val totalCount: Int,

    val pageSize: Int,

    val pageOffset: Int,

    val totalPageCount: Int,

    )
