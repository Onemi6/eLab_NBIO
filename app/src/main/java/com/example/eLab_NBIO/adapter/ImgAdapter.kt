package com.example.eLab_NBIO.adapter

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.models.Attach

class ImgAdapter {
    /*private val VIEW_TYPE = -1
    private var imgList: List<Attach>? = null
    private var mActivity: Activity? = null
    private var mOnClickListener: OnClickListener? = null
    private var mOnLongClickListener: OnLongClickListener? = null

    fun ImgAdapter(activity: Activity?, imgList: List<Attach>?) {
        mActivity = activity
        this.imgList = imgList
    }

    //加载item 的布局  创建ViewHolder实例
    fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val view: View =
            LayoutInflater.from(mActivity).inflate(R.layout.item_img_add, parent, false)
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
        return ViewHolder(view)
    }

    //对RecyclerView子项数据进行赋值
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val picPath: String = imgList!![position].getPATH()
            Glide.with(mActivity).load(picPath)
                .placeholder(R.mipmap.logo)
                .error(R.mipmap.error)
                .into(holder.img_add)
            holder.imageNum.text = (position + 1).toString()
            holder.itemView.setTag(position)
            *//*if ((position == 0 || position == 1 || position == 2) && picPath.equals("加号")) {
                //((ViewHolder) holder).img_add.setImageResource(R.mipmap.ic_add);
                Glide.with(mActivity).load(R.mipmap.ic_add)
                        .placeholder(R.mipmap.logo)
                        .error(R.mipmap.error)
                        .into(((ImgAdapter.ViewHolder) holder).img_add);
            } else if (position == 0 && picPath.equals("空白")) {
                //((ViewHolder) holder).img_add.setImageResource(R.mipmap.logo);
            } else {
                Glide.with(mActivity).load(picPath)
                        .placeholder(R.mipmap.logo)
                        .error(R.mipmap.error)
                        .into(((ImgAdapter.ViewHolder) holder).img_add);
            }
            if (position == 0) {
                ((ImgAdapter.ViewHolder) holder).imageNum.setText(String.valueOf(""));
            } else {
                ((ImgAdapter.ViewHolder) holder).imageNum.setText(String.valueOf(position));
            }*//*
        }
    }

    //返回子项个数
    fun getItemCount(): Int {
        //获取传入adapter的条目数，没有则返回 1
        //return imgList.size() > 0 ? imgList.size() : 1;
        return imgList!!.size
    }

    fun getItemViewType(position: Int): Int {
        return if (imgList!!.size <= 0) {
            VIEW_TYPE
        } else super.getItemViewType(position)
    }

    fun getImgList(): List<Attach>? {
        return imgList
    }

    fun removeItem(position: Int) {
        imgList.removeAt(position)
        notifyDataSetChanged()
    }

    fun changeList_add(imgList: List<Attach>?) {
        this.imgList = imgList
        notifyDataSetChanged()
    }

    *//************
     * Listener
     *//*
    fun setOnClickListener(listener: OnClickListener?) {
        mOnClickListener = listener
    }

    fun onClick(view: View) {
        if (null != mOnClickListener) {
            mOnClickListener!!.onClick(view, view.tag as Int)
        }
    }

    fun setOnLongClickListener(listener: OnLongClickListener?) {
        mOnLongClickListener = listener
    }

    fun onLongClick(view: View): Boolean {
        if (null != mOnLongClickListener) {
            mOnLongClickListener!!.onLongClick(view, view.tag as Int)
        }
        // 消耗事件，否则长按逻辑执行完成后还会进入点击事件的逻辑处理
        return true
    }

    *//**
     * 手动添加点击事件
     *//*
    interface OnClickListener {
        fun onClick(view: View?, position: Int)
    }

    *//**
     * 手动添加长按事件
     *//*
    interface OnLongClickListener {
        fun onLongClick(view: View?, position: Int)
    }

    internal class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img_add: ImageView = view.findViewById(R.id.imageView)
        val imageNum: TextView = view.findViewById(R.id.imageNum)
    }

    fun getBM(path: String?): Bitmap? {
        if (!TextUtils.isEmpty(path)) {
            val opt = BitmapFactory.Options()
            opt.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, opt)
            val imageHeight = opt.outHeight
            val imageWidth = opt.outWidth
            val display = mActivity!!.windowManager.defaultDisplay
            val point = Point()
            // 该方法已过时，使用getRealSize()方法替代。也可以使用getSize()，但是不能准确的获取到分辨率
            // int screenHeight = display.getHeight();
            // int screenWidth = display.getWidth();
            display.getRealSize(point)
            val screenHeight = point.y
            val screenWidth = point.x
            var scale = 1
            val scaleWidth = imageWidth / screenWidth / 3
            val scaleHeigh = imageHeight / screenHeight
            if (scaleWidth >= scaleHeigh && scaleWidth > 1) {
                scale = scaleWidth
            } else if (scaleWidth < scaleHeigh && scaleHeigh > 1) {
                scale = scaleHeigh
            }
            opt.inSampleSize = scale
            opt.inJustDecodeBounds = false
            return BitmapFactory.decodeFile(path, opt)
        }
        return null
    }*/
}