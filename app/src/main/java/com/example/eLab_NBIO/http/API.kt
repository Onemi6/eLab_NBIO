package com.example.eLab_NBIO.http

import com.example.eLab_NBIO.models.Login
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

class API {
    companion object {
        const val BASE_URL = "http://139.155.250.179:83/"
    }

    interface WebApi {

        //登录
        @POST("api/Login")
        fun login(@Query("data") data: String?): Observable<Login>
    }
}