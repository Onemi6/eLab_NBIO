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
import com.example.eLab_NBIO.adapter.TaskAdapter
import com.example.eLab_NBIO.http.RetrofitService
import com.example.eLab_NBIO.models.Task
import com.example.eLab_NBIO.models.sampling.*
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


class MainActivity : AppCompatActivity(), RecyclerItemClickListener {

    private val requestLocationPermissionCode = 100
    private var mExitTime: Long = 0
    private var _context: Context? = null
    private lateinit var taskAdapter: TaskAdapter<Task>
    private lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _context = this
        CrashHandler.getInstance()?.init(_context)
        initView()
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
        //tv_title_main.text = resources.getString(R.string.menu_sampling_type1)

        val headerView: View = nav_view.getHeaderView(0)
        val tvUserName = headerView.findViewById<TextView>(R.id.tv_user_name)
        val tvAppVersionName = headerView.findViewById<TextView>(R.id.tv_app_versionName)
        val name: String = SpValueUtil.getString("NAME")
        val versionName: String? = Util.getVersionName()
        if (name.isNotEmpty()) {
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

            taskAdapter = TaskAdapter(_context, Tasks.taskList, this)
            rv_mainInfo_add.adapter = taskAdapter
            attemptGetTasks()
            //showLocation()
            initLocation()
        } else {
            toLogin()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /*设置监听器*/
    private fun setListener() {
        nav_view.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                /*               R.id.nav_sampling1 -> {
                                   setType(1)
                                   attemptGetSamplings()
                               }
                               R.id.nav_sampling2 -> {
                                   setType(2)
                                   attemptGetSamplings()
                               }
                               R.id.nav_sampling3 -> {
                                   setType(3)
                                   attemptGetSamplings()
                               }
                               R.id.nav_sampling4 -> {
                                   setType(4)
                                   attemptGetSamplings()
                               }
                               R.id.nav_sampling5 -> {
                                   setType(5)
                                   attemptGetSamplings()
                               }
                               R.id.nav_sampling6 -> {
                                   setType(6)
                                   attemptGetSamplings()
                               }
                               R.id.nav_sampling7 -> {
                                   setType(7)
                                   attemptGetSamplings()
                               }*/
                R.id.nav_logout -> {
                    val builder =
                        androidx.appcompat.app.AlertDialog.Builder(this@MainActivity).apply {
                            setTitle("提示")
                            setMessage("确定退出？")
                            setPositiveButton("确定") { _, _ ->
                                toLogin()
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
            R.id.action_refresh -> {
                //attemptGetSamplings()
                attemptGetTasks()
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

    private fun attemptGetTasks() {
        val dialogLogin = DialogUIUtils.showLoading(
            _context, "获取任务中...", false, true,
            false,
            false
        )
        dialogLogin.show()
        val userId: Int = SpValueUtil.getInt("ID")
        RetrofitService.getApiService()
            .getTasks(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<MutableList<Task>> {
                override fun onComplete() {
                    Log.i("getTasks", "onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.i("getTasks", "onSubscribe")
                }

                override fun onNext(t: MutableList<Task>) {
                    Log.i("getTasks", "onNext")
                    Tasks.taskList = t
                    taskAdapter.changList(Tasks.taskList)
                }

                override fun onError(e: Throwable) {
                    Log.i("getTasks", e.message.toString())
                    if (e.message.toString() == "HTTP 401 Unauthorized") {
                        TokenUtil.attemptToken()
                    }
                }
            })
        DialogUIUtils.dismiss(dialogLogin)
    }

    private fun toLogin() {
        val intentLogin = Intent()
        intentLogin.setClass(
            this@MainActivity,
            LoginActivity::class.java
        )
        startActivity(intentLogin)
        finish()
    }

    override fun onRecyclerViewItemClick(view: View, position: Int) {
        setPos(position)
    }

    private fun setPos(pos: Int) {
        Tasks.position = pos
        task = Tasks.taskList[Tasks.position]
        attemptGetSamplings()
    }

    private fun attemptGetSamplings() {
        when (task.ORIGIN_RECORD_MODULE_SAMPLING_ID) {
            1 -> {
                RetrofitService.getApiService()
                    .getSamplingOrigin1(
                        task.ORIGIN_RECORD_MODULE_SAMPLING_TYPE,
                        task.ORIGIN_RECORD_SAMPLING_ID
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Sampling1> {
                        override fun onComplete() {
                            Log.i("getSamplings1", "onComplete")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.i("getSamplings2", "onSubscribe")
                        }

                        override fun onNext(t: Sampling1) {
                            Log.i("getSamplings3", "onNext")
                            Tasks.sampling1 = t
                            toDetails()
                        }

                        override fun onError(e: Throwable) {
                            Log.i("getSamplings4", e.message.toString())
                            if (e.message.toString() == "HTTP 401 Unauthorized") {
                                TokenUtil.attemptToken()
                            }
                        }
                    })
            }
            2 -> {
                RetrofitService.getApiService()
                    .getSamplingOrigin2(
                        task.ORIGIN_RECORD_MODULE_SAMPLING_TYPE,
                        task.ORIGIN_RECORD_SAMPLING_ID
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Sampling2> {
                        override fun onComplete() {
                            Log.i("getSamplings1", "onComplete")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.i("getSamplings2", "onSubscribe")
                        }

                        override fun onNext(t: Sampling2) {
                            Log.i("getSamplings3", "onNext")
                            Tasks.sampling2 = t
                            toDetails()
                        }

                        override fun onError(e: Throwable) {
                            Log.i("getSamplings4", e.message.toString())
                            if (e.message.toString() == "HTTP 401 Unauthorized") {
                                TokenUtil.attemptToken()
                            }
                        }
                    })
            }
            3 -> {
                RetrofitService.getApiService()
                    .getSamplingOrigin3(
                        task.ORIGIN_RECORD_MODULE_SAMPLING_TYPE,
                        task.ORIGIN_RECORD_SAMPLING_ID
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Sampling3> {
                        override fun onComplete() {
                            Log.i("getSamplings1", "onComplete")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.i("getSamplings2", "onSubscribe")
                        }

                        override fun onNext(t: Sampling3) {
                            Log.i("getSamplings3", "onNext")
                            Tasks.sampling3 = t
                            toDetails()
                        }

                        override fun onError(e: Throwable) {
                            Log.i("getSamplings4", e.message.toString())
                            if (e.message.toString() == "HTTP 401 Unauthorized") {
                                TokenUtil.attemptToken()
                            }
                        }
                    })
            }
            4 -> {
                RetrofitService.getApiService()
                    .getSamplingOrigin4(
                        task.ORIGIN_RECORD_MODULE_SAMPLING_TYPE,
                        task.ORIGIN_RECORD_SAMPLING_ID
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Sampling4> {
                        override fun onComplete() {
                            Log.i("getSamplings1", "onComplete")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.i("getSamplings2", "onSubscribe")
                        }

                        override fun onNext(t: Sampling4) {
                            Log.i("getSamplings3", "onNext")
                            Tasks.sampling4 = t
                            toDetails()
                        }

                        override fun onError(e: Throwable) {
                            Log.i("getSamplings4", e.message.toString())
                            if (e.message.toString() == "HTTP 401 Unauthorized") {
                                TokenUtil.attemptToken()
                            }
                        }
                    })
            }
            5 -> {
                RetrofitService.getApiService()
                    .getSamplingOrigin5(
                        task.ORIGIN_RECORD_MODULE_SAMPLING_TYPE,
                        task.ORIGIN_RECORD_SAMPLING_ID
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Sampling5> {
                        override fun onComplete() {
                            Log.i("getSamplings1", "onComplete")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.i("getSamplings2", "onSubscribe")
                        }

                        override fun onNext(t: Sampling5) {
                            Log.i("getSamplings3", "onNext")
                            toDetails()
                        }

                        override fun onError(e: Throwable) {
                            Log.i("getSamplings4", e.message.toString())
                            if (e.message.toString() == "HTTP 401 Unauthorized") {
                                TokenUtil.attemptToken()
                            }
                        }
                    })
            }
            6 -> {
                RetrofitService.getApiService()
                    .getSamplingOrigin6(
                        task.ORIGIN_RECORD_MODULE_SAMPLING_TYPE,
                        task.ORIGIN_RECORD_SAMPLING_ID
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Sampling6> {
                        override fun onComplete() {
                            Log.i("getSamplings1", "onComplete")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.i("getSamplings2", "onSubscribe")
                        }

                        override fun onNext(t: Sampling6) {
                            Log.i("getSamplings3", "onNext")
                            Tasks.sampling6 = t
                            toDetails()
                        }

                        override fun onError(e: Throwable) {
                            Log.i("getSamplings4", e.message.toString())
                            if (e.message.toString() == "HTTP 401 Unauthorized") {
                                TokenUtil.attemptToken()
                            }
                        }
                    })
            }
            7 -> {
                RetrofitService.getApiService()
                    .getSamplingOrigin7(
                        task.ORIGIN_RECORD_MODULE_SAMPLING_TYPE,
                        task.ORIGIN_RECORD_SAMPLING_ID
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Sampling7> {
                        override fun onComplete() {
                            Log.i("getSamplings1", "onComplete")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.i("getSamplings2", "onSubscribe")
                        }

                        override fun onNext(t: Sampling7) {
                            Log.i("getSamplings3", "onNext")
                            Tasks.sampling7 = t
                            toDetails()
                        }

                        override fun onError(e: Throwable) {
                            Log.i("getSamplings4", e.message.toString())
                            if (e.message.toString() == "HTTP 401 Unauthorized") {
                                TokenUtil.attemptToken()
                            }
                        }
                    })
            }
            else ->{
                Snackbar.make(
                    toolbar_main, "当前任务没有对应的记录模板!",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null)
                    .show()
            }
        }
    }

    private fun toDetails() {
        val intentDetails = Intent()
        intentDetails.setClass(
            this@MainActivity,
            DetailsActivity::class.java
        )
        startActivity(intentDetails)
    }
}