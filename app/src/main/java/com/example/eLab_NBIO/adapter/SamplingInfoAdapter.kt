package com.example.eLab_NBIO.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.models.SamplingInfo

class SamplingInfoAdapter(
    context: Context?,
    private var samplingInfoList: MutableList<SamplingInfo>?,
    private var type: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener,
    View.OnLongClickListener {

    private val viewType = -1
    private lateinit var samplingInfo: SamplingInfo
    private var mContext: Context? = context
    private var mOnClickListener: OnClickListener? = null
    private var mOnLongClickListener: OnLongClickListener? = null
    private val pos = intArrayOf(-1, -1)
    private lateinit var view: View
    private var defItem: Int = -1


    //加载item 的布局  创建ViewHolder实例
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val emptyView: View =
            LayoutInflater.from(mContext).inflate(R.layout.rv_empty, parent, false)
        if (this.viewType == viewType) {
            return EmptyViewHolder(emptyView)
        } else {
            when (type) {
                1 -> {
                    view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_info_sampling1, parent, false)
                }
                2 -> {
                    view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_info_sampling2, parent, false)
                }
                3 -> {
                    view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_info_sampling3, parent, false)
                }
                4 -> {
                    view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_info_sampling4, parent, false)
                }
                5 -> {
                    view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_info_sampling5, parent, false)
                }
                6 -> {
                    view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_info_sampling6, parent, false)
                }
                7 -> {
                    view = LayoutInflater.from(mContext)
                        .inflate(
                            R.layout.item_info_sampling7, parent, false
                        )
                }
            }
        }
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
        return ViewHolder(view)
    }

    //对RecyclerView子项数据进行赋值
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            if (!samplingInfoList.isNullOrEmpty()) {
                holder.samplingInfoNum.text = (position + 1).toString()
/*                samplingInfo = samplingInfoList!![position]
                holder.samplingInfoNum.text = (position + 1).toString()
                when (type) {
*//*                1->{
                    holder.info1.setText(samplingInfo.info1)
                    holder.info2.setText(samplingInfo.info2)
                    holder.info3.setText(samplingInfo.info3)
                    holder.info4.setText(samplingInfo.info4)
                    holder.info5.setText(samplingInfo.info5)
                }*//*
                    7 -> {
                        *//*holder.samplingInfoNum.text = (position + 1).toString()*//*
                        holder.info1.setText(samplingInfo.info1)
                        holder.info2.setText(samplingInfo.info2)
                        holder.info3.setText(samplingInfo.info3)
                        holder.info4.setText(samplingInfo.info4)
                        holder.info5.setText(samplingInfo.info5)
                    }
                }*/
            }
        }
    }

    //返回子项个数
    override fun getItemCount(): Int {
        //获取传入adapter的条目数，没有则返回 1
        return if (samplingInfoList?.isNotEmpty() == true) samplingInfoList!!.size else -1
    }

    override fun getItemViewType(position: Int): Int {
        return if (samplingInfoList.isNullOrEmpty()) {
            viewType
        } else super.getItemViewType(position)
    }

    fun getItem(position: Int): SamplingInfo? {
        this.defItem = position;
        notifyDataSetChanged();
        return samplingInfoList?.get(position);
    }

    fun removeItem(position: Int) {
        this.samplingInfoList?.removeAt(position)
        notifyDataSetChanged()
    }

    fun changList(samplingInfoList: MutableList<SamplingInfo>?) {
        //this.samplingInfoList.clear();
        this.samplingInfoList = samplingInfoList
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

    fun setOnClickListener(listener: OnClickListener?) {
        mOnClickListener = listener
    }

    override fun onClick(view: View) {
        if (null != mOnClickListener) {
            mOnClickListener!!.onClick(view, view.tag as Int) //getTag()获取数据
        }
    }

    fun setOnLongClickListener(listener: OnLongClickListener?) {
        mOnLongClickListener = listener
    }

    override fun onLongClick(view: View): Boolean {
        if (null != mOnLongClickListener) {
            mOnLongClickListener!!.onLongClick(view, view.tag as Int)
        }
        // 消耗事件，否则长按逻辑执行完成后还会进入点击事件的逻辑处理
        return true
    }

    /**
     * 手动添加点击事件
     */
    interface OnClickListener {
        fun onClick(view: View?, position: Int)
    }

    /**
     * 手动添加长按事件
     */
    interface OnLongClickListener {
        fun onLongClick(view: View?, position: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val samplingInfoNum: TextView = view.findViewById(R.id.samplingInfo_num)
/*        val info1: EditText = view.findViewById(R.id.info1)
        val info2: EditText = view.findViewById(R.id.info2)
        val info3: EditText = view.findViewById(R.id.info3)
        val info4: EditText = view.findViewById(R.id.info4)
        val info5: EditText = view.findViewById(R.id.info5)*/
    }

    class EmptyViewHolder  //private TextView mEmptyTextView;
        (view: View?) : RecyclerView.ViewHolder(view!!)
}