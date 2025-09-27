package com.example.interactivedigitaljournal.auth.presentation.model

import com.example.interactivedigitaljournal.auth.domain.models.SignInModel
import com.example.interactivedigitaljournal.auth.domain.models.SignUpModel
import com.example.interactivedigitaljournal.auth.domain.models.User
import com.example.interactivedigitaljournal.auth.domain.repository.AuthResponse

data class AuthUIState(
    val signUpModel: SignUpModel,
    val signInModel: SignInModel,
    val errors: Map<String, String>,
    val signUpResponse: AuthResponse<User>? = null,
    val signInResponse: AuthResponse<String>? = null,
    val isAuthorizedResponse: AuthResponse<Unit>? = null,
    val logoutResponse: AuthResponse<Unit>? = null,
    val getUserResponse: AuthResponse<User>? = null,
    val isLoading: Boolean = false,
)