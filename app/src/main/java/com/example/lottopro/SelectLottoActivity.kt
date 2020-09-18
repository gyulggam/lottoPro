package com.example.lottopro

import android.app.ActionBar
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.select_lotto.*
import timber.log.Timber
import java.util.*

class SelectLottoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setContentView(R.layout.select_lotto)

        var gMaxSelLotto = 6
        var gMaxLottoNum = 46
        var gSelLotto = mutableListOf<Int>()

        linearLayout1.margin(0F, 10F, 0F, 0F)
        linearLayout2.margin(0F, 10F, 0F, 0F)
        linearLayout3.margin(0F, 10F, 0F, 0F)
        linearLayout4.margin(0F, 10F, 0F, 0F)
        linearLayout5.margin(0F, 10F, 0F, 0F)
        linearLayout6.margin(0F, 10F, 0F, 0F)
        linearLayout7.margin(0F, 10F, 0F, 0F)
        linearLayout8.margin(0F, 10F, 0F, 0F)
        linearLayout9.margin(0F, 10F, 0F, 0F)
        linearLayout10.margin(0F, 10F, 0F, 0F)
        linearLayout11.margin(0F, 10F, 0F, 0F)
        linearLayout12.margin(0F, 10F, 0F, 0F)

        for (i in 1 until gMaxLottoNum) {
            var sIsClick = false
            val sBtn = Button(this).apply {
                text = i.toString()
                width = dpToPx(85F)
                height = dpToPx(85F)

                this.setOnClickListener {
                    if(sIsClick) {
                        this.setBackgroundResource(R.drawable.button_shape)
                        gSelLotto.remove(i)
                        sIsClick = false
                    } else {
                        if (gMaxSelLotto === gSelLotto.size) return@setOnClickListener

                        when {
                            i < 11 -> this.setBackgroundResource(R.drawable.lotto_num1)
                            i < 21 -> this.setBackgroundResource(R.drawable.lotto_num10)
                            i < 31 -> this.setBackgroundResource(R.drawable.lotto_num20)
                            i < 41 -> this.setBackgroundResource(R.drawable.lotto_num30)
                            else-> this.setBackgroundResource(R.drawable.lotto_num40)
                        }

                        gSelLotto.add(i.toInt())
                        sIsClick = true
                    }

                    Log.d("Select Lotto", gSelLotto.toString())
                }
            }

            sBtn.setBackgroundResource(R.drawable.button_shape)

            when {
                i < 5 -> {
                    linearLayout1.addView(sBtn)
                }
                i < 9 -> {
                    linearLayout2.addView(sBtn)
                }
                i < 13 -> {
                    linearLayout3.addView(sBtn)
                }
                i < 17 -> {
                    linearLayout4.addView(sBtn)
                }
                i < 21 -> {
                    linearLayout5.addView(sBtn)
                }
                i < 25 -> {
                    linearLayout6.addView(sBtn)
                }
                i < 29 -> {
                    linearLayout7.addView(sBtn)
                }
                i < 33 -> {
                    linearLayout8.addView(sBtn)
                }
                i < 37 -> {
                    linearLayout9.addView(sBtn)
                }
                i < 41 -> {
                    linearLayout10.addView(sBtn)
                }
                i < 45 -> {
                    linearLayout11.addView(sBtn)
                }
                else -> {
                    linearLayout12.addView(sBtn)
                }
            }
        }
    }

    // 레이아웃에 마진 적용 할때 쓰는 함수
    fun View.margin(left: Float? = null, top: Float? = null, right: Float? = null, bottom: Float? = null) {
        layoutParams<ViewGroup.MarginLayoutParams> {
            left?.run { leftMargin = dpToPx(this) }
            top?.run { topMargin = dpToPx(this) }
            right?.run { rightMargin = dpToPx(this) }
            bottom?.run { bottomMargin = dpToPx(this) }
        }
    }

    inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
        if (layoutParams is T) block(layoutParams as T)
    }

    fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
    fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
    // 레이아웃에 마진 적용 할때 쓰는 함수
}