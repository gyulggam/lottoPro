package com.example.lottopro.Adapter

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
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
                sText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)
                if(sTotalBtn > 2){
                    sText.setTextColor(Color.parseColor("#5E4BE1"))
                }else {
                    if(i == 0){
                        sText.setTextColor(Color.parseColor("#3F3D55"))
                    }else if(i == 1){
                        sText.setTextColor(Color.parseColor("#40CBEA"))
                    }
                }
            } else {
                sText = view as TextView
            }


        return sText
    }
}