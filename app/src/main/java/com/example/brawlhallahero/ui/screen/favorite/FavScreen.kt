@file:OptIn(ExperimentalFoundationApi::class)

package com.example.brawlhallahero.ui.screen.favorite

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import com.example.brawlhallahero.R
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.brawlhallahero.ViewModelFactory
import com.example.brawlhallahero.di.Injection
import com.example.brawlhallahero.model.Brawl
import com.example.brawlhallahero.ui.common.UiState
import com.example.brawlhallahero.ui.components.BrawlHero


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavScreen(
    modifier: Modifier = Modifier,
    viewModel: FavViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateToDetail: (Long) -> Unit,
) {

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()

    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.fav_menu),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        )
        Box(
            modifier = modifier
                .fillMaxSize()

        ) {
            viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        viewModel.getAddedFavBrawl()
                    }

                    is UiState.Success -> {
                        if (uiState.data.isEmpty()) {
                            Text(
                                modifier = modifier
                                    .align(Alignment.Center)
                                    .testTag("fav_error"),
                                text = stringResource(id = R.string.error_noitem),

                                )
                        } else {
                            FavContent(
                                brawlFav = uiState.data,
                                modifier = modifier,
                                navigateToDetail = navigateToDetail,
                                onFav = { id ->
                                    viewModel.cekFavBrawl(brawlId = id)
                                    viewModel.getAddedFavBrawl()
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
}

@Composable
fun FavContent(
    brawlFav: List<Brawl>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    onFav: (id: Long) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.testTag("FavList")
    ) {
        items(brawlFav, key = { it.id }) { item ->
            BrawlHero(
                brawlId = item.id,
                image = item.image,
                title = item.name,
                isFav = item.isFav,
                modifier = Modifier
                    .clickable {
                        navigateToDetail(item.id)
                    }
                    .animateItemPlacement(tween(durationMillis = 250)),
                onFav = onFav
            )
        }
    }

}
