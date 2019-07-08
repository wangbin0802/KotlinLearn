package tv.rings

import android.app.Application
import android.util.Log

class PlayerApp : BaseApp() {
    companion object {
        private var instance: Application? = null
        fun instance() = instance!!
    }
    override fun onCreate() {
        super.onCreate()
        instance = this

        deviceInfo()
    }

    private fun deviceInfo() {
        val configuration = resources.configuration
        val screenWidthDp = configuration.screenWidthDp //The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.
        val smallestScreenWidthDp = configuration.smallestScreenWidthDp //The smallest screen size an application will see in normal operation, corresponding to smallest screen width resource qualifier.
        Log.w("PlayerApp", "deviceInfo screenWidthDp:$screenWidthDp smallestScreenWidthDp:$smallestScreenWidthDp")
    }
}