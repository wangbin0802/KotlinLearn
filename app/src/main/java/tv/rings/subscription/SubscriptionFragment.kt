package tv.rings.subscription

import android.databinding.DataBindingUtil
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.TextView
import tv.rings.data.ProductRelease
import tv.rings.kotlinloops.app.R
import tv.rings.kotlinloops.app.databinding.FragmentSubscriptionBinding

class SubscriptionFragment : Fragment(), OnViewChange {
    override fun onProductInfoChange(text: String) {
        releaseTv.text = text
    }

    private lateinit var surfaceView: SurfaceView
    private lateinit var releaseTv: TextView
    private lateinit var binding: FragmentSubscriptionBinding
    companion object {
        val TAG = SubscriptionFragment::class.java.simpleName
        fun newInstance() = SubscriptionFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_subscription, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    private fun initData() {
        surfaceView = activity!!.findViewById(R.id.surfaceView)
        surfaceView.holder.addCallback(holderCallback)

        releaseTv = activity!!.findViewById(R.id.release_info)

        val productRelease = ProductRelease("south_african", "2019.7.8", "1.1.3")
        binding.productT = productRelease
        binding.onViewChange = this
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