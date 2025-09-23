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
    }

    override suspend fun saveAccessJwt(accessToken: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_JWT_KEY] = accessToken
        }
    }

    override suspend fun getAccessJwt(): String? {
        return dataStore.data.map { preferences ->
            preferences[ACCESS_JWT_KEY]
        }.first()
    }

    override suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_JWT_KEY)
        }
    }
}