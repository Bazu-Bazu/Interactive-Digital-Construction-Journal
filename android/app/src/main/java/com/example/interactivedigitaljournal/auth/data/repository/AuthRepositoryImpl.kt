package com.example.interactivedigitaljournal.auth.data.repository

import com.example.interactivedigitaljournal.auth.data.api.dto.SignInRequestDto
import com.example.interactivedigitaljournal.auth.data.api.dto.SignUpRequestDto
import com.example.interactivedigitaljournal.auth.data.api.service.AuthService
import com.example.interactivedigitaljournal.auth.data.db.dao.UserDao
import com.example.interactivedigitaljournal.auth.data.db.entity.UserEntity
import com.example.interactivedigitaljournal.auth.domain.models.SignInModel
import com.example.interactivedigitaljournal.auth.domain.models.SignUpModel
import com.example.interactivedigitaljournal.auth.domain.models.User
import com.example.interactivedigitaljournal.auth.domain.models.UserRole
import com.example.interactivedigitaljournal.auth.domain.repository.AuthRepository
import com.example.interactivedigitaljournal.auth.domain.repository.AuthResponse
import com.example.interactivedigitaljournal.auth.domain.utils.JwtTokenManager
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.String

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val jwtDataStore: JwtTokenManager,
    private val userDao: UserDao,
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
        return try {
            withContext(Dispatchers.IO) {
                val res = authService.singIn(SignInRequestDto.fromDomainModel(singInModel))
                jwtDataStore.saveAccessJwt(res.accessToken ?: "", res.refreshToken ?: "")
                userDao.insert(
                    UserEntity(
                        email=res.email,
                        surname=res.surname,
                        patronymic=res.patronymic,
                        firstName=res.firstName,
                        role= SignInRequestDto.mapRole(res.role),
                    )
                )
                AuthResponse.Success(res.accessToken ?: "")
            }
        } catch (e: Exception) {
            AuthResponse.Error()
        }
    }

    override suspend fun authorize(toke: String): AuthResponse<Unit> {
        return AuthResponse.Success(Unit)
    }
}