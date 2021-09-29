package com.example.eLab_NBIO.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.adapter.MyViewPagerAdapter
import com.example.eLab_NBIO.models.Task
import com.example.eLab_NBIO.models.Tasks
import com.example.eLab_NBIO.ui.samplingCyd.*
import com.example.eLab_NBIO.util.LocationUtil
import kotlinx.android.synthetic.main.activity_cyd.*
import kotlinx.android.synthetic.main.content_main.*

class CydActivity : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var task: Task
    //private val requestLocationPermissionCode = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cyd)
        context = this
        initView()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cyd, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        toolbar_cyd.title = ""
        setSupportActionBar(toolbar_cyd)
        /*显示Home图标*/
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /*设置ToolBar标题，使用TestView显示*/
        tv_title_cyd.text = resources.getString(R.string.title_cyd)
        initFragments()
    }

    private fun initFragments() {
        val viewPagerAdapter = MyViewPagerAdapter(supportFragmentManager)
        task = Tasks.taskList[Tasks.position]
        when (task.ORIGIN_RECORD_MODULE_SAMPLING_ID) {
            1 -> {
                viewPagerAdapter.apply { addFragment(SamplingCyd1()) }
            }
            2 -> {
                viewPagerAdapter.apply { addFragment(SamplingCyd2()) }
            }
            3 -> {
                viewPagerAdapter.apply {addFragment(SamplingCyd3()) }
            }
            4 -> {
                viewPagerAdapter.apply { addFragment(SamplingCyd4()) }
            }
            5 -> {
                viewPagerAdapter.apply { addFragment(SamplingCyd5())}
            }
            6 -> {
                viewPagerAdapter.apply { addFragment(SamplingCyd6()) }
            }
            7 -> {
                viewPagerAdapter.apply { addFragment(SamplingCyd7()) }
            }
        }
        view_pager_cyd.offscreenPageLimit = 1
        view_pager_cyd.adapter = viewPagerAdapter
    }

    /*override fun onPause() {
        super.onPause()
        //LocationUtil.removeLocationUpdatesListener()
    }

    override fun onResume() {
        super.onResume()
        //initLocation()
    }

    private fun initLocation() {
        //挂上LocationListener, 在状态变化时刷新位置显示，因为requestPermissionss是异步执行的，所以要先确认是否有权限
        if (LocationUtil.getInstance(this@CydActivity)?.isLocationProviderEnabled() == true) {
            val location = LocationUtil.getInstance(this@CydActivity)?.showLocation()
            if (location != null) {
                //tv_location.text = "地理位置：lon:${location.longitude};lat:${location.latitude}"
                val address: String = "纬度：" + location.latitude + "经度：" + location.longitude
                tv_location.text = address
                //tv_location.text=getAddress(location.getLongitude(),location.getLatitude())
            }
        } else {
            requestLocation()
        }
    }

    *//**
     * 检查地理位置开关是否打开，如果未打开，则提示用户打开地理位置开关。
     * 如果已打开，则显示地理位置；如果被拒绝，直接关闭窗口。
     *//*
    private fun requestLocation() {
        val message = "本应用需要获取地理位置，请打开获取位置的开关"
        val alertDialog = AlertDialog.Builder(this).setMessage(message).setCancelable(false)
            .setPositiveButton(android.R.string.ok)
            { dialog, _ ->
                dialog.dismiss()
                gotoSysLocationSettingsPage()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .create()
        alertDialog.show()
    }

    private fun gotoSysLocationSettingsPage() {
        val intent = Intent()
        intent.action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
        startActivityForResult(intent, requestLocationPermissionCode)


*//*        val  intent = Intent(this,SecondActivity::class.java).apply {
            putExtra("name","Hello,技术最TOP")
        }
        myActivityLauncher.launch(intent)*//*
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            requestLocationPermissionCode -> {
                //initLocation()
            }
            else -> {
            }
        }
    }*/

/*    private val myActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult ->
        if(activityResult.resultCode == Activity.RESULT_OK){
            val result = activityResult.data?.getStringExtra("result")
            Toast.makeText(applicationContext,result,Toast.LENGTH_SHORT).show()
            textView.text = "回传数据：$result"
        }
    }

    button.setOnClickListener {
        val  intent = Intent(this,SecondActivity::class.java).apply {
            putExtra("name","Hello,技术最TOP")
        }
        myActivityLauncher.launch(intent)
    }*/
}