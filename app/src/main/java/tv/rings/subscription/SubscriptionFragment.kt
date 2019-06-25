package tv.rings.subscription

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import tv.rings.BaseFragment
import tv.rings.home.HomeFragment
import tv.rings.kotlinloops.app.R

class SubscriptionFragment : BaseFragment() {
    private lateinit var surfaceView: SurfaceView
    companion object {
        val TAG = SubscriptionFragment::class.java.simpleName
        fun newInstance() = SubscriptionFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_subscription
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    private fun initData() {
        surfaceView = activity!!.findViewById(R.id.surfaceView)
        surfaceView.holder.addCallback(holderCallback)
    }

    private val holderCallback = object: SurfaceHolder.Callback {
        override fun surfaceDestroyed(p0: SurfaceHolder?) {

        }

        override fun surfaceCreated(p0: SurfaceHolder?) {
            paintSurface()
        }

        override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

        }

    }

    private fun paintSurface() {
        val surfaceHolder = surfaceView.holder
        val rect = Rect(0, 0, 400, 400)
        val canvas: Canvas = surfaceHolder.lockCanvas(rect)
        val paint = Paint()
        paint.color = Color.RED
        canvas.drawRect(rect, paint)
        surfaceHolder.unlockCanvasAndPost(canvas)
    }
}