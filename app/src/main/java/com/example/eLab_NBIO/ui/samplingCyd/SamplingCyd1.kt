package com.example.eLab_NBIO.ui.samplingCyd

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.dou361.dialogui.DialogUIUtils
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.models.Cyds
import com.example.eLab_NBIO.models.samplingCyd.SamplingCyd1
import com.example.eLab_NBIO.util.StringUtils
import com.google.android.material.snackbar.Snackbar
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import kotlinx.android.synthetic.main.item_sampling_cyd_1.*

class SamplingCyd1 : Fragment() {
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
        viewAction()
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

    private  fun viewAction(){
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

    private fun initData() {
        cyd=Cyds.cydList1[Cyds.position]
        mView.findViewById<EditText>(R.id.CYDMC).setText(cyd.CYDMC)
        mView.findViewById<TextView>(R.id.CYSJ).text = cyd.CYSJ
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
}
