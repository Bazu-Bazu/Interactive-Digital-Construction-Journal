package com.example.interactivedigitaljournal.construction_objects.di

import com.example.interactivedigitaljournal.auth.domain.repository.AuthRepository
import com.example.interactivedigitaljournal.auth.domain.utils.JwtTokenManager
import com.example.interactivedigitaljournal.construction_objects.data.api.service.ConstructionObjectService
import com.example.interactivedigitaljournal.construction_objects.data.repository.ConstructionObjectRepositoryImpl
import com.example.interactivedigitaljournal.construction_objects.domain.repository.ConstructionObjectRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConstructionObjectModule {
    @Provides
    @Singleton
    fun provideConstructionObjectService(httpClient: HttpClient, jwtTokenManager: JwtTokenManager): ConstructionObjectService {
        return ConstructionObjectService(httpClient, jwtTokenManager)
    }

    @Provides
    @Singleton
    fun provideConstructionObjectRepository(
        constructionObjectService: ConstructionObjectService,
        authRepository: AuthRepository,
    ): ConstructionObjectRepository {
        return ConstructionObjectRepositoryImpl(constructionObjectService, authRepository)
    }
}