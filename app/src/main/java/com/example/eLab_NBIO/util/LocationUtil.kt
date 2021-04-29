package com.example.eLab_NBIO.util

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log

object LocationUtil {
    private var mInstance: LocationUtil? = null
    lateinit var locationManager: LocationManager
    lateinit var locationProvider: String
    var location: Location? = null
    lateinit var mContext: Context

    private fun LocationUtil(context: Context): LocationUtil {
        mContext = context
        getLocation()
        return this
    }

    //采用Double CheckLock(DCL)实现单例
    fun getInstance(context: Context): LocationUtil? {
        if (mInstance == null) {
            mInstance = LocationUtil(context.applicationContext)
        }
        return mInstance
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        //1.获取位置管理器
        locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //2.获取位置提供器，GPS或是NetWork
        val providers: List<String> = locationManager.getProviders(true)
        locationProvider = when {
            providers.contains(LocationManager.NETWORK_PROVIDER) -> {
                //如果是网络定位
                Log.d(TAG, "是网络定位")
                LocationManager.NETWORK_PROVIDER
            }
            providers.contains(LocationManager.GPS_PROVIDER) -> {
                //如果是GPS定位
                Log.d(TAG, "是GPS定位")
                LocationManager.GPS_PROVIDER
            }
            else -> {
                Log.d(TAG, "没有可用的位置提供器")
                return
            }
        }
        //3.获取上次的位置，一般第一次运行，此值为null
        var location: Location?
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  //从gps获取经纬度
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location == null) { //当GPS信号弱没获取到位置的时候再从网络获取
                location = getLocationByNetwork()
            }
        } else {    //从网络获取经纬度
            location = getLocationByNetwork()
        }

        setLocation(location)
        // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
        locationManager.requestLocationUpdates(locationProvider, 1000, 0F, locationListener)
    }

    @SuppressLint("MissingPermission")
    private fun getLocationByNetwork(): Location? {
        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            1000,
            0f,
            locationListener
        )
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        return location
    }

    @JvmName("setLocation1")
    fun setLocation(location: Location?) {
        this.location = location
        val address: String = "纬度：" + (location?.latitude) + "经度：" + (location?.longitude)
        Log.d(TAG, address)
    }

    //获取经纬度
    fun showLocation(): Location? {
        return location
    }

    // 移除定位监听
    @SuppressLint("MissingPermission")
    fun removeLocationUpdatesListener() {
        // 需要检查权限,否则编译不过
        locationManager.removeUpdates(locationListener)
    }

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */
    //使用匿名内部类创建了LocationListener的实例
    private val locationListener = object : LocationListener {
        /**某个设备关闭时*/
        override fun onProviderDisabled(provider: String?) {
            Log.d(TAG, "onProviderDisabled")
        }

        /**
         * 某个设备打开时
         */
        override fun onProviderEnabled(provider: String?) {
            Log.d(TAG, "onProviderEnabled")
        }

        override fun onLocationChanged(location: Location?) {
            Log.d(TAG, "onLocationChanged")
            LocationUtil.location?.accuracy//精确度
            setLocation(LocationUtil.location)
        }

        /**
         * 当某个位置提供者的状态发生改变时
         */
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            Log.d(TAG, "onStatusChanged")
        }
    }

    /**
     * 判断是否开启了GPS或网络定位开关
     * @return
     */
    fun isLocationProviderEnabled(): Boolean {
        var result = false
        val locationManager =
            mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        ) {
            result = true
        }
        return result
    }
}