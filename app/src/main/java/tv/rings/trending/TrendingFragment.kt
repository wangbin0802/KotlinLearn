package tv.rings.trending

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.SurfaceHolder
import kotlinx.android.synthetic.main.fragment_trending.*
import tv.rings.BaseFragment
import tv.rings.kotlinloops.app.R
import java.io.IOException
import java.util.*
import kotlin.Comparator

class TrendingFragment : BaseFragment(), SurfaceHolder.Callback {
    override fun surfaceChanged(surfaceHolder: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        surfaceHolder?.let { startPreview(it) }
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        releaseCamera()
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        initCamera()
    }

    private var camera: Camera? = null

    companion object {
        val TAG = TrendingFragment::class.java.simpleName
        fun newInstance() = TrendingFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_trending
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        if (ContextCompat.checkSelfPermission(context as Activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            initSurfaceView()
        } else {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CAMERA), 0)
        }
    }

    private fun initSurfaceView() {
        val surfaceViewHolder = surfaceView.holder
        surfaceViewHolder.addCallback(this)
    }

    private fun initCamera() {
        try {
            camera = Camera.open()
            val parameters = camera?.parameters
            parameters?.setRecordingHint(true)
            camera?.setParameters(parameters)
            setPreviewSize(camera!!, 720, 1280)
            setPictureSize(camera!!, 720, 1280)
            camera?.setDisplayOrientation(90)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun startPreview(surfaceHolder: SurfaceHolder) {
        try {
            camera?.setPreviewDisplay(surfaceHolder)
            camera?.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    /**
     * 设置拍摄的照片大小
     * @param camera
     * @param expectWidth
     * @param expectHeight
     */
    fun setPictureSize(camera: Camera, expectWidth: Int, expectHeight: Int) {
        val parameters = camera.parameters
        val size = calculatePerfectSize(parameters.supportedPictureSizes,
                expectWidth, expectHeight)
        parameters.setPictureSize(size.width, size.height)
        camera.parameters = parameters
    }

    fun setPreviewSize(camera: Camera, expectWidth: Int, expectHeight: Int) {
        val parameters = camera.parameters
        val size = calculatePerfectSize(parameters.supportedPreviewSizes,
                expectWidth, expectHeight)
        parameters.setPreviewSize(size.width, size.height)
        camera.parameters = parameters
    }

    /**
     * 计算最完美的Size
     * @param sizes
     * @param expectWidth
     * @param expectHeight
     * @return
     */
    fun calculatePerfectSize(sizes: List<Camera.Size>, expectWidth: Int,
                             expectHeight: Int): Camera.Size {
        sortList(sizes) // 根据宽度进行排序
        var result: Camera.Size = sizes[0]
        var widthOrHeight = false // 判断存在宽或高相等的Size
        // 辗转计算宽高最接近的值
        for (size in sizes) {
            // 如果宽高相等，则直接返回
            if (size.width == expectWidth && size.height == expectHeight) {
                result = size
                break
            }
            // 仅仅是宽度相等，计算高度最接近的size
            if (size.width == expectWidth) {
                widthOrHeight = true
                if (Math.abs(result.height - expectHeight) > Math.abs(size.height - expectHeight)) {
                    result = size
                }
            } else if (size.height == expectHeight) {
                widthOrHeight = true
                if (Math.abs(result.width - expectWidth) > Math.abs(size.width - expectWidth)) {
                    result = size
                }
            } else if (!widthOrHeight) {
                if (Math.abs(result.width - expectWidth) > Math.abs(size.width - expectWidth) && Math.abs(result.height - expectHeight) > Math.abs(size.height - expectHeight)) {
                    result = size
                }
            }// 如果之前的查找不存在宽或高相等的情况，则计算宽度和高度都最接近的期望值的Size
            // 高度相等，则计算宽度最接近的Size
        }
        return result
    }

    /**
     * 排序
     * @param list
     */
    private fun sortList(list: List<Camera.Size>) {
        Collections.sort(list, object : Comparator<Camera.Size> {
            override fun compare(pre: Camera.Size, after: Camera.Size): Int {
                if (pre.width > after.width) {
                    return 1
                } else if (pre.width < after.width) {
                    return -1
                }
                return 0
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun releaseCamera() {
        try {
            camera?.stopPreview()
            camera?.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val grantResult = permissions.map {
            context?.let { it1 -> ContextCompat.checkSelfPermission(it1, it) } == PackageManager.PERMISSION_GRANTED
        }
        if (grantResult.all { it }) {
            initSurfaceView()
        }
    }

}