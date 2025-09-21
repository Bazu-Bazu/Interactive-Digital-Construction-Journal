package com.example.interactivedigitaljournal.auth.data.repository

import com.example.interactivedigitaljournal.auth.data.api.dto.SignUpRequestDto
import com.example.interactivedigitaljournal.auth.data.api.dto.UserDto
import com.example.interactivedigitaljournal.auth.data.api.service.AuthService
import com.example.interactivedigitaljournal.auth.domain.models.SignInModel
import com.example.interactivedigitaljournal.auth.domain.models.SignUpModel
import com.example.interactivedigitaljournal.auth.domain.models.User
import com.example.interactivedigitaljournal.auth.domain.repository.AuthRepository
import com.example.interactivedigitaljournal.auth.domain.repository.AuthResponse
import jakarta.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRepository {
    override suspend fun singUp(singUpModel: SignUpModel): AuthResponse<User> {
        try {
            val res = authService.singUp(
                SignUpRequestDto
                    .fromDomainModel(singUpModel)
            )
            return AuthResponse.Success(res.toDomainModel())
        } catch (e: Exception) {
            return AuthResponse.Error()
        }
    }

    override suspend fun singIn(singInModel: SignInModel): AuthResponse<String> {
        return AuthResponse.Success("")
    }

    override suspend fun authorize(toke: String): AuthResponse<Unit> {
        return AuthResponse.Success(Unit)
    }
}