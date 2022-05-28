package com.rtu.welearn.ui.camera

import android.Manifest
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.media.Image
import android.media.ImageReader
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.Surface
import androidx.appcompat.widget.AppCompatImageView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.rtu.welearn.BaseActivity
import com.rtu.welearn.R
import com.rtu.welearn.WeLearnApp.Companion.mDatabase
import com.rtu.welearn.utils.Constants.Companion.CAMERA
import java.io.ByteArrayOutputStream
import java.util.*


open class CameraActivity : BaseActivity(), ImageReader.OnImageAvailableListener {

    companion object {
        fun getIntent(mContext: Context): Intent {
            var intent = Intent(mContext, CameraActivity::class.java)
            return intent
        }
    }

    private var mCloudEndPoint: DatabaseReference? = mDatabase?.child(CAMERA)

    private lateinit var imagePreview: AppCompatImageView
    private var saperation = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        imagePreview = findViewById(R.id.ivPreview)
        mCloudEndPoint?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {

                    var byteArray= Base64.getDecoder().decode(snapshot.value.toString().replace("[","").replace("]",""))
                    var bitmap=BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    imagePreview.setImageBitmap(bitmap)

                }

            }


            override fun onCancelled(error: DatabaseError) {

            }
        })
        //TODO ask for permission of camera upon first launch of application
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
            ) {
                val permission = arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                requestPermissions(permission, 1122)
            } else {
                //TODO show live camera footage
                setFragment()
            }
        } else {
            //TODO show live camera footage
            setFragment()
        }


    }

    fun toList(genreIdString: String): List<Int> =
        genreIdString
            .substring(0, genreIdString.length - 1)
            .split(", ")
            .map { it.toInt() }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //TODO show live camera footage
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //TODO show live camera footage
            setFragment()
        } else {
            finish()
        }
    }

    var previewHeight = 0
    var previewWidth = 0
    var sensorOrientation = 0

    //TODO fragment which show llive footage from camera
    protected fun setFragment() {
        val manager =
            getSystemService(Context.CAMERA_SERVICE) as CameraManager
        var cameraId: String? = null
        try {
            cameraId = manager.cameraIdList[0]
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
        val fragment: Fragment
        val camera2Fragment = CameraConnectionFragment.newInstance(
            object :
                CameraConnectionFragment.ConnectionCallback {
                override fun onPreviewSizeChosen(size: Size?, rotation: Int) {
                    previewHeight = size!!.height
                    previewWidth = size.width
                    sensorOrientation = rotation - getScreenOrientation()
                }
            },
            this,
            R.layout.camera_fragment,
            Size(640, 480)
        )
        camera2Fragment.setCamera(cameraId)
        fragment = camera2Fragment
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    protected fun getScreenOrientation(): Int {
        return when (windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_270 -> 270
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_90 -> 90
            else -> 0
        }
    }


    private var isProcessingFrame = false
    private val yuvBytes = arrayOfNulls<ByteArray>(3)
    private var rgbBytes: IntArray? = null
    private var yRowStride = 0
    private var postInferenceCallback: Runnable? = null
    private var imageConverter: Runnable? = null
    private var rgbFrameBitmap: Bitmap? = null

    override fun onImageAvailable(reader: ImageReader) {
//        Log.d("onImageAvailable",""+rgbBytes)

        // We need wait until we have some size from onPreviewSizeChosen
        if (previewWidth == 0 || previewHeight == 0) {
            return
        }
        if (rgbBytes == null) {
            rgbBytes = IntArray(previewWidth * previewHeight)
        }
        try {
            val image = reader.acquireLatestImage() ?: return
            if (isProcessingFrame) {
                image.close()
                return
            }
            isProcessingFrame = true
            val planes = image.planes
            fillBytes(planes, yuvBytes)
            yRowStride = planes[0].rowStride
            val uvRowStride = planes[1].rowStride
            val uvPixelStride = planes[1].pixelStride
            imageConverter = Runnable {
                ImageUtils.convertYUV420ToARGB8888(
                    yuvBytes[0]!!,
                    yuvBytes[1]!!,
                    yuvBytes[2]!!,
                    previewWidth,
                    previewHeight,
                    yRowStride,
                    uvRowStride,
                    uvPixelStride,
                    rgbBytes!!
                )
            }
            postInferenceCallback = Runnable {
                image.close()
                isProcessingFrame = false
            }
            processImage()
        } catch (e: Exception) {
            return
        }
    }

    var isNotSet = true
    private fun processImage() {
//        Log.d("processImage",""+ Arrays.toString(rgbBytes))

        imageConverter!!.run()

        var rgbFrameBitmap =
            Bitmap.createBitmap(previewWidth, previewHeight, Bitmap.Config.ARGB_8888)
        rgbFrameBitmap?.setPixels(
            rgbBytes,
            0,
            previewWidth,
            0,
            0,
            previewWidth,
            previewHeight
        )
        postInferenceCallback!!.run()

        val stream = ByteArrayOutputStream()
        rgbFrameBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray: ByteArray = stream.toByteArray()

        var encode= Base64.getEncoder().encode(byteArray);

        mCloudEndPoint?.child("0")?.setValue(String(encode))
        rgbFrameBitmap.recycle()
        isNotSet = false
    }

    open fun getBytes(
        source: ByteArray?, srcBegin: Int, srcEnd: Int, destination: ByteArray?,
        dstBegin: Int
    ) {
        System.arraycopy(source, srcBegin, destination, dstBegin, srcEnd - srcBegin)
    }

    protected fun fillBytes(
        planes: Array<Image.Plane>,
        yuvBytes: Array<ByteArray?>
    ) {
        // Because of the variable row stride it's not possible to know in
        // advance the actual necessary dimensions of the yuv planes.
        for (i in planes.indices) {
            val buffer = planes[i].buffer
            if (yuvBytes[i] == null) {
                yuvBytes[i] = ByteArray(buffer.capacity())
            }
            buffer[yuvBytes[i]]
        }
    }
}