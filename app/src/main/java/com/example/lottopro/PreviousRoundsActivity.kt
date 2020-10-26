package com.example.lottopro

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.example.lottopro.Adapter.PreviousButtonAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.constellation.*
import kotlinx.android.synthetic.main.header_lotto.*
import kotlinx.android.synthetic.main.main.*
import kotlinx.android.synthetic.main.previous_rounds.*
import kotlinx.android.synthetic.main.previous_rounds.adView

lateinit var gLottoGridView1:GridView
lateinit var gLottoGridViewR1:GridView
lateinit var gBannerTitle1:TextView

lateinit var gLottoGridView2:GridView
lateinit var gLottoGridViewR2:GridView
lateinit var gBannerTitle2:TextView

lateinit var gLottoGridView3:GridView
lateinit var gLottoGridViewR3:GridView
lateinit var gBannerTitle3:TextView

lateinit var gLottoGridView4:GridView
lateinit var gLottoGridViewR4:GridView
lateinit var gBannerTitle4:TextView

lateinit var gLottoGridView5:GridView
lateinit var gLottoGridViewR5:GridView
lateinit var gBannerTitle5:TextView

class PreviousRoundsActivity : AppCompatActivity() {


    var gLottoCount = LottoNumberMaker.getCount()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.previous_rounds)

        //mainText 색변경
        var sMainStr = Description.text.toString()
        var sSpannable = SpannableString(sMainStr)
        var sChangeStr = "이전 회차"
        var sStartStr = sMainStr.indexOf(sChangeStr)
        var sEndStr = sStartStr + sChangeStr.length
        sSpannable.setSpan(ForegroundColorSpan(Color.parseColor("#5E4BE1")), sStartStr, sEndStr, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        Description.text = sSpannable
        //mainText 색변경

        MobileAds.initialize(this) {}
        var  mAdView = adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        var gestureListener = MyGesture()
        var gesturedetector = GestureDetector(this, gestureListener)

        gLottoGridView1 = findViewById(R.id.LottoGridView1)
        gLottoGridViewR1 = findViewById(R.id.LottoGridViewR1)
        gBannerTitle1 = findViewById(R.id.bannerTitle1)

        gLottoGridView2 = findViewById(R.id.LottoGridView2)
        gLottoGridViewR2 = findViewById(R.id.LottoGridViewR2)
        gBannerTitle2 = findViewById(R.id.bannerTitle2)

        gLottoGridView3 = findViewById(R.id.LottoGridView3)
        gLottoGridViewR3 = findViewById(R.id.LottoGridViewR3)
        gBannerTitle3 = findViewById(R.id.bannerTitle3)

        gLottoGridView4 = findViewById(R.id.LottoGridView4)
        gLottoGridViewR4 = findViewById(R.id.LottoGridViewR4)
        gBannerTitle4 = findViewById(R.id.bannerTitle4)

        gLottoGridView5 = findViewById(R.id.LottoGridView5)
        gLottoGridViewR5 = findViewById(R.id.LottoGridViewR5)
        gBannerTitle5 = findViewById(R.id.bannerTitle5)
        preview.setOnTouchListener { v, event ->
            return@setOnTouchListener gesturedetector.onTouchEvent(event)
        }
//        Toast.makeText(applicationContext, "최근회차 번호 ${sLottoNum2}", Toast.LENGTH_LONG).show()

        for(i in 0..5){
             var num = gLottoCount?.toInt()
            if (num != null) {
              num=  num-i
            }
            var sLottoNum = LottoNumberMaker.getPreNumber(num.toString())?.toTypedArray()

            println("")
            var sLottoNumLast = LottoNumberMaker.getPreNumberBonus(num.toString())?.toTypedArray()
            var sLottoNumCount = LottoNumberMaker.getPreCount(num.toString())

            var sLottoNumCountR = sLottoNumCount?.toString()?.replace("[", "제 ")
            sLottoNumCountR =sLottoNumCountR?.replace("]", "회")

            var sAdapter = PreviousButtonAdapter(this, sLottoNum)
            var sAdapterLast = PreviousButtonAdapter(this, sLottoNumLast)

            if(i == 0){
                gLottoGridView1.adapter = sAdapter
                gLottoGridViewR1.adapter = sAdapterLast
                gBannerTitle1.text = sLottoNumCountR
            }
            else if(i == 1){
                gLottoGridView2.adapter = sAdapter
                gLottoGridViewR2.adapter = sAdapterLast
                gBannerTitle2.text = sLottoNumCountR
            }
            else if(i == 2){
                gLottoGridView3.adapter = sAdapter
                gLottoGridViewR3.adapter = sAdapterLast
                gBannerTitle3.text = sLottoNumCountR
            }
            else if(i == 3){
                gLottoGridView4.adapter = sAdapter
                gLottoGridViewR4.adapter = sAdapterLast
                gBannerTitle4.text = sLottoNumCountR
            }
            else if(i == 4){
                gLottoGridView5.adapter = sAdapter
                gLottoGridViewR5.adapter = sAdapterLast
                gBannerTitle5.text = sLottoNumCountR
            }
        }
        viewBackBtn.setOnClickListener {
            finish()
        }
    }

    fun downSwipe(){
        // 5번째 회차 toInt
        gLottoCount = gLottoCount?.toString()?.replace("[", "")
        gLottoCount = gLottoCount?.replace("]", "")
        gLottoCount = (gLottoCount?.toInt()?.minus(5)).toString()

        println("gLottoCount ::::::::: ${gLottoCount}")

        for(i in 0..5){

            var sLottoCount=  (gLottoCount?.toInt()?.minus(i)).toString()
            var sLottoNum = LottoNumberMaker.getPreNumber(sLottoCount.toString())?.toTypedArray()
            var sLottoNum2 = LottoNumberMaker.getPreNumber(sLottoCount.toString())
            println("sLottoNum :::::::::: ${sLottoNum2.toString()}")
            var sLottoNumLast = LottoNumberMaker.getPreNumberBonus(sLottoCount.toString())?.toTypedArray()
            var sLottoNumCount = LottoNumberMaker.getPreCount(sLottoCount.toString())

            var sLottoNumCountR = sLottoNumCount?.toString()?.replace("[", "제 ")
            sLottoNumCountR =sLottoNumCountR?.replace("]", "회")

            var sAdapter = PreviousButtonAdapter(this, sLottoNum)
            var sAdapterLast = PreviousButtonAdapter(this, sLottoNumLast)

            if(i == 0){
                gLottoGridView1.adapter = sAdapter
                gLottoGridViewR1.adapter = sAdapterLast
                gBannerTitle1.text = sLottoNumCountR
            }
            else if(i == 1){
                gLottoGridView2.adapter = sAdapter
                gLottoGridViewR2.adapter = sAdapterLast
                gBannerTitle2.text = sLottoNumCountR
            }
            else if(i == 2){
                gLottoGridView3.adapter = sAdapter
                gLottoGridViewR3.adapter = sAdapterLast
                gBannerTitle3.text = sLottoNumCountR
            }
            else if(i == 3){
                gLottoGridView4.adapter = sAdapter
                gLottoGridViewR4.adapter = sAdapterLast
                gBannerTitle4.text = sLottoNumCountR
            }
            else if(i == 4){
                gLottoGridView5.adapter = sAdapter
                gLottoGridViewR5.adapter = sAdapterLast
                gBannerTitle5.text = sLottoNumCountR
            }
        }
    }

    fun upSwipe(){
        // 5번째 회차 toInt
        gLottoCount = gLottoCount?.toString()?.replace("[", "")
        gLottoCount = gLottoCount?.replace("]", "")
        gLottoCount = (gLottoCount?.toInt()?.plus(5)).toString()

        println("gLottoCount ::::::::: ${gLottoCount}")

        for(i in 0..5){

            var sLottoCount=  (gLottoCount?.toInt()?.minus(i)).toString()
            var sLottoNum = LottoNumberMaker.getPreNumber(sLottoCount.toString())?.toTypedArray()
            var sLottoNum2 = LottoNumberMaker.getPreNumber(sLottoCount.toString())
            println("sLottoNum :::::::::: ${sLottoNum2.toString()}")
            var sLottoNumLast = LottoNumberMaker.getPreNumberBonus(sLottoCount.toString())?.toTypedArray()
            var sLottoNumCount = LottoNumberMaker.getPreCount(sLottoCount.toString())

            var sLottoNumCountR = sLottoNumCount?.toString()?.replace("[", "제 ")
            sLottoNumCountR =sLottoNumCountR?.replace("]", "회")

            var sAdapter = PreviousButtonAdapter(this, sLottoNum)
            var sAdapterLast = PreviousButtonAdapter(this, sLottoNumLast)

            if(i == 0){
                gLottoGridView1.adapter = sAdapter
                gLottoGridViewR1.adapter = sAdapterLast
                gBannerTitle1.text = sLottoNumCountR
            }
            else if(i == 1){
                gLottoGridView2.adapter = sAdapter
                gLottoGridViewR2.adapter = sAdapterLast
                gBannerTitle2.text = sLottoNumCountR
            }
            else if(i == 2){
                gLottoGridView3.adapter = sAdapter
                gLottoGridViewR3.adapter = sAdapterLast
                gBannerTitle3.text = sLottoNumCountR
            }
            else if(i == 3){
                gLottoGridView4.adapter = sAdapter
                gLottoGridViewR4.adapter = sAdapterLast
                gBannerTitle4.text = sLottoNumCountR
            }
            else if(i == 4){
                gLottoGridView5.adapter = sAdapter
                gLottoGridViewR5.adapter = sAdapterLast
                gBannerTitle5.text = sLottoNumCountR
            }
        }
    }


   private inner class MyGesture  : GestureDetector.OnGestureListener  {

        private val SWIPE_MIN_DISTANCE = 120
        private val SWIPE_MAX_OFF_PATH = 250
        private val SWIPE_THRESHOLD_VELOCITY = 500
        // 제스처 이벤트를 받아서 text를 변경
        override fun onShowPress(e: MotionEvent?) {
        }
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return true
        }
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }
        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            try {
                if (e1 != null) {
                    if (e2 != null) {
                        if (e1.y - e2.y > SWIPE_MIN_DISTANCE
                        ) {
                            println("위로올릴때 swipe :::::::::::::::::::::::::::::::::::::::")

                            downSwipe()
                        } else if (e2.y - e1.y > SWIPE_MIN_DISTANCE
                        ) {
                            println("내릴때 right swipe :::::::::::::::::::::::::::::::::::::::")

                           upSwipe()
                        }
                    }
                }
            } catch (e: Exception) {
            }
            return true
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            return true
        }
        override fun onLongPress(e: MotionEvent?) {
        }

    }
}



