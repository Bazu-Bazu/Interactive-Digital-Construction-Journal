package com.example.interactivedigitaljournal.auth.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTokenRequest(
    val refreshToken: String,
)