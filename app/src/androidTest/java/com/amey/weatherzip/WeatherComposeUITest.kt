package com.amey.weatherzip

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.amey.weatherzip.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// TODO - 1. Test case to check if the search is showing proper data
// TODO - 2. Test case to check if the blank search is showing proper error
// TODO - 3. Test case to check if the search is showing proper error for invalid zip code

@RunWith(AndroidJUnit4::class)
class WeatherComposeUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun checkIfSearchBarIsDisplayed() {
        composeTestRule.onNodeWithText("Search ZIP Code").assertIsDisplayed()
    }

    @Test
    fun performClickOnZipSearchbar() {
        composeTestRule.onNodeWithText("Search ZIP Code").performClick()
    }

    @Test
    fun checkIfTextIsWritingOnSearchBar() {
        val mNode = composeTestRule.onNodeWithText("Search ZIP Code")
        mNode.performTextInput("94020")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("94020").assertIsDisplayed()
        composeTestRule.waitForIdle()
// TODO - fix this
//        composeTestRule.onNodeWithText("94020").performKeyPress(KeyEvent(NativeKeyEvent(NativeKeyEvent.ACTION_DOWN, NativeKeyEvent.KEYCODE_ENTER)))
    }

    @Test
    fun checkIfDefaultZipIsVisible() {
        val mNode = composeTestRule.onNodeWithText("Search ZIP Code")
        mNode.performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("02119").performClick()
    }

}
