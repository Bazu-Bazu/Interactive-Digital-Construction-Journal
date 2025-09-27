package com.example.interactivedigitaljournal.construction_objects.domain.repository

sealed class ConstructionObjectResponse<T>(val data: T? = null) {
    class Success<T>(data: T) : ConstructionObjectResponse<T>(data)
    class Error<T> : ConstructionObjectResponse<T>()
    class Unauthorized<T> : ConstructionObjectResponse<T>()
}