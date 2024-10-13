package com.sedakarana.facedetection.view.screen

import android.Manifest
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceLandmark
import com.sedakarana.facedetection.R
import com.sedakarana.facedetection.ui.theme.ColorRow
import com.sedakarana.facedetection.viewModel.HomeViewModel
import com.sedakarana.facedetectionapp.graphic.GraphicOverlay

@ExperimentalPermissionsApi
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    when {
        cameraPermissionState.hasPermission -> {
            CameraPreview(homeViewModel)
        }

        !cameraPermissionState.shouldShowRationale -> {
            LaunchedEffect(true) {
                cameraPermissionState.launchPermissionRequest()
            }
        }
        else -> {
            // Kullanıcı izin vermek istemediğinde, izni istemek için tekrar bir çağrı yapılır.
            // Burada, bir diyalog gösterebiliriz
            ShowPermissionDeniedDialog(cameraPermissionState)
        }
    }
}

@Composable
fun CameraPreview(homeViewModel: HomeViewModel) {
    val context = LocalContext.current
    val previewView = remember { PreviewView(context) }
    val graphicOverlay = remember { GraphicOverlay<GraphicOverlay.Graphic>(context, null) }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(true) {
        homeViewModel.initializeCameraManager(
            context = context,
            previewView = previewView,
            graphicOverlay = graphicOverlay,
            lifecycleOwner = lifecycleOwner
        )
        homeViewModel.cameraManager?.cameraStart()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                previewView
            }
        )
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                graphicOverlay
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        homeViewModel.cameraManager?.cameraStart()
                    }
                    .padding(5.dp)
                    .background(ColorRow, shape = RoundedCornerShape(5.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.play),
                    contentDescription = "Play Icon",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(id = R.string.s_start),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        homeViewModel.cameraManager?.changeCamera()
                    }
                    .padding(5.dp)
                    .background(ColorRow, shape = RoundedCornerShape(5.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.change),
                    contentDescription = "Change Icon",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(id = R.string.s_change),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        homeViewModel.cameraManager?.cameraStop()
                    }
                    .padding(5.dp)
                    .background(ColorRow, shape = RoundedCornerShape(5.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.stop),
                    contentDescription = "Delete Icon",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(id = R.string.s_stop),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShowPermissionDeniedDialog(cameraPermissionState: PermissionState) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Kamera İzni Gerekiyor") },
        text = { Text("Bu uygulama kameraya erişmek için izin istiyor. Lütfen izin verin.") },
        confirmButton = {
            TextButton(onClick = {
                cameraPermissionState.launchPermissionRequest() // Kullanıcıya tekrar izin isteme ekranı gösterilir
            }) {
                Text("İzin Ver")
            }
        },
        dismissButton = {
            TextButton(onClick = { }) {
                Text("Vazgeç")
            }
        }
    )
}

