package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.StartOrderScreen
import com.example.cupcake.R

import onNodeWithStringId
import org.junit.Rule
import org.junit.Test

class StartScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun startScreen_verifyContent(){
        val quantityOptions = DataSource.quantityOptions
        composeTestRule.setContent {
            StartOrderScreen(quantityOptions = quantityOptions,{})
        }

        quantityOptions.forEach{button ->
            composeTestRule.onNodeWithStringId(button.first).assertIsDisplayed()
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(R.string.app_name)
        ).assertIsDisplayed()

        composeTestRule.onNodeWithStringId(R.string.order_cupcakes)
    }
}