package com.example.interactivedigitaljournal.auth.domain.models

data class SignUpModel(
    val email: String,
    val surname: String,
    val patronymic: String,
    val firstName: String,
    val password: String,
    val role: UserRole,
)
