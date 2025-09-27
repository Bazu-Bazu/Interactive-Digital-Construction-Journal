package com.example.interactivedigitaljournal.construction_objects.data.util

import kotlinx.datetime.LocalDate
import java.time.LocalDate as JavaLocalDate

fun JavaLocalDate.toKotlinxLocalDate(): LocalDate {
    return LocalDate(year, monthValue, dayOfMonth)
}
