package com.example.interactivedigitaljournal.auth.data.api.service

import com.example.interactivedigitaljournal.auth.data.api.AuthApiRoutes
import com.example.interactivedigitaljournal.auth.data.api.dto.SignInRequestDto
import com.example.interactivedigitaljournal.auth.data.api.dto.SignUpRequestDto
import com.example.interactivedigitaljournal.auth.data.api.dto.UpdateTokenRequest
import com.example.interactivedigitaljournal.auth.data.api.dto.UserDto
import com.example.interactivedigitaljournal.auth.domain.utils.JwtTokenManager
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import javax.inject.Inject
import kotlin.String

class AuthService @Inject constructor(
    private val httpClient: HttpClient,
    private val jwtTokenManager: JwtTokenManager,
) {
    suspend fun singUp(signUpModel: SignUpRequestDto): UserDto {
        val res = httpClient.post(AuthApiRoutes.signUp) {
            contentType(ContentType.Application.Json)
            setBody(signUpModel)
        }
        return res.body<UserDto>()
    }

    suspend fun singIn(singInModel: SignInRequestDto): UserDto {
        val res = httpClient.post(AuthApiRoutes.signIn) {
            contentType(ContentType.Application.Json)
            setBody(singInModel)
        }
        return res.body<UserDto>()
    }

    suspend fun logout() {
        val accessJwt = jwtTokenManager.getJwt().first
        httpClient.post(AuthApiRoutes.logout) {
            contentType(ContentType.Application.Json)
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Bearer $accessJwt",
                )
            }
        }
    }

    suspend fun updateToken(refresh: String): UserDto {
        val res = httpClient.post(AuthApiRoutes.updateToken) {
            contentType(ContentType.Application.Json)
            setBody(
                UpdateTokenRequest(
                    refreshToken = refresh
                )
            )
        }
        return res.body<UserDto>()
    }
}