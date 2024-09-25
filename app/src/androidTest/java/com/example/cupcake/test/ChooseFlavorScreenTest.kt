package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.cupcake.CupcakeScreen
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.R
import onNodeWithStringId
import org.junit.Rule
import org.junit.Test

class ChooseFlavorScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun chooseFlavorScreen_verifyNextButtonEnabledWhenOptionSelected(){

        composeTestRule.setContent {

            val context = LocalContext.current
            val uiState by OrderViewModel().uiState.collectAsState()

            SelectOptionScreen(
                subtotal = uiState.price,
                options = DataSource.flavors.map { id -> context.resources.getString(id) },
                onNextButtonClicked = { },
                onSelectionChanged = { },
                onCancelButtonClicked = {},
            )

        }
        composeTestRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()
        composeTestRule.onNodeWithStringId(R.string.vanilla).performClick()
        composeTestRule.onNodeWithStringId(R.string.next).assertIsEnabled()

    }

}