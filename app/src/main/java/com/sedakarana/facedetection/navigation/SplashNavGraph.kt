package com.sedakarana.facedetection.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.sedakarana.facedetection.view.screen.SplashScreen
import com.sedakarana.navigation.navigation.Screen

@SuppressLint("UnsafeOptInUsageError")
@ExperimentalPermissionsApi
@Composable


fun SplashNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

    }
}