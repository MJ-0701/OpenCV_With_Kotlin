package com.example.opencv45

import android.Manifest.permission.CAMERA
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.widget.TextView
import com.example.opencv45.databinding.ActivityMainBinding
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.OpenCVLoader
import org.opencv.core.Mat
import java.util.*

class MainActivity : AppCompatActivity() , CameraBridgeViewBase.CvCameraViewListener2 {

    private lateinit var binding: ActivityMainBinding

    private val TAG = "MainActivity"

    private var m_Camidx : Int = 0

    private lateinit var m_CameraView : CameraBridgeViewBase

    private val  CAMERA_PERMISSION_CODE : Int = 200;

    init {
        val isIntialized = OpenCVLoader.initDebug()
        Log.d(TAG, "isIntialized = $isIntialized")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        m_CameraView = findViewById(R.id.activity_surface_view);
        m_CameraView.setVisibility(SurfaceView.VISIBLE)
        m_CameraView.setCvCameraViewListener(this)
        m_CameraView.setCameraIndex(m_Camidx)


        // Example of a call to a native method


    }

    override fun onStart() {
        super.onStart()

        var _Permission : Boolean = true

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){ // 최소 버전보다 높은지 확인
            if(checkSelfPermission(CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(CAMERA),CAMERA_PERMISSION_CODE)
                _Permission = false
            }
        }

        if(_Permission){
            onCameraPermissionGranted()
        }

    }

    protected fun onCameraPermissionGranted() {
        var cameraViews : List<CameraBridgeViewBase> = getCameraViewList()

        if(cameraViews == null){
            return
        }

        for(CameraBridgeViewBase in cameraViews){
            if(CameraBridgeViewBase != null){
                CameraBridgeViewBase.setCameraPermissionGranted()
            }
        }
    }


    protected fun getCameraViewList(): List<CameraBridgeViewBase> {

        return Collections.singletonList(m_CameraView)
    }

    /**
     * A native method that is implemented by the 'opencv45' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'opencv45' library on application startup.
        init {
            System.loadLibrary("opencv45")
        }
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun onCameraViewStopped() {
        TODO("Not yet implemented")
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        TODO("Not yet implemented")
    }
}