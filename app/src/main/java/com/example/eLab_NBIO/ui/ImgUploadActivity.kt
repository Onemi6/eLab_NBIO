package com.example.eLab_NBIO.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dou361.dialogui.DialogUIUtils
import com.dou361.dialogui.bean.BuildBean
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.adapter.ImgAdapter
import com.example.eLab_NBIO.models.Attach
import com.example.eLab_NBIO.util.ClickUtil
import com.example.eLab_NBIO.util.NetworkUtil
import com.google.android.material.snackbar.Snackbar
import com.zxy.tiny.Tiny
import me.nereo.multi_image_selector.MultiImageSelector
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ImgUploadActivity : AppCompatActivity() {

/*    private var _context: Context? = null
    private var btn_uploadImg: Button? = null
    private var number: String? = null
    private var token:String? = null
    private val status: MutableList<String> = ArrayList()
    private var imageInfoList: List<Attach>? =ArrayList<Attach>()
    private  var picList:MutableList<Attach?>? = ArrayList<Attach?>()
    private var picList_add: MutableList<String> =ArrayList<String>()
    private var adapter_img_1: ImgAdapter? =null
    private var adapter_img_add_1: AddImgAdapter? =null
    private var fail_num = 0
    private  var finish:Int = 0
    private  var picNum:Int = 0
    private var dialog_ImgUpload: BuildBean? = null
    private var sharedPreferences: SharedPreferences? = null
    private val TYPE_IMAGE_1 = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_img_upload)
        _context = this
        val intent = intent
        number = intent.getStringExtra("NO")
        sharedPreferences = getSharedPreferences("Info", MODE_PRIVATE)
        Tiny.getInstance().init(application)
        initView()
        initData()
        ViewAction()
    }

    fun initView() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_ImgUpload)
        toolbar.title = "图片上传"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        //设置toolbar
        setSupportActionBar(toolbar)
        //左边的小箭头（注意需要在setSupportActionBar(toolbar)之后才有效果）
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white)
        //菜单点击事件（注意需要在setSupportActionBar(toolbar)之后才有效果）
        //toolbar.setOnMenuItemClickListener(onMenuItemClick);

        //已上传的三种图片
        val rv_img_1: RecyclerView = findViewById(R.id.rv_img)
        //待上传的三种图片
        val rv_img_1_add: RecyclerView = findViewById(R.id.rv_img_add)
        btn_uploadImg = findViewById(R.id.btn_uploadImage)

        //已上传
        //GridLayoutManager 对象 这里使用 GridLayoutManager 是网格布局的意思
        val layoutManager_1 = LinearLayoutManager(this)
        layoutManager_1.setOrientation(LinearLayoutManager.HORIZONTAL)
        //设置RecyclerView 布局
        rv_img_1.setLayoutManager(layoutManager_1)
        //设置Adapter
        adapter_img_1 = ImgAdapter(this, picList)
        rv_img_1.setAdapter(adapter_img_1)

        //待上传
        val layoutManager_add_1 = LinearLayoutManager(this)
        layoutManager_add_1.setOrientation(LinearLayoutManager.HORIZONTAL)
        rv_img_1_add.setLayoutManager(layoutManager_add_1)
        adapter_img_add_1 = AddImgAdapter(this, picList_add)
        rv_img_1_add.setAdapter(adapter_img_add_1)
    }

    fun initData() {
        picList_add.add(String("现场照片", "加号"))
        attemptAttach()
    }

    fun ViewAction() {
        //已上传照片的点击事件
        adapter_img_1.setOnClickListener(object : OnClickListener() {
            fun onClick(view: View?, position: Int) {
                ImgOnClick(picList.get(position).getPATH())
            }
        })
        adapter_img_1.setOnLongClickListener(object : OnLongClickListener() {
            fun onLongClick(view: View?, position: Int) {
                ImgOnLongClick(position, picList.get(position))
            }
        })
        //待上传照片的点击事件
        adapter_img_add_1.setOnClickListener(object : OnClickListener() {
            fun onClick(view: View?, position: Int) {
                ImgAddOnClick(position, picList_add[position])
            }
        })
        adapter_img_add_1.setOnLongClickListener(object : OnLongClickListener() {
            fun onLongClick(view: View?, position: Int) {
                ImgAddOnLongClick(position, picList_add[position])
            }
        })
        //上传按钮点击事件
        btn_uploadImg!!.setOnClickListener {
            if (ClickUtil.isFastClick()) {
                ImgUpload()
            } else {
                Snackbar.make(
                    btn_uploadImg, "点击太快了，请稍后再试",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null)
                    .show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> finish()
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val selectPaths: List<String>
            var imageInfoAddList: MutableList<String> = ArrayList<String>()
            selectPaths = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT)
            when (requestCode) {
                TYPE_IMAGE_1 -> {
                    for (path in selectPaths) {
                        imageInfoAddList.add(String("现场照片", path))
                    }
                    picList_add.addAll(imageInfoAddList)
                }
                else -> {
                }
            }
            *//*selectPaths = null;-
            imageInfoAddList = null;*//*
        }
    }

    override fun onResume() {
        super.onResume()
        adapter_img_add_1.changeList_add(picList_add)
    }

    fun attemptAttach() {
        if (NetworkUtil.isNetworkAvailable(_context)) {
            val request: FDA_API = HttpUtils.JsonApi()
            if ((application as MyApplication).getTOKEN() == null) {
                token = sharedPreferences!!.getString("TOKEN", null)
            } else {
                token = (application as MyApplication).getTOKEN()
            }
            val call: Call<List<Attach>> = request.Attach(token, number)
            call.enqueue(object : Callback<List<Attach>?> {
                override fun onResponse(
                    call: Call<List<Attach>?>,
                    response: Response<List<Attach>?>
                ) {
                    if (response.code() == 401) {
                        Log.v("Attach请求", "token过期")
                        val intent_login = Intent()
                        intent_login.setClass(
                            this@ImgUploadActivity,
                            LoginActivity::class.java
                        )
                        intent_login.putExtra("login_type", 1)
                        startActivity(intent_login)
                    } else if (response.code() == 200) {
                        if (response.body() != null) {
                            imageInfoList = response.body()
                            Log.v("imageInfoList.size()", "" + imageInfoList!!.size)
                            if (imageInfoList!!.size > 0) {
                                for (imageInfo in imageInfoList!!) {
                                    attemptImage(imageInfo)
                                }
                            }
                        } else {
                            Log.v("Attach请求成功!", "response.body is null")
                        }
                    } else {
                        Log.v("Attach请求成功!", "" + response.code())
                    }
                }

                override fun onFailure(call: Call<List<Attach>?>, t: Throwable) {
                    Log.v("Attach请求失败!", t.message)
                }
            })
        } else {
            Snackbar.make(btn_uploadImg, "当前无网络", Snackbar.LENGTH_LONG).setAction("Action", null)
                .show()
        }
    }

    fun attemptImage(imageInfo: Attach) {
        val image_path = Environment.getExternalStorageDirectory().toString() + "/FDA/Image/" +
                imageInfo.getID() + ".jpg"
        imageInfo.setPATH(image_path)
        val image = File(image_path)
        if (image.exists()) {
            Log.v("图片", "已经存在")
            picListChange(imageInfo)
        } else {
            if (NetworkUtil.isNetworkAvailable(_context)) {
                val request: FDA_API = HttpUtils.JsonApi()
                if ((application as MyApplication).getTOKEN() == null) {
                    token = sharedPreferences!!.getString("TOKEN", null)
                } else {
                    token = (application as MyApplication).getTOKEN()
                }
                val call: Call<ResponseBody> = request.Image(token, imageInfo.getID())
                call.enqueue(object : Callback<ResponseBody?> {
                    override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                    ) {
                        if (response.code() == 401) {
                            Log.v("Image请求", "token过期")
                            val intent_login = Intent()
                            intent_login.setClass(
                                this@ImgUploadActivity,
                                LoginActivity::class.java
                            )
                            intent_login.putExtra("login_type", 1)
                            startActivity(intent_login)
                        } else if (response.code() == 200) {
                            if (response.body() != null) {
                                try {
                                    // 获取文件的输出流对象
                                    val outStream = FileOutputStream(image)
                                    // 获取字符串对象的byte数组并写入文件流
                                    outStream.write(response.body()!!.bytes())
                                    // 最后关闭文件输出流
                                    outStream.close()
                                    Log.v("图片", "下载成功")
                                    picListChange(imageInfo)
                                } catch (e: FileNotFoundException) {
                                    e.printStackTrace()
                                    Log.v("ResponseBody", "FileNotFoundException")
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                    Log.v("ResponseBody", "IOException")
                                }
                            } else {
                                Log.v("Image请求成功!", "response.body is null")
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                        Log.v("Image请求失败!", t.message)
                    }
                })
            } else {
                Snackbar.make(btn_uploadImg, "当前无网络", Snackbar.LENGTH_LONG).setAction(
                    "Action",
                    null
                ).show()
            }
        }
    }

    fun picListChange(imageInfo: Attach) {
        when (imageInfo.getIMG_TYPE()) {
            "现场照片" -> {
                picList.add(imageInfo)
                adapter_img_1.changeList_add(picList)
            }
        }
    }

    fun showImage(path: String?) {
        val inflater = layoutInflater
        val layout: View = inflater.inflate(
            R.layout.item_img_show,
            findViewById(R.id.dialog_layout) as ViewGroup
        )
        val imageview = layout
            .findViewById<ImageView>(R.id.imageView)
        val dialog_img = AlertDialog.Builder(
            this@ImgUploadActivity
        ).setView(layout)
            .setPositiveButton("确定", null)
        dialog_img.show()
        Glide.with(_context).load(path)
            .placeholder(R.mipmap.logo)
            .error(R.mipmap.error)
            .into(imageview)
    }

    fun ImgOnClick(path: String?) {
        showImage(path)
    }

    fun ImgOnLongClick(pos: Int, imageInfo: Attach) {
        // 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        val builder = AlertDialog.Builder(_context)
        // 设置Title的图标
        builder.setIcon(R.mipmap.ic_launcher)
        // 设置Title的内容
        builder.setTitle("提示")
        // 设置Content来显示一个信息
        builder.setMessage("确定删除第" + (pos + 1) + "张图片?")
        // 设置一个PositiveButton
        builder.setPositiveButton(
            "确定"
        ) { dialog, which -> //dialog.dismiss();
            delSamplingImg(pos, imageInfo)
        }
        // 设置一个NegativeButton
        builder.setNegativeButton(
            "取消"
        ) { dialog, which ->
            //dialog.dismiss();
        }
        // 显示出该对话框
        builder.show()
    }

    fun SignatureOnClick(pos: Int) {
        val intent_signature = Intent()
        intent_signature.setClass(this@ImgUploadActivity, SignatureActivity::class.java)
        intent_signature.putExtra("NO", number)
        if (pos < picList_2_add.size - 1 && pos > 1) {
            showImage(picList_2_add.get(pos).getPATH())
        } else {
            intent_signature.putExtra("type", picList_2_add.get(pos).getIMG_TYPE())
            *//*if (pos == 0) {
                intent_signature.putExtra("type", picList_0_add.get(pos).getIMG_TYPE());
            } else if (pos == 1) {
                intent_signature.putExtra("type", "被抽样单位人员");
            } else {
                intent_signature.putExtra("type", "同行抽样人员");
            }*//*startActivityForResult(intent_signature, TYPE_IMAGE_2)
        }
    }

    fun ImgAddOnClick(pos: Int, imageInfoAdd: String) {
        if (pos == 0) {
            val TYPE_IMAGE: Int
            TYPE_IMAGE = when (imageInfoAdd.getIMG_TYPE()) {
                "现场照片" -> TYPE_IMAGE_1
                "告知书&抽样单" -> TYPE_IMAGE_3
                else -> 0
            }
            if (TYPE_IMAGE == TYPE_IMAGE_1 || TYPE_IMAGE == TYPE_IMAGE_3) MultiImageSelector.create()
                .multi()
                .start(this@ImgUploadActivity, TYPE_IMAGE)
        } else {
            showImage(imageInfoAdd.getPATH())
        }
    }

    fun ImgAddOnLongClick(pos: Int, imageInfoAdd: String) {
        if (pos != 0) {
            // 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            val builder = AlertDialog.Builder(_context)
            // 设置Title的图标
            builder.setIcon(R.mipmap.ic_launcher)
            // 设置Title的内容
            builder.setTitle("提示")
            // 设置Content来显示一个信息
            builder.setMessage("确定删除第" + pos + "张图片?")
            // 设置一个PositiveButton
            builder.setPositiveButton(
                "确定"
            ) { dialog, which -> //dialog.dismiss();
                imgAddDel(pos, imageInfoAdd)
                Snackbar.make(btn_uploadImg, "删除图片成功", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            // 设置一个NegativeButton
            builder.setNegativeButton(
                "取消"
            ) { dialog, which ->
                //dialog.dismiss();
            }
            // 显示出该对话框
            builder.show()
        }
    }

    fun ImgUpload() {
        fail_num = 0
        finish = 0
        picNum = 0
        status.clear()
        picList_add = adapter_img_add_1.getImgList()

        //签名的张数
        var pic_0_num = 0
        for (pic in picList_2_add) {
            if (!pic.getPATH().equals("加号")) {
                pic_0_num++
            }
        }
        if (picList_add.size == 1 && pic_0_num == 0 && picList_3_add.size == 1) {
            Snackbar.make(btn_uploadImg, "没有上传任何图片", Snackbar.LENGTH_LONG).setAction(
                "Action",
                null
            ).show()
        } else if (picList_add.size == 1 && picList.size == 0) {
            Snackbar.make(
                btn_uploadImg, resources.getString(R.string.img_type_1) +
                        "至少选择一张", Snackbar.LENGTH_LONG
            ).setAction("Action", null).show()
        } else if (picList_3_add.size == 1 && picList_3.size == 0) {
            Snackbar.make(
                btn_uploadImg, resources.getString(R.string.img_type_3) +
                        "至少选择一张", Snackbar.LENGTH_LONG
            ).setAction("Action", null).show()
        } else {
            picNum = picList_add.size + picList_3_add.size - 2 + pic_0_num
            for (pic in picList_add) {
                if (!pic.getPATH().equals("加号")) {
                    pic_compress(resources.getString(R.string.img_type_1), pic.getPATH())
                }
            }
            for (pic in picList_2_add) {
                if (!pic.getPATH().equals("加号")) {
                    pic_compress(resources.getString(R.string.img_type_2), pic.getPATH())
                }
            }
            for (pic in picList_3_add) {
                if (!pic.getPATH().equals("加号")) {
                    pic_compress(resources.getString(R.string.img_type_3), pic.getPATH())
                }
            }
        }
    }

    fun pic_compress(type: String?, filePath: String) {
        *//*1、quality-压缩质量，默认为76
        2、isKeepSampling-是否保持原数据源图片的宽高
        3、fileSize-压缩后文件大小
        4、outfile-压缩后文件存储路径
        如果不配置，Tiny内部会根据默认压缩质量进行压缩，压缩后文件默认存储在：
        ExternalStorage/Android/data/${packageName}/tiny/目录下*//*
        val options: Tiny.FileCompressOptions = FileCompressOptions()
        Tiny.getInstance().source(filePath).asFile().withOptions(options)
            .compress(object : FileCallback() {
                fun callback(isSuccess: Boolean, outfile: String?) {
                    //return the compressed file path
                    when (type) {
                        "现场照片" -> {
                            var i = 0
                            while (i < picList_add.size) {
                                if (filePath == picList_add[i].getPATH()) {
                                    picList_add[i].setPATH(outfile)
                                    adapter_img_add_1.changeList_add(picList_add)
                                    break
                                }
                                i++
                            }
                        }
                        "签名" -> {
                            var i = 0
                            while (i < picList_2_add.size) {
                                if (filePath == picList_2_add.get(i).getPATH()) {
                                    picList_2_add.get(i).setPATH(outfile)
                                    adapter_img_add_2.changeList_add(picList_2_add)
                                    break
                                }
                                i++
                            }
                        }
                        "告知书&抽样单" -> {
                            var i = 0
                            while (i < picList_3_add.size) {
                                if (filePath == picList_3_add.get(i).getPATH()) {
                                    picList_3_add.get(i).setPATH(outfile)
                                    adapter_img_add_3.changeList_add(picList_3_add)
                                    break
                                }
                                i++
                            }
                        }
                        else -> {
                        }
                    }
                    attemptImgUpload(type, outfile)
                }
            })
    }

    fun attemptImgUpload(image_type: String?, image_path: String?) {
        if (NetworkUtil.isNetworkAvailable(_context)) {
            val request: FDA_API = HttpUtils.JsonApi()
            if ((application as MyApplication).getTOKEN() == null) {
                token = sharedPreferences!!.getString("TOKEN", null)
            } else {
                token = (application as MyApplication).getTOKEN()
            }
            val file = File(image_path)
            val id = RequestBody.create(MediaType.parse("multipart/form-data"), number)
            val type = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                image_type
            )
            val requestFile = RequestBody.create(MediaType.parse("image/png"), file)
            val fileData = MultipartBody.Part.createFormData(
                "file", image_path,
                requestFile
            )
            val call: Call<UploadImg> = request.ImageUpload(token, id, type, fileData)
            call.enqueue(object : Callback<UploadImg?> {
                override fun onResponse(call: Call<UploadImg?>, response: Response<UploadImg?>) {
                    if (response.code() == 401) {
                        Log.v("ImgUpload请求", "token过期")
                        val intent_login = Intent()
                        intent_login.setClass(
                            this@ImgUploadActivity,
                            LoginActivity::class.java
                        )
                        intent_login.putExtra("login_type", 1)
                        DialogUIUtils.dismiss(dialog_ImgUpload)
                        startActivity(intent_login)
                    } else if (response.code() == 200) {
                        if (response.body() != null) {
                            if (response.body().getStatus().equals("success")) {
                                status.add("1")
                                //上传成功将图片移至已上传
                                imgUploadSuccess(
                                    Attach(image_type, image_path),
                                    String(image_type, image_path)
                                )
                                Log.v("图片", response.body().getMessage())
                            } else {
                                status.add("0")
                                fail_num++
                                Log.v("图片", response.body().getMessage())
                            }
                            DialogUIUtils.dismiss(dialog_ImgUpload)
                            dialog_ImgUpload = DialogUIUtils.showLoading(
                                _context, "已上传 " +
                                        status.size + "/" + picNum, false, true, false, false
                            )
                            dialog_ImgUpload.show()
                        } else {
                            Log.v("ImgUpload请求成功!", "response.body is null")
                        }
                        if (status.size + 8 >= picList_add.size + picList_2_add.size +
                            picList_3_add.size
                        ) {
                            finish = 1
                        }
                        if (finish == 1) {
                            DialogUIUtils.dismiss(dialog_ImgUpload)
                            Snackbar.make(
                                btn_uploadImg, "共上传" + status.size + "张图片,其中失败" +
                                        fail_num + "张", Snackbar.LENGTH_LONG
                            ).setAction(
                                "Action",
                                null
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<UploadImg?>, t: Throwable) {
                    Log.v("ImgUpload请求失败!", t.message)
                    DialogUIUtils.dismiss(dialog_ImgUpload)
                    Snackbar.make(btn_uploadImg, "上传请求失败!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
            })
        } else {
            DialogUIUtils.dismiss(dialog_ImgUpload)
            Snackbar.make(btn_uploadImg, "当前无网络", Snackbar.LENGTH_LONG).setAction("Action", null)
                .show()
        }
    }

    fun delSamplingImg(pos: Int, imageInfo: Attach) {
        if (NetworkUtil.isNetworkAvailable(_context)) {
            val request: FDA_API = HttpUtils.JsonApi()
            if ((application as MyApplication).getTOKEN() == null) {
                token = sharedPreferences!!.getString("TOKEN", null)
            } else {
                token = (application as MyApplication).getTOKEN()
            }
            val call: Call<DelImgStatus> = request.delSamplingImg(token, imageInfo.getID())
            call.enqueue(object : Callback<DelImgStatus?> {
                override fun onResponse(
                    call: Call<DelImgStatus?>,
                    response: Response<DelImgStatus?>
                ) {
                    if (response.code() == 401) {
                        Log.v("delImage请求", "token过期")
                        val intent_login = Intent()
                        intent_login.setClass(
                            this@ImgUploadActivity,
                            LoginActivity::class.java
                        )
                        intent_login.putExtra("login_type", 1)
                        startActivity(intent_login)
                    } else if (response.code() == 200) {
                        if (response.body() != null) {
                            val delImgStatus: DelImgStatus? = response.body()
                            if (delImgStatus.getMessage().equals("执行完成！")) {
                                imgDel(pos, imageInfo)
                                Snackbar.make(
                                    btn_uploadImg, "删除成功!",
                                    Snackbar.LENGTH_LONG
                                ).setAction("Action", null)
                                    .show()
                            } else {
                                Snackbar.make(
                                    btn_uploadImg, delImgStatus.getMessage(),
                                    Snackbar.LENGTH_LONG
                                ).setAction("Action", null)
                                    .show()
                            }
                        } else {
                            Log.v("delImage请求成功!", "response.body is null")
                        }
                    }
                }

                override fun onFailure(call: Call<DelImgStatus?>, t: Throwable) {
                    Log.v("delImage请求失败!", t.message)
                }
            })
        } else {
            Snackbar.make(btn_uploadImg, "当前无网络", Snackbar.LENGTH_LONG).setAction(
                "Action",
                null
            ).show()
        }
    }

    fun imgDel(pos: Int, imageInfo: Attach) {
        when (imageInfo.getIMG_TYPE()) {
            "现场照片" -> adapter_img_1.removeItem(pos)
            "签名" -> adapter_img_2.removeItem(pos)
            "告知书&抽样单" -> adapter_img_3.removeItem(pos)
            else -> {
            }
        }
    }

    fun imgAddDel(pos: Int, imageInfo: String) {
        when (imageInfo.getIMG_TYPE()) {
            "现场照片" -> adapter_img_add_1.removeItem(pos)
            "签名" -> adapter_img_add_2.removeItem(pos)
            "告知书&抽样单" -> adapter_img_add_3.removeItem(pos)
            else -> {
            }
        }
    }

    fun imgUploadSuccess(imageInfo: Attach, imageInfoAdd: String) {
        picListChange(imageInfo)
        picListAddChange(imageInfoAdd)
    }

    fun picListAddChange(imageInfoAdd: String) {
        when (imageInfoAdd.getIMG_TYPE()) {
            "现场照片" -> {
                val iterator1: MutableIterator<String> = picList_add.iterator()
                while (iterator1.hasNext()) {
                    val i: String = iterator1.next()
                    if (imageInfoAdd.getPATH().equals(i.getPATH())) {
                        iterator1.remove() //使用迭代器的删除方法删除
                    }
                }
                adapter_img_add_1.changeList_add(picList_add)
            }
            "签名" -> {
                val iterator2: MutableIterator<String> = picList_2_add.iterator()
                while (iterator2.hasNext()) {
                    val i: String = iterator2.next()
                    if (imageInfoAdd.getPATH().equals(i.getPATH())) {
                        iterator2.remove() //使用迭代器的删除方法删除
                    }
                }
                adapter_img_add_2.changeList_add(picList_2_add)
            }
            "告知书&抽样单" -> {
                val iterator3: MutableIterator<String> = picList_3_add.iterator()
                while (iterator3.hasNext()) {
                    val i: String = iterator3.next()
                    if (imageInfoAdd.getPATH().equals(i.getPATH())) {
                        iterator3.remove() //使用迭代器的删除方法删除
                    }
                }
                adapter_img_add_3.changeList_add(picList_3_add)
            }
            else -> {
            }
        }
    }*/
}