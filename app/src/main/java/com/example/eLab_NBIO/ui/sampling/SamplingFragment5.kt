package com.example.eLab_NBIO.ui.sampling

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dou361.dialogui.DialogUIUtils
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.adapter.SamplingCydAdapter
import com.example.eLab_NBIO.http.RetrofitService
import com.example.eLab_NBIO.models.*
import com.example.eLab_NBIO.models.sampling.Sampling5
import com.example.eLab_NBIO.models.samplingCyd.SamplingCyd5
import com.example.eLab_NBIO.ui.CydActivity
import com.example.eLab_NBIO.util.SpValueUtil
import com.example.eLab_NBIO.util.StringUtils
import com.example.eLab_NBIO.util.TokenUtil
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.loper7.date_time_picker.DateTimePicker
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.fragment_sampling5.*

class SamplingFragment5 : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var mView: View
    private lateinit var rvSamplingInfo: RecyclerView
    private lateinit var cydAdapter: SamplingCydAdapter<SamplingCyd5>
    private var sampling5: Sampling5 = Sampling5()
    private var _context: Context? = null
    private var defaultDate: Long = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true);
        mView = inflater.inflate(R.layout.fragment_sampling5, container, false)
        _context = activity
        initView()
        initData()
        viewAction()
        return mView
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_info_add -> {
                Log.v("action", "add")
                val cyd = SamplingCyd5()
                Cyds.cydList5.add(cyd)
                cydAdapter.changList(Cyds.cydList5)
            }
            R.id.action_info_save -> {
                Log.v("action", "save")
                val dialogLogin = DialogUIUtils.showLoading(
                    _context, "正在提交...", false, true,
                    false,
                    false
                )
                dialogLogin.show()

                Tasks.sampling5.CYHQ = CYHQ.text.toString()
                Tasks.sampling5.HC = HC.text.toString()
                Tasks.sampling5.CYSJ = CYSJ.text.toString()
                Tasks.sampling5.BH = BH.text.toString()

                val samplingData = SamplingData(Tasks.sampling5, Cyds.cydList5)
                val submitSamplingData = SubmitSamplingData(
                    resources.getString(R.string.billName_type5),
                    Gson().toJson(samplingData), SpValueUtil.getInt("ID")
                )

                RetrofitService.getApiService()
                    .submitSamplingData(submitSamplingData)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Response> {
                        override fun onComplete() {
                            Log.i("SubmitSamplingData", "onComplete")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.i("SubmitSamplingData", "onSubscribe")
                        }

                        override fun onNext(t: Response) {
                            Log.i("SubmitSamplingData", "onNext")
                            Snackbar.make(
                                rvSamplingInfo, t.Message.toString(),
                                Snackbar.LENGTH_LONG
                            ).setAction("Action", null)
                                .show()
                        }

                        override fun onError(e: Throwable) {
                            Log.i("SubmitSamplingData", e.message.toString())
                            if (e.message.toString() == "HTTP 401 Unauthorized") {
                                TokenUtil.attemptToken()
                            }
                        }

                    })
                DialogUIUtils.dismiss(dialogLogin)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("CutPasteId")
    private fun initView() {
        //初始化RecyclerView
        rvSamplingInfo = mView.findViewById(R.id.rv_samplingInfo5_add)
        //rvSamplingInfo.setHasFixedSize(true);
        rvSamplingInfo.isNestedScrollingEnabled = false

        //创建LinearLayoutManager 对象 这里使用 LinearLayoutManager 是线性布局的意思
        val layoutManager = LinearLayoutManager(activity)
        //设置RecyclerView 布局
        rvSamplingInfo.layoutManager = layoutManager
        //设置Adapter
        cydAdapter = SamplingCydAdapter(context, Cyds.cydList5, 5)
        rvSamplingInfo.adapter = cydAdapter
    }

    private fun initData() {
        sampling5 = Tasks.sampling5

        mView.findViewById<TextView>(R.id.SAMPLE_NO).text = sampling5.SAMPLE_NO
        mView.findViewById<TextView>(R.id.SAMPLING_POS).text = sampling5.SAMPLING_POS
        mView.findViewById<TextView>(R.id.SAMPLE_MARK).text = sampling5.SAMPLE_MARK
        mView.findViewById<EditText>(R.id.CYHQ).setText(sampling5.CYHQ)
        mView.findViewById<EditText>(R.id.HC).setText(sampling5.HC)
        mView.findViewById<TextView>(R.id.CYSJ).text = sampling5.CYSJ
        mView.findViewById<EditText>(R.id.BH).setText(sampling5.BH)
        getCYDBySamplingId()
    }

    private fun viewAction() {
        cydAdapter.setOnKotlinItemClickListener(object :
            SamplingCydAdapter.IKotlinItemClickListener {
            override fun onItemClickListener(position: Int) {
                Log.v("position", position.toString())
                Cyds.position = position
                val intentDetails = Intent()
                activity?.let {
                    intentDetails.setClass(
                        it,
                        CydActivity::class.java
                    )
                }
                startActivity(intentDetails)
            }
        })

        mView.findViewById<TextView>(R.id.CYSJ).setOnClickListener {
            _context?.let { it1 ->
                CardDatePickerDialog.builder(it1)
                    .setTitle("采样时间")
                    .setDisplayType(0,1,2)
                    .showBackNow(false)//显示回到今日
                    .showDateLabel(false)//显示单位
                    .setOnChoose("选择",object :CardDatePickerDialog.OnChooseListener{
                        @SuppressLint("SetTextI18n")
                        override fun onChoose(millisecond: Long) {
                            mView.findViewById<TextView>(R.id.CYSJ).text = StringUtils.conversionTime(
                                millisecond,"yyyy-MM-dd HH:mm:ss"
                            )
                        }
                    })
                    .setOnCancel("关闭",object :CardDatePickerDialog.OnCancelListener{
                        override fun onCancel() {

                        }
                    }).build().show()
            }
        }
    }

    private fun getCYDBySamplingId() {
        val dialogLogin = DialogUIUtils.showLoading(
            _context, "获取采样点中...", false, true,
            false,
            false
        )
        dialogLogin.show()

        sampling5.ID?.let {
            RetrofitService.getApiService()
                .getCYDBySamplingId5(resources.getString(R.string.billName_type5), it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MutableList<SamplingCyd5>> {
                    override fun onComplete() {
                        Log.i("getCYDBySamplingId", "onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.i("getCYDBySamplingId", "onSubscribe")
                    }

                    override fun onNext(t: MutableList<SamplingCyd5>) {
                        Log.i("getCYDBySamplingId", "onNext")
                        Cyds.cydList5 = t
                        cydAdapter.changList(Cyds.cydList5)
                    }

                    override fun onError(e: Throwable) {
                        Log.i("getCYDBySamplingId", e.message.toString())
                        if (e.message.toString() == "HTTP 401 Unauthorized") {
                            TokenUtil.attemptToken()
                        }
                    }
                })
        }
        DialogUIUtils.dismiss(dialogLogin)
    }

    override fun onResume() {
        super.onResume()
        cydAdapter.changList(Cyds.cydList5)
    }
}