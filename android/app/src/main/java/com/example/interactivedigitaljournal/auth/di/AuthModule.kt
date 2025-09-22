package com.example.interactivedigitaljournal.auth.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.interactivedigitaljournal.auth.data.api.service.AuthService
import com.example.interactivedigitaljournal.auth.data.db.JournalDatabase
import com.example.interactivedigitaljournal.auth.data.repository.AuthRepositoryImpl
import com.example.interactivedigitaljournal.auth.domain.repository.AuthRepository
import com.example.interactivedigitaljournal.auth.domain.utils.JwtTokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            appContext.preferencesDataStoreFile("AUTH_PREFERENCES")
        }
    }

    @Provides
    @Singleton
    fun provideAuthService(httpClient: HttpClient): AuthService =
        AuthService(httpClient)

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService, jwtTokenManager: JwtTokenManager): AuthRepository =
        AuthRepositoryImpl(authService, jwtTokenManager)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): RoomDatabase = Room.databaseBuilder(
        appContext,
        JournalDatabase::class.java, "database-name"
    ).build()
}