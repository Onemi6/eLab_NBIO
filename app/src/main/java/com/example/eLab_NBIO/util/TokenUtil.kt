package com.example.eLab_NBIO.util

import android.util.Log
import com.example.eLab_NBIO.http.RetrofitService
import com.example.eLab_NBIO.models.Login
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object TokenUtil {
    fun attemptToken() {
        val map = mutableMapOf<String, Any>()
        map["loginName"] = SpValueUtil.getString("LOGIN_NAME")
        map["password"] = SpValueUtil.getString("PASSWORD")
        map["lab"] = SpValueUtil.getString("LAB_ID")
        RetrofitService.getApiService()
            .login(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Login> {
                override fun onComplete() {
                    Log.i("Login", "onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.i("Login", "onSubscribe")
                }

                override fun onNext(t: Login) {
                    Log.i("Login", "onNext")
                    if (t.message == null) {
                        SpValueUtil.setString("token", t.token)
                    }
                }

                override fun onError(e: Throwable) {
                    Log.i("Login", e.message.toString())
                }
            })
    }
}