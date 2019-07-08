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
    @BindingAdapter(value = ["onEvent", "onChange"], requireAll = false) // onEvent, onChange和xml里面bind:onEvent,bind:onChange，名字相对应
    fun onEvent(releaseBtn: Button, product: ProductRelease, onViewChange: OnViewChange) { // product, onViewChange传递@{对象}
        releaseBtn.setOnClickListener {
            onViewChange.onProductInfoChange("Release info:${product.country}, ${product.date}, ${product.version}")
        }
    }
}