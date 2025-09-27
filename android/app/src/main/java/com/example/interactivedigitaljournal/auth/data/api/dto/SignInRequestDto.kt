package com.example.interactivedigitaljournal.auth.data.api.dto

import com.example.interactivedigitaljournal.auth.domain.models.SignInModel
import com.example.interactivedigitaljournal.auth.domain.models.UserRole
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestDto(
    val email: String,
    val password: String,
) {
    companion object {
        fun mapRole(role: ServerUserRole): UserRole =
            when (role) {
                ServerUserRole.ROLE_FOREMAN -> UserRole.FOREMAN
                ServerUserRole.ROLE_CUSTOMER -> UserRole.CUSTOMER
                ServerUserRole.ROLE_INSPECTOR -> UserRole.INSPECTOR
            }

        fun fromDomainModel(signInModel: SignInModel) =
            SignInRequestDto(
                signInModel.email,
                signInModel.password,
            )
    }
}