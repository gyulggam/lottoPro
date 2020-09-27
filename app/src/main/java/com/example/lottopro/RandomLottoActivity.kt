package com.example.lottopro

import android.os.Bundle
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_constellation.*


class RandomLottoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_lotto)

        MobileAds.initialize(this) {}
        var  mAdView = adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

//        로또 번호 확인 버튼의 클릭이벤트 리스너 설정
        goResultButton.setOnClickListener{
            GridLayoutLayout3.removeAllViewsInLayout()
            var array = LottoNumberMaker.getRandomLottoNumbers()

            for (i in 1.. 6) {
                GridLayoutLayout3.columnCount = 6
                GridLayoutLayout3.rowCount = 1
                val sCol: GridLayout.Spec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1F)
                val sRow: GridLayout.Spec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1F)
                var sGridParam: GridLayout.LayoutParams
                val sBall = "ball_"+array[i-1]
                val sBtn = ImageButton(this)
//            sBtn.setBackgroundResource(R.drawable.ball_1)
                sBtn.setBackgroundResource(resources.getIdentifier(sBall,"drawable", packageName))
                sBtn.foregroundGravity = Gravity.CENTER_HORIZONTAL

                sGridParam = GridLayout.LayoutParams(sRow, sCol)
                GridLayoutLayout3.addView(sBtn, sGridParam)
                val animation: Animation =
                    AnimationUtils.loadAnimation(applicationContext, R.anim.overshoot)
                GridLayoutLayout3.startAnimation(animation)
            }


        }

//       로또번호저장 버튼의 클릭이벤트 리스너 설정
        goSaveButton.setOnClickListener{
            Toast.makeText(applicationContext, "로또번호를 저장했습니다.", Toast.LENGTH_SHORT).show()

        }

    }
}