package com.bucketbingo.api.adapter.out.repository

import com.bucketbingo.api.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {

    fun findByEmail(email: String): User?

}
