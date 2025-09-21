package com.example.interactivedigitaljournal.auth.di

import com.example.interactivedigitaljournal.auth.data.api.service.AuthService
import com.example.interactivedigitaljournal.auth.data.repository.AuthRepositoryImpl
import com.example.interactivedigitaljournal.auth.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthService(httpClient: HttpClient): AuthService =
        AuthService(httpClient)

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService): AuthRepository =
        AuthRepositoryImpl(authService)
}