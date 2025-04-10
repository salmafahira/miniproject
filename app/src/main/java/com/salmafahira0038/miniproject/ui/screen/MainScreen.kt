package com.salmafahira0038.miniproject.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.salmafahira0038.miniproject.R
import com.salmafahira0038.miniproject.navigation.Screen
import com.salmafahira0038.miniproject.ui.theme.MiniProjectTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController){
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.app_name),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ){ innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var suhu by rememberSaveable  { mutableStateOf("") }
    var suhuError by rememberSaveable { mutableStateOf(false) }

    var hasil by remember { mutableStateOf<Float?>(null) }

    val pilihKonversi =
        arrayOf(R.string.celcius, R.string.fahrenheit, R.string.kelvin)

    val expandedAwal = remember { mutableStateOf(false) }

    val selectedAwal = remember { mutableIntStateOf(pilihKonversi[0]) }

    val radioOption = listOf(
        R.drawable.celsius to R.string.celcius,
        R.drawable.fahrenheit to R.string.fahrenheit,
        R.drawable.kelvin to R.string.kelvin
    )


    var pilihSuhuAwal by remember { mutableIntStateOf(R.string.celcius) }

    val context = LocalContext.current

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
            label = { Text(text = stringResource(R.string.masukkan_suhu)) }, singleLine = true,
            trailingIcon = { IconPicker(suhuError, "°") },
            supportingText = { ErrorHint(suhuError) },
            isError = suhuError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.suhu_awal),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
        )

            Row (
                modifier = Modifier.padding(vertical = 8.dp).border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
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

                            .padding(horizontal = 8.dp)
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
            onClick = {
                suhuError = (suhu == "" || suhu == "-")
                if (suhuError) return@Button

                hasil = konversiSuhu(suhu.toFloat(), pilihSuhuAwal, selectedAwal.intValue)
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.hitung))
        }
        if (hasil != null) {
            Text(
                text = stringResource(R.string.hasil_konversi) + hasil,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 12.dp)
            )

            Button(
                onClick = {
                    val hasilFormatted = "%.2f".format(hasil)
                    val pesan = context.getString(
                        R.string.bagikan_template,
                        suhu,
                        context.getString(pilihSuhuAwal),
                        context.getString(selectedAwal.intValue),
                        hasilFormatted.uppercase()
                    )
                    shareData(context, pesan)
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.bagikan))
            }

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
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 15.dp).padding(bottom = 15.dp)
                .width(40.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

private fun konversiSuhu(suhu: Float, dari: Int, ke: Int): Float {
    return when (dari) {
        R.string.celcius -> when (ke) {
            R.string.fahrenheit -> (suhu * 9 / 5) + 32
            R.string.kelvin -> suhu + 273.15f
            else -> suhu
        }
        R.string.fahrenheit -> when (ke) {
            R.string.celcius -> (suhu - 32) * 5 / 9
            R.string.kelvin -> ((suhu - 32) * 5 / 9) + 273.15f
            else -> suhu
        }
        R.string.kelvin -> when (ke) {
            R.string.celcius -> suhu - 273.15f
            R.string.fahrenheit -> ((suhu - 273.15f) * 9 / 5) + 32
            else -> suhu
        }
        else -> suhu
    }
}


private fun shareData(context: Context, message: String){
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null){
        context.startActivity(shareIntent)
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String){
    if(isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean){
    if(isError){
        Text(text = stringResource(R.string.input_invalid))
    }
}

    @Preview(showBackground = true)
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
    @Composable
    fun MainScreenPreview() {
        MiniProjectTheme {
            MainScreen(rememberNavController())
        }
    }
