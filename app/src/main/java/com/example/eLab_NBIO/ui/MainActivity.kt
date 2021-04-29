package com.example.eLab_NBIO.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.util.ActivityUtil
import com.example.eLab_NBIO.util.SpValueUtil
import com.example.eLab_NBIO.util.Util
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MainActivity : AppCompatActivity() {

    private var mExitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                    tv_title_main.text = resources.getString(R.string.menu_sampling_type1)
                }
                R.id.nav_sampling2 -> {
                    tv_title_main.text = resources.getString(R.string.menu_sampling_type2)
                }
                R.id.nav_sampling3 -> {
                    tv_title_main.text = resources.getString(R.string.menu_sampling_type3)
                }
                R.id.nav_sampling4 -> {
                    tv_title_main.text = resources.getString(R.string.menu_sampling_type4)
                }
                R.id.nav_sampling5 -> {
                    tv_title_main.text = resources.getString(R.string.menu_sampling_type5)
                }
                R.id.nav_sampling6 -> {
                    tv_title_main.text = resources.getString(R.string.menu_sampling_type6)
                }
                R.id.nav_sampling7 -> {
                    tv_title_main.text = resources.getString(R.string.menu_sampling_type7)
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
            R.id.action_sampleTag -> {
                val intentDetails = Intent()
                intentDetails.setClass(
                    this@MainActivity,
                    DetailsActivity::class.java
                )
                when (tv_title_main.text) {
                    resources.getString(R.string.menu_sampling_type1) -> {
                        intentDetails.putExtra("fragment_type", 1)
                    }
                    resources.getString(R.string.menu_sampling_type2) -> {
                        intentDetails.putExtra("fragment_type", 2)
                    }
                    resources.getString(R.string.menu_sampling_type3) -> {
                        intentDetails.putExtra("fragment_type", 3)
                    }
                    resources.getString(R.string.menu_sampling_type4) -> {
                        intentDetails.putExtra("fragment_type", 4)
                    }
                    resources.getString(R.string.menu_sampling_type5) -> {
                        intentDetails.putExtra("fragment_type", 5)
                    }
                    resources.getString(R.string.menu_sampling_type6) -> {
                        intentDetails.putExtra("fragment_type", 6)
                    }
                    resources.getString(R.string.menu_sampling_type7) -> {
                        intentDetails.putExtra("fragment_type", 7)
                    }
                }
                startActivity(intentDetails)
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
                    toolbar_main, "再按一次退出" + resources.getString(R.string.app_name),
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null)
                    .show()
                mExitTime = System.currentTimeMillis()
            } else {
                ActivityUtil.closeAllActivity()
            }
        }
        //super.onBackPressed()
    }
}