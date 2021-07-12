package com.example.eLab_NBIO.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.adapter.MyViewPagerAdapter
import com.example.eLab_NBIO.ui.Sampling.*
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private var fragmentType = 0
    private lateinit var context: Context

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
        val intent = intent
        fragmentType = intent.getIntExtra("fragment_type", 0)
        //position = intent.getIntExtra("position", -1)

        //Log.v("fragment_type", fragmentType.toString())
        toolbar_details.title = ""
        setSupportActionBar(toolbar_details)
        /*显示Home图标*/
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /*设置ToolBar标题，使用TestView显示*/
        tv_title_details.text = resources.getString(R.string.title_details)
        initFragments()
        /*when (fragmentType) {
            1 -> {
                view_pager.currentItem = 0
            }
            2 -> {
                view_pager.currentItem = 1
            }
            3 -> {
                view_pager.currentItem = 2
            }
            4 -> {
                view_pager.currentItem = 3
            }
            5 -> {
                view_pager.currentItem = 4
            }
            6 -> {
                view_pager.currentItem = 5
            }
            7 -> {
                view_pager.currentItem = 6
            }
        }*/
    }

    /**
     * 初始化Fragment
     */
    private fun initFragments() {
        val viewPagerAdapter = MyViewPagerAdapter(supportFragmentManager)
        when (fragmentType) {
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

}