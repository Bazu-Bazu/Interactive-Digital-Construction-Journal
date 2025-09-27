package com.example.interactivedigitaljournal.construction_objects.data.api.dto

import com.example.interactivedigitaljournal.construction_objects.data.util.toKotlinxLocalDate
import com.example.interactivedigitaljournal.construction_objects.domain.models.ConstructionObject
import com.example.interactivedigitaljournal.construction_objects.domain.models.ConstructionObjectRequest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import kotlinx.serialization.Serializable
import kotlin.Long
import java.time.LocalDate as JavaLocalDate

@Serializable
data class ConstructionObjectDto(
    val id: Long,
    val name: String,
    val coordinates: List<Double>,
    val startDate: LocalDate,
    val foremanId: Long?,
    val customerId: Long?,
    val inspectorId: Long?,
    val activated: Boolean,
) {

    fun toDomainModel() =
        ConstructionObject(
            id = id,
            name = name,
            coordinates = coordinates,
            startDate = startDate.toJavaLocalDate(),
            foremanId = foremanId ?: -1,
            customerId = customerId ?: -1,
            inspectorId = inspectorId ?: -1,
            activated = activated,
        )
}