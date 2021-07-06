package com.example.eLab_NBIO.http

import com.example.eLab_NBIO.models.Lab
import com.example.eLab_NBIO.models.Login
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

class API {
    companion object {
        const val BASE_URL = "http://47.98.246.0/"
    }

    interface WebApi {
        //获取单位
        @POST("Account/getLabs")
        fun getLabs():Observable<MutableList<Lab>>

        //登录
        @POST("Account/doLogin")
        fun login(@Body any: Any): Observable<Login>
    }
}