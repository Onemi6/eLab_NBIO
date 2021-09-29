package com.example.eLab_NBIO.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.adapter.MyViewPagerAdapter
import com.example.eLab_NBIO.models.Task
import com.example.eLab_NBIO.models.Tasks
import com.example.eLab_NBIO.models.sampling.*
import com.example.eLab_NBIO.ui.sampling.*
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        context = this
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details, menu)
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
        toolbar_details.title = ""
        setSupportActionBar(toolbar_details)
        /*显示Home图标*/
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        /*设置ToolBar标题，使用TestView显示*/
        tv_title_details.text = resources.getString(R.string.title_details)
        initFragments()
    }

    /**
     * 初始化Fragment
     */
    private fun initFragments() {
        val viewPagerAdapter = MyViewPagerAdapter(supportFragmentManager)
        task = Tasks.taskList[Tasks.position]
        when (task.ORIGIN_RECORD_MODULE_SAMPLING_ID) {
            1 -> {
                viewPagerAdapter.apply { addFragment(SamplingFragment1()) }
            }
            2 -> {
                viewPagerAdapter.apply { addFragment(SamplingFragment2()) }
            }
            3 -> {
                viewPagerAdapter.apply { addFragment(SamplingFragment3()) }
            }
            4 -> {
                viewPagerAdapter.apply { addFragment(SamplingFragment4()) }
            }
            5 -> {
                viewPagerAdapter.apply { addFragment(SamplingFragment5()) }
            }
            6 -> {
                viewPagerAdapter.apply { addFragment(SamplingFragment6()) }
            }
            7 -> {
                viewPagerAdapter.apply { addFragment(SamplingFragment7()) }
            }
        }
        view_pager.offscreenPageLimit = 1
        view_pager.adapter = viewPagerAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        clearTasks()
    }

    private fun clearTasks() {
        Tasks.sampling1 = Sampling1()
        Tasks.sampling2 = Sampling2()
        Tasks.sampling3 = Sampling3()
        Tasks.sampling4 = Sampling4()
        Tasks.sampling5 = Sampling5()
        Tasks.sampling6 = Sampling6()
        Tasks.sampling7 = Sampling7()
    }
}