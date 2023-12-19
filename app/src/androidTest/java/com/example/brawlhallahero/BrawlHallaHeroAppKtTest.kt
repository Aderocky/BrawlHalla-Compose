package com.example.brawlhallahero

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.brawlhallahero.model.FakeBrawlDataSource
import com.example.brawlhallahero.ui.navigation.Screen
import com.example.brawlhallahero.ui.theme.BrawlHallaHeroTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BrawlHallaHeroAppKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            BrawlHallaHeroTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                BrawlHallaHeroApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("BrawlList").performScrollToIndex(3)
        composeTestRule.onNodeWithText(FakeBrawlDataSource.dummyBrawl[3].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailBrawl.route)
        composeTestRule.onNodeWithText(FakeBrawlDataSource.dummyBrawl[3].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(FakeBrawlDataSource.dummyBrawl[3].deskripsi)
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.Kembali))
            .performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_fav).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun favHero_check_andDelete() {
        composeTestRule.onNodeWithTag("BrawlList").performScrollToIndex(11)
        composeTestRule.onNodeWithContentDescription("Fav 11").performClick()

        composeTestRule.onNodeWithStringId(R.string.menu_fav).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)

        composeTestRule.onNodeWithContentDescription("Fav 11").performClick()

        composeTestRule.onNodeWithTag("fav_error").assertIsDisplayed()

        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun fav_isEmpty() {
        composeTestRule.onNodeWithStringId(R.string.menu_fav).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithTag("fav_error").assertIsDisplayed()
    }

}