package com.salmafahira0038.miniproject.ui.screen

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
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

    val selectedAwal = remember { mutableIntStateOf(pilihKonversi[0]) }

    val radioOption = listOf(
        R.drawable.celsius to R.string.pilih_celcius,
        R.drawable.fahrenheit to R.string.pilih_fahrenheit,
        R.drawable.kelvin to R.string.pilih_kelvin
    )

    var pilihSuhuAwal by remember { mutableIntStateOf(R.string.pilih_celcius) }



    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
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
            onValueChange = { suhu = it },
            label = { Text(text = stringResource(R.string.masukkan_suhu)) },
//            trailingIcon = { Text(text = "kg") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

            Row (
                modifier = Modifier.padding(top = 6.dp).border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
            ) {
                radioOption.forEach { (iconRes, labelRes) ->
                    ConvertionOption(
                        iconRes = iconRes,
                        label = stringResource(id = labelRes),
                        isSelected = pilihSuhuAwal == labelRes,
                        modifier = Modifier
                            .selectable(
                                selected = pilihSuhuAwal == labelRes,
                                onClick = { pilihSuhuAwal = labelRes },
                                role = Role.RadioButton
                            )
                            .weight(2f)
                            .padding(9.dp)
                    )
                }
            }

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
        Button(
            onClick = {},
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.hitung))
        }
    }
}
@Composable
fun ConvertionOption(iconRes: Int, label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 8.dp)
                .width(24.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
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
