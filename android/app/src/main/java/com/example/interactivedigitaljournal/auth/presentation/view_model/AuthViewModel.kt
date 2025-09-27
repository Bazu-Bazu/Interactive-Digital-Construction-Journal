package com.example.interactivedigitaljournal.auth.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interactivedigitaljournal.auth.domain.models.SignInModel
import com.example.interactivedigitaljournal.auth.domain.models.SignUpModel
import com.example.interactivedigitaljournal.auth.domain.models.UserRole
import com.example.interactivedigitaljournal.auth.domain.repository.AuthRepository
import com.example.interactivedigitaljournal.auth.presentation.model.AuthUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        AuthUIState(
            signUpModel = SignUpModel(
                "",
                "",
                "",
                "",
                "",
                UserRole.CUSTOMER,
            ),
            signInModel = SignInModel(
                "",
                "",
            ),
            mapOf()
        )
    )
    val uiState: StateFlow<AuthUIState> = _uiState.asStateFlow()

    fun updateSignUpModel(transform: SignUpModel.() -> SignUpModel) {
        _uiState.update { currentState ->
            currentState.copy(signUpModel = currentState.signUpModel.transform())
        }
    }

    fun updateSignInModel(transform: SignInModel.() -> SignInModel) {
        _uiState.update { currentState ->
            currentState.copy(signInModel = currentState.signInModel.transform())
        }
    }

    private fun validateSignUpModel(signUpModel: SignUpModel): Map<String, String> {
        val errors = mutableMapOf<String, String>()

        // Check for empty fields
        if (signUpModel.email.isBlank()) {
            errors["email"] = "Электронная почта не может быть пустой"
        }
        if (signUpModel.surname.isBlank()) {
            errors["surname"] = "Фамилия не может быть пустой"
        }
        if (signUpModel.patronymic.isBlank()) {
            errors["patronymic"] = "Отчество не может быть пустым"
        }
        if (signUpModel.firstName.isBlank()) {
            errors["firstName"] = "Имя не может быть пустым"
        }
        if (signUpModel.password.isBlank()) {
            errors["password"] = "Пароль не может быть пустым"
        }

        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        if (signUpModel.email.isNotBlank() && !signUpModel.email.matches(Regex(emailPattern))) {
            errors["email"] = "Неверный формат электронной почты"
        }

        if (signUpModel.password.isNotBlank()) {
            if (signUpModel.password.length < 8) {
                errors["password"] = "Пароль должен содержать не менее 8 символов"
            } else if (!signUpModel.password.contains(Regex("[A-Za-z]")) ||
                !signUpModel.password.contains(Regex("[0-9]"))) {
                errors["password"] = "Пароль должен содержать буквы и цифры"
            }
        }

        return errors
    }

    private fun validateSignInModel(signInModel: SignInModel): Map<String, String> {
        val errors = mutableMapOf<String, String>()

        if (signInModel.email.isBlank()) {
            errors["email"] = "Электронная почта не может быть пустой"
        }

        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        if (signInModel.email.isNotBlank() && !signInModel.email.matches(Regex(emailPattern))) {
            errors["email"] = "Неверный формат электронной почты"
        }

        if (signInModel.password.isNotBlank()) {
            if (signInModel.password.length < 8) {
                errors["password"] = "Пароль должен содержать не менее 8 символов"
            } else if (!signInModel.password.contains(Regex("[A-Za-z]")) ||
                !signInModel.password.contains(Regex("[0-9]"))) {
                errors["password"] = "Пароль должен содержать буквы и цифры"
            }
        }

        return errors
    }

    fun signUp() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val validateResult = validateSignUpModel(_uiState.value.signUpModel)

            if (validateResult.isNotEmpty()) {
                _uiState.value = _uiState.value.copy(errors = validateResult)
                _uiState.value = _uiState.value.copy(isLoading = false)
                return@launch
            }

            val res = authRepository.singUp(_uiState.value.signUpModel)
            _uiState.value = _uiState.value.copy(isLoading = false, signUpResponse = res)
        }
    }

    fun signIn() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val validateResult = validateSignInModel(_uiState.value.signInModel)

            if (validateResult.isNotEmpty()) {
                _uiState.value = _uiState.value.copy(isLoading = false, errors = validateResult)
                return@launch
            }

            val res = authRepository.singIn(_uiState.value.signInModel)
            _uiState.value = _uiState.value.copy(isLoading = false, signInResponse = res)
        }
    }

    fun logout() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            _uiState.value = _uiState.value.copy(
                isAuthorizedResponse = authRepository.logout(),
                isLoading = false,
            )
        }
    }

    fun isAuthorized() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            _uiState.value = _uiState.value.copy(
                isAuthorizedResponse = authRepository.isAuthorized(),
                isLoading = false,
            )
        }
    }

    fun getUser() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            _uiState.value = _uiState.value.copy(
                getUserResponse = authRepository.getCurrentUser(),
                isLoading = false,
            )
        }
    }
}
