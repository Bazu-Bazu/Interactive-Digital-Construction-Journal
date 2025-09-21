package com.example.interactivedigitaljournal.auth.domain.repository

import com.example.interactivedigitaljournal.auth.domain.models.SignInModel
import com.example.interactivedigitaljournal.auth.domain.models.SignUpModel
import com.example.interactivedigitaljournal.auth.domain.models.User

interface AuthRepository {
    suspend fun singUp(singUpModel: SignUpModel) : AuthResponse<User>

    suspend fun singIn(singUpModel: SignInModel) : AuthResponse<String>

    suspend fun authorize(toke: String) : AuthResponse<Unit>
}