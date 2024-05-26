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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.navarro.spotifygold.R
import com.navarro.spotifygold.ui.theme.Black0
import com.navarro.spotifygold.ui.theme.Black20
import com.navarro.spotifygold.ui.theme.Black60
import com.navarro.spotifygold.ui.theme.Black80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: MutableState<String>,
    isFocused: MutableState<Boolean>,
    onSearch: () -> Unit
) {
    TextField(
        value = query.value,
        onValueChange = { query.value = it },
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_searchbox_placeholder),
                color = Black60,
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .onFocusChanged { focusState ->
                isFocused.value = focusState.isFocused
            },
        textStyle = TextStyle(color = Black20, fontSize = 18.sp),
        trailingIcon = {
            IconButton(
                onClick = onSearch
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Black20
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Black0,
            containerColor = Black80,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(5.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch()
        })
    )
}