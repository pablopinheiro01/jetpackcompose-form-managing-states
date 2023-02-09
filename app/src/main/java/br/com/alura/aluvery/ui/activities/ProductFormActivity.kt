package br.com.alura.aluvery.ui.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alura.aluvery.ui.theme.AluveryTheme

class ProductFormActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AluveryTheme {
                Surface {
                    ProductFormScreen()
                }
            }
        }
    }

}


@Composable
fun ProductFormScreen(modifier: Modifier = Modifier) {

    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Criando o produto",
            modifier = modifier.fillMaxWidth(),
            fontSize = 28.sp,
        )

        var url by remember {
            mutableStateOf("")
        }

        TextField(value = url, onValueChange = {
            url = it
        }, modifier.fillMaxWidth(), label = {
            Text(text = "Url da Imagem")
        })

        var name by remember {
            mutableStateOf("")
        }

        TextField(value = name, onValueChange = {
            name = it
        }, modifier.fillMaxWidth(), label = {
            Text(text = "Nome")
        })

        var price by remember {
            mutableStateOf("")
        }

        TextField(value = price, onValueChange = {
            price = it
        }, modifier.fillMaxWidth(), label = {
            Text(text = "Preço")
        })


        var description by remember {
            mutableStateOf("")
        }

        TextField(value = description, onValueChange = {
            description = it
        }, modifier.fillMaxWidth()
            .heightIn(min = 100.dp, max = 200.dp),
            label = {
            Text(text = "Descrição")
        })

        Button(onClick = { }) {
            Text(text = "Salvar")
        }


    }

}

@Preview
@Composable
fun ProductFormScreenPreview() {
    AluveryTheme {
        Surface {
            ProductFormScreen()
        }
    }
}