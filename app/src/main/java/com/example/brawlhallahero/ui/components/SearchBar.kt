package com.example.brawlhallahero.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.SearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.brawlhallahero.R
import com.example.brawlhallahero.ui.theme.Pink80
import com.example.brawlhallahero.ui.theme.black

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarHero(
    query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = null, tint = black
            )
        },
        placeholder = {
            Text(
                stringResource(R.string.search), color = black
            )
        },
        shape = MaterialTheme.shapes.large,
        colors = SearchBarDefaults.colors(Pink80),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {}
}