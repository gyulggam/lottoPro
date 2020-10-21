package com.example.lottopro

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MotionEventCompat
import com.example.lottopro.Adapter.PreviousButtonAdapter
import kotlinx.android.synthetic.main.header_lotto.*
import kotlinx.android.synthetic.main.previous_rounds.*


class PreviousRoundsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.previous_rounds)
        var sLottoCount = LottoNumberMaker.getCount()
        var gestureListener = MyGesture()
        var gesturedetector = GestureDetector(this, gestureListener)

        preview.setOnTouchListener { v, event ->
            return@setOnTouchListener gesturedetector.onTouchEvent(event)
        }
//        Toast.makeText(applicationContext, "최근회차 번호 ${sLottoNum2}", Toast.LENGTH_LONG).show()

        for(i in 0..5){
             var num = sLottoCount?.toInt()
            if (num != null) {
              num=  num-i
            }
            var sLottoNum = LottoNumberMaker.getPreNumber(num.toString())?.toTypedArray()
            var sLottoNumLast = LottoNumberMaker.getPreNumberBonus(num.toString())?.toTypedArray()
            var sLottoNumCount = LottoNumberMaker.getPreCount(num.toString())
            println("sLottoNumCount :::: ${sLottoNumCount}")

            var sLottoNumCountR = sLottoNumCount?.toString()?.replace("[", "제 ")
            sLottoNumCountR =sLottoNumCountR?.replace("]", "회")
            println("sLottoNumCountR :::: ${sLottoNumCount}")

            var sAdapter = PreviousButtonAdapter(this, sLottoNum)
            var sAdapterLast = PreviousButtonAdapter(this, sLottoNumLast)

            if(i == 0){
                LottoGridView1.adapter = sAdapter
                LottoGridViewR1.adapter = sAdapterLast
                bannerTitle1.text = sLottoNumCountR
            }
            else if(i == 1){
                LottoGridView2.adapter = sAdapter
                LottoGridViewR2.adapter = sAdapterLast
                bannerTitle2.text = sLottoNumCountR
            }
            else if(i == 2){
                LottoGridView3.adapter = sAdapter
                LottoGridViewR3.adapter = sAdapterLast
                bannerTitle3.text = sLottoNumCountR
            }
            else if(i == 3){
                LottoGridView4.adapter = sAdapter
                LottoGridViewR4.adapter = sAdapterLast
                bannerTitle4.text = sLottoNumCountR
            }
            else if(i == 4){
                LottoGridView5.adapter = sAdapter
                LottoGridViewR5.adapter = sAdapterLast
                bannerTitle5.text = sLottoNumCountR
            }
        }
        viewBackBtn.setOnClickListener {
            finish()
        }

    }
}

class MyGesture : GestureDetector.OnGestureListener {

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
       println("내린다 :::::::::::::::::::::::::::::::::::::::")
        return true
    }
    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return true
    }
    override fun onLongPress(e: MotionEvent?) {
    }

}