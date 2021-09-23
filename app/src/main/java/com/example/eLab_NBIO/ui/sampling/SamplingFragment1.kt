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
import com.example.eLab_NBIO.models.sampling.Sampling1
import com.example.eLab_NBIO.models.samplingCyd.SamplingCyd1
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
import kotlinx.android.synthetic.main.fragment_sampling1.*

class SamplingFragment1 : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var mView: View
    private lateinit var rvSamplingInfo: RecyclerView
    private lateinit var cydAdapter: SamplingCydAdapter<SamplingCyd1>
    private var sampling1: Sampling1 = Sampling1()
    private var _context: Context? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true);
        mView = inflater.inflate(R.layout.fragment_sampling1, container, false)
        _context = activity
        initView()
        initData()
        viewAction()
        return mView
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 != null) {
            when (p0.id) {
                R.id.YPLX -> {
                    if (mView.findViewById<Spinner>(R.id.YPLX).selectedItem.toString() == "其它")
                        mView.findViewById<TableRow>(R.id.layout_YPLXQT).visibility = View.VISIBLE
                    else {
                        mView.findViewById<TableRow>(R.id.layout_YPLXQT).visibility = View.GONE
                    }
                }
                R.id.FSLX -> {
                    if (mView.findViewById<Spinner>(R.id.FSLX).selectedItem.toString() == "其它")
                        mView.findViewById<TableRow>(R.id.layout_FSLXQT).visibility = View.VISIBLE
                    else {
                        mView.findViewById<TableRow>(R.id.layout_FSLXQT).visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        /*TODO("Not yet implemented")*/
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val context: Context? = this.context

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_info_add -> {
                Log.v("action", "add")
                val cyd = SamplingCyd1()
                Cyds.cydList1.add(cyd)
                cydAdapter.changList(Cyds.cydList1)
            }
            R.id.action_info_save -> {
                Log.v("action", "save")
                val dialogLogin = DialogUIUtils.showLoading(
                    _context, "正在提交...", false, true,
                    false,
                    false
                )
                dialogLogin.show()

                Tasks.sampling1.SYSBH = SYSBH.text.toString()
                Tasks.sampling1.YPLX = YPLX.selectedItem.toString()
                Tasks.sampling1.YPLXQT = YPLXQT.text.toString()
                Tasks.sampling1.WTDW = WTDW.text.toString()
                Tasks.sampling1.CYYJ = CYYJ.selectedItem.toString()
                Tasks.sampling1.SBXH = SBXH.selectedItem.toString()
                Tasks.sampling1.SBBH = SBBH.text.toString()
                Tasks.sampling1.XCCDYJ = XCCDYJ.selectedItem.toString()
                Tasks.sampling1.FSLX = FSLX.selectedItem.toString()
                Tasks.sampling1.FSLXQT = FSLXQT.text.toString()
                Tasks.sampling1.CLSS = CLSS.text.toString()
                Tasks.sampling1.JZD = JZD.selectedItem.toString()
                Tasks.sampling1.BZRY = BZRY.selectedItem.toString()
                Tasks.sampling1.CDZ = CDZ.text.toString()
                Tasks.sampling1.GDJJRQK = GDJJRQK.selectedItem.toString()

                val samplingData = SamplingData(Tasks.sampling1, Cyds.cydList1)
                val submitSamplingData = SubmitSamplingData(resources.getString(R.string.billName_type1), Gson().toJson(samplingData),SpValueUtil.getInt("ID"))

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
        rvSamplingInfo = mView.findViewById(R.id.rv_samplingInfo1_add)
        //rvSamplingInfo.setHasFixedSize(true);
        rvSamplingInfo.isNestedScrollingEnabled = false

        //创建LinearLayoutManager 对象 这里使用 LinearLayoutManager 是线性布局的意思
        val layoutManager = LinearLayoutManager(activity)
        //设置RecyclerView 布局
        rvSamplingInfo.layoutManager = layoutManager
        //设置Adapter
        cydAdapter = SamplingCydAdapter(context, Cyds.cydList1, 1)
        rvSamplingInfo.adapter = cydAdapter

        val ada1: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.SHFS_YPLX,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.YPLX).adapter = ada1
        mView.findViewById<Spinner>(R.id.YPLX).onItemSelectedListener = this

        val ada2: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.SHFS_CYYJ,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.CYYJ).adapter = ada2

        val ada3: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.SHFS_SBXH,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.SBXH).adapter = ada3

        val ada4: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.SHFS_XCCDYJ,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.XCCDYJ)?.adapter = ada4

        val ada5: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.SHFS_FSLX,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.FSLX).adapter = ada5
        mView.findViewById<Spinner>(R.id.FSLX).onItemSelectedListener = this

        val ada6: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.SHFS_JZD,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.JZD).adapter = ada6

        val ada7: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.SHFS_BZRY,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.BZRY).adapter = ada7

        val ada8: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.SHFS_GDJJRQK,
            android.R.layout.simple_spinner_dropdown_item
        )
        mView.findViewById<Spinner>(R.id.GDJJRQK).adapter = ada8
    }

    private fun initData() {
        sampling1 = Tasks.sampling1
        mView.findViewById<TextView>(R.id.SAMPLE_NO).text = sampling1.SAMPLE_NO
        mView.findViewById<TextView>(R.id.SAMPLING_POS).text = sampling1.SAMPLING_POS
        mView.findViewById<TextView>(R.id.SAMPLE_MARK).text = sampling1.SAMPLE_MARK
        mView.findViewById<EditText>(R.id.SYSBH).setText(sampling1.SYSBH)
        val adapter_YPLX: SpinnerAdapter = mView.findViewById<Spinner>(R.id.YPLX).adapter
        for (i in 0 until adapter_YPLX.count) {
            if (sampling1.YPLX.toString() == adapter_YPLX.getItem(i)) {
                mView.findViewById<Spinner>(R.id.YPLX).setSelection(i, true)
                break
            }
        }
        mView.findViewById<EditText>(R.id.YPLXQT).setText(sampling1.YPLXQT.toString())
        mView.findViewById<EditText>(R.id.WTDW).setText(sampling1.WTDW.toString())
        val adapter_CYYJ: SpinnerAdapter = mView.findViewById<Spinner>(R.id.CYYJ).adapter
        for (i in 0 until adapter_CYYJ.count) {
            if (sampling1.CYYJ.toString() == adapter_CYYJ.getItem(i)) {
                mView.findViewById<Spinner>(R.id.CYYJ).setSelection(i, true)
                break
            }
        }
        val adapter_SBXH: SpinnerAdapter = mView.findViewById<Spinner>(R.id.SBXH).adapter
        for (i in 0 until adapter_SBXH.count) {
            if (sampling1.SBXH.toString() == adapter_CYYJ.getItem(i)) {
                mView.findViewById<Spinner>(R.id.SBXH).setSelection(i, true)
                break
            }
        }
        mView.findViewById<EditText>(R.id.SBBH).setText(sampling1.SBBH.toString())
        val adapter_XCCDYJ: SpinnerAdapter = mView.findViewById<Spinner>(R.id.XCCDYJ).adapter
        for (i in 0 until adapter_XCCDYJ.count) {
            if (sampling1.XCCDYJ.toString() == adapter_XCCDYJ.getItem(i)) {
                mView.findViewById<Spinner>(R.id.XCCDYJ).setSelection(i, true)
                break
            }
        }
        val adapter_FSLX: SpinnerAdapter = mView.findViewById<Spinner>(R.id.FSLX).adapter
        for (i in 0 until adapter_FSLX.count) {
            if (sampling1.FSLX.toString() == adapter_FSLX.getItem(i)) {
                mView.findViewById<Spinner>(R.id.FSLX).setSelection(i, true)
                break
            }
        }
        mView.findViewById<EditText>(R.id.FSLXQT).setText(sampling1.FSLXQT.toString())
        mView.findViewById<EditText>(R.id.CLSS).setText(sampling1.CLSS.toString())
        val adapter_JZD: SpinnerAdapter = mView.findViewById<Spinner>(R.id.JZD).adapter
        for (i in 0 until adapter_JZD.count) {
            if (sampling1.JZD.toString() == adapter_JZD.getItem(i)) {
                mView.findViewById<Spinner>(R.id.JZD).setSelection(i, true)
                break
            }
        }
        val adapter_BZRY: SpinnerAdapter = mView.findViewById<Spinner>(R.id.BZRY).adapter
        for (i in 0 until adapter_BZRY.count) {
            if (sampling1.BZRY.toString() == adapter_BZRY.getItem(i)) {
                mView.findViewById<Spinner>(R.id.BZRY).setSelection(i, true)
                break
            }
        }
        mView.findViewById<EditText>(R.id.CDZ).setText(sampling1.CDZ.toString())
        val adapter_GDJJRQK: SpinnerAdapter = mView.findViewById<Spinner>(R.id.GDJJRQK).adapter
        for (i in 0 until adapter_GDJJRQK.count) {
            if (sampling1.GDJJRQK.toString() == adapter_GDJJRQK.getItem(i)) {
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
                //intentDetails.putExtra("cydType", 1)
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
        map["samplingId"] = sampling1.ID*/

        sampling1.ID?.let {
            RetrofitService.getApiService()
                .getCYDBySamplingId1(resources.getString(R.string.billName_type1), it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MutableList<SamplingCyd1>> {
                    override fun onComplete() {
                        Log.i("getCYDBySamplingId", "onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.i("getCYDBySamplingId", "onSubscribe")
                    }

                    override fun onNext(t: MutableList<SamplingCyd1>) {
                        Log.i("getCYDBySamplingId", "onNext")
                        Cyds.cydList1 = t
                        cydAdapter.changList(Cyds.cydList1)
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
        cydAdapter.changList(Cyds.cydList1)
    }
}