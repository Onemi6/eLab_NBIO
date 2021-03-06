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
import com.example.eLab_NBIO.models.sampling.Sampling3
import com.example.eLab_NBIO.models.samplingCyd.SamplingCyd3
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
import kotlinx.android.synthetic.main.fragment_sampling3.*

class SamplingFragment3 : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var mView: View
    private lateinit var rvSamplingInfo: RecyclerView
    private lateinit var cydAdapter: SamplingCydAdapter<SamplingCyd3>
    private var sampling3: Sampling3 = Sampling3()
    private var _context: Context? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true);
        mView = inflater.inflate(R.layout.fragment_sampling3, container, false)
        _context = activity
        initView()
        initData()
        viewAction()
        return mView
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 != null) {
            when (p0.id) {
                R.id.XH1 -> {
                    if (mView.findViewById<Spinner>(R.id.XH1).selectedItem.toString() == "??????")
                        mView.findViewById<TableRow>(R.id.layout_XH1QT).visibility = View.VISIBLE
                    else {
                        mView.findViewById<TableRow>(R.id.layout_XH1QT).visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_info_add -> {
                Log.v("action", "add")
                val cyd = SamplingCyd3()
                Cyds.cydList3.add(cyd)
                cydAdapter.changList(Cyds.cydList3)
            }
            R.id.action_info_save -> {
                Log.v("action", "save")
                val dialogLogin = DialogUIUtils.showLoading(
                    _context, "????????????...", false, true,
                    false,
                    false
                )
                dialogLogin.show()

                Tasks.sampling3.SYSBH = SYSBH.text.toString()
                Tasks.sampling3.HQMC = HQMC.text.toString()
                Tasks.sampling3.TQ = TQ.text.toString()
                Tasks.sampling3.QW = QW.text.toString()
                Tasks.sampling3.QY = QY.text.toString()
                Tasks.sampling3.SD = SD.text.toString()
                Tasks.sampling3.WTDW = WTDW.text.toString()
                Tasks.sampling3.CYYJ = CYYJ.text.toString()
                Tasks.sampling3.XH1 = XH1.selectedItem.toString()
                Tasks.sampling3.BH1 = BH1.text.toString()
                Tasks.sampling3.XH2 = XH2.text.toString()
                Tasks.sampling3.BH2 = BH2.text.toString()
                Tasks.sampling3.XCXMCDYJ = XCXMCDYJ.text.toString()
                Tasks.sampling3.JZD = JZD.text.toString()
                Tasks.sampling3.BZRY = BZRY.text.toString()
                Tasks.sampling3.CDZ = CDZ.text.toString()
                Tasks.sampling3.GDJJRQK = GDJJRQK.selectedItem.toString()

                val samplingData = SamplingData(Tasks.sampling3, Cyds.cydList3)
                val submitSamplingData = SubmitSamplingData(resources.getString(R.string.billName_type3), Gson().toJson(samplingData),
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
        //?????????RecyclerView
        rvSamplingInfo = mView.findViewById(R.id.rv_samplingInfo3_add)
        //rvSamplingInfo.setHasFixedSize(true);
        rvSamplingInfo.isNestedScrollingEnabled = false

        //??????LinearLayoutManager ?????? ???????????? LinearLayoutManager ????????????????????????
        val layoutManager = LinearLayoutManager(activity)
        //??????RecyclerView ??????
        rvSamplingInfo.layoutManager = layoutManager
        //??????Adapter
        cydAdapter = SamplingCydAdapter(context, Cyds.cydList3, 3)
        rvSamplingInfo.adapter = cydAdapter

        val ada1: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.HS_XH,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.XH1).adapter = ada1

        val ada8: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.HS_GDJJRQK,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.GDJJRQK).adapter = ada8
    }

    private fun initData() {
        sampling3 = Tasks.sampling3

        mView.findViewById<TextView>(R.id.SAMPLE_NO).text = sampling3.SAMPLE_NO
        mView.findViewById<TextView>(R.id.SAMPLING_POS).text = sampling3.SAMPLING_POS
        mView.findViewById<TextView>(R.id.SAMPLE_MARK).text = sampling3.SAMPLE_MARK
        mView.findViewById<EditText>(R.id.SYSBH).setText(sampling3.SYSBH)
        mView.findViewById<EditText>(R.id.HQMC).setText(sampling3.HQMC)
        mView.findViewById<EditText>(R.id.TQ).setText(sampling3.TQ)
        mView.findViewById<EditText>(R.id.QW).setText(sampling3.QW)
        mView.findViewById<EditText>(R.id.QY).setText(sampling3.QY)
        mView.findViewById<EditText>(R.id.SD).setText(sampling3.SD)
        mView.findViewById<EditText>(R.id.WTDW).setText(sampling3.WTDW)
        mView.findViewById<EditText>(R.id.CYYJ).setText(sampling3.CYYJ)
        val adapter_XH1: SpinnerAdapter = mView.findViewById<Spinner>(R.id.XH1).adapter
        for (i in 0 until adapter_XH1.count) {
            if (sampling3.XH1.toString() == adapter_XH1.getItem(i)) {
                mView.findViewById<Spinner>(R.id.XH1).setSelection(i, true)
                break
            }
        }
        mView.findViewById<EditText>(R.id.XH1QT).setText(sampling3.XH1QT.toString())
        mView.findViewById<EditText>(R.id.BH1).setText(sampling3.BH1.toString())
        mView.findViewById<EditText>(R.id.XH2).setText(sampling3.XH2.toString())
        mView.findViewById<EditText>(R.id.BH2).setText(sampling3.BH2.toString())
        mView.findViewById<EditText>(R.id.XCXMCDYJ).setText(sampling3.XCXMCDYJ)
        mView.findViewById<EditText>(R.id.JZD).setText(sampling3.JZD.toString())
        mView.findViewById<EditText>(R.id.BZRY).setText(sampling3.BZRY.toString())
        mView.findViewById<EditText>(R.id.CDZ).setText(sampling3.CDZ.toString())
        val adapter_GDJJRQK: SpinnerAdapter = mView.findViewById<Spinner>(R.id.GDJJRQK).adapter
        for (i in 0 until adapter_GDJJRQK.count) {
            if (sampling3.GDJJRQK.toString() == adapter_GDJJRQK.getItem(i)) {
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
            _context, "??????????????????...", false, true,
            false,
            false
        )
        dialogLogin.show()

        sampling3.ID?.let {
            RetrofitService.getApiService()
                .getCYDBySamplingId3(resources.getString(R.string.billName_type3), it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MutableList<SamplingCyd3>> {
                    override fun onComplete() {
                        Log.i("getCYDBySamplingId", "onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.i("getCYDBySamplingId", "onSubscribe")
                    }

                    override fun onNext(t: MutableList<SamplingCyd3>) {
                        Log.i("getCYDBySamplingId", "onNext")
                        Cyds.cydList3 = t
                        cydAdapter.changList(Cyds.cydList3)
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
        cydAdapter.changList(Cyds.cydList3)
    }
}