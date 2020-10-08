package com.example.lottopro

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lottopro.Adapter.ButtonAdapter
import com.example.lottopro.DataBase.PatternSelSql
import com.example.lottopro.DataBase.PatternTypeSql
import com.example.lottopro.DataBase.SqlHelper
import com.example.lottopro.Str.LottoNum
import com.example.lottopro.Str.PatternSelNum
import com.example.lottopro.Str.PatternType
import com.google.android.gms.common.config.GservicesValue.value
import kotlinx.android.synthetic.main.activity_select_lotto.*
import kotlinx.android.synthetic.main.pattern.*
import kotlinx.android.synthetic.main.select_lotto.*
import org.json.JSONObject
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class PatternLottoActivity : AppCompatActivity() {
    private lateinit var gDb: SqlHelper
    private lateinit var gPatternDb: PatternSelSql
    private lateinit var gPatternTypeDb : PatternTypeSql
    private var gSelLottoList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree() )
        gDb = SqlHelper(this)
        gPatternDb = PatternSelSql(this)
        gPatternTypeDb = PatternTypeSql(this)
        setContentView(R.layout.activity_pattern)

        var sPatternSelectNum = gPatternDb.gPatternSelNum
        var sPatternType = gPatternTypeDb.gPatternType

        var gMaxCol = 7
        var gMaxRow = 7
        val sGridSpace: GridLayout = GridLayout(this)
        val sCol : GridLayout.Spec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1F)
        val sRow : GridLayout.Spec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1F)
        var sGridSpacePram : GridLayout.LayoutParams

        patSelNum.setPadding(30,25,30,25)
        patSelPattern.setPadding(30,25,30,25)

        sGridSpace.columnCount = gMaxCol
        sGridSpace.rowCount = gMaxRow

        patSelNum.addView(sGridSpace)

        if (sPatternSelectNum != null) {
            for((index, value) in sPatternSelectNum.withIndex()) {
                var sStr = value.number
                var sSelectList : Array<String>

                if (sStr !== null) {
                    sSelectList= sStr?.split(",").toTypedArray()

                    for (selVal in sSelectList) {
                        var sBall = "ball_$selVal"
                        val sBtn = ImageButton(this)

                        sBtn.setBackgroundResource(
                            resources.getIdentifier(sBall, "drawable", packageName)
                        )
                        sBtn.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                        )

                        sGridSpacePram = GridLayout.LayoutParams(sRow, sCol)
                        sGridSpace.addView(sBtn, sGridSpacePram)
                    }
                }
            }
        }

        if (sPatternType !== null) {
            for((index,value) in sPatternType.withIndex()) {
                val sPatternText = TextView(this)
                sPatternText.text = value.type.toString()
                sPatternText.textSize = 20F
                patSelPattern.addView(sPatternText)
            }
        }

        patSelNum.setOnClickListener{
            val sIntent = Intent(this@PatternLottoActivity, PatternSelectLottoActivity::class.java  )
            startActivity(sIntent);
        }

        patSelPattern.setOnClickListener{
            val sIntent = Intent(this@PatternLottoActivity, PatternTypeActivity::class.java  )
            startActivity(sIntent);
        }

        createBtn.setOnClickListener {
            createPatternLotto(sPatternSelectNum, sPatternType)
        }
    }

    private fun createPatternLotto(aSelectArray : List<PatternSelNum>, aPatternList : List<PatternType>) {
        var sSelNumList = aSelectArray[0].number?.split(",")
        var sPatternNumList = aPatternList[0].type?.split(",")
        var sOneList = ArrayList<String>()
        var sTenList = ArrayList<String>()
        var sTwoList = ArrayList<String>()
        var sTreeList = ArrayList<String>()
        var sFourList = ArrayList<String>()
        var sResultLotto = ArrayList<String>()
        var sPatternJson = JSONObject()

        if (sSelNumList !== null) {
            for((index, value) in sSelNumList.withIndex()) {
                var sVal = value.toInt()
                when(sVal) {
                    in 0..11 -> sOneList.add(value)
                    in 11..21 -> sTenList.add(value)
                    in 21..31 -> sTwoList.add(value)
                    in 31..41 -> sTreeList.add(value)
                    in 41..45 -> sFourList.add(value)
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
            var sRandom = Random().nextInt(sOneList.size)
            sResultLotto.add(sOneList[sRandom])
        }

        for(i in 1..sPatternJson.getInt("ten")) {
            var sRandom = Random().nextInt(sTenList.size)
            sResultLotto.add(sTenList[sRandom])
        }

        for(i in 1..sPatternJson.getInt("two")) {
            var sRandom = Random().nextInt(sTwoList.size)
            sResultLotto.add(sTwoList[sRandom])
        }

        for(i in 1..sPatternJson.getInt("tree")) {
            var sRandom = Random().nextInt(sTreeList.size)
            sResultLotto.add(sTreeList[sRandom])
        }

        for(i in 1..sPatternJson.getInt("four")) {
            var sRandom = Random().nextInt(sFourList.size)
            sResultLotto.add(sFourList[sRandom])
        }

        println("sResultLotto $sResultLotto")
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

