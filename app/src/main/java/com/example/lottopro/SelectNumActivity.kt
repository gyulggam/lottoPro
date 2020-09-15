package com.example.lottopro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.view.View
import timber.log.Timber
import org.w3c.dom.Text

class SelectNumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setContentView(R.layout.lotto_select_num)
        ClickEventClass()
    }
}

class ClickEventClass : AppCompatActivity() {
    fun selectNumClick(view: View) {
        val sSelectNum = findViewById<Button>(R.id.lottoNum)
        val sTextCount = findViewById<TextView>(R.id.textCount)
        var sCnt = 0


        sSelectNum.setOnClickListener {
            sCnt++
            sTextCount.text = sCnt.toString()

        }
    }
}


