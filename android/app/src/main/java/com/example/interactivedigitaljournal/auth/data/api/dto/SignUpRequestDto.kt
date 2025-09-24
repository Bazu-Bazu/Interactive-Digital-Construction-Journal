package com.example.interactivedigitaljournal.auth.data.api.dto

import com.example.interactivedigitaljournal.auth.domain.models.SignUpModel
import com.example.interactivedigitaljournal.auth.domain.models.UserRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.String

@Serializable
data class SignUpRequestDto(
    val email: String,
    @SerialName("lastName")  val surname: String,
    val patronymic: String,
    val firstName: String,
    val password: String,
    val role: ServerUserRole,
) {
    companion object {
        fun mapRole(role: UserRole): ServerUserRole =
            when (role) {
                UserRole.FOREMAN -> ServerUserRole.ROLE_FOREMAN
                UserRole.CUSTOMER -> ServerUserRole.ROLE_CUSTOMER
                UserRole.INSPECTOR -> ServerUserRole.ROLE_INSPECTOR
            }

        fun fromDomainModel(signUpModel: SignUpModel) = SignUpRequestDto(
            signUpModel.email,
            signUpModel.surname,
            signUpModel.patronymic,
            signUpModel.firstName,
            signUpModel.password,
            mapRole(signUpModel.role),
        )
    }
}