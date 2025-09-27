package com.example.interactivedigitaljournal.construction_objects.presentation.model

import com.example.interactivedigitaljournal.construction_objects.domain.models.ConstructionObject
import com.example.interactivedigitaljournal.construction_objects.domain.models.ConstructionObjectRequest
import com.example.interactivedigitaljournal.construction_objects.domain.models.Part
import com.example.interactivedigitaljournal.construction_objects.domain.repository.ConstructionObjectResponse

data class ConstructionObjectUiState(
    val currentPart: Part,
    val partList: List<Part>,
    val currentConstructionObject: ConstructionObjectRequest,
    val address: String = "",
    val isLoading: Boolean = false,
    val createConstructionObjectResponse: ConstructionObjectResponse<ConstructionObject>? = null,
    val createConstructionObjectPartListResponse: ConstructionObjectResponse<List<Part>>? = null,
)