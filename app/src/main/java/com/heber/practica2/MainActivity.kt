package com.heber.practica2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.heber.practica2.ui.theme.Practica2Theme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import android.content.res.Configuration
import androidx.activity.viewModels
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.heber.practica2.ViewModels.CounterViewModel


class MainActivity : ComponentActivity() {
    private val counterViewModel: CounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Content(
                        paddingValues = innerPadding,
                        viewModel = counterViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Content(
    paddingValues: PaddingValues,
    viewModel: CounterViewModel
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Counter: ${viewModel.counter.value}")
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                viewModel.add()
            }
        ) {
            Text(text = "+")
        }
    }
}