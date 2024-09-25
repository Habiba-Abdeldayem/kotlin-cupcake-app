package com.example.cupcake.test

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.cupcake.R
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderViewModel
import onNodeWithStringId
import org.junit.Rule
import org.junit.Test

class SummaryScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    var items = emptyList<Pair<String,String>>()
    var tmp = ""
    @Test
     fun summaryScreen_verifyContent(){
         val numberOfCupcakes = "3"
         composeTestRule.setContent {
            val orderViewModel = OrderViewModel()
             val orderUiState by orderViewModel.uiState.collectAsState()
             orderViewModel.setQuantity(3)
             orderViewModel.setFlavor("Choco")
             orderViewModel.setDate("ddd")
             tmp = orderUiState.price
             val resources = LocalContext.current.resources

             items = listOf(
                // Summary line 1: display selected quantity

                Pair(stringResource(R.string.quantity).uppercase(), resources.getQuantityString(
                    R.plurals.cupcakes,
                    orderUiState.quantity,
                    orderUiState.quantity
                )),
                // Summary line 2: display selected flavor
                Pair(stringResource(R.string.flavor).uppercase(), orderUiState.flavor),
                // Summary line 3: display selected pickup date
                Pair(stringResource(R.string.pickup_date).uppercase(), orderUiState.date)
            )
             OrderSummaryScreen(
                 orderUiState = orderUiState, {}, {subject: String, summary: String ->}
             )

         }
             items.forEach {item ->
                 Log.d("hahahaha", item.first)
                 Log.d("hahahaha", item.second)
                 composeTestRule.onNodeWithText(item.first).assertIsDisplayed()
                 composeTestRule.onNodeWithText(item.second).assertIsDisplayed()
             }

        var currentActivity = composeTestRule.activity
        val subtotalText = currentActivity.getString(R.string.subtotal_price, tmp)
        val sendText = currentActivity.getString(R.string.send)
        val cancelText = currentActivity.getString(R.string.cancel)
        composeTestRule.onNodeWithText(subtotalText).assertIsDisplayed()
        composeTestRule.onNodeWithText(sendText).assertIsDisplayed()
        composeTestRule.onNodeWithText(cancelText).assertIsDisplayed()




     }
}