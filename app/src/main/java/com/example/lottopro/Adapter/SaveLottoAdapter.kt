package com.example.lottopro.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.lottopro.Str.LottoNum
import java.util.ArrayList

class SaveLottoAdapter(private val aContext: Context, internal val aLottoNum: Array<String>?) : BaseAdapter() {
    private val sTotalBtn = aLottoNum!!.size

    override fun getCount(): Int {
        return sTotalBtn
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        val sBtn: ImageButton
        var sBall = "save_ball_${aLottoNum?.get(i)}"

        if (view == null) {
            sBtn = ImageButton(aContext)
            sBtn.setBackgroundResource(aContext.resources.getIdentifier(sBall,"drawable", aContext.packageName))
        } else {
            sBtn = view as ImageButton
        }

        sBtn.setOnClickListener {

        }
        return sBtn
    }
}