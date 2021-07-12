package com.example.eLab_NBIO.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.adapter.MyViewPagerAdapter
import com.example.eLab_NBIO.ui.SamplingCyd.*
import kotlinx.android.synthetic.main.activity_cyd.*

class CydActivity : AppCompatActivity() {

    private var cydType = 0
    private lateinit var context: Context

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
        val intent = intent
        cydType = intent.getIntExtra("cydType", 0)

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
        when (cydType) {
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
}