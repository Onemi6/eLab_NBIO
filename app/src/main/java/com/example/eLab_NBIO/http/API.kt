package com.example.eLab_NBIO.http

import com.example.eLab_NBIO.models.Lab
import com.example.eLab_NBIO.models.Login
import com.example.eLab_NBIO.models.Response
import com.example.eLab_NBIO.models.Sampling.*
import com.example.eLab_NBIO.models.SamplingCyd.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

class API {
    companion object {
        const val BASE_URL = "http://47.98.246.0/"
    }

    interface WebApi {
        //获取单位
        @POST("Account/getLabs")
        fun getLabs(): Observable<MutableList<Lab>>

        //登录
        @POST("Account/doLogin")
        fun login(@Body any: Any): Observable<Login>

        //获取采样任务
        @POST("Sampling/getSamplings")
        fun getSamplings1(@Query("billName") billName: String): Observable<MutableList<Sampling1>>

        @POST("Sampling/getSamplings")
        fun getSamplings2(@Query("billName") billName: String): Observable<MutableList<Sampling2>>

        @POST("Sampling/getSamplings")
        fun getSamplings3(@Query("billName") billName: String): Observable<MutableList<Sampling3>>

        @POST("Sampling/getSamplings")
        fun getSamplings4(@Query("billName") billName: String): Observable<MutableList<Sampling4>>

        @POST("Sampling/getSamplings")
        fun getSamplings5(@Query("billName") billName: String): Observable<MutableList<Sampling5>>

        @POST("Sampling/getSamplings")
        fun getSamplings6(@Query("billName") billName: String): Observable<MutableList<Sampling6>>

        @POST("Sampling/getSamplings")
        fun getSamplings7(@Query("billName") billName: String): Observable<MutableList<Sampling7>>

        //获取采样点
        @POST("Sampling/getCYDBySamplingId")
        fun getCYDBySamplingId1(@Query("billName") billName: String,@Query("samplingId") samplingId: Int): Observable<MutableList<SamplingCyd1>>

        @POST("Sampling/getCYDBySamplingId")
        fun getCYDBySamplingId2(@Query("billName") billName: String,@Query("samplingId") samplingId: Int): Observable<MutableList<SamplingCyd2>>

        @POST("Sampling/getCYDBySamplingId")
        fun getCYDBySamplingId3(@Query("billName") billName: String,@Query("samplingId") samplingId: Int): Observable<MutableList<SamplingCyd3>>

        @POST("Sampling/getCYDBySamplingId")
        fun getCYDBySamplingId4(@Query("billName") billName: String,@Query("samplingId") samplingId: Int): Observable<MutableList<SamplingCyd4>>

        @POST("Sampling/getCYDBySamplingId")
        fun getCYDBySamplingId5(@Query("billName") billName: String,@Query("samplingId") samplingId: Int): Observable<MutableList<SamplingCyd5>>

        @POST("Sampling/getCYDBySamplingId")
        fun getCYDBySamplingId6(@Query("billName") billName: String,@Query("samplingId") samplingId: Int): Observable<MutableList<SamplingCyd6>>

        @POST("Sampling/getCYDBySamplingId")
        fun getCYDBySamplingId7(@Query("billName") billName: String,@Query("samplingId") samplingId: Int): Observable<MutableList<SamplingCyd7>>

        //提交采样数据
        @POST("Sampling/SubmitSamplingData")
        fun submitSamplingData(@Body any: Any): Observable<Response>
    }
}