package com.example.eLab_NBIO.ui.sampling

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.adapter.SamplingInfoAdapter
import com.example.eLab_NBIO.models.SamplingInfo

class SamplingFragment4 : Fragment() {
    private lateinit var mView: View
    private lateinit var rvSamplingInfo: RecyclerView
    private lateinit var adapter: SamplingInfoAdapter
    private var samplingInfoList: MutableList<SamplingInfo>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true);
        mView = inflater.inflate(R.layout.fragment_sampling4, container, false)
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val context: Context? = this.context
        //初始化RecyclerView
        rvSamplingInfo = mView.findViewById(R.id.rv_samplingInfo4_add)
        rvSamplingInfo.setHasFixedSize(true);
        rvSamplingInfo.isNestedScrollingEnabled = false

        //创建LinearLayoutManager 对象 这里使用 LinearLayoutManager 是线性布局的意思
        val layoutManager = LinearLayoutManager(activity)
        //设置RecyclerView 布局
        rvSamplingInfo.layoutManager = layoutManager
        //设置Adapter
        adapter = SamplingInfoAdapter(context, samplingInfoList, 4)
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
}