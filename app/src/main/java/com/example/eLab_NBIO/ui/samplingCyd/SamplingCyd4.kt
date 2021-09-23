package com.example.eLab_NBIO.ui.samplingCyd

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.dou361.dialogui.DialogUIUtils
import com.example.eLab_NBIO.R
import com.example.eLab_NBIO.models.Cyds
import com.example.eLab_NBIO.models.samplingCyd.SamplingCyd4
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_sampling_cyd_4.*

class SamplingCyd4 : Fragment(),  AdapterView.OnItemSelectedListener {
    private lateinit var mView: View
    private var cyd: SamplingCyd4 = SamplingCyd4()
    private var _context: Context? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true);
        mView = inflater.inflate(R.layout.item_sampling_cyd_4, container, false)
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
        cyd=Cyds.cydList4[Cyds.position]
        mView.findViewById<EditText>(R.id.ZWH).setText(cyd.ZWH)
        mView.findViewById<EditText>(R.id.YPBH).setText(cyd.YPBH)
        mView.findViewById<EditText>(R.id.CJDLX).setText(cyd.CJDLX)
        mView.findViewById<EditText>(R.id.BZ).setText(cyd.BZ)
    }

    private  fun saveCyd(){
        Cyds.cydList4[Cyds.position].ZWH = ZWH.text.toString()
        Cyds.cydList4[Cyds.position].YPBH = YPBH.text.toString()
        Cyds.cydList4[Cyds.position].CJDLX = CJDLX.text.toString()
        Cyds.cydList4[Cyds.position].BZ = BZ.text.toString()
        Snackbar.make(ZWH, "保存成功",
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
