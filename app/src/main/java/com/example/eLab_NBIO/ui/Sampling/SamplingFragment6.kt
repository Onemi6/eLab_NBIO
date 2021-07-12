package com.example.eLab_NBIO.ui.Sampling

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dou361.dialogui.DialogUIUtils
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.adapter.SamplingCydAdapter
import com.example.eLab_NBIO.http.RetrofitService
import com.example.eLab_NBIO.models.*
import com.example.eLab_NBIO.models.Sampling.Sampling6
import com.example.eLab_NBIO.models.SamplingCyd.SamplingCyd6
import com.example.eLab_NBIO.ui.CydActivity
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_sampling6.*

class SamplingFragment6 : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var mView: View
    private lateinit var rvSamplingInfo: RecyclerView
    private lateinit var cydAdapter: SamplingCydAdapter<SamplingCyd6>
    private var sampling6: Sampling6 = Sampling6()
    private var _context: Context? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true);
        mView = inflater.inflate(R.layout.fragment_sampling6, container, false)
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
                val cyd = SamplingCyd6()
                Cyds.cydList6.add(cyd)
                cydAdapter.changList(Cyds.cydList6)
            }
            R.id.action_info_save -> {
                Log.v("action", "save")
                val dialogLogin = DialogUIUtils.showLoading(
                    _context, "登录中...", false, true,
                    false,
                    false
                )
                dialogLogin.show()

                Tasks.taskList6[Tasks.position].CYHQ = CYHQ.text.toString()
                Tasks.taskList6[Tasks.position].HC = HC.text.toString()
                Tasks.taskList6[Tasks.position].CYD = CYD.text.toString()
                Tasks.taskList6[Tasks.position].CYSJ = CYSJ.text.toString()
                Tasks.taskList6[Tasks.position].BH = BH.text.toString()
                Tasks.taskList6[Tasks.position].SC = SC.text.toString()

                val samplingData = SamplingData(Tasks.taskList6[Tasks.position], Cyds.cydList6)
                val submitSamplingData = SubmitSamplingData(resources.getString(R.string.billName_type6), Gson().toJson(samplingData))

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
                            Log.i("message", t.Message.toString())
                        }

                        override fun onError(e: Throwable) {
                            Log.i("SubmitSamplingData", e.message.toString())
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
        rvSamplingInfo = mView.findViewById(R.id.rv_samplingInfo6_add)
        rvSamplingInfo.setHasFixedSize(true);
        rvSamplingInfo.isNestedScrollingEnabled = false

        //创建LinearLayoutManager 对象 这里使用 LinearLayoutManager 是线性布局的意思
        val layoutManager = LinearLayoutManager(activity)
        //设置RecyclerView 布局
        rvSamplingInfo.layoutManager = layoutManager
        //设置Adapter
        cydAdapter = SamplingCydAdapter(context, Cyds.cydList6, 6)
        rvSamplingInfo.adapter = cydAdapter
    }

    private fun initData() {
        sampling6 = Tasks.taskList6[Tasks.position]

        mView.findViewById<EditText>(R.id.CYHQ).setText(sampling6.CYHQ)
        mView.findViewById<EditText>(R.id.HC).setText(sampling6.HC)
        mView.findViewById<EditText>(R.id.CYD).setText(sampling6.CYD)
        mView.findViewById<EditText>(R.id.CYSJ).setText(sampling6.CYSJ)
        mView.findViewById<EditText>(R.id.BH).setText(sampling6.BH)
        mView.findViewById<EditText>(R.id.SC).setText(sampling6.SC)

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
                intentDetails.putExtra("cydType", 6)
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

        sampling6.ID?.let {
            RetrofitService.getApiService()
                .getCYDBySamplingId6(resources.getString(R.string.billName_type6), it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MutableList<SamplingCyd6>> {
                    override fun onComplete() {
                        Log.i("getCYDBySamplingId", "onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.i("getCYDBySamplingId", "onSubscribe")
                    }

                    override fun onNext(t: MutableList<SamplingCyd6>) {
                        Log.i("getCYDBySamplingId", "onNext")
                        Cyds.cydList6 = t
                        cydAdapter.changList(Cyds.cydList6)
                    }

                    override fun onError(e: Throwable) {
                        Log.i("getCYDBySamplingId", e.message.toString())
                    }
                })
        }
        DialogUIUtils.dismiss(dialogLogin)
    }

    override fun onResume() {
        super.onResume()
        cydAdapter.changList(Cyds.cydList6)
    }
}