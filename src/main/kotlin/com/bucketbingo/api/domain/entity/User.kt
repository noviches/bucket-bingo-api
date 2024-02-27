package com.bucketbingo.api.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp

@Entity(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val username: String,
    val password: String,
    val email: String,
    @CreationTimestamp
    val createDate: Timestamp?,

    val provider: String?, // GOOGLE
    val providerId: String?, // sub (google에서 사용 중인 PK 같은 친구)

    val role: String = "ROLE_USER",
)