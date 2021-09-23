package com.example.eLab_NBIO.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.models.samplingCyd.*

class SamplingCydAdapter<T>(
    var context: Context?,
    var itemList: MutableList<T>?,
    private var type: Int
) :
    RecyclerView.Adapter<SamplingCydAdapter.ViewHolder>() {

    private val viewType = -1
    private val pos = intArrayOf(-1, -1)
    private var defItem: Int = -1
    private var itemClickListener: IKotlinItemClickListener? = null

    //加载item 的布局  创建ViewHolder实例
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(context)
            .inflate(R.layout.item_sampling_cyd, parent, false)
        return ViewHolder(view)
    }

    //对RecyclerView子项数据进行赋值
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!itemList.isNullOrEmpty()) {
            when (type) {
                1 -> {
                    val item = itemList!![position] as SamplingCyd1
                    holder.numSampling.text = (position + 1).toString()
                    holder.keyInfo1.text = "采样点名称"
                    holder.valueInfo1.text = item.CYDMC
                    holder.keyInfo2.text = "采样时间"
                    holder.valueInfo2.text = item.CYSJ
                    holder.taskSelect.tag = position
                }
                2 -> {
                    val item = itemList!![position] as SamplingCyd2
                    holder.numSampling.text = (position + 1).toString()
                    holder.keyInfo1.text = "采样点名称"
                    holder.valueInfo1.text = item.CYDMC
                    holder.keyInfo2.text = "采样时间"
                    holder.valueInfo2.text = item.CYSJ
                    holder.taskSelect.tag = position
                }
                3 -> {
                    val item = itemList!![position] as SamplingCyd3
                    holder.numSampling.text = (position + 1).toString()
                    holder.keyInfo1.text = "采样点名称"
                    holder.valueInfo1.text = item.CYDMC
                    holder.keyInfo2.text = "采样时间"
                    holder.valueInfo2.text = item.CYSJ
                    holder.taskSelect.tag = position
                }
                4 -> {
                    val item = itemList!![position] as SamplingCyd4
                    holder.numSampling.text = (position + 1).toString()
                    holder.keyInfo1.text = "站位号"
                    holder.valueInfo1.text = item.ZWH
                    holder.keyInfo2.text = "样品标号"
                    holder.valueInfo2.text = item.YPBH
                    holder.taskSelect.tag = position
                }
                5 -> {
                    val item = itemList!![position] as SamplingCyd5
                    holder.numSampling.text = (position + 1).toString()
                    holder.keyInfo1.text = "站位号"
                    holder.valueInfo1.text = item.ZWH
                    holder.keyInfo2.text = "样品标号"
                    holder.valueInfo2.text = item.YPBH
                    holder.taskSelect.tag = position
                }
                6 -> {
                    val item = itemList!![position] as SamplingCyd6
                    holder.numSampling.text = (position + 1).toString()
                    holder.keyInfo1.text = "站位号"
                    holder.valueInfo1.text = item.ZWH
                    holder.keyInfo2.text = "样品标号"
                    holder.valueInfo2.text = item.YPBH
                    holder.taskSelect.tag = position
                }
                7 -> {
                    val item = itemList!![position] as SamplingCyd7
                    holder.numSampling.text = (position + 1).toString()
                    holder.keyInfo1.text = "站位号"
                    holder.valueInfo1.text = item.ZWH
                    holder.keyInfo2.text = "样品标号"
                    holder.valueInfo2.text = item.YPBH
                    holder.taskSelect.tag = position
                }
            }
        }
        // 点击事件
        holder.taskSelect.setOnClickListener {
            itemClickListener!!.onItemClickListener(position)
        }
    }

    //返回子项个数
    override fun getItemCount(): Int {
        //获取传入adapter的条目数，没有则返回 1
        return if (itemList?.isNotEmpty() == true) itemList!!.size else -1
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList.isNullOrEmpty()) {
            viewType
        } else super.getItemViewType(position)
    }

    fun getItem(position: Int): T? {
        this.defItem = position;
        notifyDataSetChanged();
        return itemList?.get(position);
    }

    fun removeItem(position: Int) {
        this.itemList?.removeAt(position)
        notifyDataSetChanged()
    }

    fun changList(itemList: MutableList<T>?) {
        //this.itemList.clear();
        this.itemList = itemList
        notifyDataSetChanged()
    }

    fun Refresh_item(position: Int) {
        pos[1] = pos[0]
        pos[0] = position
        notifyItemChanged(position)
    }

    fun Refresh_all() {
        pos[1] = -1
        pos[0] = -1
        notifyDataSetChanged()
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val taskSelect: LinearLayout = view.findViewById(R.id.taskSelect)
        val numSampling: TextView = view.findViewById(R.id.numSampling)
        val keyInfo1: TextView = view.findViewById(R.id.keyInfo1)
        val valueInfo1: TextView = view.findViewById(R.id.valueInfo1)
        val keyInfo2: TextView = view.findViewById(R.id.keyInfo2)
        val valueInfo2: TextView = view.findViewById(R.id.valueInfo2)
    }

    // 提供set方法
    fun setOnKotlinItemClickListener(itemClickListener: IKotlinItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    //自定义接口
    interface IKotlinItemClickListener {
        fun onItemClickListener(position: Int)
    }
}