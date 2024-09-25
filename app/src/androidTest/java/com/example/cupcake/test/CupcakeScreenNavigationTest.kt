package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.CupcakeApp
import com.example.cupcake.CupcakeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import com.example.cupcake.R
import onNodeWithStringId
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class CupcakeScreenNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupCupcakeNavHost(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            CupcakeApp(navController = navController)
        }
    }

    /*
    * Note: The AndroidComposeTestRule you created automatically launches the app, displaying the CupcakeApp composable before the execution of any @Test method. Therefore, you do not need to take any extra steps in the test methods to launch the app.
    */
    @Test
    fun cupcakeNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // In Cupcake, the Up button has a content description set to the string from the R.string.back_button resource.
    @Test
    fun cupcakeNavHost_verifyBackNavigationNotShownOnStartOrderScreen() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun cupcakeNavHost_clickOneCupcake_navigatesToSelectFlavorScreen(){
        composeTestRule.onNodeWithStringId(R.string.one_cupcake).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }

    @Test
    fun cupcakeNavHost_clickUpButtonFromFlavorScreen_navigatesToStartScreen(){
        navigateToFlavorScreen()
        performNavigateUp()
        cupcakeNavHost_verifyStartDestination()
    }

    @Test
    fun cupcakeNavHost_clickCancelButtonFromFlavorScreen_navigatesToStartScreen(){
        navigateToFlavorScreen()
        performCancel()
        cupcakeNavHost_verifyStartDestination()
    }

    @Test
    fun cupcakeNavHost_clickChocolate_navigatesToPickupScreen(){
        navigateToPickupScreen()
        navController.assertCurrentRouteName(CupcakeScreen.Pickup.name)
    }

    @Test
    fun cupcakeNavHost_clickUpButtonFromPickupScreen_navigatesToFlavorScreen(){
        navigateToPickupScreen()
        performCancel()
        cupcakeNavHost_verifyStartDestination()
    }

    @Test
    fun cupcakeNavHost_clickCancelButtonFromPickupScreen_navigatesToStartScreen(){
        navigateToPickupScreen()
        performCancel()
        cupcakeNavHost_verifyStartDestination()
    }

    @Test
    fun cupcakeNavHost_clickCancelButtonFromSummaryScreen_navigatesToStartScreen(){
        navigateToSummaryScreen()
        performCancel()
        cupcakeNavHost_verifyStartDestination()
    }

    @Test
    fun cupcakeNavHost_chooseDate_navigatesToSummaryScreen(){
        navigateToSummaryScreen()
        navController.assertCurrentRouteName(CupcakeScreen.Summary.name)

    }

    private fun navigateToFlavorScreen(){
        composeTestRule.onNodeWithStringId(R.string.one_cupcake).performClick()
        composeTestRule.onNodeWithStringId(R.string.chocolate).performClick()
    }

    private fun navigateToPickupScreen(){
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
    }

    private fun navigateToSummaryScreen(){
        navigateToPickupScreen()
        composeTestRule.onNodeWithText(getFormattedDate()).performClick()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
    }

    private fun getFormattedDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(java.util.Calendar.DATE, 1)
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    private fun performNavigateUp(){
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }

    private fun performCancel(){
        val cancelText = composeTestRule.activity.getString(R.string.cancel)
        composeTestRule.onNodeWithText(cancelText).performClick()
    }
}
