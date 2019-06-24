package tv.rings.util

import android.app.Activity
import android.content.res.Resources

/**
 * Created by wangbin on 2019-06-24.
 */
object DeviceUtil {
    fun getDeviceWidth(activity: Activity): Int {
        val resource: Resources = activity.resources
        val dm = resource.displayMetrics
        return dm.widthPixels
    }

    fun getDeviceHeight(activity: Activity): Int {
        val resource: Resources = activity.resources
        val dm = resource.displayMetrics
        return dm.heightPixels
    }
}

