package com.example.eLab_NBIO.ui.SamplingCyd

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.dou361.dialogui.DialogUIUtils
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.models.*
import com.example.eLab_NBIO.models.SamplingCyd.SamplingCyd3
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_cyd.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_sampling1.*
import kotlinx.android.synthetic.main.item_sampling_cyd_1.*
import kotlinx.android.synthetic.main.item_sampling_cyd_1.BZ
import kotlinx.android.synthetic.main.item_sampling_cyd_1.CYDMC
import kotlinx.android.synthetic.main.item_sampling_cyd_1.CYSJ
import kotlinx.android.synthetic.main.item_sampling_cyd_1.PH
import kotlinx.android.synthetic.main.item_sampling_cyd_1.QW
import kotlinx.android.synthetic.main.item_sampling_cyd_1.SW
import kotlinx.android.synthetic.main.item_sampling_cyd_1.XZ
import kotlinx.android.synthetic.main.item_sampling_cyd_1.YPXH
import kotlinx.android.synthetic.main.item_sampling_cyd_1.YS
import kotlinx.android.synthetic.main.item_sampling_cyd_3.*

class SamplingCyd3 : Fragment(),  AdapterView.OnItemSelectedListener {
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
        cyd=Cyds.cydList3[Cyds.position]
        mView.findViewById<EditText>(R.id.CYDMC).setText(cyd.CYDMC)
        mView.findViewById<EditText>(R.id.CYSJ).setText(cyd.CYSJ)
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

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
