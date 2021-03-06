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
        toolbar.title = "????????????"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        //??????toolbar
        setSupportActionBar(toolbar)
        //????????????????????????????????????setSupportActionBar(toolbar)?????????????????????
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white)
        //????????????????????????????????????setSupportActionBar(toolbar)?????????????????????
        //toolbar.setOnMenuItemClickListener(onMenuItemClick);

        //????????????????????????
        val rv_img_1: RecyclerView = findViewById(R.id.rv_img)
        //????????????????????????
        val rv_img_1_add: RecyclerView = findViewById(R.id.rv_img_add)
        btn_uploadImg = findViewById(R.id.btn_uploadImage)

        //?????????
        //GridLayoutManager ?????? ???????????? GridLayoutManager ????????????????????????
        val layoutManager_1 = LinearLayoutManager(this)
        layoutManager_1.setOrientation(LinearLayoutManager.HORIZONTAL)
        //??????RecyclerView ??????
        rv_img_1.setLayoutManager(layoutManager_1)
        //??????Adapter
        adapter_img_1 = ImgAdapter(this, picList)
        rv_img_1.setAdapter(adapter_img_1)

        //?????????
        val layoutManager_add_1 = LinearLayoutManager(this)
        layoutManager_add_1.setOrientation(LinearLayoutManager.HORIZONTAL)
        rv_img_1_add.setLayoutManager(layoutManager_add_1)
        adapter_img_add_1 = AddImgAdapter(this, picList_add)
        rv_img_1_add.setAdapter(adapter_img_add_1)
    }

    fun initData() {
        picList_add.add(String("????????????", "??????"))
        attemptAttach()
    }

    fun ViewAction() {
        //??????????????????????????????
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
        //??????????????????????????????
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
        //????????????????????????
        btn_uploadImg!!.setOnClickListener {
            if (ClickUtil.isFastClick()) {
                ImgUpload()
            } else {
                Snackbar.make(
                    btn_uploadImg, "?????????????????????????????????",
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
                        imageInfoAddList.add(String("????????????", path))
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
                        Log.v("Attach??????", "token??????")
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
                            Log.v("Attach????????????!", "response.body is null")
                        }
                    } else {
                        Log.v("Attach????????????!", "" + response.code())
                    }
                }

                override fun onFailure(call: Call<List<Attach>?>, t: Throwable) {
                    Log.v("Attach????????????!", t.message)
                }
            })
        } else {
            Snackbar.make(btn_uploadImg, "???????????????", Snackbar.LENGTH_LONG).setAction("Action", null)
                .show()
        }
    }

    fun attemptImage(imageInfo: Attach) {
        val image_path = Environment.getExternalStorageDirectory().toString() + "/FDA/Image/" +
                imageInfo.getID() + ".jpg"
        imageInfo.setPATH(image_path)
        val image = File(image_path)
        if (image.exists()) {
            Log.v("??????", "????????????")
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
                            Log.v("Image??????", "token??????")
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
                                    // ??????????????????????????????
                                    val outStream = FileOutputStream(image)
                                    // ????????????????????????byte????????????????????????
                                    outStream.write(response.body()!!.bytes())
                                    // ???????????????????????????
                                    outStream.close()
                                    Log.v("??????", "????????????")
                                    picListChange(imageInfo)
                                } catch (e: FileNotFoundException) {
                                    e.printStackTrace()
                                    Log.v("ResponseBody", "FileNotFoundException")
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                    Log.v("ResponseBody", "IOException")
                                }
                            } else {
                                Log.v("Image????????????!", "response.body is null")
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                        Log.v("Image????????????!", t.message)
                    }
                })
            } else {
                Snackbar.make(btn_uploadImg, "???????????????", Snackbar.LENGTH_LONG).setAction(
                    "Action",
                    null
                ).show()
            }
        }
    }

    fun picListChange(imageInfo: Attach) {
        when (imageInfo.getIMG_TYPE()) {
            "????????????" -> {
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
            .setPositiveButton("??????", null)
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
        // ??????AlertDialog.Builder????????????????????????????????????AlertDialog?????????
        val builder = AlertDialog.Builder(_context)
        // ??????Title?????????
        builder.setIcon(R.mipmap.ic_launcher)
        // ??????Title?????????
        builder.setTitle("??????")
        // ??????Content?????????????????????
        builder.setMessage("???????????????" + (pos + 1) + "??????????")
        // ????????????PositiveButton
        builder.setPositiveButton(
            "??????"
        ) { dialog, which -> //dialog.dismiss();
            delSamplingImg(pos, imageInfo)
        }
        // ????????????NegativeButton
        builder.setNegativeButton(
            "??????"
        ) { dialog, which ->
            //dialog.dismiss();
        }
        // ?????????????????????
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
                intent_signature.putExtra("type", "?????????????????????");
            } else {
                intent_signature.putExtra("type", "??????????????????");
            }*//*startActivityForResult(intent_signature, TYPE_IMAGE_2)
        }
    }

    fun ImgAddOnClick(pos: Int, imageInfoAdd: String) {
        if (pos == 0) {
            val TYPE_IMAGE: Int
            TYPE_IMAGE = when (imageInfoAdd.getIMG_TYPE()) {
                "????????????" -> TYPE_IMAGE_1
                "?????????&?????????" -> TYPE_IMAGE_3
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
            // ??????AlertDialog.Builder????????????????????????????????????AlertDialog?????????
            val builder = AlertDialog.Builder(_context)
            // ??????Title?????????
            builder.setIcon(R.mipmap.ic_launcher)
            // ??????Title?????????
            builder.setTitle("??????")
            // ??????Content?????????????????????
            builder.setMessage("???????????????" + pos + "??????????")
            // ????????????PositiveButton
            builder.setPositiveButton(
                "??????"
            ) { dialog, which -> //dialog.dismiss();
                imgAddDel(pos, imageInfoAdd)
                Snackbar.make(btn_uploadImg, "??????????????????", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            // ????????????NegativeButton
            builder.setNegativeButton(
                "??????"
            ) { dialog, which ->
                //dialog.dismiss();
            }
            // ?????????????????????
            builder.show()
        }
    }

    fun ImgUpload() {
        fail_num = 0
        finish = 0
        picNum = 0
        status.clear()
        picList_add = adapter_img_add_1.getImgList()

        //???????????????
        var pic_0_num = 0
        for (pic in picList_2_add) {
            if (!pic.getPATH().equals("??????")) {
                pic_0_num++
            }
        }
        if (picList_add.size == 1 && pic_0_num == 0 && picList_3_add.size == 1) {
            Snackbar.make(btn_uploadImg, "????????????????????????", Snackbar.LENGTH_LONG).setAction(
                "Action",
                null
            ).show()
        } else if (picList_add.size == 1 && picList.size == 0) {
            Snackbar.make(
                btn_uploadImg, resources.getString(R.string.img_type_1) +
                        "??????????????????", Snackbar.LENGTH_LONG
            ).setAction("Action", null).show()
        } else if (picList_3_add.size == 1 && picList_3.size == 0) {
            Snackbar.make(
                btn_uploadImg, resources.getString(R.string.img_type_3) +
                        "??????????????????", Snackbar.LENGTH_LONG
            ).setAction("Action", null).show()
        } else {
            picNum = picList_add.size + picList_3_add.size - 2 + pic_0_num
            for (pic in picList_add) {
                if (!pic.getPATH().equals("??????")) {
                    pic_compress(resources.getString(R.string.img_type_1), pic.getPATH())
                }
            }
            for (pic in picList_2_add) {
                if (!pic.getPATH().equals("??????")) {
                    pic_compress(resources.getString(R.string.img_type_2), pic.getPATH())
                }
            }
            for (pic in picList_3_add) {
                if (!pic.getPATH().equals("??????")) {
                    pic_compress(resources.getString(R.string.img_type_3), pic.getPATH())
                }
            }
        }
    }

    fun pic_compress(type: String?, filePath: String) {
        *//*1???quality-????????????????????????76
        2???isKeepSampling-???????????????????????????????????????
        3???fileSize-?????????????????????
        4???outfile-???????????????????????????
        ??????????????????Tiny?????????????????????????????????????????????????????????????????????????????????
        ExternalStorage/Android/data/${packageName}/tiny/?????????*//*
        val options: Tiny.FileCompressOptions = FileCompressOptions()
        Tiny.getInstance().source(filePath).asFile().withOptions(options)
            .compress(object : FileCallback() {
                fun callback(isSuccess: Boolean, outfile: String?) {
                    //return the compressed file path
                    when (type) {
                        "????????????" -> {
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
                        "??????" -> {
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
                        "?????????&?????????" -> {
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
                        Log.v("ImgUpload??????", "token??????")
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
                                //????????????????????????????????????
                                imgUploadSuccess(
                                    Attach(image_type, image_path),
                                    String(image_type, image_path)
                                )
                                Log.v("??????", response.body().getMessage())
                            } else {
                                status.add("0")
                                fail_num++
                                Log.v("??????", response.body().getMessage())
                            }
                            DialogUIUtils.dismiss(dialog_ImgUpload)
                            dialog_ImgUpload = DialogUIUtils.showLoading(
                                _context, "????????? " +
                                        status.size + "/" + picNum, false, true, false, false
                            )
                            dialog_ImgUpload.show()
                        } else {
                            Log.v("ImgUpload????????????!", "response.body is null")
                        }
                        if (status.size + 8 >= picList_add.size + picList_2_add.size +
                            picList_3_add.size
                        ) {
                            finish = 1
                        }
                        if (finish == 1) {
                            DialogUIUtils.dismiss(dialog_ImgUpload)
                            Snackbar.make(
                                btn_uploadImg, "?????????" + status.size + "?????????,????????????" +
                                        fail_num + "???", Snackbar.LENGTH_LONG
                            ).setAction(
                                "Action",
                                null
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<UploadImg?>, t: Throwable) {
                    Log.v("ImgUpload????????????!", t.message)
                    DialogUIUtils.dismiss(dialog_ImgUpload)
                    Snackbar.make(btn_uploadImg, "??????????????????!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
            })
        } else {
            DialogUIUtils.dismiss(dialog_ImgUpload)
            Snackbar.make(btn_uploadImg, "???????????????", Snackbar.LENGTH_LONG).setAction("Action", null)
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
                        Log.v("delImage??????", "token??????")
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
                            if (delImgStatus.getMessage().equals("???????????????")) {
                                imgDel(pos, imageInfo)
                                Snackbar.make(
                                    btn_uploadImg, "????????????!",
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
                            Log.v("delImage????????????!", "response.body is null")
                        }
                    }
                }

                override fun onFailure(call: Call<DelImgStatus?>, t: Throwable) {
                    Log.v("delImage????????????!", t.message)
                }
            })
        } else {
            Snackbar.make(btn_uploadImg, "???????????????", Snackbar.LENGTH_LONG).setAction(
                "Action",
                null
            ).show()
        }
    }

    fun imgDel(pos: Int, imageInfo: Attach) {
        when (imageInfo.getIMG_TYPE()) {
            "????????????" -> adapter_img_1.removeItem(pos)
            "??????" -> adapter_img_2.removeItem(pos)
            "?????????&?????????" -> adapter_img_3.removeItem(pos)
            else -> {
            }
        }
    }

    fun imgAddDel(pos: Int, imageInfo: String) {
        when (imageInfo.getIMG_TYPE()) {
            "????????????" -> adapter_img_add_1.removeItem(pos)
            "??????" -> adapter_img_add_2.removeItem(pos)
            "?????????&?????????" -> adapter_img_add_3.removeItem(pos)
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
            "????????????" -> {
                val iterator1: MutableIterator<String> = picList_add.iterator()
                while (iterator1.hasNext()) {
                    val i: String = iterator1.next()
                    if (imageInfoAdd.getPATH().equals(i.getPATH())) {
                        iterator1.remove() //????????????????????????????????????
                    }
                }
                adapter_img_add_1.changeList_add(picList_add)
            }
            "??????" -> {
                val iterator2: MutableIterator<String> = picList_2_add.iterator()
                while (iterator2.hasNext()) {
                    val i: String = iterator2.next()
                    if (imageInfoAdd.getPATH().equals(i.getPATH())) {
                        iterator2.remove() //????????????????????????????????????
                    }
                }
                adapter_img_add_2.changeList_add(picList_2_add)
            }
            "?????????&?????????" -> {
                val iterator3: MutableIterator<String> = picList_3_add.iterator()
                while (iterator3.hasNext()) {
                    val i: String = iterator3.next()
                    if (imageInfoAdd.getPATH().equals(i.getPATH())) {
                        iterator3.remove() //????????????????????????????????????
                    }
                }
                adapter_img_add_3.changeList_add(picList_3_add)
            }
            else -> {
            }
        }
    }*/
}