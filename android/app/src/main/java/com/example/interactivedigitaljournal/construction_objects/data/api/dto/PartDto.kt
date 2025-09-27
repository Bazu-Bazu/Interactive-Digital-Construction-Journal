package com.example.interactivedigitaljournal.construction_objects.data.api.dto

import com.example.interactivedigitaljournal.construction_objects.domain.models.Part
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import kotlin.Long
import java.time.LocalDate as JavaLocalDate


@Serializable
data class PartDto(
    val objectId: Long,
    val name: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val done: Boolean,
) {
    companion object {
        fun fromDomain(domainModel: Part) =
            PartDto(
                domainModel.objectId,
                domainModel.name,
                domainModel.description,
                domainModel.startDate.toKotlinxLocalDate(),
                domainModel.endDate.toKotlinxLocalDate(),
                domainModel.done,
            )

        private fun JavaLocalDate.toKotlinxLocalDate(): LocalDate {
            return LocalDate(year, monthValue, dayOfMonth)
        }
    }

    fun toDomainModel() =
        Part(
            objectId,
            name,
            description,
            startDate.toJavaLocalDate(),
            endDate.toJavaLocalDate(),
            done,
        )
}