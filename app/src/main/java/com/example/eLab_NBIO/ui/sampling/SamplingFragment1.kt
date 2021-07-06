package com.example.eLab_NBIO.ui.sampling

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TableRow
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.adapter.SamplingInfoAdapter
import com.example.eLab_NBIO.models.SamplingInfo

class SamplingFragment1 : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var mView: View
    private lateinit var spinner1: Spinner
    private lateinit var tableRow1: TableRow
    private lateinit var spinner5: Spinner
    private lateinit var tableRow5: TableRow
    private lateinit var rvSamplingInfo: RecyclerView
    private lateinit var adapter: SamplingInfoAdapter
    private var samplingInfoList: MutableList<SamplingInfo>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true);
        mView = inflater.inflate(R.layout.fragment_sampling1, container, false)
        initView()
        return mView
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 != null) {
            when (p0.id) {
                R.id.details_SAMPLE_TYPE -> {
                    if (spinner1.selectedItem.toString() == "其它")
                        tableRow1.visibility = View.VISIBLE
                    else {
                        tableRow1.visibility = View.GONE
                    }
                }
                R.id.details_5 -> {
                    if (mView.findViewById<Spinner>(R.id.details_5).selectedItem.toString() == "其它")
                        mView.findViewById<TableRow>(R.id.details_5_other).visibility = View.VISIBLE
                    else {
                        mView.findViewById<TableRow>(R.id.details_5_other).visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        /*TODO("Not yet implemented")*/
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val context: Context? = this.context
        //初始化RecyclerView
        rvSamplingInfo = mView.findViewById(R.id.rv_samplingInfo1_add)
        rvSamplingInfo.setHasFixedSize(true);
        rvSamplingInfo.isNestedScrollingEnabled = false

        //创建LinearLayoutManager 对象 这里使用 LinearLayoutManager 是线性布局的意思
        val layoutManager = LinearLayoutManager(activity)
        //设置RecyclerView 布局
        rvSamplingInfo.layoutManager = layoutManager
        //设置Adapter
        adapter = SamplingInfoAdapter(context, samplingInfoList, 1)
        rvSamplingInfo.adapter = adapter
    }

/*    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.details, menu)
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_info_add -> {
                Log.v("action", "add")
                //val samplingInfo = SamplingInfo("1", "1", "1", "1", "1")
                val samplingInfo = SamplingInfo()
                samplingInfoList?.add(samplingInfo)
                adapter.changList(samplingInfoList)
            }
            R.id.action_info_save -> {
                Log.v("action", "save")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        val ada1: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            context!!,
            R.array.SAMPLE_TYPE,
            android.R.layout.simple_spinner_dropdown_item
        )
        spinner1 = mView.findViewById(R.id.details_SAMPLE_TYPE)
        spinner1.adapter = ada1
        spinner1.onItemSelectedListener = this
        tableRow1 = mView.findViewById(R.id.details_SAMPLE_TYPE_other)

        val ada2: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            context!!,
            R.array.yiju,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.details_1)?.adapter = ada2

        val ada3: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            context!!,
            R.array.xinghao,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.details_2)?.adapter = ada3

        val ada4: ArrayAdapter<*> = ArrayAdapter.`createFromResource`(
            context!!,
            R.array.cedingyiju,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.details_4)?.adapter = ada4

        val ada5: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            context!!,
            R.array.liuxiang,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.details_5).adapter = ada5
        mView.findViewById<Spinner>(R.id.details_5).onItemSelectedListener = this
    }
}