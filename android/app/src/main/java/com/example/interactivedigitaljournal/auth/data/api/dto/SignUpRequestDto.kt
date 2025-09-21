package com.example.interactivedigitaljournal.auth.data.api.dto

import com.example.interactivedigitaljournal.auth.domain.models.SignUpModel
import com.example.interactivedigitaljournal.auth.domain.models.UserRole
import kotlinx.serialization.SerialName
import kotlin.String

data class SignUpRequestDto(
    val email: String,
    val surname: String,
    val patronymic: String,
    @SerialName("first_name") val firstName: String,
    val password: String,
    val role: UserRole,
) {
    companion object {
        fun fromDomainModel(signUpModel: SignUpModel) = SignUpRequestDto(
            signUpModel.email,
            signUpModel.surname,
            signUpModel.patronymic,
            signUpModel.firstName,
            signUpModel.password,
            signUpModel.role,
        )
    }
}