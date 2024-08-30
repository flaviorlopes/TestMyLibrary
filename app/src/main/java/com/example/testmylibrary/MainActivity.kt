package com.example.testmylibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mylibrary.model.Service
import com.example.mylibrary.model.request.SaveStringRequestData
import com.example.mylibrary.model.response.Result
import com.example.testmylibrary.components.CustomDialog
import com.example.testmylibrary.ui.theme.TestMyLibraryTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestMyLibraryTheme(dynamicColor = false) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Mobiweb - Test MyLibrary App") },
                            colors = TopAppBarDefaults.topAppBarColors().copy(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = Color.White
                            )
                        )}
                    ) { innerPadding ->
                    HomeApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeApp(modifier: Modifier = Modifier) {
    val textToSave = remember {
        mutableStateOf("")
    }

    val openDialog = remember {
        mutableStateOf(false)
    }

    val dialogText = remember {
        mutableStateOf("")
    }

    val iconDialog = remember {
        mutableStateOf(Icons.Default.Info)
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier.padding(16.dp),
            label = { Text(text = "Type here your message") },
            value = textToSave.value, onValueChange = {
                textToSave.value = it
            })
        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                val requestData = SaveStringRequestData(
                    myString = textToSave.value
                )
                val result = Service.saveString(requestData)
                when(result) {
                    is Result.Error -> {
                        iconDialog.value = Icons.Default.Info
                        dialogText.value = result.errorMessage.toString()
                    }
                    is Result.Success -> {
                        iconDialog.value = Icons.Default.CheckCircle
                        dialogText.value = result.resultMessage.toString()
                    }
                }
                openDialog.value = true
            }

        }) {
            Text(text = "Save Message")
        }
    }

    when {
        openDialog.value -> {
            CustomDialog(
                onConfirmation = { openDialog.value = false },
                dialogTitle = "Result operation",
                dialogText = dialogText.value,
                icon = iconDialog.value
            )
        }
    }
}
