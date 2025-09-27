package com.example.interactivedigitaljournal.construction_objects.domain.models

import java.time.LocalDate

data class Part(
    val objectId: Long,
    val name: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val done: Boolean,
)