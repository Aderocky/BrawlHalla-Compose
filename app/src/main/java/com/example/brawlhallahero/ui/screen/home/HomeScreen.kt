package com.example.brawlhallahero.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.brawlhallahero.R
import com.example.brawlhallahero.ViewModelFactory
import com.example.brawlhallahero.di.Injection
import com.example.brawlhallahero.model.Brawl
import com.example.brawlhallahero.ui.common.UiState
import com.example.brawlhallahero.ui.components.BrawlHero
import com.example.brawlhallahero.ui.components.SearchBarHero

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {

    val query by viewModel.query
    viewModel.getSearchHero(query)

    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()

    ) {
        SearchBarHero(
            query = query,
            onQueryChange = viewModel::getSearchHero,
        )
        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    viewModel.getAllBrawll()
                }

                is UiState.Success -> {
                    if (uiState.data.isEmpty()) {
                        Text(
                            modifier = modifier
                                .align(Alignment.Center),
                            text = stringResource(id = R.string.error_nohero),

                            )
                    } else {
                        HomeContent(
                            brawlFav = uiState.data,
                            modifier = modifier,
                            navigateToDetail = navigateToDetail,
                            onFav = { id ->
                                viewModel.cekFavBrawl(brawlId = id)
                                viewModel.getSearchHero(query)
                            }
                        )
                    }
                }

                is UiState.Error -> {
                    Toast.makeText(
                        context,
                        stringResource(id = R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}


@Composable
fun HomeContent(
    brawlFav: List<Brawl>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    onFav: (id: Long) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.testTag("BrawlList")
    ) {
        item {
            Spacer(modifier = modifier.padding(top = 75.dp))
        }
        items(brawlFav, key = { it.id }) { data ->
            BrawlHero(
                brawlId = data.id,
                image = data.image,
                title = data.name,
                isFav = data.isFav,
                modifier = Modifier
                    .clickable {
                        navigateToDetail(data.id)
                    },
                onFav = onFav
            )
        }
    }
}
