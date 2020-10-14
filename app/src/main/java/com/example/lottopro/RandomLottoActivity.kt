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
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.lottopro.Adapter.ButtonAdapter
import com.example.lottopro.DataBase.SqlHelper
import com.example.lottopro.Str.LottoNum
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.common.util.CollectionUtils
import kotlinx.android.synthetic.main.constellation.*
import kotlinx.android.synthetic.main.header_lotto.*
import java.util.ArrayList


class RandomLottoActivity : AppCompatActivity() {
    internal lateinit var  gDb: SqlHelper
    internal var gLottoList:List<LottoNum> = ArrayList<LottoNum>()
    private var gSelLotto = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gDb = SqlHelper(this)
        setContentView(R.layout.random_lotto)

        //mainText 색변경
        var sMainStr = mainText.text.toString()
        var sSpannable = SpannableString(sMainStr)
        var sChangeStr = "랜덤"
        var sStartStr = sMainStr.indexOf(sChangeStr)
        var sEndStr = sStartStr + sChangeStr.length

        sSpannable.setSpan(ForegroundColorSpan(Color.parseColor("#5E4BE1")), sStartStr, sEndStr, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        mainText.text = sSpannable
        //mainText 색변경


        val sToolbar = lottoHeader as Toolbar?
        setSupportActionBar(sToolbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        headerText.text = "랜덤로또"

        MobileAds.initialize(this) {}
        var  mAdView = adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

//        로또 번호 확인 버튼의 클릭이벤트 리스너 설정
        goResultButton.setOnClickListener{
            GridLayoutLayout3.removeAllViewsInLayout()
            gSelLotto = LottoNumberMaker.getRandomLottoNumbers()

            for (i in 1.. 6) {
                GridLayoutLayout3.columnCount = 6
                GridLayoutLayout3.rowCount = 1
                GridLayoutLayout3.useDefaultMargins = true
                val sCol: GridLayout.Spec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1F)
                val sRow: GridLayout.Spec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1F)
                var sGridParam: GridLayout.LayoutParams
                val sBall = "ball_"+gSelLotto[i-1]
                val sBtn = ImageButton(this)
//            sBtn.setBackgroundResource(R.drawable.ball_1)
                sBtn.setBackgroundResource(resources.getIdentifier(sBall,"drawable", packageName))
                sBtn.foregroundGravity = Gravity.CENTER_HORIZONTAL

                sGridParam = GridLayout.LayoutParams(sRow, sCol)
                GridLayoutLayout3.addView(sBtn, sGridParam)
//                val animation: Animation =
//                    AnimationUtils.loadAnimation(applicationContext, R.anim.overshoot)
//                GridLayoutLayout3.startAnimation(animation)
            }
        }

//       로또번호저장 버튼의 클릭이벤트 리스너 설정
        goSaveButton.setOnClickListener{
            if(CollectionUtils.isEmpty(gSelLotto)){
                Toast.makeText(applicationContext, "번호를 뽑아주세요!", Toast.LENGTH_SHORT).show()
            }else{

                val sStrArray = gSelLotto.map{ it.toString() }.toTypedArray()
                val sLottoNum = LottoNum(0, sStrArray.joinToString(","))

                gDb.addLottoNum(sLottoNum)
                Toast.makeText(applicationContext, "로또번호를 저장했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        viewBackBtn.setOnClickListener {
            finish()
        }
    }

//    private fun refreshData() {
//        toolLottoLay.removeAllViews()
//        gLottoList = gDb.gAllLottoNum
//        val sDelBtn = Button(this)
//        val sDelBtnPam =  LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT)
//        toolLottoLay.margin(20F,0F,20F,10F)
//
//        sDelBtnPam.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
//        sDelBtnPam.topMargin = 30
//
//        sDelBtn.layoutParams = sDelBtnPam
//        sDelBtn.text = "전체 삭제"
//        sDelBtn.gravity = Gravity.CENTER
//        sDelBtn.background = ContextCompat.getDrawable(this,R.drawable.save_button)
//        sDelBtn.setTextColor(Color.parseColor("#ffffff"))
//
//        sDelBtn.setOnClickListener {
//            dbReset()
//        }
//
//        for ((index, value) in gLottoList.withIndex()) {
//            lateinit var sLottoList : Array<String>
//            val sAddGrid: GridView = GridView(this)
//            val sAddGridPram = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//            var sStr = value.number
//
//            sAddGridPram.height = dpToPx(43F)
//            sAddGrid.layoutParams = sAddGridPram
//            sAddGrid.numColumns = 6
//            sAddGrid.background = ContextCompat.getDrawable(this,R.drawable.lotto_grid_view)
//
//            toolLottoLay.addView(sAddGrid)
//
//            if (sStr !== null) {
//                sLottoList= sStr?.split(",").toTypedArray()
//            }
//
//            if (sLottoList != null) {
//                var sAdapter = ButtonAdapter(this, sLottoList)
//
//                sAddGrid.adapter = sAdapter
//            }
//        }
//        toolLottoLay.addView(sDelBtn)
//    }
//    private fun dbReset() {
//        for (value in gLottoList) {
//            val sLottoNum = LottoNum(value.id, "")
//            gDb.deleteLottoNum(sLottoNum)
//        }
//        refreshData()
//    }

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