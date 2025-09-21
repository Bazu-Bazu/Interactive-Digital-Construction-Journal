package com.example.interactivedigitaljournal.auth.presentation.model

import com.example.interactivedigitaljournal.auth.domain.models.SignUpModel
import com.example.interactivedigitaljournal.auth.domain.models.User
import com.example.interactivedigitaljournal.auth.domain.repository.AuthResponse

data class AuthUIState(
    val signUpModel: SignUpModel,
    val errors: Map<String, String>,
    val repositoryResponse: AuthResponse<User>? = null,
    val isLoading: Boolean = false,
)