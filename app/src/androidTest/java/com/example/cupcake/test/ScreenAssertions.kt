package com.example.cupcake.test
import org.junit.Assert.assertEquals

import androidx.navigation.NavController

fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)

}