package com.example.interactivedigitaljournal.construction_objects.data.repository

import com.example.interactivedigitaljournal.auth.domain.repository.AuthRepository
import com.example.interactivedigitaljournal.auth.domain.repository.AuthResponse
import com.example.interactivedigitaljournal.construction_objects.data.api.dto.ConstructionObjectRequestDto
import com.example.interactivedigitaljournal.construction_objects.data.api.dto.PartDto
import com.example.interactivedigitaljournal.construction_objects.data.api.service.ConstructionObjectService
import com.example.interactivedigitaljournal.construction_objects.domain.models.ConstructionObject
import com.example.interactivedigitaljournal.construction_objects.domain.models.ConstructionObjectRequest
import com.example.interactivedigitaljournal.construction_objects.domain.models.Part
import com.example.interactivedigitaljournal.construction_objects.domain.repository.ConstructionObjectRepository
import com.example.interactivedigitaljournal.construction_objects.domain.repository.ConstructionObjectResponse
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class ConstructionObjectRepositoryImpl @Inject constructor(
    private val constructionObjectService: ConstructionObjectService,
    private val authRepository: AuthRepository
) : ConstructionObjectRepository {

    override suspend fun addConstructionObject(newConstructionObject: ConstructionObjectRequest):
            ConstructionObjectResponse<ConstructionObject> =
        try {
            ConstructionObjectResponse.Success(
                constructionObjectService.addConstructionObject(
                    ConstructionObjectRequestDto.fromDomain(newConstructionObject)
                )?.toDomainModel() ?: throw Exception("Null response from service")
            )
        } catch(e: ClientRequestException) {
            println("ClientRequestException: ${e.response.status}")
            if (e.response.status == HttpStatusCode.Unauthorized || e.response.status == HttpStatusCode.Forbidden) {
                handleAuthError(newConstructionObject)
            } else {
                ConstructionObjectResponse.Error()
            }
        } catch(e: NoTransformationFoundException) {
            println("NoTransformationFoundException - Likely auth error")
            handleAuthError(newConstructionObject)
        } catch (e: Exception) {
            println("General error: $e")
            ConstructionObjectResponse.Error()
        }

    private suspend fun handleAuthError(newConstructionObject: ConstructionObjectRequest): ConstructionObjectResponse<ConstructionObject> {
        return try {
            // First refresh the token
            val tokenRefreshed = authRepository.updateToken()
            if (tokenRefreshed is AuthResponse.Success) {
                // Then retry the request with new token
                ConstructionObjectResponse.Success(
                    constructionObjectService.addConstructionObject(
                        ConstructionObjectRequestDto.fromDomain(newConstructionObject)
                    )?.toDomainModel() ?: throw Exception("Null response after token refresh")
                )
            } else {
                ConstructionObjectResponse.Unauthorized()
            }
        } catch (retryException: Exception) {
            println("Retry failed: $retryException")
            ConstructionObjectResponse.Unauthorized()
        }
    }

    override suspend fun addConstructionObjectPartList(newPartList: List<Part>):
            ConstructionObjectResponse<List<Part>> =
        try {
            ConstructionObjectResponse.Success(
                constructionObjectService.addConstructionObjectPartList(
                    newPartList.map { PartDto.fromDomain(it) }
                ).map { it.toDomainModel() }
            )
        } catch(e: ClientRequestException) {
            println("ClientRequestException in part list: ${e.response.status}")
            if (e.response.status == HttpStatusCode.Unauthorized || e.response.status == HttpStatusCode.Forbidden) {
                handlePartListAuthError(newPartList)
            } else {
                ConstructionObjectResponse.Error()
            }
        } catch(e: NoTransformationFoundException) {
            println("NoTransformationFoundException in part list")
            handlePartListAuthError(newPartList)
        } catch (e: Exception) {
            println("General error in part list: $e")
            ConstructionObjectResponse.Error()
        }

    private suspend fun handlePartListAuthError(newPartList: List<Part>): ConstructionObjectResponse<List<Part>> {
        return try {
            val tokenRefreshed = authRepository.updateToken()
            if (tokenRefreshed is AuthResponse.Success) {
                ConstructionObjectResponse.Success(
                    constructionObjectService.addConstructionObjectPartList(
                        newPartList.map { PartDto.fromDomain(it) }
                    ).map { it.toDomainModel() }
                )
            } else {
                ConstructionObjectResponse.Unauthorized()
            }
        } catch (retryException: Exception) {
            println("Part list retry failed: $retryException")
            ConstructionObjectResponse.Unauthorized()
        }
    }
}