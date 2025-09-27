package com.example.interactivedigitaljournal.auth.data.api

import com.example.interactivedigitaljournal.common.data.ApiRoutes

object AuthApiRoutes {
    val signUp = ApiRoutes.baseUrl + "/auth/signup"
    val signIn = ApiRoutes.baseUrl + "/auth/login"
    val logout = ApiRoutes.baseUrl + "/auth/logout"
    val updateToken = ApiRoutes.baseUrl + "/auth/update-token"
}