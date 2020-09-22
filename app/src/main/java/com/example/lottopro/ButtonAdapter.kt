package com.example.lottopro

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.Toast


class ButtonAdapter(private val aContext: Context) : BaseAdapter() {
    private var sBtnId = 0
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
        val sBtn: Button

        if (view == null) {
            sBtn = Button(aContext)
            sBtn.text = "Button " + ++sBtnId
        } else {
            sBtn = view as Button
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