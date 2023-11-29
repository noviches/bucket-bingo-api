package com.bucketbingo.api.application.port.`in`

import com.bucketbingo.api.domain.User

interface UseCase<in Req, out Res> {

    fun execute(user: User, data: Req): Res

}
