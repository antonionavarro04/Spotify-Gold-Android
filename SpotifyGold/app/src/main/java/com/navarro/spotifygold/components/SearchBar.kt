package com.navarro.spotifygold.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.navarro.spotifygold.R
import com.navarro.spotifygold.StaticToast
import com.navarro.spotifygold.ui.theme.Black50
import com.navarro.spotifygold.ui.theme.Black60
import com.navarro.spotifygold.ui.theme.White100
import com.navarro.spotifygold.views.NIY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: MutableState<String>
) {
    TextField(
        value = query.value,
        onValueChange = { query.value = it },
        placeholder = {
            Text(
                text = stringResource(id = R.string.and_search_searchbox_placeholder),
                color = Black60,
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        textStyle = TextStyle(color = Black50, fontSize = 18.sp),
        trailingIcon = {
            IconButton(
                onClick = { StaticToast.showToast("Not Implemented Yet...") },
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Black50
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            containerColor = White100,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(5.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                StaticToast.showToast(query.value)
            }
        ),
    )
}