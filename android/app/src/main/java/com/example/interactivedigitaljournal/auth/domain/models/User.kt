package com.example.interactivedigitaljournal.auth.domain.models

data class User(
    val email: String,
    val surname: String,
    val patronymic: String,
    val firstName: String,
    val role: UserRole,
)
