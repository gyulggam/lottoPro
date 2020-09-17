package com.example.lottopro

import android.app.ActionBar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import timber.log.Timber
import android.graphics.Color
import org.w3c.dom.Text
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.*
import kotlin.math.roundToInt

class SelectNumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())

        val sMaxLottoCnt = 46

        for(i in 1..sMaxLottoCnt) {
            drawLottoNum(i)
            Timber.d("$i")
        }
    }

    private  fun drawLottoNum(aCnt: Int) {
//        val sView = findViewById<LinearLayout>(R.id.lottoNumView)
        val sBtn = Button(this)
        val sLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        sBtn.layoutParams = sLayoutParams
        sBtn.id = aCnt
        sBtn.text = aCnt.toString()
        sBtn.top
        sBtn.setTextColor(Color.BLACK)

        when {
            aCnt < 11 -> sBtn.setBackgroundColor(Color.RED)
            aCnt < 21 -> sBtn.setBackgroundColor(Color.GREEN)
            aCnt < 31 -> sBtn.setBackgroundColor(Color.GRAY)
            aCnt < 41 -> sBtn.setBackgroundColor(Color.MAGENTA)
            else -> sBtn.setBackgroundColor(Color.YELLOW)
        }

        sLayoutParams.setMargins(changeDP(10),changeDP(20),changeDP(10),0)
    }

    private fun changeDP(value : Int) : Int{
        var displayMetrics = resources.displayMetrics
        var dp = (value * displayMetrics.density).roundToInt()
        return dp
    }
}
