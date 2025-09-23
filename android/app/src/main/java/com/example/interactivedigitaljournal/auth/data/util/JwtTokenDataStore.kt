package com.example.interactivedigitaljournal.auth.data.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.interactivedigitaljournal.auth.domain.utils.JwtTokenManager
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class JwtTokenDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : JwtTokenManager {
    companion object {
        val ACCESS_JWT_KEY = stringPreferencesKey("access_jwt")
        val REFRESH_JWT_KEY = stringPreferencesKey("refresh_jwt")
    }

    override suspend fun saveAccessJwt(
        accessToken: String,
        refreshToken: String,
    ) {
        dataStore.edit { preferences ->
            preferences[ACCESS_JWT_KEY] = accessToken
        }
        dataStore.edit { preferences ->
            preferences[REFRESH_JWT_KEY] = refreshToken
        }
    }

    override suspend fun getAccessJwt(): Pair<String?, String?> {
        return Pair(
            dataStore.data.map { preferences ->
                preferences[ACCESS_JWT_KEY]
            }.first(),
            dataStore.data.map { preferences ->
                preferences[REFRESH_JWT_KEY]
            }.first(),
        )
    }

    override suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_JWT_KEY)
        }
    }
}