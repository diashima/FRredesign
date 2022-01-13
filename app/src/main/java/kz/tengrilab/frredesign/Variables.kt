package kz.tengrilab.frredesign

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.fragment.app.FragmentActivity

object Variables {
    //const val url = "http://10.150.34.15"
    const val url = "http://10.150.34.13"
    //const val url = "http://10.121.128.10"
    const val port = ":10150"
    const val headers = "Accept: application/json; version=1.0"
    const val headers2 = "Token "
    const val sharedPrefToken = "token"
    const val sharedPrefLogin = "login"
    const val saveLogin = "saveLogin"
    const val savePass = "savePass"
    const val sharedPrefId = "id"
    const val sharedPrefIp = "ip"
    const val username = "username"
    const val password = "password"


    fun loadCredentials(activity: FragmentActivity) : String? {
        val sharedPref = activity.getSharedPreferences(
            sharedPrefLogin,
            Context.MODE_PRIVATE
        )!!
        return sharedPref.getString(sharedPrefToken, null)
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context) : String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}