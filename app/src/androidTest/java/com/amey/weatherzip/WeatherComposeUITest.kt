package com.amey.weatherzip

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.input.ImeAction
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.amey.weatherzip.model.AddressInfo
import com.amey.weatherzip.model.LocationInfo
import com.amey.weatherzip.model.Observation
import com.amey.weatherzip.model.Place
import com.amey.weatherzip.model.PlaceInfo
import com.amey.weatherzip.model.WeatherResponse
import com.amey.weatherzip.ui.LoadingIcon
import com.amey.weatherzip.ui.MainActivity
import com.amey.weatherzip.ui.NewLocationCard
import com.amey.weatherzip.ui.WeatherMonitoringAnimation
import com.amey.weatherzip.ui.ZipSearchBar
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherComposeUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        // Initialize any dependencies or perform setup if needed.
        // For example, you can set up a mock ViewModel for testing.
    }

    @Test
    fun testZipSearchBar() {
        // Launch the Composable you want to test
        composeTestRule.onNodeWithText("Search ZIP Code").assertIsDisplayed()
    }

    @Test
    fun performClickOnZipSearchbar() {
        // Launch the Composable you want to test
        composeTestRule.onNodeWithText("Search ZIP Code").performClick()
    }

    @Test
    fun performClickOnZipSearchbarAndSearch() {
        // Launch the Composable you want to test
        composeTestRule.onNodeWithText("Search ZIP Code").performTextInput(ImeAction.Done.toString())
    }

}
