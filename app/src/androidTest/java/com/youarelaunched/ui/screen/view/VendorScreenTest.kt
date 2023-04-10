package com.youarelaunched.ui.screen.view

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.youarelaunched.challenge.MainActivity
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import com.youarelaunched.challenge.ui.screen.view.VendorsScreen
import com.youarelaunched.challenge.ui.theme.VendorAppTheme
import org.junit.Rule
import org.junit.Test

class VendorScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun vendorsScreen_noResultItemVisibleIfVendorListEmpty() {
        // Given
        val uiState = VendorsScreenUiState(vendors = emptyList(), searchInput = "")

        // When
        composeTestRule.activity.setContent {
            VendorAppTheme {
                VendorsScreen(uiState = uiState, onEvent = {})
            }
        }

        // Then
        composeTestRule.onNodeWithText("Sorry! No results found…")
            .assertIsDisplayed()
    }

    @Test
    fun vendorsScreen_atLeastOneItemDisplayedIfVendorListNotEmpty() {
        // Given
        val vendorsList = listOf(
            Vendor(
                id = 1,
                companyName = "Vendor 1",
                coverPhoto = "cover_photo_url",
                area = "Area 1",
                favorite = false,
                categories = null,
                tags = "tag1, tag2"
            )
        )
        val uiState = VendorsScreenUiState(vendors = vendorsList, searchInput = "")

        // When
        composeTestRule.activity.setContent {
            VendorAppTheme {
                VendorsScreen(uiState = uiState, onEvent = {})
            }
        }

        // Then
        composeTestRule.onNodeWithText("Vendor 1")
            .assertIsDisplayed()

    }


}