package com.example.brawlhallahero.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brawlhallahero.R
import com.example.brawlhallahero.ui.theme.BrawlHallaHeroTheme
import com.example.brawlhallahero.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.menu_profile),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        )
        Column(
            modifier = modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = modifier
                    .padding(start = 80.dp, end = 80.dp)
                    .border(5.dp, Purple40, CircleShape)
                    .padding(10.dp)
                    .clip(CircleShape)
                    .background(Purple40),
                painter = painterResource(id = R.drawable.adee),
                contentDescription = "Ade",
            )
            Text(
                modifier = modifier
                    .padding(top = 10.dp, bottom = 5.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .padding(10.dp),
                text = stringResource(id = R.string.name),
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = stringResource(id = R.string.email),
                fontSize = 14.sp
            )
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    BrawlHallaHeroTheme {
        ProfileScreen()
    }
}