package com.example.interactivedigitaljournal.auth.domain.models

data class SignInModel(
    val email: String,
    val password: String,
    val role: UserRole,
)
