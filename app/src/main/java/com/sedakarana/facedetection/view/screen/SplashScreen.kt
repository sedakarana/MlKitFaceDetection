package com.sedakarana.facedetection.view.screen

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sedakarana.facedetection.R
import com.sedakarana.facedetection.view.activity.MainActivity
import com.sedakarana.facedetection.viewModel.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val activity = (LocalContext.current as? Activity)


    LaunchedEffect(true) {
        delay(100L)
        activity?.startActivity(Intent(activity, MainActivity::class.java))
        activity?.finish()

    }

    Column(Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.coin), contentDescription = "")
    }
}