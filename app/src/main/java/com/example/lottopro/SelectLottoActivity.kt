package com.example.lottopro

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_select_lotto.*
import kotlinx.android.synthetic.main.select_lotto.*
import timber.log.Timber

class SelectLottoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())

        setContentView(R.layout.activity_select_lotto)

        var gMaxSelLotto = 6
        var gMaxLottoNum = 46
        var gSelLotto = mutableListOf<Int>()
        var gRowIdx = 0
        var gColIdx = 0
        var gMaxCol = 7
        var gMaxRow = 8

        lottoGridLayout.columnCount = gMaxCol
        lottoGridLayout.rowCount = gMaxRow
        saveBtn.text = "번호 저장"

        lottoGrid.adapter = ButtonAdapter(this)

        for (i in 1 until gMaxLottoNum) {
            var sIsClick = false
            val sCol : GridLayout.Spec = GridLayout.spec(GridLayout.UNDEFINED,1, 1F)
            val sRow : GridLayout.Spec = GridLayout.spec(GridLayout.UNDEFINED,1, 1F)
            var sGridParam :GridLayout.LayoutParams

            val sBtn = ImageButton(this).apply {
                this.setOnClickListener {
                    if(sIsClick) {
                        this.setBackgroundResource(R.drawable.button_shape)
                        gSelLotto.remove(i)
                        sIsClick = false
                    } else {
                        if (gMaxSelLotto === gSelLotto.size) {
                            Toast.makeText(applicationContext, "어림없지 6개까지다 쒜끼야~", Toast.LENGTH_LONG).show();
                            return@setOnClickListener
                        }

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
            sBtn.foregroundGravity = Gravity.CENTER_HORIZONTAL

            if (i % 4 == 0) {
                gRowIdx++
                gColIdx = 0
            }

            sGridParam = GridLayout.LayoutParams(sRow, sCol)
            lottoGridLayout.addView(sBtn, sGridParam)

            gColIdx++
        }

        saveBtn.setOnClickListener {
            saveBtn(gSelLotto)
        }
    }

    private fun saveBtn(gSelLotto: MutableList<Int>) {
        if (gSelLotto.size < 6) {
            Toast.makeText(applicationContext, "6개 번호를 선택해야 합니다.", Toast.LENGTH_LONG).show();
        } else {

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