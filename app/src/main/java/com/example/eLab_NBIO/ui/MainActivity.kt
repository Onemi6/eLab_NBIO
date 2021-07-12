package com.example.eLab_NBIO.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dou361.dialogui.DialogUIUtils
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.adapter.SamplingAdapter
import com.example.eLab_NBIO.http.RetrofitService
import com.example.eLab_NBIO.models.Sampling.*
import com.example.eLab_NBIO.models.Tasks
import com.example.eLab_NBIO.util.*
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private val requestLocationPermissionCode = 100
    private var mExitTime: Long = 0
    private var _context: Context? = null
    private lateinit var billName: String
    private var type: Int = 0
    private lateinit var sampling1Adapter: SamplingAdapter<Sampling1>
    private var itemList1: MutableList<Sampling1>? = ArrayList(0)

    private lateinit var sampling2Adapter: SamplingAdapter<Sampling2>
    private var itemList2: MutableList<Sampling2>? = ArrayList(0)

    private lateinit var sampling3Adapter: SamplingAdapter<Sampling3>
    private var itemList3: MutableList<Sampling3>? = ArrayList(0)

    private lateinit var sampling4Adapter: SamplingAdapter<Sampling4>
    private var itemList4: MutableList<Sampling4>? = ArrayList(0)

    private lateinit var sampling5Adapter: SamplingAdapter<Sampling5>
    private var itemList5: MutableList<Sampling5>? = ArrayList(0)

    private lateinit var sampling6Adapter: SamplingAdapter<Sampling6>
    private var itemList6: MutableList<Sampling6>? = ArrayList(0)

    private lateinit var sampling7Adapter: SamplingAdapter<Sampling7>
    private var itemList7: MutableList<Sampling7>? = ArrayList(0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _context = this
        CrashHandler.getInstance()?.init(_context)
        initView()
        viewAction()
        /*设置监听器*/
        setListener()
    }

    private fun initView() {
        Util.init(this.application)
        /*设置ActionBar
        *不使用toolbar自带的标题
         */
        toolbar_main.title = ""
        setSupportActionBar(toolbar_main)
        /*显示Home图标*/
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /*设置ToolBar标题，使用TestView显示*/
        tv_title_main.text = resources.getString(R.string.menu_sampling_type1)

        val headerView: View = nav_view.getHeaderView(0)
        val tvUserName = headerView.findViewById<TextView>(R.id.tv_user_name)
        val tvAppVersionName = headerView.findViewById<TextView>(R.id.tv_app_versionName)
        val name: String = SpValueUtil.getString("NAME")
        val versionName: String? = Util.getVersionName()
        tvUserName.text = String.format(resources.getString(R.string.user_name), name)
        tvAppVersionName.text = String.format(
            resources.getString(R.string.app_versionName), versionName
        )
        /*设置Drawerlayout的开关,并且和Home图标联动*/
        val mToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar_main, 0, 0)
        drawer_layout.addDrawerListener(mToggle)
        /*同步drawerlayout的状态*/
        mToggle.syncState()

        //初始化RecyclerView
        rv_mainInfo_add.setHasFixedSize(true)
        rv_mainInfo_add.isNestedScrollingEnabled = false

        //创建LinearLayoutManager 对象 这里使用 LinearLayoutManager 是线性布局的意思
        val layoutManager = LinearLayoutManager(_context)
        //设置RecyclerView 布局
        rv_mainInfo_add.layoutManager = layoutManager

        //设置Adapter
        sampling1Adapter = SamplingAdapter(_context, itemList1, 1)
        sampling2Adapter = SamplingAdapter(_context, itemList2, 2)
        sampling3Adapter = SamplingAdapter(_context, itemList3, 3)
        sampling4Adapter = SamplingAdapter(_context, itemList4, 4)
        sampling5Adapter = SamplingAdapter(_context, itemList5, 5)
        sampling6Adapter = SamplingAdapter(_context, itemList6, 6)
        sampling7Adapter = SamplingAdapter(_context, itemList7, 7)
        //默认加载1
        rv_mainInfo_add.adapter = sampling1Adapter

        setType(1)
        //showLocation()
        initLocation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /*设置监听器*/
    private fun setListener() {
        nav_view.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_sampling1 -> {
                    setType(1)
                }
                R.id.nav_sampling2 -> {
                    setType(2)
                }
                R.id.nav_sampling3 -> {
                    setType(3)
                }
                R.id.nav_sampling4 -> {
                    setType(4)
                }
                R.id.nav_sampling5 -> {
                    setType(5)
                }
                R.id.nav_sampling6 -> {
                    setType(6)
                }
                R.id.nav_sampling7 -> {
                    setType(7)
                }
                R.id.nav_logout -> {
                    val builder =
                        androidx.appcompat.app.AlertDialog.Builder(this@MainActivity).apply {
                            setTitle("提示")
                            setMessage("确定退出？")
                            setPositiveButton("确定") { _, _ ->
/*                            SpUtilKt.setBoolean(MyConfig.IS_LOGIN, false)
                            SpUtilKt.removeByKey(MyConfig.COOKIE)*/
                                val intentLogin = Intent()
                                intentLogin.setClass(
                                    this@MainActivity,
                                    LoginActivity::class.java
                                )
                                intentLogin.putExtra("login_type", -1)
                                startActivity(intentLogin)
                            }
                            setNegativeButton("取消", null)
                        }
                    builder.create().show()
                }
            }
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                when (type) {
                    1 -> {
                        val task = Sampling1()
                        Tasks.taskList1.add(task)
                        sampling1Adapter.changList(Tasks.taskList1)
                    }
                    2 -> {
                        val task = Sampling2()
                        Tasks.taskList2.add(task)
                        sampling2Adapter.changList(Tasks.taskList2)
                    }
                    3 -> {
                        val task = Sampling3()
                        Tasks.taskList3.add(task)
                        sampling3Adapter.changList(Tasks.taskList3)
                    }
                    4 -> {
                        val task = Sampling4()
                        Tasks.taskList4.add(task)
                        sampling4Adapter.changList(Tasks.taskList4)
                    }
                    5 -> {
                        val task = Sampling5()
                        Tasks.taskList5.add(task)
                        sampling5Adapter.changList(Tasks.taskList5)
                    }
                    6 -> {
                        val task = Sampling6()
                        Tasks.taskList6.add(task)
                        sampling6Adapter.changList(Tasks.taskList6)
                    }
                    7 -> {
                        val task = Sampling7()
                        Tasks.taskList7.add(task)
                        sampling7Adapter.changList(Tasks.taskList7)
                    }
                }
            }
            R.id.action_refresh -> {
                attemptGetSamplings()
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 拦截返回事件，自处理
     */
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                // Object mHelperUtils;
                Snackbar.make(
                    toolbar_main, "再按一次退出",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null)
                    .show()
                mExitTime = System.currentTimeMillis()
            } else {
                ActivityUtil.closeAllActivity()
            }
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        LocationUtil.removeLocationUpdatesListener()
    }

    override fun onResume() {
        //挂上LocationListener, 在状态变化时刷新位置显示，因为requestPermissionss是异步执行的，所以要先确认是否有权限
        super.onResume()
        initLocation()
    }

    private fun initLocation() {
        if (LocationUtil.getInstance(this@MainActivity)?.isLocationProviderEnabled() == true) {
            val location = LocationUtil.getInstance(this@MainActivity)?.showLocation()
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

    /**
     * 检查地理位置开关是否打开，如果未打开，则提示用户打开地理位置开关。
     * 如果已打开，则显示地理位置；如果被拒绝，直接关闭窗口。
     */
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            requestLocationPermissionCode -> {
                initLocation()
            }
            else -> {
            }
        }
    }

    private fun attemptGetSamplings() {
        val dialogLogin = DialogUIUtils.showLoading(
            _context, "刷新任务中...", false, true,
            false,
            false
        )
        dialogLogin.show()
        when (type) {
            1 -> {
                RetrofitService.getApiService()
                    .getSamplings1(billName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<MutableList<Sampling1>> {
                        override fun onComplete() {
                            Log.i("getSamplings", "onComplete")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.i("getSamplings", "onSubscribe")
                        }

                        override fun onNext(t: MutableList<Sampling1>) {
                            Log.i("getSamplings", "onNext")
                            rv_mainInfo_add.adapter = sampling1Adapter
                            itemList1 = t
                            sampling1Adapter.changList(itemList1)

                            Tasks.taskList1 = itemList1 as MutableList<Sampling1>
                        }

                        override fun onError(e: Throwable) {
                            Log.i("getSamplings", e.message.toString())
                        }
                    })
            }
            2 -> {
                RetrofitService.getApiService()
                    .getSamplings2(billName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<MutableList<Sampling2>> {
                        override fun onComplete() {
                            Log.i("getSamplings", "onComplete")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.i("getSamplings", "onSubscribe")
                        }

                        override fun onNext(t: MutableList<Sampling2>) {
                            Log.i("getSamplings", "onNext")
                            rv_mainInfo_add.adapter = sampling2Adapter
                            itemList2 = t
                            sampling2Adapter.changList(itemList2)
                        }

                        override fun onError(e: Throwable) {
                            Log.i("getSamplings", e.message.toString())
                        }
                    })
            }
            3 -> {
                RetrofitService.getApiService()
                    .getSamplings3(billName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<MutableList<Sampling3>> {
                        override fun onComplete() {
                            Log.i("getSamplings", "onComplete")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.i("getSamplings", "onSubscribe")
                        }

                        override fun onNext(t: MutableList<Sampling3>) {
                            Log.i("getSamplings", "onNext")
                            rv_mainInfo_add.adapter = sampling3Adapter
                            itemList3 = t
                            sampling3Adapter.changList(itemList3)
                        }

                        override fun onError(e: Throwable) {
                            Log.i("getSamplings", e.message.toString())
                        }
                    })
            }
            4 -> {
                RetrofitService.getApiService()
                    .getSamplings4(billName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<MutableList<Sampling4>> {
                        override fun onComplete() {
                            Log.i("getSamplings", "onComplete")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.i("getSamplings", "onSubscribe")
                        }

                        override fun onNext(t: MutableList<Sampling4>) {
                            Log.i("getSamplings", "onNext")
                            rv_mainInfo_add.adapter = sampling4Adapter
                            itemList4 = t
                            sampling4Adapter.changList(itemList4)
                        }

                        override fun onError(e: Throwable) {
                            Log.i("getSamplings", e.message.toString())
                        }
                    })
            }
            5 -> {
                RetrofitService.getApiService()
                    .getSamplings5(billName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<MutableList<Sampling5>> {
                        override fun onComplete() {
                            Log.i("getSamplings", "onComplete")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.i("getSamplings", "onSubscribe")
                        }

                        override fun onNext(t: MutableList<Sampling5>) {
                            Log.i("getSamplings", "onNext")
                            rv_mainInfo_add.adapter = sampling5Adapter
                            itemList5 = t
                            sampling5Adapter.changList(itemList5)
                        }

                        override fun onError(e: Throwable) {
                            Log.i("getSamplings", e.message.toString())
                        }
                    })
            }
            6 -> {
                RetrofitService.getApiService()
                    .getSamplings6(billName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<MutableList<Sampling6>> {
                        override fun onComplete() {
                            Log.i("getSamplings", "onComplete")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.i("getSamplings", "onSubscribe")
                        }

                        override fun onNext(t: MutableList<Sampling6>) {
                            Log.i("getSamplings", "onNext")
                            rv_mainInfo_add.adapter = sampling6Adapter
                            itemList6 = t
                            sampling6Adapter.changList(itemList6)
                        }

                        override fun onError(e: Throwable) {
                            Log.i("getSamplings", e.message.toString())
                        }
                    })
            }
            7 -> {
                RetrofitService.getApiService()
                    .getSamplings7(billName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<MutableList<Sampling7>> {
                        override fun onComplete() {
                            Log.i("getSamplings", "onComplete")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.i("getSamplings", "onSubscribe")
                        }

                        override fun onNext(t: MutableList<Sampling7>) {
                            Log.i("getSamplings", "onNext")
                            rv_mainInfo_add.adapter = sampling7Adapter
                            itemList7 = t
                            sampling7Adapter.changList(itemList7)
                        }

                        override fun onError(e: Throwable) {
                            Log.i("getSamplings", e.message.toString())
                        }
                    })
            }

        }
        DialogUIUtils.dismiss(dialogLogin)
    }

    private fun setType(t: Int) {
        when (t) {
            1 -> {
                type = 1
                billName = resources.getString(R.string.billName_type1)
                tv_title_main.text = resources.getString(R.string.menu_sampling_type1)
            }
            2 -> {
                type = 2
                billName = resources.getString(R.string.billName_type2)
                tv_title_main.text = resources.getString(R.string.menu_sampling_type2)
            }
            3 -> {
                type = 3
                billName = resources.getString(R.string.billName_type3)
                tv_title_main.text = resources.getString(R.string.menu_sampling_type3)
            }
            4 -> {
                type = 4
                billName = resources.getString(R.string.billName_type4)
                tv_title_main.text = resources.getString(R.string.menu_sampling_type4)
            }
            5 -> {
                type = 5
                billName = resources.getString(R.string.billName_type5)
                tv_title_main.text = resources.getString(R.string.menu_sampling_type5)
            }
            6 -> {
                type = 6
                billName = resources.getString(R.string.billName_type6)
                tv_title_main.text = resources.getString(R.string.menu_sampling_type6)
            }
            7 -> {
                type = 7
                billName = resources.getString(R.string.billName_type7)
                tv_title_main.text = resources.getString(R.string.menu_sampling_type7)
            }
        }
        attemptGetSamplings()
    }

    private fun viewAction() {
        sampling1Adapter.setOnKotlinItemClickListener(object :
            SamplingAdapter.IKotlinItemClickListener {
            override fun onItemClickListener(position: Int) {
                Log.v("position", position.toString())
                Tasks.position = position
                val intentDetails = Intent()
                intentDetails.setClass(
                    this@MainActivity,
                    DetailsActivity::class.java
                )
                intentDetails.putExtra("fragment_type", type)
                //intentDetails.putExtra("position", position)
                startActivity(intentDetails)
            }
        })
        sampling2Adapter.setOnKotlinItemClickListener(object :
            SamplingAdapter.IKotlinItemClickListener {
            override fun onItemClickListener(position: Int) {
                Tasks.position = position
                val intentDetails = Intent()
                intentDetails.setClass(
                    this@MainActivity,
                    DetailsActivity::class.java
                )
                intentDetails.putExtra("fragment_type", type)
                intentDetails.putExtra("position", position)
                startActivity(intentDetails)
            }
        })
        sampling3Adapter.setOnKotlinItemClickListener(object :
            SamplingAdapter.IKotlinItemClickListener {
            override fun onItemClickListener(position: Int) {
                Tasks.position = position
                val intentDetails = Intent()
                intentDetails.setClass(
                    this@MainActivity,
                    DetailsActivity::class.java
                )
                intentDetails.putExtra("fragment_type", type)
                intentDetails.putExtra("position", position)
                startActivity(intentDetails)
            }
        })
        sampling4Adapter.setOnKotlinItemClickListener(object :
            SamplingAdapter.IKotlinItemClickListener {
            override fun onItemClickListener(position: Int) {
                Tasks.position = position
                val intentDetails = Intent()
                intentDetails.setClass(
                    this@MainActivity,
                    DetailsActivity::class.java
                )
                intentDetails.putExtra("fragment_type", type)
                intentDetails.putExtra("position", position)
                startActivity(intentDetails)
            }
        })
        sampling5Adapter.setOnKotlinItemClickListener(object :
            SamplingAdapter.IKotlinItemClickListener {
            override fun onItemClickListener(position: Int) {
                Tasks.position = position
                val intentDetails = Intent()
                intentDetails.setClass(
                    this@MainActivity,
                    DetailsActivity::class.java
                )
                intentDetails.putExtra("fragment_type", type)
                intentDetails.putExtra("position", position)
                startActivity(intentDetails)
            }
        })
        sampling6Adapter.setOnKotlinItemClickListener(object :
            SamplingAdapter.IKotlinItemClickListener {
            override fun onItemClickListener(position: Int) {
                Tasks.position = position
                val intentDetails = Intent()
                intentDetails.setClass(
                    this@MainActivity,
                    DetailsActivity::class.java
                )
                intentDetails.putExtra("fragment_type", type)
                intentDetails.putExtra("position", position)
                startActivity(intentDetails)
            }
        })
        sampling7Adapter.setOnKotlinItemClickListener(object :
            SamplingAdapter.IKotlinItemClickListener {
            override fun onItemClickListener(position: Int) {
                Tasks.position = position
                val intentDetails = Intent()
                intentDetails.setClass(
                    this@MainActivity,
                    DetailsActivity::class.java
                )
                intentDetails.putExtra("fragment_type", type)
                intentDetails.putExtra("position", position)
                startActivity(intentDetails)
            }
        })
    }
}