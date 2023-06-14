import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cars.CarEvent
import com.example.cars.CarState

@Composable
fun AddCarDialog(
    state: CarState,
    onEvent: (CarEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(CarEvent.HideDialog)
        },
        title = { Text(text = "Add a car") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.brand,
                    onValueChange = {
                        onEvent(CarEvent.SetBrand(it))
                    },
                    placeholder = {
                        Text(text = "Brand")
                    }
                )
                TextField(
                    value = state.model,
                    onValueChange = {
                        onEvent(CarEvent.SetModel(it))
                    },
                    placeholder = {
                        Text(text = "Model")
                    }
                )
                TextField(
                    value = state.year,
                    onValueChange = {
                        onEvent(CarEvent.SetYear(it))
                    },
                    placeholder = {
                        Text(text = "Year")
                    }
                )
            }
        },
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(CarEvent.SaveCar)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}