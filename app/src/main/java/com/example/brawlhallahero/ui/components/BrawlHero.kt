package com.example.brawlhallahero.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brawlhallahero.R
import com.example.brawlhallahero.ui.theme.BrawlHallaHeroTheme
import com.example.brawlhallahero.ui.theme.Pink
import com.example.brawlhallahero.ui.theme.Pink80

@Composable
fun BrawlHero(
    brawlId: Long,
    image: Int,
    title: String,
    isFav: Boolean,
    onFav: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .background(Pink80),

        ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                text = title,
                fontSize = 40.sp,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Icon(
                imageVector =
                if (isFav) ImageVector.vectorResource(
                    R.drawable.baseline_star_24
                )
                else ImageVector.vectorResource(
                    R.drawable.baseline_star_border_24
                ),
                contentDescription = "Fav $brawlId",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onFav(brawlId) },
                tint = Pink,
            )

        }
    }
}

@Composable
@Preview(showBackground = true)
fun BrawlHeroItemPreview() {
    BrawlHallaHeroTheme {
        BrawlHero(
            4, R.drawable.bodvar, "Bodvar", true, onFav = {}
        )
    }
}
