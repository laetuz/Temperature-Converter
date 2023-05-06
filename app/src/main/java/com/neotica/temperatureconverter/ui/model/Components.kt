package com.neotica.temperatureconverter.ui.model

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotica.temperatureconverter.R
import com.neotica.temperatureconverter.Scale
import com.neotica.temperatureconverter.ui.theme.TemperatureConverterTheme
import com.neotica.temperatureconverter.ui.utils.convertToCelsius
import com.neotica.temperatureconverter.ui.utils.convertToFahrenheit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatefulTemperatureInput(
    modifier: Modifier = Modifier
) {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }
    Column(modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.stateful_converter),
            style = MaterialTheme.typography.headlineMedium
        )
        OutlinedTextField(
            value = input,
            onValueChange = {
                input = it
                output = convertToFahrenheit(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = stringResource(id = R.string.enter_celsius, output)) }
        )
        Text(stringResource(R.string.temperature_fahrenheit, output))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessTemperatureInput(
    input: String,
    output: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.stateless_converter),
            style = MaterialTheme.typography.headlineMedium
        )
        OutlinedTextField(
            value = input,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = stringResource(id = R.string.enter_celsius)) }
        )
        Text(text = stringResource(id = R.string.temperature_fahrenheit, output))
    }
}

@Composable
fun ConverterApp(
    modifier: Modifier = Modifier
) {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }
    Column(modifier) {
        StatelessTemperatureInput(input = input, output = output, onValueChange = {
            input = it
            output = convertToFahrenheit(it)
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralTemperatureInput(
    scale: Scale,
    input: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        OutlinedTextField(
            value = input,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = stringResource(
                        id = R.string.enter_temperature,
                        scale.scaleName
                    )
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun TwoWayConverterApp(
    modifier: Modifier = Modifier
) {
    var celsius by remember { mutableStateOf("") }
    var fahrenheit by remember { mutableStateOf("") }
    Column(modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.two_way_converter),
            style = MaterialTheme.typography.headlineMedium
        )
        GeneralTemperatureInput(scale = Scale.CELSIUS,
            input = celsius,
            onValueChange = {
                celsius = it
                fahrenheit = convertToFahrenheit(it)
            }
        )
        GeneralTemperatureInput(
            scale = Scale.FAHRENHEIT,
            input = fahrenheit,
            onValueChange = {
                fahrenheit = it
                celsius = convertToCelsius(it)
            }
        )
    }
}

@Preview
@Composable
fun AppPreview() {
    TemperatureConverterTheme {
        ConverterApp()
    }
}