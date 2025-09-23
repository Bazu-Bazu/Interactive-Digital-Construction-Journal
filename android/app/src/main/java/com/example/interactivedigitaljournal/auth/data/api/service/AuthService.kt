package com.example.interactivedigitaljournal.auth.data.api.service

import com.example.interactivedigitaljournal.auth.data.api.AuthApiRoutes
import com.example.interactivedigitaljournal.auth.data.api.dto.ServerUserRole
import com.example.interactivedigitaljournal.auth.data.api.dto.SignUpRequestDto
import com.example.interactivedigitaljournal.auth.data.api.dto.UserDto
import com.example.interactivedigitaljournal.auth.domain.models.SignInModel
import com.example.interactivedigitaljournal.auth.domain.models.UserRole
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import kotlin.String

class AuthService @Inject constructor(
    private val httpClient: HttpClient,
) {
    suspend fun singUp(signUpModel: SignUpRequestDto): UserDto {
        println(signUpModel)
        val res = httpClient.post(AuthApiRoutes.signUp) {
            contentType(ContentType.Application.Json)
            setBody(signUpModel)
        }
        println(res)
        return res.body<UserDto>()
//        return UserDto(
//            "id",
//            "email@emial.com",
//            "surname",
//            "patronymic",
//            "firstName",
//            UserRole.CUSTOMER,
//            "",
//            "",
//        )
    }

    suspend fun singIn(singUpModel: SignInModel): UserDto {
        return UserDto(
//            "id",
            "email@emial.com",
            "surname",
            "patronymic",
            "firstName",
            ServerUserRole.ROLE_CUSTOMER,
            "",
            "",
        )
    }

    suspend fun authorize(toke: String) {}
}