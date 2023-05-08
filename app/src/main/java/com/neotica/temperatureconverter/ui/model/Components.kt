package com.neotica.temperatureconverter.ui.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotica.temperatureconverter.R
import com.neotica.temperatureconverter.Scale
import com.neotica.temperatureconverter.ui.theme.TemperatureConverterTheme
import com.neotica.temperatureconverter.ui.utils.convertToCelsius
import com.neotica.temperatureconverter.ui.utils.convertToFahrenheit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawerApp(
    modifier: Modifier = Modifier,
) {
    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val snackBarState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            DefTopBar(
                onMenuClick = {
                    scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        } else if (drawerState.isOpen) {
                            drawerState.close()
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarState) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center,
        ) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                gesturesEnabled = drawerState.isOpen,
                drawerContent = {
                    ModalDrawerSheet() {
                        Spacer(modifier = Modifier.height(8.dp))
                        NavigationDrawerContent(
                            scope = scope,
                            drawerState = drawerState,
                            snackBarHostState = snackBarState
                        )
                    }
                }
            ) {
                Column(
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                ) {
                    TwoWayConverterApp()
                    Text(text = stringResource(id = R.string.hello_world))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefTopBar(onMenuClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick =
            { onMenuClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.menu)
                )
            }
        },
        title = { Text(text = stringResource(id = R.string.app_name)) }
    )
}

data class MenuItem(val icon: ImageVector, val title: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerContent(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    scope: CoroutineScope = rememberCoroutineScope(),
    snackBarHostState: SnackbarHostState
) {
    val items = listOf(
        MenuItem(Icons.Default.Home, "Home"),
        MenuItem(Icons.Default.Favorite, "Favorite"),
        MenuItem(Icons.Default.AccountCircle, "Profile"),
    )
    var selectedItem by remember { mutableStateOf(items[0]) }
    Column(modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .height(190.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        )
        for (item in items) {
            NavigationDrawerItem(
                label = {
                    Row {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            modifier.padding(horizontal = 12.dp)
                        )
                        Text(text = item.title)
                    }
                },
                selected = item == selectedItem,
                onClick = {
                    scope.launch {
                        drawerState.close()
                        snackBarHostState.showSnackbar(
                            message = item.title,
                            actionLabel = null,
                            withDismissAction = true
                        )
                    }
                    selectedItem = item
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    TemperatureConverterTheme {
        NavDrawerApp()
    }
}