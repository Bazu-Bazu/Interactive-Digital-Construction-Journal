package com.example.interactivedigitaljournal.auth.data.repository

import com.example.interactivedigitaljournal.auth.data.api.dto.SignInRequestDto
import com.example.interactivedigitaljournal.auth.data.api.dto.SignUpRequestDto
import com.example.interactivedigitaljournal.auth.data.api.service.AuthService
import com.example.interactivedigitaljournal.auth.data.db.dao.UserDao
import com.example.interactivedigitaljournal.auth.data.db.entity.UserEntity
import com.example.interactivedigitaljournal.auth.domain.models.SignInModel
import com.example.interactivedigitaljournal.auth.domain.models.SignUpModel
import com.example.interactivedigitaljournal.auth.domain.models.User
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

    override suspend fun isAuthorized(): AuthResponse<Unit> {
        try {
            if ((jwtDataStore.getJwt().first == null) || (jwtDataStore.getJwt().first == ""))
                return AuthResponse.Unauthorized()
            return AuthResponse.Success(Unit)
        } catch (e: Exception) {
            return AuthResponse.Error()
        }
    }

    override suspend fun getCurrentUser(): AuthResponse<User> =
        try {
            withContext(Dispatchers.IO) {
                AuthResponse.Success(userDao.findFirst().toDomain())
            }
        } catch (e: Exception) {
            AuthResponse.Error()
        }

    override suspend fun logout(): AuthResponse<Unit> =
        try {
            withContext(Dispatchers.IO) {
                userDao.deleteAll()
                jwtDataStore.clearAll()
                AuthResponse.Success(authService.logout())
            }
        } catch (e: Exception) {
            AuthResponse.Error()
        }

    override suspend fun updateToken(): AuthResponse<User> {
        return try {
            val refreshToken = jwtDataStore.getJwt().second
            if (refreshToken == null) {
                println("No refresh token available")
                return AuthResponse.Error()
            }

            println("Attempting token refresh...")
            val userDto = authService.updateToken(refreshToken)

            withContext(Dispatchers.IO) {
                userDao.deleteAll()
                jwtDataStore.clearAll()
                jwtDataStore.saveAccessJwt(
                    userDto.accessToken!!,
                    userDto.refreshToken!!,
                )
                val user = userDto.toDomainModel()
                userDao.insert(UserEntity.fromDomain(user))
                user
            }

            println("Token refresh successful")
            AuthResponse.Success(userDto.toDomainModel())
        } catch (e: Exception) {
            println("Token refresh failed: ${e.message}")
            AuthResponse.Error()
        }
    }

}