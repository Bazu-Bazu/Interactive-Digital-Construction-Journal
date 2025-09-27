package com.example.interactivedigitaljournal.construction_objects.data.api.dto

import com.example.interactivedigitaljournal.construction_objects.data.util.toKotlinxLocalDate
import com.example.interactivedigitaljournal.construction_objects.domain.models.ConstructionObjectRequest
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate
import java.time.LocalDate as JavaLocalDate

@Serializable
data class ConstructionObjectRequestDto(
    val name: String,
    val coordinates: List<Double>,
    val startDate: LocalDate,
) {
    companion object {
        fun fromDomain(domainModel: ConstructionObjectRequest) =
            ConstructionObjectRequestDto(
                name = domainModel.name,
                coordinates = domainModel.coordinates,
                startDate = domainModel.startDate.toKotlinxLocalDate(),
            )
    }
}