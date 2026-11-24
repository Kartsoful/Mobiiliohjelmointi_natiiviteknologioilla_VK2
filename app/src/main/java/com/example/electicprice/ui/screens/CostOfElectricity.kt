package com.example.electicprice.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.electicprice.ui.components.Heading
import com.example.electicprice.ui.components.NumericField

@Composable
fun CostOfElectricity(modifier: Modifier = Modifier) {
    var consumption by remember { mutableStateOf("0") }
    var price by remember { mutableFloatStateOf(0f) }
    var taxIncluded: Boolean by remember { mutableStateOf(false) }
    var result by remember { mutableFloatStateOf(0f) }

    fun calculateResult() {
        val amount = consumption.toFloatOrNull() ?: 0f
        val basePrice = amount * price

        result = if (taxIncluded) {
            basePrice * 1.10f
        } else {
            basePrice * 1.24f
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp)
    ) {
        Heading(Header = "Cost of Electricity")
        NumericField(
            label = "Consumption",
            input = consumption,
            onValueChange = {
                consumption = it
                calculateResult()
            }
        )

        Text(
            text = "Price/kWh %.2f".format(price)
        )

        Slider(
            value = price,
            onValueChange = {
                price = it
                calculateResult()
            },
            valueRange = 0f..1f,
            steps = 99
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = taxIncluded,
                onCheckedChange = {
                    taxIncluded = it
                    calculateResult()
                },
            )
            Text(
                text = "VAT 10%"
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    text = "%.2f €".format(result),
                    modifier = Modifier.padding(6.dp),
                    color = Color.White
                )
            }
        }
    }
}