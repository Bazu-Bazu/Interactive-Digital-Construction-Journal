package com.example.interactivedigitaljournal.construction_objects.domain.models

import java.time.LocalDate

data class ConstructionObject(
    val id: Long,
    val name: String,
    val coordinates: List<Double>,
    val foremanId: Long,
    val customerId: Long,
    val inspectorId: Long,
    val activated: Boolean,
    val startDate: LocalDate,
)
