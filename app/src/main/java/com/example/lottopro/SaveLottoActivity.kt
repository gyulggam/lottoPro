package com.example.lottopro

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import com.example.lottopro.Adapter.ButtonAdapter
import com.example.lottopro.Adapter.PatternButtonAdapter
import com.example.lottopro.DataBase.SqlHelper
import com.example.lottopro.Str.LottoNum
import kotlinx.android.synthetic.main.header_lotto.*
import kotlinx.android.synthetic.main.save_lotto.*

class SaveLottoActivity : AppCompatActivity() {
    private lateinit var  gDb: SqlHelper
    private var gLottoList:List<LottoNum> = ArrayList<LottoNum>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gDb = SqlHelper(this)
        setContentView(R.layout.save_lotto)
        drawSaveLotto()

        //mainText 색변경
        var sMainStr = saveTopLayoutText.text.toString()
        var sSpannable = SpannableString(sMainStr)
        var sChangeStr = "저장한 번호"
        var sStartStr = sMainStr.indexOf(sChangeStr)
        var sEndStr = sStartStr + sChangeStr.length

        sSpannable.setSpan(ForegroundColorSpan(Color.parseColor("#5E4BE1")),
            sStartStr,
            sEndStr,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        saveTopLayoutText.text = sSpannable
        //mainText 색변경

        val sToolbar = lottoHeader as Toolbar?
        setSupportActionBar(sToolbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        headerText.text = "번호 내역"

        allDeleteBtn.setOnClickListener {
            dbReset()
            drawSaveLotto()
        }
        viewBackBtn.setOnClickListener {
            finish()
        }
    }

    private fun drawSaveLotto() {
        gLottoList = gDb.gAllLottoNum

        saveLottoView.removeAllViews()

        for ((index, value) in gLottoList.withIndex()) {
            lateinit var sLottoList : Array<String>
            val sAddGrid: GridView = GridView(this)
            val sAddGridPram = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            var sStr = value.number

            sAddGridPram.height = dpToPx(42F)
            sAddGrid.layoutParams = sAddGridPram
            sAddGrid.margin(0F, 10F,0F,0F)
            sAddGrid.numColumns = 6
            sAddGrid.horizontalSpacing = 20

            if (index !== gLottoList.size -1) {
                sAddGrid.background = ContextCompat.getDrawable(this, R.drawable.save_lotto_grid)
            }

            saveLottoView.addView(sAddGrid)

            if (sStr !== null) {
                sLottoList= sStr?.split(",").toTypedArray()
            }

            if (sLottoList != null) {
                var sAdapter = PatternButtonAdapter(this, sLottoList)
                sAddGrid.adapter = sAdapter
            }
        }
    }

    private fun dbReset() {
        for (value in gLottoList) {
            val sLottoNum = LottoNum(value.id, "")
            gDb.deleteLottoNum(sLottoNum)
        }
    }

    // 레이아웃에 마진 적용 할때 쓰는 함수
    fun View.margin(
        left: Float? = null,
        top: Float? = null,
        right: Float? = null,
        bottom: Float? = null
    ) {
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
    fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources.displayMetrics
    ).toInt()
    // 레이아웃에 마진 적용 할때 쓰는 함수
}

