package com.example.interactivedigitaljournal.construction_objects.domain.repository

import com.example.interactivedigitaljournal.construction_objects.domain.models.ConstructionObject
import com.example.interactivedigitaljournal.construction_objects.domain.models.ConstructionObjectRequest
import com.example.interactivedigitaljournal.construction_objects.domain.models.Part

interface ConstructionObjectRepository {
    suspend fun addConstructionObject(newConstructionObject: ConstructionObjectRequest):
            ConstructionObjectResponse<ConstructionObject>

    suspend fun addConstructionObjectPartList(newPartList: List<Part>): ConstructionObjectResponse<List<Part>>
}