package com.example.eLab_NBIO.ui.SamplingCyd

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dou361.dialogui.DialogUIUtils
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.adapter.SamplingCydAdapter
import com.example.eLab_NBIO.http.RetrofitService
import com.example.eLab_NBIO.models.*
import com.example.eLab_NBIO.models.Sampling.Sampling1
import com.example.eLab_NBIO.models.SamplingCyd.SamplingCyd1
import com.example.eLab_NBIO.ui.CydActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cyd.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_sampling1.*
import kotlinx.android.synthetic.main.item_sampling_cyd_1.*
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class SamplingCyd1 : Fragment(),  AdapterView.OnItemSelectedListener {
    private lateinit var mView: View
    private var cyd: SamplingCyd1 = SamplingCyd1()
    private var _context: Context? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true);
        mView = inflater.inflate(R.layout.item_sampling_cyd_1, container, false)
        _context = activity
        initData()
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val context: Context? = this.context
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cyd_save -> {
                Log.v("action", "save")
                val dialogLogin = DialogUIUtils.showLoading(
                    _context, "保存中...", false, true,
                    false,
                    false
                )
                dialogLogin.show()
                saveCyd()
                DialogUIUtils.dismiss(dialogLogin)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun initData() {
        cyd=Cyds.cydList1[Cyds.position]
        mView.findViewById<EditText>(R.id.CYDMC).setText(cyd.CYDMC)
        mView.findViewById<EditText>(R.id.CYSJ).setText(cyd.CYSJ)
        mView.findViewById<EditText>(R.id.YPXH).setText(cyd.YPXH)
        mView.findViewById<EditText>(R.id.FXXM).setText(cyd.FXXM)
        mView.findViewById<EditText>(R.id.YS).setText(cyd.YS)
        mView.findViewById<EditText>(R.id.QW).setText(cyd.QW)
        mView.findViewById<EditText>(R.id.XZ).setText(cyd.XZ)
        mView.findViewById<EditText>(R.id.FY).setText(cyd.FY)
        mView.findViewById<EditText>(R.id.SW).setText(cyd.SW)
        mView.findViewById<EditText>(R.id.PH).setText(cyd.PH)
        mView.findViewById<EditText>(R.id.BCTJ).setText(cyd.BCTJ)
        mView.findViewById<EditText>(R.id.BZ).setText(cyd.BZ)
    }

    private  fun saveCyd(){
        Cyds.cydList1[Cyds.position].CYDMC = CYDMC.text.toString()
        Cyds.cydList1[Cyds.position].CYSJ = CYSJ.text.toString()
        Cyds.cydList1[Cyds.position].YPXH = YPXH.text.toString()
        Cyds.cydList1[Cyds.position].FXXM = FXXM.text.toString()
        Cyds.cydList1[Cyds.position].YS = YS.text.toString()
        Cyds.cydList1[Cyds.position].QW = QW.text.toString()
        Cyds.cydList1[Cyds.position].XZ = XZ.text.toString()
        Cyds.cydList1[Cyds.position].FY = FY.text.toString()
        Cyds.cydList1[Cyds.position].SW = SW.text.toString()
        Cyds.cydList1[Cyds.position].PH = PH.text.toString()
        Cyds.cydList1[Cyds.position].BCTJ = BCTJ.text.toString()
        Cyds.cydList1[Cyds.position].BZ = BZ.text.toString()

        Snackbar.make(CYDMC, "保存成功",
            Snackbar.LENGTH_LONG).setAction("Action", null)
            .show();
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
