package com.navarro.spotifygold.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun QueryResultItem(

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable { }
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .fillMaxHeight()
        ) {
            AsyncImage(
                model = "https://24ai.tech/en/wp-content/uploads/sites/3/2023/10/01_product_1_sdelat-izobrazhenie-1-1-5-scaled.jpg",
                contentDescription = null,
                modifier = Modifier.fillMaxSize(1.0f)
            )
        }
        Column (
            // Arrange items evenly
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Adicto a ti"
            )
            Text(
                text = "Song・Walter Olmos",
                color = Color.LightGray
            )
        }
    }
}

@Preview
@Composable
fun ObjectsPreview() {
    LazyColumn(content = {
        listOf(
            0,
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9
        ).forEach {
            item {
                QueryResultItem()
            }
        }
    })
}
