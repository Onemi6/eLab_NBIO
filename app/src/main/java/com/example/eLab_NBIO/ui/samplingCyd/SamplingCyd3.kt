package com.example.eLab_NBIO.ui.samplingCyd

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.dou361.dialogui.DialogUIUtils
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.models.Cyds
import com.example.eLab_NBIO.models.samplingCyd.SamplingCyd3
import com.example.eLab_NBIO.util.LocationUtil
import com.example.eLab_NBIO.util.StringUtils
import com.google.android.material.snackbar.Snackbar
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import kotlinx.android.synthetic.main.item_sampling_cyd_2.*
import kotlinx.android.synthetic.main.item_sampling_cyd_3.*
import kotlinx.android.synthetic.main.item_sampling_cyd_3.BZ
import kotlinx.android.synthetic.main.item_sampling_cyd_3.CYDMC
import kotlinx.android.synthetic.main.item_sampling_cyd_3.CYSD
import kotlinx.android.synthetic.main.item_sampling_cyd_3.CYSJ
import kotlinx.android.synthetic.main.item_sampling_cyd_3.DDL
import kotlinx.android.synthetic.main.item_sampling_cyd_3.DO
import kotlinx.android.synthetic.main.item_sampling_cyd_3.JD
import kotlinx.android.synthetic.main.item_sampling_cyd_3.PH
import kotlinx.android.synthetic.main.item_sampling_cyd_3.QW
import kotlinx.android.synthetic.main.item_sampling_cyd_3.SS
import kotlinx.android.synthetic.main.item_sampling_cyd_3.SW
import kotlinx.android.synthetic.main.item_sampling_cyd_3.TMD
import kotlinx.android.synthetic.main.item_sampling_cyd_3.WD
import kotlinx.android.synthetic.main.item_sampling_cyd_3.XM
import kotlinx.android.synthetic.main.item_sampling_cyd_3.XZ
import kotlinx.android.synthetic.main.item_sampling_cyd_3.YPXH
import kotlinx.android.synthetic.main.item_sampling_cyd_3.YS

class SamplingCyd3 : Fragment() {
    private lateinit var mView: View
    private var cyd: SamplingCyd3 = SamplingCyd3()
    private var _context: Context? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true);
        mView = inflater.inflate(R.layout.item_sampling_cyd_3, container, false)
        _context = activity
        initData()
        viewAction()
        return mView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cyd_part, menu)
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
            R.id.action_cyd_location -> {
                if ( activity?.let { LocationUtil.getInstance(it)?.isLocationProviderEnabled() } == true) {
                    val location = LocationUtil.getInstance(requireActivity())?.showLocation()
                    if (location != null) {
                        //tv_location.text = "地理位置：lon:${location.longitude};lat:${location.latitude}"
                        //val address: String = "纬度：" + location.latitude + ",经度：" + location.longitude
                        //tv_location.text = address
                        //tv_location.text=getAddress(location.getLongitude(),location.getLatitude())

                        mView.findViewById<EditText>(R.id.JD).setText(location.longitude.toString())
                        mView.findViewById<EditText>(R.id.WD).setText(location.latitude.toString())

                        LocationUtil.removeLocationUpdatesListener()
                    }
                } else {
                    Snackbar.make(
                        CYDMC, "需要定位权限,请先开启!",
                        Snackbar.LENGTH_LONG
                    ).setAction("Action", null)
                        .show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun viewAction() {
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
        cyd=Cyds.cydList3[Cyds.position]
        mView.findViewById<EditText>(R.id.CYDMC).setText(cyd.CYDMC)
        mView.findViewById<TextView>(R.id.CYSJ).text = cyd.CYSJ
        mView.findViewById<EditText>(R.id.YPXH).setText(cyd.YPXH)
        mView.findViewById<EditText>(R.id.XM).setText(cyd.XM)
        mView.findViewById<EditText>(R.id.JD).setText(cyd.JD)
        mView.findViewById<EditText>(R.id.WD).setText(cyd.WD)
        mView.findViewById<EditText>(R.id.SS).setText(cyd.SS)
        mView.findViewById<EditText>(R.id.CYSD).setText(cyd.CYSD)
        mView.findViewById<EditText>(R.id.TMD).setText(cyd.TMD)
        mView.findViewById<EditText>(R.id.SHUISE).setText(cyd.SHUISE)
        mView.findViewById<EditText>(R.id.SW).setText(cyd.SW)
        mView.findViewById<EditText>(R.id.DDL).setText(cyd.DDL)
        mView.findViewById<EditText>(R.id.PH).setText(cyd.PH)
        mView.findViewById<EditText>(R.id.DO).setText(cyd.DO)
        mView.findViewById<EditText>(R.id.YS).setText(cyd.YS)
        mView.findViewById<EditText>(R.id.QW).setText(cyd.QW)
        mView.findViewById<EditText>(R.id.XZ).setText(cyd.XZ)
        mView.findViewById<EditText>(R.id.BZ).setText(cyd.BZ)
    }

    private  fun saveCyd(){
        Cyds.cydList3[Cyds.position].CYDMC = CYDMC.text.toString()
        Cyds.cydList3[Cyds.position].CYSJ = CYSJ.text.toString()
        Cyds.cydList3[Cyds.position].YPXH = YPXH.text.toString()
        Cyds.cydList3[Cyds.position].XM = XM.text.toString()
        Cyds.cydList3[Cyds.position].JD = JD.text.toString()
        Cyds.cydList3[Cyds.position].WD = WD.text.toString()
        Cyds.cydList3[Cyds.position].SS = SS.text.toString()
        Cyds.cydList3[Cyds.position].CYSD = CYSD.text.toString()
        Cyds.cydList3[Cyds.position].TMD = TMD.text.toString()
        Cyds.cydList3[Cyds.position].SHUISE = SHUISE.text.toString()
        Cyds.cydList3[Cyds.position].SW = SW.text.toString()
        Cyds.cydList3[Cyds.position].DDL = DDL.text.toString()
        Cyds.cydList3[Cyds.position].PH = PH.text.toString()
        Cyds.cydList3[Cyds.position].DO = DO.text.toString()
        Cyds.cydList3[Cyds.position].YS = YS.text.toString()
        Cyds.cydList3[Cyds.position].QW = QW.text.toString()
        Cyds.cydList3[Cyds.position].XZ = XZ.text.toString()
        Cyds.cydList3[Cyds.position].BZ = BZ.text.toString()

        Snackbar.make(CYDMC, "保存成功",
            Snackbar.LENGTH_LONG).setAction("Action", null)
            .show();
    }
}
