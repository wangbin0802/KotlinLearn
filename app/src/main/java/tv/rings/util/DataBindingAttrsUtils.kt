package tv.rings.util

import android.databinding.BindingAdapter
import android.widget.Button
import tv.rings.data.ProductRelease
import tv.rings.subscription.OnViewChange

/**
 * Created by wangbin on 2019-07-08.
 */
object DataBindingAttrsUtils {
    @JvmStatic
    @BindingAdapter(value = ["onEvent", "onChange"], requireAll = false)
    fun onEvent(releaseBtn: Button, productT: ProductRelease, onViewChange: OnViewChange) {
        releaseBtn.setOnClickListener {
            onViewChange.onProductInfoChange("Release info:${productT.country}, ${productT.date}, ${productT.version}")
        }
    }
}