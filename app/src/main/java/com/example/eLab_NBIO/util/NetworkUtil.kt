package com.example.eLab_NBIO.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Handler
import android.util.Log
import com.dou361.dialogui.DialogUIUtils
import com.example.eLab_NBIO.http.RetrofitService
import com.example.eLab_NBIO.models.Lab
import com.example.eLab_NBIO.models.Login
import com.example.eLab_NBIO.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

object NetworkUtil {
    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @param context Context
     * @return true 表示网络可用
     */
    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        val connectivity =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        //新版本调用方法获取网络状态
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val networks = connectivity.allNetworks
            var networkInfo: NetworkInfo
            for (mNetwork in networks) {
                networkInfo = connectivity.getNetworkInfo(mNetwork)
                if (networkInfo.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        } else {
            //否则调用旧版本方法
            if (connectivity != null) {
                val info = connectivity.allNetworkInfo
                if (info != null) {
                    for (anInfo in info) {
                        if (anInfo.isConnected) {
                            // 当前网络是连接的
                            if (anInfo.state == NetworkInfo.State.CONNECTED) {
                                // 当前所连接的网络可用
                                Log.d("Network", "NETWORKNAME: " + anInfo.typeName)
                                return true
                            }
                        }
                    }
                }
            }
        }
        return false
    }
}