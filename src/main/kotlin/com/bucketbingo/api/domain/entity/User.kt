package com.bucketbingo.api.domain.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val email: String,
    val nickname: String,
    @CreatedDate
    @Column(updatable = false)
    var createdAt: LocalDateTime? = null,
    @CreatedDate
    @Column(updatable = false)
    var lastLogInAt: LocalDateTime? = null,
)