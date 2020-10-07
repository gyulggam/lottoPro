package com.example.lottopro

import android.os.Bundle
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lottopro.DataBase.SqlHelper
import com.example.lottopro.Str.LottoNum
import kotlinx.android.synthetic.main.pattern_type.*
import kotlinx.android.synthetic.main.select_lotto.*
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

class PatternTypeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree() )
        setContentView(R.layout.pattern_type)

        var sStr = firstNum.text.toString()
        var sFirstNum = sStr.toInt()
        sStr = secondNum.text.toString()
        var sSecondNum = sStr.toInt()
        sStr = thirdNum.text.toString()
        var sThirdNum = sStr.toInt()
        sStr = fourthNum.text.toString()
        var sFourthNum = sStr.toInt()
        sStr = fiveNum.text.toString()

        var sTotalNum = 0
        var sFiveNum = sStr.toInt()

        var sNumJson = JSONObject()

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
            if ((sFirstNum + sSecondNum + sThirdNum + sFourthNum + sFiveNum) < 6) {
                sTotalNum++
                sFirstNum++
            }
            firstNum.text = sFirstNum.toString()
        }

        secondDown.setOnClickListener {
            if (sTotalNum > 0 && sSecondNum > 0) {
                sTotalNum--
                println("sTotalNum ::::: $sTotalNum")
            }
            if (sSecondNum > 0) {
                sSecondNum--
            }
            secondNum.text = sSecondNum.toString()
        }

        secondUp.setOnClickListener {
            if ((sFirstNum + sSecondNum + sThirdNum + sFourthNum + sFiveNum) < 6) {
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
            if ((sFirstNum + sSecondNum + sThirdNum + sFourthNum + sFiveNum) < 6) {
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
            if ((sFirstNum + sSecondNum + sThirdNum + sFourthNum + sFiveNum) < 6) {
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
            if ((sFirstNum + sSecondNum + sThirdNum + sFourthNum + sFiveNum) < 6) {
                sTotalNum++
                sFiveNum++
            }
            fiveNum.text = sFiveNum.toString()
        }

        sNumJson.put("FirstNum", sFirstNum)
        sNumJson.put("SecondNum", sSecondNum)
        sNumJson.put("ThirdNum", sThirdNum)
        sNumJson.put("FourthNum", sFourthNum)
        sNumJson.put("FiveNum", sFiveNum)

        println("sNumJson ::::: $sNumJson")
    }
}
