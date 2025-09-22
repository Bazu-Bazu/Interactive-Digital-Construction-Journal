package com.example.interactivedigitaljournal.auth.presentation.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interactivedigitaljournal.auth.domain.repository.AuthResponse
import com.example.interactivedigitaljournal.auth.presentation.view_model.AuthViewModel

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
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
                text="Вход",
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium,
            )

            OutlinedTextField(
                value = authUiState.signInModel.email,
                onValueChange = { authViewModel.updateSignInModel { copy(email = it) } },
                label = { Text("Email") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.DarkGray,
                    unfocusedTextColor = Color.Gray,
                ),
            )

            OutlinedTextField(
                value = authUiState.signInModel.password,
                onValueChange = { authViewModel.updateSignInModel { copy(password = it) } },
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.DarkGray,
                    unfocusedTextColor = Color.Gray,
                ),
            )

            Button(onClick = authViewModel::signIn) {
                Text("Войти")
            }

            if (authUiState.signInResponse is AuthResponse.Error)
                Text("Что-то не так")
        }
    }
}