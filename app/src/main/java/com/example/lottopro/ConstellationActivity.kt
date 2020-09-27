package com.example.lottopro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.widget.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_constellation.*
import kotlinx.android.synthetic.main.activity_constellation.adView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.select_lotto.*
import java.util.*

class ConstellationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constellation)

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

        }


    }
/* 현재 DatePicker 의 월, 일 정보로 별자리 텍스트 변경 */
        textView.setText(makeConstellationString(datePicker.month, datePicker.dayOfMonth))
//        DatePicker 의  날짜가 변화하면 별자리를 보여주는 텍스트뷰도 변경
        val sCalendar = Calendar.getInstance()
        datePicker.init(sCalendar.get(Calendar.YEAR), sCalendar.get(Calendar.MONTH), sCalendar.get(Calendar.DAY_OF_MONTH), object:CalendarView.OnDateChangeListener, DatePicker.OnDateChangedListener {
            override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
//                변경된 시점의 DatePicker 의 월, 일 정보로 별자리 텍스트 변경
                textView.setText(makeConstellationString(datePicker.month, datePicker.dayOfMonth))
            }

            override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, dayOfMonth: Int) {

            }

        })

//       로또번호저장 버튼의 클릭이벤트 리스너 설정
        goSaveButton.setOnClickListener{
            Toast.makeText(applicationContext, "로또번호를 저장했습니다.", Toast.LENGTH_SHORT).show()

        }
    }

    //        전달받은 월정보, 일정보 기준으로 별자리를 반환한다.
    fun makeConstellationString(aMonth: Int, aDay: Int): String {
//        전달받은 월 정보와 일 정보를 기반으로 정수형태의 값을 만든다.
//        ex) 1월 5일 --> 105, 11월 1일 --> 1101
        val sTarget = "${aMonth + 1}${String.format("%02d", aDay)}".toInt()
        var TargetVal = ""
        var TargetName = ""
        when(sTarget){
            in 101..119 -> {
                TargetVal="goat"
                TargetName="염소자리"
                //염소자리
            }
            in 120..218 -> {
                TargetVal="aquarius"
                TargetName="물병자리"
//                물병자리
            }
            in 219..320 -> {
                TargetVal="pisces"
                TargetName="물고기자리"
                //물고기자리
            }
            in 321..419 -> {
                TargetVal="aries"
                TargetName="양자리자리"
//                양자리
            }
            in 420..520 -> {
                TargetVal="taurus"
                TargetName="황소자리"
//                황소자리
            }
            in 521..621 -> {
                TargetVal="gemini"
                TargetName="쌍둥이자리"
//                쌍둥이자리
            }
            in 622..722 -> {
                TargetVal="crap"
                TargetName="게자리"
//                게자리
            }
            in 723..822 -> {
                TargetVal="leo"
                TargetName="사자자리"
//                사자자리
            }
            in 823..923 -> {
                TargetVal="virgo"
                TargetName="처녀자리"
//                처녀자리
            }
            in 924..1022 -> {
                TargetVal="libra"
                TargetName="천칭자리"
//                천칭자리
            }
            in 1023..1122 -> {
                TargetVal="scorpio"
                TargetName="전갈자리"
//                전갈자리
            }
            in 1123..1224 -> {
                TargetVal="sagittarius"
                TargetName="사수자리"
//                사수자리
            }
            in 1225..1231 -> {
                TargetVal="goat"
                TargetName="염소자리"
//                염소자리
            }
            else -> {
                TargetVal="기타별자리"
                TargetName="기타별자리"
//                기타별자리
            }
        }
        makeConstellationImage(TargetVal)
        return TargetName

    }
    fun makeConstellationImage(sTarget: String)  {
//        전달받은 월 정보와 일 정보를 기반으로 정수형태의 값을 만든다.
//        ex) 1월 5일 --> 105, 11월 1일 --> 1101
//        imageView.setImageResource(R.drawable.sTarget);
        var packName = this.packageName // 패키지명

        imageView.setImageResource(resources.getIdentifier(sTarget,"drawable", packageName))

    }

}