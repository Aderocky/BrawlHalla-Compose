package com.example.brawlhallahero.ui.screen.detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.brawlhallahero.R
import com.example.brawlhallahero.ViewModelFactory
import com.example.brawlhallahero.di.Injection
import com.example.brawlhallahero.ui.common.UiState
import com.example.brawlhallahero.ui.theme.Pink80
import com.example.brawlhallahero.ui.theme.navColor

@Composable
fun DetailScreen(
    brawlId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current
    viewModel.resultState.collectAsState(initial = UiState.Loading).value.let { resulState ->
        when (resulState) {
            is UiState.Loading -> {
                viewModel.getBrawlById(brawlId)
            }

            is UiState.Success -> {
                val data = resulState.data
                DetailContent(
                    data.name,
                    data.image,
                    data.deskripsi,
                    onBackClick = navigateBack,
                )
            }

            is UiState.Error -> {
                Toast.makeText(
                    context, stringResource(id = R.string.error), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
fun DetailContent(
    name: String,
    image: Int,
    description: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = modifier
                .fillMaxHeight(0.33f)
                .fillMaxWidth()
                .zIndex(1f)
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = name,
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.Kembali),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onBackClick() }
                    .clip(CircleShape)
                    .background(Pink80)
                    .padding(5.dp),
            )
            ElevatedCard(
                modifier = modifier
                    .wrapContentWidth()
                    .align(Alignment.BottomCenter)
                    .offset(y = 40.dp)
                    .padding(start = 30.dp, end = 30.dp),
                colors = CardDefaults.cardColors(navColor)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(20.dp),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Pink80)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .padding(top = 50.dp),
        ) {
            DetailComponent(section = stringResource(id = R.string.Deskripsi), value = description)

        }
    }
}

@Composable
fun DetailComponent(
    modifier: Modifier = Modifier, section: String, value: String
) {
    Text(
        text = section,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        modifier = modifier.padding(bottom = 5.dp),
        color = Color.Black
    )
    Divider(
        color = Color.Black, thickness = 1.dp, modifier = modifier.padding(bottom = 5.dp)
    )

    Text(
        text = value,
        fontSize = 16.sp,
        modifier = modifier.padding(bottom = 16.dp),
        color = Color.Black
    )
}