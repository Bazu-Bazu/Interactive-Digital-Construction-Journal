package com.example.interactivedigitaljournal.construction_objects.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interactivedigitaljournal.construction_objects.domain.models.ConstructionObjectRequest
import com.example.interactivedigitaljournal.construction_objects.domain.models.Part
import com.example.interactivedigitaljournal.construction_objects.domain.repository.ConstructionObjectRepository
import com.example.interactivedigitaljournal.construction_objects.domain.repository.ConstructionObjectResponse
import com.example.interactivedigitaljournal.construction_objects.presentation.model.ConstructionObjectUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ConstructionObjectViewModel @Inject constructor(
    private val constructionObjectRepository: ConstructionObjectRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        ConstructionObjectUiState(
            currentPart = Part(
                objectId = -1,
                name = "",
                description = "",
                startDate = LocalDate.now(),
                endDate = LocalDate.now(),
                done = false,
            ),
            partList = listOf(),
            currentConstructionObject = ConstructionObjectRequest(
                name = "",
                coordinates = listOf(),
                startDate = LocalDate.now(),
            ),
        )
    )
    val uiState = _uiState.asStateFlow()

    fun updateCurrentPart(transform: Part.() -> Part) {
        _uiState.update { currentState ->
            currentState.copy(currentPart = currentState.currentPart.transform())
        }
    }
    fun updateConstructionObject(transform: ConstructionObjectRequest.() -> ConstructionObjectRequest) {
        _uiState.update { currentState ->
            currentState.copy(
                currentConstructionObject = currentState.currentConstructionObject.transform()
            )
        }
    }

    fun updateAddress(address: String) {
        _uiState.value = _uiState.value.copy(address = address)
    }

    private fun validateCurrentPart(part: Part): Boolean = (part.name.isNotEmpty() && part.description.isNotEmpty())

    private fun validateConstructionObject() =
        (_uiState.value.currentConstructionObject.name.isNotEmpty() &&
                _uiState.value.currentConstructionObject.coordinates.isNotEmpty())

    fun addPart() {
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )
        val validateRes = validateCurrentPart(_uiState.value.currentPart)
        if (validateRes)
            _uiState.value = _uiState.value.copy(
                partList = _uiState.value.partList + listOf(_uiState.value.currentPart)
            )
        _uiState.value = _uiState.value.copy(
            isLoading = false
        )
    }

    fun createObjectWithParts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val validateRes = validateConstructionObject()
            println(_uiState.value)
            println(validateRes)
            if (!validateRes) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                return@launch
            }

            val res = constructionObjectRepository.addConstructionObject(
                _uiState.value.currentConstructionObject
            )
            println(res)
            _uiState.value = _uiState.value.copy(createConstructionObjectResponse = res)
            if (_uiState.value.createConstructionObjectResponse !is ConstructionObjectResponse.Success) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                return@launch
            }

            val partList = _uiState.value.partList.map {
                Part(
                    res.data!!.id,
                    it.name,
                    it.description,
                    it.startDate,
                    it.endDate,
                    it.done,
                )
            }
            println(partList)

            val resPartList = constructionObjectRepository.addConstructionObjectPartList(
                partList
            )
            println(resPartList)
            if (_uiState.value.createConstructionObjectResponse is ConstructionObjectResponse.Success)
                _uiState.value = _uiState.value.copy(
                    createConstructionObjectPartListResponse = resPartList,
                )

            _uiState.value = _uiState.value.copy(
                isLoading = false,
            )
        }
    }
}