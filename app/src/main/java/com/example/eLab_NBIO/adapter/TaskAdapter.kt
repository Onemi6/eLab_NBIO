package com.example.eLab_NBIO.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.models.Task
import com.example.eLab_NBIO.models.sampling.*
import com.example.eLab_NBIO.util.RecyclerItemClickListener

class TaskAdapter<T>(
    var context: Context?,
    private var itemList: MutableList<T>?,
    private val mOnItemClickListener: RecyclerItemClickListener?
) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    private val viewType = -1
    private val pos = intArrayOf(-1, -1)
    private var defItem: Int = -1

    //加载item 的布局  创建ViewHolder实例
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    //对RecyclerView子项数据进行赋值
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!itemList.isNullOrEmpty()) {
            val item = itemList!![position] as Task
            holder.numTask.text = (position + 1).toString()
            holder.valueInfo1.text = item.SAMPLE_NO
            holder.valueInfo2.text = item.SAMPLE_NAME
            holder.valueInfo3.text = item.ITEM_NAME
            holder.valueInfo4.text = item.ITEM_METHOD_CODE
            holder.valueInfo5.text = item.ORIGIN_RECORD_MODULE_SAMPLING_NAME
            holder.taskSelect.tag = position
            //holder.keyInfo1.text = "实验室编号"
            //holder.keyInfo2.text = "委托单位"
            // 点击事件
            // item 点击回调
            mOnItemClickListener?.let {
                holder.itemView.setOnClickListener {
                    mOnItemClickListener.onRecyclerViewItemClick(holder.itemView, position);
                }
            }
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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskSelect: LinearLayout = view.findViewById(R.id.taskSelect)
        val numTask: TextView = view.findViewById(R.id.numTask)
        val keyInfo1: TextView = view.findViewById(R.id.keyInfo1)
        val valueInfo1: TextView = view.findViewById(R.id.valueInfo1)
        val keyInfo2: TextView = view.findViewById(R.id.keyInfo2)
        val valueInfo2: TextView = view.findViewById(R.id.valueInfo2)
        val keyInfo3: TextView = view.findViewById(R.id.keyInfo3)
        val valueInfo3: TextView = view.findViewById(R.id.valueInfo3)
        val keyInfo4: TextView = view.findViewById(R.id.keyInfo4)
        val valueInfo4: TextView = view.findViewById(R.id.valueInfo4)
        val keyInfo5: TextView = view.findViewById(R.id.keyInfo5)
        val valueInfo5: TextView = view.findViewById(R.id.valueInfo5)
    }
}