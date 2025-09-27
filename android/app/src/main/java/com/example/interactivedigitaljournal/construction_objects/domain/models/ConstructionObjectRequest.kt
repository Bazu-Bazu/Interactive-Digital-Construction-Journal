package com.example.interactivedigitaljournal.construction_objects.domain.models

import java.time.LocalDate

data class ConstructionObjectRequest(
    val name: String,
    val coordinates: List<Double>,
    val startDate: LocalDate,
)
