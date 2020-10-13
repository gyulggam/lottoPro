package com.example.lottopro

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lottopro.Adapter.ButtonAdapter
import com.example.lottopro.DataBase.SqlHelper
import com.example.lottopro.Str.LottoNum
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main3.*
import timber.log.Timber
import kotlin.collections.ArrayList


class MainActivity3 : AppCompatActivity() {
    internal lateinit var  gDb: SqlHelper
    internal var gLottoList:List<LottoNum> = ArrayList<LottoNum>()
    private var gSelLotto = mutableListOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Disable the `NetworkOnMainThreadException` and make sure it is just logged.
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())


        gDb = SqlHelper(this)

        Timber.plant(Timber.DebugTree())
        setContentView(R.layout.activity_main)

        refreshData()

        MobileAds.initialize(this) {}
        var  mAdView = adView
        val adRequest = AdRequest.Builder().build()



        var sIntent = Intent();

        menuBtn1.setOnClickListener{
            sIntent = Intent(this@MainActivity3, ConstellationActivity::class.java)
            startActivity(sIntent);
        }
        menuBtn2.setOnClickListener{
            val sIntent = Intent(this@MainActivity3, RandomLottoActivity::class.java)
            startActivity(sIntent);
        }
        menuBtn3.setOnClickListener{
            val sIntent = Intent(this@MainActivity3, PatternLottoActivity::class.java)
            startActivity(sIntent);
        }
        menuBtn4.setOnClickListener{
            val intent = Intent(this@MainActivity3, SelectLottoActivity::class.java)
            startActivity(intent);
        }



    }

    override fun onResume() {
        super.onResume()
        refreshData()

    }

    private fun refreshData() {
        var slottoApi = LottoNumberMaker.getNumber()?.toTypedArray()
//        toolMiddle.setText("${lottoApi3}번회차 당첨번호")

        val sAddGridPram = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        sAddGridPram.height = dpToPx(43F)

        toolLottoLay.removeAllViews()
        toolLottoLay2.removeAllViews()
        gLottoList = gDb.gAllLottoNum
        val sDelBtn = Button(this)
        val sDelBtnPam =  LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        toolLottoLay.margin(20F, 0F, 20F, 10F)
        toolLottoLay2.margin(20F, 0F, 20F, 10F)
        sDelBtnPam.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
        sDelBtnPam.topMargin = 30

        sDelBtn.layoutParams = sDelBtnPam
        sDelBtn.text = "전체 삭제"
        sDelBtn.gravity = Gravity.CENTER
        sDelBtn.background = ContextCompat.getDrawable(this, R.drawable.save_button)
        sDelBtn.setTextColor(Color.parseColor("#ffffff"))

        sDelBtn.setOnClickListener {
            dbReset()
        }

        for ((index, value) in gLottoList.withIndex()) {
            lateinit var sLottoList : Array<String>
            val sAddGrid: GridView = GridView(this)

            var sStr = value.number

            sAddGrid.layoutParams = sAddGridPram
            sAddGrid.numColumns = 6
            sAddGrid.background = ContextCompat.getDrawable(this, R.drawable.lotto_grid_view)

            toolLottoLay.addView(sAddGrid)

            if (sStr !== null) {
                sLottoList= sStr?.split(",").toTypedArray()
            }

            if (sLottoList != null) {
                var sAdapter = ButtonAdapter(this, sLottoList)

                sAddGrid.adapter = sAdapter
            }


        }
        val sAddGrid2: GridView = GridView(this)
        sAddGrid2.layoutParams = sAddGridPram
        sAddGrid2.numColumns = 7
        sAddGrid2.background = ContextCompat.getDrawable(this, R.drawable.lotto_grid_view)
        toolLottoLay2.addView(sAddGrid2)
        var sAdapter2 = ButtonAdapter(this, slottoApi)
        sAddGrid2.adapter = sAdapter2
        toolLottoLay.addView(sDelBtn)
    }
    private fun dbReset() {
        for (value in gLottoList) {
            val sLottoNum = LottoNum(value.id, "")
            gDb.deleteLottoNum(sLottoNum)
        }
        refreshData()
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