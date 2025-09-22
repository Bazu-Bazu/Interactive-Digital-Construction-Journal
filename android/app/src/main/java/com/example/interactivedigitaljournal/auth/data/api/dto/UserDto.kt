package com.example.interactivedigitaljournal.auth.data.api.dto

import com.example.interactivedigitaljournal.auth.domain.models.User
import com.example.interactivedigitaljournal.auth.domain.models.UserRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.String

@Serializable
data class UserDto(
    val id: String,
    val email: String,
    @SerialName("lastName") val surname: String,
    val patronymic: String,
    val firstName: String,
    val role: UserRole,
    val accessToken: String,
    val refreshToken: String,
) {
    fun toDomainModel() = User(
        email,
        surname,
        patronymic,
        firstName,
        role,
    )
}