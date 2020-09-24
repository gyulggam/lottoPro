package com.example.lottopro.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.lottopro.Str.LottoNum


class GridAdapter(private val aContext: Context, internal val aLottoNum: List<LottoNum>) : BaseAdapter() {
    private val sGrid = aLottoNum.size

    override fun getCount(): Int {
        return sGrid
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        val sGridView: GridView

        if (view == null) {
            sGridView = GridView(aContext)
        } else {
            sGridView = view as GridView
        }

        for(value in aLottoNum) {
            println(value.id)
            println(value.number)
        }
        return sGridView
    }
}