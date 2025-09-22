package com.example.interactivedigitaljournal.auth.domain.utils

interface JwtTokenManager {
    suspend fun saveAccessJwt(accessToken: String)
    suspend fun getAccessJwt(): String?
    suspend fun clearAll()
}