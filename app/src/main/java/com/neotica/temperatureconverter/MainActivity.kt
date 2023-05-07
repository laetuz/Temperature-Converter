package com.neotica.temperatureconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.neotica.temperatureconverter.ui.model.NavDrawerApp
import com.neotica.temperatureconverter.ui.theme.TemperatureConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemperatureConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavDrawerApp()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    TemperatureConverterTheme {
        NavDrawerApp()
    }
}

enum class Scale(val scaleName: String) {
    CELSIUS("Celsius"),
    FAHRENHEIT("Fahrenheit")
}