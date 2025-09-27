package com.example.interactivedigitaljournal.auth.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.interactivedigitaljournal.auth.domain.models.User
import com.example.interactivedigitaljournal.auth.domain.models.UserRole
import com.example.interactivedigitaljournal.auth.domain.repository.AuthResponse
import com.example.interactivedigitaljournal.auth.presentation.view_model.AuthViewModel
import kotlin.String


@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    user: User,
) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
    ) {
        Text("${user.surname} ", fontSize = 20.sp)
        Text("${user.patronymic} ", fontSize = 20.sp)
        Text(user.firstName, fontSize = 20.sp)
    }
    Text(
        user.email,
        modifier = Modifier
            .padding(top=16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray)
            .padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
//    user: User = User(
//        "email@email.ru",
//        "Mysurname",
//        "Mypatronymics",
//        "Firstname",
//        UserRole.CUSTOMER,
//    ),
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    authViewModel.getUser()
    val authState = authViewModel.uiState.collectAsState().value
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Icon(
            Icons.Default.AccountCircle,
            contentDescription = "",
            modifier = Modifier
                .clip(CircleShape)
                .size(128.dp)
        )

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top=64.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            when(authState.getUserResponse) {
                is AuthResponse.Success<User> -> authState.getUserResponse.data?.let {
                    UserInfo(user = it)
                }
                else -> Text("Что-то не так")
            }
        }
        Button(
            authViewModel::logout,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .padding(8.dp),
            colors = ButtonColors(
                containerColor = Color.Red,
                contentColor = Color.White,
                disabledContainerColor = Color.Red,
                disabledContentColor = Color.White,
            )
        ) {
            Text("Выйти", color = Color.White)
        }
//        when (authState.getUserResponse) {
//            is AuthResponse.Success<User> -> authState.getUserResponse.data?.let {
//                UserInfo(user = it)
//            }
//            else -> Text("Что-то не так")
//        }
    }
}