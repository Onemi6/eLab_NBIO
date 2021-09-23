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
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dou361.dialogui.DialogUIUtils
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.adapter.SamplingCydAdapter
import com.example.eLab_NBIO.http.RetrofitService
import com.example.eLab_NBIO.models.*
import com.example.eLab_NBIO.models.sampling.Sampling2
import com.example.eLab_NBIO.models.samplingCyd.SamplingCyd2
import com.example.eLab_NBIO.ui.CydActivity
import com.example.eLab_NBIO.util.SpValueUtil
import com.example.eLab_NBIO.util.TokenUtil
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.fragment_sampling2.*

class SamplingFragment2 : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var mView: View
    private lateinit var rvSamplingInfo: RecyclerView
    private lateinit var cydAdapter: SamplingCydAdapter<SamplingCyd2>
    private var sampling2: Sampling2 = Sampling2()
    private var _context: Context? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true);
        mView = inflater.inflate(R.layout.fragment_sampling2, container, false)
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
                val cyd = SamplingCyd2()
                Cyds.cydList2.add(cyd)
                cydAdapter.changList(Cyds.cydList2)
            }
            R.id.action_info_save -> {
                Log.v("action", "save")
                val dialogLogin = DialogUIUtils.showLoading(
                    _context, "正在提交...", false, true,
                    false,
                    false
                )
                dialogLogin.show()

                Tasks.sampling2.SYSBH = SYSBH.text.toString()
                Tasks.sampling2.TQ = TQ.text.toString()
                Tasks.sampling2.QW = QW.text.toString()
                Tasks.sampling2.QY = QY.text.toString()
                Tasks.sampling2.SD = SD.text.toString()
                Tasks.sampling2.WTDW = WTDW.text.toString()
                Tasks.sampling2.XH = XH.selectedItem.toString()
                Tasks.sampling2.BH = BH.text.toString()
                Tasks.sampling2.CYYJ = CYYJ.selectedItem.toString()
                Tasks.sampling2.CDYJ = CDYJ.selectedItem.toString()
                Tasks.sampling2.JZD = JZD.selectedItem.toString()
                Tasks.sampling2.BZRY = BZRY.selectedItem.toString()
                Tasks.sampling2.CDZ = CDZ.text.toString()
                Tasks.sampling2.GDJJRQK = GDJJRQK.selectedItem.toString()

                val samplingData = SamplingData(Tasks.sampling2, Cyds.cydList2)
                val submitSamplingData = SubmitSamplingData(resources.getString(R.string.billName_type2), Gson().toJson(samplingData),
                    SpValueUtil.getInt("ID"))

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
        rvSamplingInfo = mView.findViewById(R.id.rv_samplingInfo2_add)
        //rvSamplingInfo.setHasFixedSize(true);
        rvSamplingInfo.isNestedScrollingEnabled = false

        //创建LinearLayoutManager 对象 这里使用 LinearLayoutManager 是线性布局的意思
        val layoutManager = LinearLayoutManager(activity)
        //设置RecyclerView 布局
        rvSamplingInfo.layoutManager = layoutManager
        //设置Adapter
        cydAdapter = SamplingCydAdapter(context, Cyds.cydList2, 2)
        rvSamplingInfo.adapter = cydAdapter

        val ada1: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.DBS_XH,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.XH).adapter = ada1

        val ada2: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.DBS_CYYJ,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.CYYJ).adapter = ada2

        val ada3: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.DBS_CDYJ,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.CDYJ).adapter = ada3

        val ada6: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.DBS_JZD,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.JZD).adapter = ada6

        val ada7: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.DBS_BZRY,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.BZRY).adapter = ada7

        val ada8: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.DBS_GDJJRQK,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.GDJJRQK).adapter = ada8
    }

    private fun initData() {
        sampling2 = Tasks.sampling2

        mView.findViewById<TextView>(R.id.SAMPLE_NO).text = sampling2.SAMPLE_NO
        mView.findViewById<TextView>(R.id.SAMPLING_POS).text = sampling2.SAMPLING_POS
        mView.findViewById<TextView>(R.id.SAMPLE_MARK).text = sampling2.SAMPLE_MARK
        mView.findViewById<EditText>(R.id.SYSBH).setText(sampling2.SYSBH)
        mView.findViewById<EditText>(R.id.TQ).setText(sampling2.TQ)
        mView.findViewById<EditText>(R.id.QW).setText(sampling2.QW)
        mView.findViewById<EditText>(R.id.QY).setText(sampling2.QY)
        mView.findViewById<EditText>(R.id.SD).setText(sampling2.SD)
        mView.findViewById<EditText>(R.id.WTDW).setText(sampling2.WTDW)
        val adapter_XH: SpinnerAdapter = mView.findViewById<Spinner>(R.id.XH).adapter
        for (i in 0 until adapter_XH.count) {
            if (sampling2.XH.toString() == adapter_XH.getItem(i)) {
                mView.findViewById<Spinner>(R.id.SBXH).setSelection(i, true)
                break
            }
        }
        mView.findViewById<EditText>(R.id.BH).setText(sampling2.BH.toString())
        val adapter_CYYJ: SpinnerAdapter = mView.findViewById<Spinner>(R.id.CYYJ).adapter
        for (i in 0 until adapter_CYYJ.count) {
            if (sampling2.CYYJ.toString() == adapter_CYYJ.getItem(i)) {
                mView.findViewById<Spinner>(R.id.CYYJ).setSelection(i, true)
                break
            }
        }
        val adapter_CDYJ: SpinnerAdapter = mView.findViewById<Spinner>(R.id.CDYJ).adapter
        for (i in 0 until adapter_CDYJ.count) {
            if (sampling2.CDYJ.toString() == adapter_CDYJ.getItem(i)) {
                mView.findViewById<Spinner>(R.id.XCCDYJ).setSelection(i, true)
                break
            }
        }
        val adapter_JZD: SpinnerAdapter = mView.findViewById<Spinner>(R.id.JZD).adapter
        for (i in 0 until adapter_JZD.count) {
            if (sampling2.JZD.toString() == adapter_JZD.getItem(i)) {
                mView.findViewById<Spinner>(R.id.JZD).setSelection(i, true)
                break
            }
        }
        val adapter_BZRY: SpinnerAdapter = mView.findViewById<Spinner>(R.id.BZRY).adapter
        for (i in 0 until adapter_BZRY.count) {
            if (sampling2.BZRY.toString() == adapter_BZRY.getItem(i)) {
                mView.findViewById<Spinner>(R.id.BZRY).setSelection(i, true)
                break
            }
        }
        mView.findViewById<EditText>(R.id.CDZ).setText(sampling2.CDZ.toString())
        val adapter_GDJJRQK: SpinnerAdapter = mView.findViewById<Spinner>(R.id.GDJJRQK).adapter
        for (i in 0 until adapter_GDJJRQK.count) {
            if (sampling2.GDJJRQK.toString() == adapter_GDJJRQK.getItem(i)) {
                mView.findViewById<Spinner>(R.id.GDJJRQK).setSelection(i, true)
                break
            }
        }
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
    }

    private fun getCYDBySamplingId() {
        val dialogLogin = DialogUIUtils.showLoading(
            _context, "获取采样点中...", false, true,
            false,
            false
        )
        dialogLogin.show()

/*        val map = mutableMapOf<String, Any?>()
        map["billName"] = resources.getString(R.string.billName_type1)
        map["samplingId"] = sampling2.ID*/

        sampling2.ID?.let {
            RetrofitService.getApiService()
                .getCYDBySamplingId2(resources.getString(R.string.billName_type2), it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MutableList<SamplingCyd2>> {
                    override fun onComplete() {
                        Log.i("getCYDBySamplingId", "onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.i("getCYDBySamplingId", "onSubscribe")
                    }

                    override fun onNext(t: MutableList<SamplingCyd2>) {
                        Log.i("getCYDBySamplingId", "onNext")
                        Cyds.cydList2 = t
                        cydAdapter.changList(Cyds.cydList2)
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
        cydAdapter.changList(Cyds.cydList2)
    }
}