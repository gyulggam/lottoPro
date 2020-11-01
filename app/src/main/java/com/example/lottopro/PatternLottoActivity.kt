package com.example.lottopro

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.lottopro.Adapter.ButtonAdapter
import com.example.lottopro.Adapter.PatternButtonAdapter
import com.example.lottopro.DataBase.PatternSelSql
import com.example.lottopro.DataBase.PatternTypeSql
import com.example.lottopro.DataBase.SqlHelper
import com.example.lottopro.Str.LottoNum
import com.example.lottopro.Str.PatternSelNum
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.header_lotto.*
import kotlinx.android.synthetic.main.main.*
import kotlinx.android.synthetic.main.pattern.*
import kotlinx.android.synthetic.main.pattern.firstDown
import kotlinx.android.synthetic.main.pattern.firstUp
import kotlinx.android.synthetic.main.pattern.fiveDown
import kotlinx.android.synthetic.main.pattern.fiveUp
import kotlinx.android.synthetic.main.pattern.fourthDown
import kotlinx.android.synthetic.main.pattern.fourthUp
import kotlinx.android.synthetic.main.pattern.secondDown
import kotlinx.android.synthetic.main.pattern.secondUp
import kotlinx.android.synthetic.main.pattern.thirdDown
import kotlinx.android.synthetic.main.pattern.thirdUp
import kotlinx.android.synthetic.main.select_lotto.*
import org.json.JSONObject
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class PatternLottoActivity : AppCompatActivity() {
    private lateinit var gDb: SqlHelper
    private lateinit var gPatternDb: PatternSelSql
    private var gIntResultLotto = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree() )
        gDb = SqlHelper(this)
        gPatternDb = PatternSelSql(this)
        setContentView(R.layout.pattern)
        val sToolbar = lottoHeader as Toolbar?
        setSupportActionBar(sToolbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        headerText.text = "패턴로또"

        MobileAds.initialize(this) {}
        var  mAdView = patternAdView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        //mainText 색변경
        var sMainStr = patTopLayoutText.text.toString()
        var sSpannable = SpannableString(sMainStr)
        var sChangeStr = "확률"
        var sStartStr = sMainStr.indexOf(sChangeStr)
        var sEndStr = sStartStr + sChangeStr.length

        sSpannable.setSpan(ForegroundColorSpan(Color.parseColor("#5E4BE1")), sStartStr, sEndStr, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        patTopLayoutText.text = sSpannable
        //mainText 색변경

        var sPatternSelectNum = gPatternDb.gPatternSelNum
        var sSelNumList : List<String>?

        var gMaxCol = 7
        var gMaxRow = 7
        val sGridSpace: GridLayout = GridLayout(this)
        val sCol : GridLayout.Spec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1F)
        val sRow : GridLayout.Spec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1F)
        var sGridSpacePram : GridLayout.LayoutParams
        var sStr = firstNum.text.toString()
        var sFirstNum = sStr.toInt()
        sStr = secondNum.text.toString()
        var sSecondNum = sStr.toInt()
        sStr = thirdNum.text.toString()
        var sThirdNum = sStr.toInt()
        sStr = fourthNum.text.toString()
        var sFourthNum = sStr.toInt()
        sStr = fiveNum.text.toString()
        var sFiveNum = sStr.toInt()
        var sTotalNum = 0

        var sOneList = ArrayList<Int>()
        var sTenList = ArrayList<Int>()
        var sTwoList = ArrayList<Int>()
        var sTreeList = ArrayList<Int>()
        var sFourList = ArrayList<Int>()

        sGridSpace.columnCount = gMaxCol
        sGridSpace.rowCount = gMaxRow
        sGridSpace.useDefaultMargins = true

        patSelNum.addView(sGridSpace)
        if (sPatternSelectNum != null && sPatternSelectNum.size !== 0) {
            sSelNumList = sPatternSelectNum[0].number?.split(",")

            for((index, value) in sSelNumList!!.withIndex()) {
                var sVal = value.toInt()
                when(sVal) {
                    in 0..10 -> sOneList.add(sVal)
                    in 11..20 -> sTenList.add(sVal)
                    in 21..30 -> sTwoList.add(sVal)
                    in 31..40 -> sTreeList.add(sVal)
                    in 41..45 -> sFourList.add(sVal)
                }
            }
        }

        if (sPatternSelectNum != null) {
            for((index, value) in sPatternSelectNum.withIndex()) {
                var sStr = value.number
                var sSelectList : Array<String>

                if (sStr !== null) {
                    sSelectList= sStr?.split(",").toTypedArray()

                    for (selVal in sSelectList) {
                        var sBall = "pattern_un_$selVal"
                        val sBtn = ImageButton(this)

                        sBtn.setBackgroundResource(
                            resources.getIdentifier(sBall, "drawable", packageName)
                        )
                        sBtn.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )

                        sGridSpacePram = GridLayout.LayoutParams(sRow, sCol)
                        sGridSpace.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)
                        sGridSpace.addView(sBtn, sGridSpacePram)
                    }
                }
            }
        }

        patSelBtn.setOnClickListener{
            val sIntent = Intent(this@PatternLottoActivity, PatternSelectLottoActivity::class.java  )
            startActivity(sIntent);
        }

        patSelBtnLay.setOnClickListener{
            val sIntent = Intent(this@PatternLottoActivity, PatternSelectLottoActivity::class.java  )
            startActivity(sIntent);
        }

        patternSaveBtn.setOnClickListener {
            saveBtn()
        }

        viewBackBtn.setOnClickListener {
            finish()
        }

        firstDown.setOnClickListener {
            if (sTotalNum > 0 && sFirstNum > 0) {
                sTotalNum--
                println("sTotalNum ::::: $sTotalNum")
            }
            if (sFirstNum > 0) {
                sFirstNum--
            }
            firstNum.text = sFirstNum.toString()
        }

        firstUp.setOnClickListener {
            if ((sFirstNum + sSecondNum + sThirdNum + sFourthNum + sFiveNum) < 6 && sOneList.size > sFirstNum) {
                sTotalNum++
                sFirstNum++
            }
            firstNum.text = sFirstNum.toString()
        }

        secondDown.setOnClickListener {
            if (sTotalNum > 0 && sSecondNum > 0) {
                sTotalNum--
            }
            if (sSecondNum > 0) {
                sSecondNum--
            }
            secondNum.text = sSecondNum.toString()
        }

        secondUp.setOnClickListener {
            if ((sFirstNum + sSecondNum + sThirdNum + sFourthNum + sFiveNum) < 6 && sTenList.size > sSecondNum) {
                sTotalNum++
                sSecondNum++
            }
            secondNum.text = sSecondNum.toString()
        }

        thirdDown.setOnClickListener {
            if (sTotalNum > 0 && sThirdNum > 0) {
                sTotalNum--
                println("sTotalNum ::::: $sTotalNum")
            }
            if (sThirdNum > 0) {
                sThirdNum--
            }
            thirdNum.text = sThirdNum.toString()
        }

        thirdUp.setOnClickListener {
            if ((sFirstNum + sSecondNum + sThirdNum + sFourthNum + sFiveNum) < 6 && sTwoList.size > sThirdNum) {
                sTotalNum++
                sThirdNum++
            }
            thirdNum.text = sThirdNum.toString()
        }

        fourthDown.setOnClickListener {
            if (sTotalNum > 0 && sFourthNum > 0) {
                sTotalNum--
                println("sTotalNum ::::: $sTotalNum")
            }
            if (sFourthNum > 0) {
                sFourthNum--
            }
            fourthNum.text = sFourthNum.toString()
        }

        fourthUp.setOnClickListener {
            if ((sFirstNum + sSecondNum + sThirdNum + sFourthNum + sFiveNum) < 6 && sTreeList.size > sFourthNum) {
                sTotalNum++
                sFourthNum++
            }
            fourthNum.text = sFourthNum.toString()
        }

        fiveDown.setOnClickListener {
            if (sTotalNum > 0 && sFiveNum > 0) {
                sTotalNum--
                println("sTotalNum ::::: $sTotalNum")
            }

            if (sFiveNum > 0) {
                sFiveNum--
            }
            fiveNum.text = sFiveNum.toString()
        }

        fiveUp.setOnClickListener {
            if ((sFirstNum + sSecondNum + sThirdNum + sFourthNum + sFiveNum) < 6 && sFourList.size > sFiveNum) {
                sTotalNum++
                sFiveNum++
            }
            fiveNum.text = sFiveNum.toString()
        }

        createBtn.setOnClickListener {
            var sStr = "${sFirstNum},${sSecondNum},${sThirdNum},${sFourthNum},${sFiveNum}"
            createPatternLotto(sPatternSelectNum, sStr)
        }
    }

    private fun saveBtn() {
        val sStrArray = gIntResultLotto.map{ it.toString() }.toTypedArray()
        if (sStrArray.joinToString(",") == "" || sStrArray.joinToString(",") == null) {
            Toast.makeText(applicationContext, "번호를 생성해 주세요", Toast.LENGTH_SHORT).show()
            return
        }
        val sLottoNum = LottoNum(0, sStrArray.joinToString(","))


        gDb.addLottoNum(sLottoNum)
        Toast.makeText(applicationContext, "저장 되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private fun createPatternLotto(aSelectArray : List<PatternSelNum>, aPatternStr : String) {
        var sSelNumList = aSelectArray[0].number?.split(",")
        var sPatternNumList = aPatternStr.split(",")
        var sOneList = ArrayList<Int>()
        var sTenList = ArrayList<Int>()
        var sTwoList = ArrayList<Int>()
        var sTreeList = ArrayList<Int>()
        var sFourList = ArrayList<Int>()
        var sPatternJson = JSONObject()
        gIntResultLotto = ArrayList<Int>()

        if (sSelNumList!![0] == "") {
            Toast.makeText(applicationContext, "사용할 번호를 선택해 주세요", Toast.LENGTH_SHORT).show()
            return
        }

        if (sSelNumList !== null) {
            for((index, value) in sSelNumList.withIndex()) {
                var sVal = value.toInt()
                when(sVal) {
                    in 0..10 -> sOneList.add(sVal)
                    in 11..20 -> sTenList.add(sVal)
                    in 21..30 -> sTwoList.add(sVal)
                    in 31..40 -> sTreeList.add(sVal)
                    in 41..45 -> sFourList.add(sVal)
                }
            }
        }

        for((index,value) in sPatternNumList.withIndex()) {
            when(index) {
                0 -> sPatternJson.put("one", value.toInt())
                1 -> sPatternJson.put("ten", value.toInt())
                2 -> sPatternJson.put("two", value.toInt())
                3 -> sPatternJson.put("tree", value.toInt())
                4 -> sPatternJson.put("four", value.toInt())
            }
        }

        for(i in 1..sPatternJson.getInt("one")) {
            var sRandom = 0
            var sValue  = 0

            if(sOneList.size == 0) {
                sRandom = (0..11).random()
                sOneList.add(sRandom)
                sValue  = sOneList[0].toInt()
            } else {
                sRandom = Random().nextInt(sOneList.size)
                sValue  = sOneList[sRandom].toInt()
            }

            while(gIntResultLotto.indexOf(sValue) > -1) {
                sValue++
            }

            gIntResultLotto.add(sValue)
        }

        for(i in 1..sPatternJson.getInt("ten")) {
            var sRandom = 0
            var sValue  = 0

            if(sTenList.size == 0) {
                sRandom = (10..21).random()
                sTenList.add(sRandom)
                sValue = sTenList[0].toInt()
            } else {
                sRandom = Random().nextInt(sTenList.size)
                sValue = sTenList[sRandom].toInt()
            }

            while(gIntResultLotto.indexOf(sValue) > -1) {
                sValue++
            }

            gIntResultLotto.add(sValue)
        }

        for(i in 1..sPatternJson.getInt("two")) {
            var sRandom = 0
            var sValue  = 0

            if(sTwoList.size == 0) {
                sRandom = (20..31).random()
                sTwoList.add(sRandom)
                sValue = sTwoList[0].toInt()
            } else {
                sRandom = Random().nextInt(sTwoList.size)
                sValue = sTwoList[sRandom].toInt()
            }

            while(gIntResultLotto.indexOf(sValue) > -1) {
                sValue++
            }

            gIntResultLotto.add(sValue)
        }

        for(i in 1..sPatternJson.getInt("tree")) {
            var sRandom = 0
            var sValue  = 0

            if(sTreeList.size == 0) {
                sRandom = (30..41).random()
                sTreeList.add(sRandom)
                sValue = sTreeList[0].toInt()
            } else {
                sRandom = Random().nextInt(sTreeList.size)
                sValue = sTreeList[sRandom].toInt()
            }

            while(gIntResultLotto.indexOf(sValue) > -1) {
                sValue++
            }

            gIntResultLotto.add(sValue)
        }

        for(i in 1..sPatternJson.getInt("four")) {
            var sRandom = 0
            var sValue  = 0

            if(sFourList.size == 0) {
                sRandom = (40..46).random()
                sFourList.add(sRandom)
                sValue = sFourList[0].toInt()
            } else {
                sRandom = Random().nextInt(sFourList.size)
                sValue = sFourList[sRandom].toInt()
            }

            while(gIntResultLotto.indexOf(sValue) > -1) {
                sValue++
            }

            gIntResultLotto.add(sValue)
        }

        gIntResultLotto.sort()
        val sResultLotto = gIntResultLotto.map { it.toString()}.toTypedArray()

        if (sResultLotto != null && sResultLotto.size >= 6) {
            var sAdapter = PatternButtonAdapter(this, sResultLotto)
            createLottoView.adapter = sAdapter
        } else {
            Toast.makeText(applicationContext, "총 6개의 패턴을 선택해 주세요", Toast.LENGTH_SHORT).show()
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

