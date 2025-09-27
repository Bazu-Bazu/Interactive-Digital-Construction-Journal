package com.example.interactivedigitaljournal.construction_objects.presentation.screen

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.room.util.copy
import com.example.interactivedigitaljournal.common.presentation.component.DatePickerInput
import com.example.interactivedigitaljournal.construction_objects.domain.models.Part
import com.example.interactivedigitaljournal.construction_objects.presentation.view_model.ConstructionObjectViewModel
import com.yandex.mapkit.geometry.Point
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun PartCard(
    part: Part,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = part.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Badge(
                    containerColor = if (part.done) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = if (part.done) "Выполнено" else "В процессе",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (part.done) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (part.description.isNotBlank()) {
                Text(
                    text = part.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Период",
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = formatDateRange(part.startDate, part.endDate),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun formatDateRange(startDate: LocalDate, endDate: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale("ru"))
    return "${startDate.format(formatter)} - ${endDate.format(formatter)}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddObjectScreen(
    modifier: Modifier = Modifier,
    constructionObjectViewModel: ConstructionObjectViewModel = hiltViewModel(),
) {
    val uiState = constructionObjectViewModel.uiState.collectAsState().value
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text("Добавить новый строительный объект", fontSize = 32.sp)

        DatePickerInput(modifier = Modifier.padding(top = 16.dp), label = "Дата начала работ", onValueChange ={ newDate ->
            constructionObjectViewModel.updateConstructionObject {
                copy(startDate = newDate)
            }
        })
        OutlinedTextField(
            value = uiState.currentConstructionObject.name,
            onValueChange = { name ->
                constructionObjectViewModel.updateConstructionObject({ copy(name = name) })
            },
            label = { Text("Название объекта") },
            modifier = Modifier
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.address,
            onValueChange = { address ->
                constructionObjectViewModel.updateAddress(address = address)
            },
            label = { Text("Адрес объекта") },
            modifier = Modifier
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .padding(top=16.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = uiState.currentPart.name,
                onValueChange = { name ->
                    constructionObjectViewModel.updateCurrentPart({ copy(name = name) })
                },
                label = { Text("Название части работы") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            TextField(
                value = uiState.currentPart.description,
                onValueChange = { name ->
                    constructionObjectViewModel.updateCurrentPart({ copy(description = name) })
                },
                label = { Text("Описание части работы") },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .height(100.dp)
            )
            DatePickerInput(
                modifier = Modifier
                    .padding(top = 8.dp),
                label = "Дата начала работ", onValueChange = { newDate ->
                constructionObjectViewModel.updateCurrentPart {
                    copy(startDate = newDate)
                }
            })
            DatePickerInput(
                modifier = Modifier
                    .padding(top = 8.dp),
                label="Дата окончания работ", onValueChange = { newDate ->
                constructionObjectViewModel.updateCurrentPart {
                    copy(endDate = newDate)
                }
            })
        }
        Button(
            onClick = {
                constructionObjectViewModel.addPart()
            },
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            Text("Добавить часть работы")
        }

        for (part in uiState.partList) {
            PartCard(part)
        }
        Button(
            onClick = {
                geocodeAddress(context, uiState.address) { newCoordinates ->
                    constructionObjectViewModel.updateConstructionObject {
                        copy(
                            coordinates = listOf(newCoordinates.longitude, newCoordinates.latitude)
                        )
                    }
                    constructionObjectViewModel.createObjectWithParts()
                }
            }
        ) {
            Text("Создать объект")
        }
    }
}

fun geocodeAddress(context: Context, address: String, onResult: (Point) -> Unit) {
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocationName(address, 1, object : Geocoder.GeocodeListener {
                override fun onGeocode(addresses: List<Address>) {
                    if (addresses.isNotEmpty()) {
                        val location = addresses[0]
                        val point = Point(location.latitude, location.longitude)
                        println(point)
                        onResult(point)
                    }
                }

                override fun onError(errorMessage: String?) {
                    println("Android Geocoder error: $errorMessage")
                }
            })
        } else {
            val addresses = geocoder.getFromLocationName(address, 1)
            if (addresses?.isNotEmpty() == true) {
                val location = addresses[0]
                val point = Point(location.latitude, location.longitude)
                onResult(point)
            }
        }
    } catch (e: Exception) {
        println("Android Geocoder error: ${e.message}")
    }
}
