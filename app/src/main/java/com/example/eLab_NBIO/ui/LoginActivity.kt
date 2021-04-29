package com.example.eLab_NBIO.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dou361.dialogui.DialogUIUtils
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.http.RetrofitService
import com.example.eLab_NBIO.models.Login
import com.example.eLab_NBIO.util.PermissionUtil
import com.example.eLab_NBIO.util.RSAUtil
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

class LoginActivity : AppCompatActivity() {

    private var loginType = 0
    private var str_account: String? = null
    private var str_password: String? = null
    private var str_imei: String? = null
    private var _context: Context? = null


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
        val isRemember: Boolean = SpValueUtil.getBoolean("isRemember", false)
        account.setText(SpValueUtil.getString("Login_Name"))
        if (isRemember) {
            password.setText(SpValueUtil.getString("Password"))
            remember_password.isChecked = true
        } else {
            //password.setText(SpValueUtil.getString("Password"))
            remember_password.isChecked = false
        }
        str_imei = if (TextUtils.isEmpty(SpValueUtil.getString("IMEI/MAC"))) {
            if (!TextUtils.isEmpty(_context?.let { Util.getIMEI(it) })) {
                _context?.let { Util.getIMEI(it) }
            } else {
                Util.getMac()?.replace(":", "")?.toUpperCase(Locale.ROOT)
            }
        } else {
            SpValueUtil.getString("IMEI/MAC")
        }
        imei_type.text = "IMEI/MAC: $str_imei"
    }

    private fun login() {
/*        if (login_type == 1) {
            str_account = SpValueUtil.getString("Login_Name")
            str_password = SpValueUtil.getString("Password")
            attemptLogin()
        }*/
        btn_login.setOnClickListener {
            str_account = account.text.toString()
            str_password = password.text.toString()
            if (TextUtils.isEmpty(str_account)) {
                account.requestFocus()
                account.error = "账号不能为空"
            } else {
                account.error = null
                if (TextUtils.isEmpty(str_password)) {
                    password.requestFocus()
                    password.error = "密码不能为空"
                } else {
                    password.error = null
                    attemptLogin()
                    //至少8个字符，至少1个大写字母，1个小写字母和1个数字
                    /*if (str_password!!.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\s\\S]{8,}$")) {
                        password.error = null
                        if (NetworkUtil.isNetworkAvailable(_context)) {
                            attemptLogin()
                        } else {
                            Snackbar.make(
                                btn_login, "当前网络不可用",
                                Snackbar.LENGTH_LONG
                            ).setAction("Action", null).show()
                        }
                    } else {
                        //Log.v("password", password+"密码强度不够");
                        password.setError("密码强度不够")
                    }*/
                }
            }
        }
    }

    private fun attemptLogin() {
        val dialogLogin = DialogUIUtils.showLoading(
            _context, "登录中...", false, true,
            false,
            false
        )
        dialogLogin.show()
        try {
            val publicKey = RSAUtil.loadPublicKey(RSAUtil.PUCLIC_KEY)
            //Log.v("publicKey",publicKey.toString());
            // 加密
            //byte[] encryptByte = RSAUtil.encryptData(password.getBytes(), publicKey);
            // 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
            /*String password_afterEncrypt = Base64.encodeToString(encryptByte, Base64.DEFAULT);
            String data = "{\"Login_Name\":\"" + account + "\"," + "\"Password\":\"" +
                    password_afterEncrypt +  "\"," + "\"IMEI\":\"" +imei + "\"}";*/

            val encryptByte = RSAUtil.encryptData("784512Mustafa".toByteArray(), publicKey)
            // 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
            val password_afterEncrypt = Base64.encodeToString(encryptByte, Base64.DEFAULT)
            val data = "{\"Login_Name\":\"" + "mustafa" + "\"," + "\"Password\":\"" +
                    password_afterEncrypt + "\"," + "\"IMEI\":\"" + "861695037552457" + "\"}"

            RetrofitService.getApiService()
                .login(data)
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
                        if (t.MESSAGE == null) {
                            if (remember_password.isChecked) {
                                SpValueUtil.setBoolean("isRemember", true)
                            } else {
                                SpValueUtil.setBoolean("isRemember", false)
                            }
                            SpValueUtil.setString("Login_Name", str_account)
                            SpValueUtil.setString("Password", str_password)
                            SpValueUtil.setString("TOKEN", t.TOKEN)
                            SpValueUtil.setString("NAME", t.NAME)
                            SpValueUtil.setString("NO", t.NO)

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
                            account.error = t.MESSAGE
                        }
                    }

                    override fun onError(e: Throwable) {
                        Log.i("Login", "onError")
                    }

                })
            DialogUIUtils.dismiss(dialogLogin)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
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