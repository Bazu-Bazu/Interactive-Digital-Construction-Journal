package com.example.interactivedigitaljournal.auth.domain.utils

interface JwtTokenManager {
    suspend fun saveAccessJwt(accessToken: String, refreshToken: String)
    suspend fun getAccessJwt(): Pair<String?, String?>
    suspend fun clearAll()
}