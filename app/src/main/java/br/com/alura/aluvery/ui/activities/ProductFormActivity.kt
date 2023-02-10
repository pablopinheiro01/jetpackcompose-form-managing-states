package br.com.alura.aluvery.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alura.aluvery.R
import br.com.alura.aluvery.dao.ProductDao
import br.com.alura.aluvery.model.Product
import br.com.alura.aluvery.ui.theme.AluveryTheme
import coil.compose.AsyncImage
import java.lang.IllegalArgumentException
import java.math.BigDecimal
import java.text.DecimalFormat

class ProductFormActivity : ComponentActivity() {

    private val dao = ProductDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AluveryTheme {
                Surface {
                    ProductFormScreen(dao = dao , onSaveFinish = {
                        finish()
                    })
                }
            }
        }
    }

}

class ProductFormScreenUiState(
    val url: String = "",
    val name: String = "",
    val price: String = "",
    val description: String = "",
    val onSaveClick: (Product) -> Unit = {  _ -> },
    val onUrlChanged: (String) -> Unit = {},
    val onTextChanged: (String) -> Unit = {},
    val onPriceChanged: (String) -> Unit = {},
    val onDescriptionChanged: (String) -> Unit = {},
    val onShowPreview: Boolean = url.isNotBlank()
)

//This composable is Stateful where I trate all states and changes
@Composable
fun ProductFormScreen(
    dao: ProductDao,
    onSaveFinish: () -> Unit = {}
) {

    var url by remember {
        mutableStateOf("")
    }

    var name by remember {
        mutableStateOf("")
    }

    var price by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    val formatter = remember {
        DecimalFormat("#.##")
    }

    val onUrlChanged: (String) -> Unit = { value -> url = value }

    val onTextChanged: (String) -> Unit = { name = it }

    val onPriceChanged: (String) -> Unit = {
        try {
            price = formatter.format(BigDecimal(it))
        } catch (e: IllegalArgumentException) {
            if (it.isEmpty()) {
                price = it
            }
        }
    }

    val onDescriptionChanged: (String) -> Unit = { description = it }

    val onSaveClick: (Product) -> Unit = { product ->
        dao.save(product)
        onSaveFinish()
    }
    

    val state = remember(
        url,
        name,
        price,
        description,
    ) {
        ProductFormScreenUiState(
            url,
            name,
            price,
            description,
            onSaveClick,
            onUrlChanged,
            onTextChanged,
            onPriceChanged,
            onDescriptionChanged,
        )
    }
    ProductFormScreen(state = state)

}

//This composable is Stateless
@Composable
fun ProductFormScreen(
    modifier: Modifier = Modifier,
    state: ProductFormScreenUiState = ProductFormScreenUiState(),
) {

    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val url = state.url
        val name = state.name
        val price = state.price
        val description = state.description
        val onSaveClick = state.onSaveClick

        Spacer(modifier = modifier)
        Text(
            text = "Criando o produto",
            modifier = modifier.fillMaxWidth(),
            fontSize = 28.sp,
        )

        if (state.onShowPreview) {
            AsyncImage(
                model = url,
                contentDescription = null,
                modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder)
            )
        }

        TextField(
            value = url,
            onValueChange = state.onUrlChanged,
            modifier.fillMaxWidth(),
            label = {
                Text(text = "Url da Imagem")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Next,
            )
        )

        TextField(
            value = name,
            onValueChange = state.onTextChanged,
            modifier.fillMaxWidth(), label = {
                Text(text = "Nome")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            )
        )

        TextField(
            value = price,
            onValueChange = state.onPriceChanged,
            modifier.fillMaxWidth(), label = {
                Text(text = "Preço")
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        TextField(
            value = description,
            onValueChange = state.onDescriptionChanged,
            modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp),
            label = {
                Text(text = "Descrição")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Sentences
            )
        )

        Button(onClick = {
            val convertedPrice = try {
                BigDecimal(state.price)
            } catch (e: NumberFormatException) {
                BigDecimal(0)
            }
            val product = Product(
                name = name,
                image = url,
                price = convertedPrice,
                description = description
            )
            onSaveClick(product)
        }) {
            Text(text = "Salvar")
        }
        Spacer(modifier)
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

@Preview
@Composable
fun ProductFormScreenFilledPreview() {
    AluveryTheme {
        Surface {
            ProductFormScreen(
                state = ProductFormScreenUiState(
                    url = "url teste",
                    name = "nome teste",
                    price = "123",
                    description = "descrição teste"
                )
            )
        }
    }
}