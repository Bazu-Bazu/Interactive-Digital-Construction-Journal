package com.example.interactivedigitaljournal.construction_objects.data.api.service

import com.example.interactivedigitaljournal.auth.domain.utils.JwtTokenManager
import com.example.interactivedigitaljournal.construction_objects.data.api.ConstructionObjectApiRoutes
import com.example.interactivedigitaljournal.construction_objects.data.api.dto.ConstructionObjectDto
import com.example.interactivedigitaljournal.construction_objects.data.api.dto.ConstructionObjectRequestDto
import com.example.interactivedigitaljournal.construction_objects.data.api.dto.PartDto
import com.example.interactivedigitaljournal.construction_objects.domain.models.Part
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import javax.inject.Inject

class ConstructionObjectService @Inject constructor(
    private val httpClient: HttpClient,
    private val jwtTokenManager: JwtTokenManager,
) {
    suspend fun addConstructionObject(
        newConstructionObjectDto: ConstructionObjectRequestDto
    ): ConstructionObjectDto? {
        val accessJwt = jwtTokenManager.getJwt().first
        val res = httpClient.post(ConstructionObjectApiRoutes.addConstructionObject) {
            contentType(ContentType.Application.Json)
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Bearer $accessJwt",
                )
            }
            setBody(newConstructionObjectDto)
        }
        return res.body<ConstructionObjectDto?>()
    }

    suspend fun addConstructionObjectPartList(newPartList: List<PartDto>):
            List<PartDto> {
        val accessJwt = jwtTokenManager.getJwt().first
        return httpClient.post(ConstructionObjectApiRoutes.addConstructionObjectPartList) {
            contentType(ContentType.Application.Json)
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Bearer $accessJwt",
                )
            }
            setBody(newPartList)
        }.body<List<PartDto>>()
    }
}