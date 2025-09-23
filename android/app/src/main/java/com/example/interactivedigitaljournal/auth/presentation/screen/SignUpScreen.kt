package com.example.interactivedigitaljournal.auth.presentation.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interactivedigitaljournal.auth.presentation.view_model.AuthViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.interactivedigitaljournal.auth.domain.models.UserRole
import com.example.interactivedigitaljournal.auth.domain.repository.AuthResponse

//@Preview(showBackground = true)
@Composable
fun SignUpScreen(
    onRegister: () -> Unit,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val roles = listOf("Заказчик", "Инспектор", "Прораб")

    var selectedRole by remember { mutableStateOf(roles[0]) }

    val authUiState by authViewModel.uiState.collectAsState()


    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .width(IntrinsicSize.Max)
                .padding(8.dp)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {

            Text(
                text="Регистрация",
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium,
            )

            OutlinedTextField(
                value = authUiState.signUpModel.email,
                onValueChange = { authViewModel.updateSignUpModel { copy(email = it) } },
                label = { Text("Email") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.DarkGray,
                    unfocusedTextColor = Color.Gray,
                ),
            )

            OutlinedTextField(
                value = authUiState.signUpModel.surname,
                onValueChange = { authViewModel.updateSignUpModel { copy(surname = it) } },
                label = { Text("Фамилия") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.DarkGray,
                    unfocusedTextColor = Color.Gray,
                ),
            )

            OutlinedTextField(
                value = authUiState.signUpModel.firstName,
                onValueChange = { authViewModel.updateSignUpModel { copy(firstName = it) } },
                label = { Text("Имя") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.DarkGray,
                    unfocusedTextColor = Color.Gray,
                ),
            )

            OutlinedTextField(
                value = authUiState.signUpModel.patronymic,
                onValueChange = { authViewModel.updateSignUpModel { copy(patronymic = it) } },
                label = { Text("Отчество") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.DarkGray,
                    unfocusedTextColor = Color.Gray,
                ),
            )

            OutlinedTextField(
                value = authUiState.signUpModel.password,
                onValueChange = { authViewModel.updateSignUpModel { copy(password = it) } },
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.DarkGray,
                    unfocusedTextColor = Color.Gray,
                ),
            )

            Column(modifier.selectableGroup()) {
                roles.forEach { role ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (role == selectedRole),
                                onClick = {
                                    selectedRole = role
                                    when (role) {
                                        roles[0] -> authViewModel.updateSignUpModel { copy(role = UserRole.CUSTOMER) }
                                        roles[1] -> authViewModel.updateSignUpModel { copy(role = UserRole.INSPECTOR) }
                                        roles[2] -> authViewModel.updateSignUpModel { copy(role = UserRole.FOREMAN) }
                                        else -> authViewModel.updateSignUpModel { copy(role = UserRole.CUSTOMER) }
                                    }
                                },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (role == selectedRole),
                            onClick = null // null recommended for accessibility with screen readers
                        )
                        Text(
                            text = role,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

            Button(onClick = authViewModel::signUp) {
                Text("Зарегистрироваться")
            }

            TextButton(onClick = onRegister) {
                Text("Войти")
            }

            if (authUiState.signUpResponse is AuthResponse.Error)
                Text("Что-то не так")
            else if (authUiState.signUpResponse is AuthResponse.Success)
                onRegister()
        }
    }
}
