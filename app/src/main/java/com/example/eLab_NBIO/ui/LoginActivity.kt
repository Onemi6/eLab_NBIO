package com.example.eLab_NBIO.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AppCompatActivity
import com.dou361.dialogui.DialogUIUtils
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.http.RetrofitService
import com.example.eLab_NBIO.models.Lab
import com.example.eLab_NBIO.models.Login
import com.example.eLab_NBIO.util.PermissionUtil
import com.example.eLab_NBIO.util.SpValueUtil
import com.example.eLab_NBIO.util.Util
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class LoginActivity : AppCompatActivity() {

    private var loginType = 0
    private var accountStr: String? = null
    private var passwordStr: String? = null
    private var _context: Context? = null
    private var labName: ArrayList<Lab> = ArrayList()
    private var adapterLab: ArrayAdapter<Lab>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Util.init(this.application)
        _context = this
        val intent = intent
        loginType = intent.getIntExtra("login_type", 0)
        initView()
        PermissionUtil.init(this, this)
        /*FileDir();*/
        try {
            Thread.sleep(1000)
            login()
        } catch (e: Exception) {
            Log.v("sleep-error", e.toString())
        }
    }


    private fun initView() {
        attemptGetLabs()
        val isRemember: Boolean = SpValueUtil.getBoolean("isRemember", false)
        account.setText(SpValueUtil.getString("LOGIN_NAME"))

        if (isRemember) {
            password.setText(SpValueUtil.getString("PASSWORD"))
            remember_password.isChecked = true
        } else {
            //password.setText(SpValueUtil.getString("Password"))
            remember_password.isChecked = false
        }
    }

    private fun login() {
        btn_login.setOnClickListener {
            accountStr = account.text.toString()
            passwordStr = password.text.toString()
            if (TextUtils.isEmpty(accountStr)) {
                account.requestFocus()
                account.error = "账号不能为空"
            } else {
                account.error = null
                if (TextUtils.isEmpty(passwordStr)) {
                    password.requestFocus()
                    password.error = "密码不能为空"
                } else {
                    password.error = null
                    attemptLogin()
                }
            }
        }
    }

    private fun attemptGetLabs() {
        RetrofitService.getApiService()
            .getLabs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<MutableList<Lab>> {
                override fun onComplete() {
                    Log.i("Login", "onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.i("Login", "onSubscribe")
                }

                override fun onNext(t: MutableList<Lab>) {
                    Log.i("Login", "onNext")
                    for (l in t) {
                        labName.add(l)
                    }
                    adapterLab = labName.let {
                        _context?.let { it1 ->
                            ArrayAdapter(
                                it1, android.R.layout.simple_spinner_dropdown_item,
                                it
                            )
                        }
                    }
                    lab.adapter = adapterLab
                    adapterLab?.notifyDataSetChanged()

                    //lab.setSelection(0)
                    val spinnerAdapter: SpinnerAdapter = lab.adapter
                    val k = spinnerAdapter.count
                    for (i in 0 until k) {
                        val item = spinnerAdapter.getItem(i) as Lab
                        if (SpValueUtil.getString("LAB_ID") == item.ID) {
                            lab.setSelection(i, true)
                            break
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    Log.i("Login", "onError")
                }

            })
    }

    private fun attemptLogin() {
        val dialogLogin = DialogUIUtils.showLoading(
            _context, "登录中...", false, true,
            false,
            false
        )
        dialogLogin.show()

        val map = mutableMapOf<String, Any>()
        map["loginName"] = accountStr.toString()
        map["password"] = passwordStr.toString()

        val item = lab.selectedItem as Lab
        map["lab"] = item.ID

/*        for (l in labs) {
            if (lab.selectedItem.toString() == l.NAME) {
                map["lab"] = l.ID
                break
            }
        }*/

        RetrofitService.getApiService()
            .login(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Login> {
                override fun onComplete() {
                    Log.i("Login", "onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.i("Login", "onSubscribe")
                }

                override fun onNext(t: Login) {
                    Log.i("Login", "onNext")
                    if (t.message == null) {
                        if (remember_password.isChecked) {
                            SpValueUtil.setBoolean("isRemember", true)
                        } else {
                            SpValueUtil.setBoolean("isRemember", false)
                        }
                        SpValueUtil.setString("ID", t.ID.toString())
                        SpValueUtil.setString("NAME", t.NAME)
                        SpValueUtil.setString("LOGIN_NAME", t.LOGIN_NAME)
                        SpValueUtil.setString("PASSWORD", passwordStr)
                        SpValueUtil.setString("LAB_ID", t.LAB_ID)
                        SpValueUtil.setString("token", t.token)

                        if (loginType == 1) {
                            Handler().postDelayed({ finish() }, 500) // 延时1s执行
                        } else if (loginType == -1) {
                            //DialogUIUtils.dismiss(dialogLogin)
                            Snackbar.make(btn_login, "登录成功", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()
                            Handler().postDelayed({
                                startActivity(
                                    Intent(
                                        this@LoginActivity,
                                        MainActivity::class.java
                                    )
                                )
                                finish()
                            }, 3000) //延时3s执行
                        }
                    } else {
                        account.requestFocus()
                        account.error = t.message
                    }
                }

                override fun onError(e: Throwable) {
                    Log.i("Login", "onError")
                }

            })
        DialogUIUtils.dismiss(dialogLogin)
    }

    fun FileDir() {
        val sdCardExist = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        val IMAGE_PATH: String
        val PAYMENT_PATH: String
        val SAMPLETAG_PATH: String
        val CRASH_PATH: String
        val BILL_PATH: String
        val BILL2_PATH: String
        val FEEDBACK_PATH: String
        val TOLDBOOK_PATH: String
        val SIGNATURE_PATH: String
        val TASK_PATH: String
        if (sdCardExist) {
            IMAGE_PATH = Environment.getExternalStorageDirectory().toString() + "/FDA/Image/"
            PAYMENT_PATH = Environment.getExternalStorageDirectory().toString() + "/FDA/payment/"
            SAMPLETAG_PATH =
                Environment.getExternalStorageDirectory().toString() + "/FDA/sampletag/"
            CRASH_PATH = Environment.getExternalStorageDirectory().toString() + "/FDA/crash/"
            BILL_PATH = Environment.getExternalStorageDirectory().toString() + "/FDA/Bill/"
            BILL2_PATH = Environment.getExternalStorageDirectory().toString() + "/FDA/Bill2/"
            FEEDBACK_PATH = Environment.getExternalStorageDirectory().toString() + "/FDA/Feedback/"
            TOLDBOOK_PATH = Environment.getExternalStorageDirectory().toString() + "/FDA/Toldbook/"
            SIGNATURE_PATH =
                Environment.getExternalStorageDirectory().toString() + "/FDA/Signature/"
            TASK_PATH = Environment.getExternalStorageDirectory().toString() + "/FDA/Task/"
            //COMPRESS_PATH = Environment.getExternalStorageDirectory() + "/FDA/Compress/";
        } else {
            TASK_PATH = this.cacheDir.toString() + "/"
            SIGNATURE_PATH = TASK_PATH
            TOLDBOOK_PATH = SIGNATURE_PATH
            FEEDBACK_PATH = TOLDBOOK_PATH
            BILL2_PATH = FEEDBACK_PATH
            BILL_PATH = BILL2_PATH
            CRASH_PATH = BILL_PATH
            SAMPLETAG_PATH = CRASH_PATH
            PAYMENT_PATH = SAMPLETAG_PATH
            IMAGE_PATH = PAYMENT_PATH
        }
        val image = File(IMAGE_PATH)
        val payment = File(PAYMENT_PATH)
        val sampleTag = File(SAMPLETAG_PATH)
        val crash = File(CRASH_PATH)
        val bill = File(BILL_PATH)
        val bill2 = File(BILL2_PATH)
        val feedback = File(FEEDBACK_PATH)
        val toldBook = File(TOLDBOOK_PATH)
        val signature = File(SIGNATURE_PATH)
        val task = File(TASK_PATH)
        if (!image.exists()) {
            image.mkdirs()
        }
        if (!payment.exists()) {
            payment.mkdirs()
        }
        if (!sampleTag.exists()) {
            sampleTag.mkdirs()
        }
        if (!crash.exists()) {
            crash.mkdirs()
        }
        if (!bill.exists()) {
            bill.mkdirs()
        }
        if (!bill2.exists()) {
            bill2.mkdirs()
        }
        if (!feedback.exists()) {
            feedback.mkdirs()
        }
        if (!toldBook.exists()) {
            toldBook.mkdirs()
        }
        if (!signature.exists()) {
            signature.mkdirs()
        }
        if (!task.exists()) {
            task.mkdirs()
        }
    }
}