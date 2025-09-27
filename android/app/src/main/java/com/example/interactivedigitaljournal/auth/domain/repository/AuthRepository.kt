package com.example.interactivedigitaljournal.auth.domain.repository

import com.example.interactivedigitaljournal.auth.domain.models.SignInModel
import com.example.interactivedigitaljournal.auth.domain.models.SignUpModel
import com.example.interactivedigitaljournal.auth.domain.models.User

interface AuthRepository {
    suspend fun singUp(singUpModel: SignUpModel) : AuthResponse<User>
    suspend fun singIn(singInModel: SignInModel) : AuthResponse<String>
    suspend fun isAuthorized() : AuthResponse<Unit>
    suspend fun getCurrentUser(): AuthResponse<User>
    suspend fun logout(): AuthResponse<Unit>
    suspend fun updateToken(): AuthResponse<User>
}