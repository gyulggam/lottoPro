package com.example.lottopro.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.lottopro.Str.LottoNum


class ButtonAdapter(private val aContext: Context, internal val aLottoNum: Array<String>) : BaseAdapter() {
    private val sTotalBtn = 6

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
        var sBall = "ball_${aLottoNum[i]}"

        if (view == null) {
            sBtn = ImageButton(aContext)
            sBtn.setBackgroundResource(aContext.resources.getIdentifier(sBall,"drawable", aContext.packageName))
        } else {
            sBtn = view as ImageButton
        }

        sBtn.setOnClickListener { v ->
            Toast.makeText(
                v.context,
                "Button #" + (i + 1),
                Toast.LENGTH_SHORT
            ).show()
        }
        return sBtn
    }
}