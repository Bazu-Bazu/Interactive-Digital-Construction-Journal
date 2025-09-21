package com.example.interactivedigitaljournal.auth.domain.repository

sealed class AuthResponse<T>(val data: T? = null) {
    class Success<T>(data: T) : AuthResponse<T>(data)
    class Error<T> : AuthResponse<T>()
}