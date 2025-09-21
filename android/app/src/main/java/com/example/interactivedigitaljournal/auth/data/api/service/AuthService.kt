package com.example.interactivedigitaljournal.auth.data.api.service

import com.example.interactivedigitaljournal.auth.data.api.dto.SignUpRequestDto
import com.example.interactivedigitaljournal.auth.data.api.dto.UserDto
import com.example.interactivedigitaljournal.auth.domain.models.SignInModel
import com.example.interactivedigitaljournal.auth.domain.models.UserRole
import io.ktor.client.HttpClient
import kotlin.String

class AuthService(
    private val httpClient: HttpClient,
) {
    suspend fun singUp(singUpModel: SignUpRequestDto): UserDto {
        return UserDto(
            "id",
            "email@emial.com",
            "surname",
            "patronymic",
            "firstName",
            UserRole.CUSTOMER,
        )
    }

    suspend fun singIn(singUpModel: SignInModel): UserDto {
        return UserDto(
            "id",
            "email@emial.com",
            "surname",
            "patronymic",
            "firstName",
            UserRole.CUSTOMER,
        )
    }

    suspend fun authorize(toke: String) {}
}