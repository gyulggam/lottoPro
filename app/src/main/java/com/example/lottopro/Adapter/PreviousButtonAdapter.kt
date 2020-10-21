package com.example.lottopro.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.lottopro.Str.LottoNum
import java.util.ArrayList

class PreviousButtonAdapter(private val aContext: Context, internal val aLottoNum: Array<String>?) : BaseAdapter() {
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
        val sText: TextView

            if (view == null) {
                sText = TextView(aContext)
                sText.text = "${aLottoNum?.get(i)}"

            } else {
                sText = view as TextView
            }


        return sText
    }
}