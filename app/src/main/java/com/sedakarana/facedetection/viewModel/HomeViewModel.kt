package com.sedakarana.facedetection.viewModel

import android.app.Application
import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.sedakarana.facedetectionapp.camera.CameraManager
import com.sedakarana.facedetectionapp.graphic.GraphicOverlay
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application
) : BaseViewModel(application) {
    var cameraManager: CameraManager? = null
    fun initializeCameraManager(
        context: Context,
        previewView: PreviewView,
        graphicOverlay: GraphicOverlay<*>,
        lifecycleOwner: LifecycleOwner
    ) {
        if (cameraManager == null) {
            cameraManager = CameraManager(
                context = context,
                previewView = previewView,
                graphicOverlay = graphicOverlay,
                lifecycleOwner = lifecycleOwner
            )
        }
    }
}
