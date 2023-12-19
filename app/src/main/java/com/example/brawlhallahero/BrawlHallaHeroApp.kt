

package com.example.brawlhallahero

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.brawlhallahero.ui.navigation.NavigationItem
import com.example.brawlhallahero.ui.navigation.Screen
import com.example.brawlhallahero.ui.screen.detail.DetailScreen
import com.example.brawlhallahero.ui.screen.favorite.FavScreen
import com.example.brawlhallahero.ui.screen.home.HomeScreen
import com.example.brawlhallahero.ui.screen.profile.ProfileScreen
import com.example.brawlhallahero.ui.theme.BrawlHallaHeroTheme
import com.example.brawlhallahero.ui.theme.iconColor
import com.example.brawlhallahero.ui.theme.navColor


@Composable
fun BrawlHallaHeroApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailBrawl.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navigateToDetail = { brawlId ->
                    navController.navigate(Screen.DetailBrawl.createRoute(brawlId))
                })
            }
            composable(Screen.Favorite.route) {
                FavScreen(navigateToDetail = { brawlId ->
                    navController.navigate(Screen.DetailBrawl.createRoute(brawlId))
                })
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailBrawl.route,
                arguments = listOf(navArgument("brawlId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("brawlId") ?: -1L
                DetailScreen(
                    brawlId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = navColor,
        contentColor = iconColor,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_fav),
                icon = Icons.Default.Face,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BrawlHallaHeroAppPreview() {
    BrawlHallaHeroTheme {
        BrawlHallaHeroApp()
    }
}