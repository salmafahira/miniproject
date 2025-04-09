package com.salmafahira0038.miniproject.ui.screen

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salmafahira0038.miniproject.R
import com.salmafahira0038.miniproject.ui.theme.MiniProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniProjectTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ){ innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var suhu by remember { mutableStateOf("") }
    val pilihKonversi =
        arrayOf(R.string.celcius, R.string.fahrenheit, R.string.kelvin)

    val expandedAwal = remember { mutableStateOf(false) }

    val selectedAwal = remember { mutableIntStateOf(pilihKonversi[0])  }

    Column (
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = suhu,
            onValueChange = {suhu = it},
            label = { Text(text = stringResource(R.string.masukkan_suhu))},
//            trailingIcon = { Text(text = "kg") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
            ExposedDropdownMenuBox(
                expanded = expandedAwal.value,
                onExpandedChange = {
                    expandedAwal.value = !expandedAwal.value
                }
            ) {
                TextField(
                    value = stringResource(selectedAwal.intValue),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(text = stringResource(R.string.pilih_konversi)) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedAwal.value)
                    },
                    textStyle = TextStyle(fontSize = 15.sp),
                    singleLine = true,
                    modifier = Modifier
                        .menuAnchor()
                        .width(500.dp)
                )
                ExposedDropdownMenu(
                    expanded = expandedAwal.value,
                    onDismissRequest = { expandedAwal.value = false }
                ) {
                    pilihKonversi.forEach { resId ->
                        DropdownMenuItem(
                            text = { Text(text = stringResource(resId)) },
                            onClick = {
                                selectedAwal.intValue = resId
                                expandedAwal.value = false
                            }
                        )
                    }
                }
            }
        }
}




@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    MiniProjectTheme {
        MainScreen()
    }
}