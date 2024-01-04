package com.bucketbingo.api.application.port.core

import com.bucketbingo.api.domain.Square

interface BingoChecker {
    val size: Int
    val squares: List<Square>

    fun getBingoCount(): Int
}