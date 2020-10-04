package com.example.lottopro

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.MarginLayoutParams
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lottopro.Adapter.ButtonAdapter
import com.example.lottopro.Adapter.SelectButtonAdapter
import com.example.lottopro.DataBase.SqlHelper
import com.example.lottopro.Str.LottoNum
import kotlinx.android.synthetic.main.activity_select_lotto.*
import kotlinx.android.synthetic.main.select_lotto.*
import timber.log.Timber

class SelectLottoActivity : AppCompatActivity() {
    private lateinit var  gDb: SqlHelper
    private var gLottoList:List<LottoNum> = ArrayList<LottoNum>()
    private var gSelLotto = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())

        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#1874CD")))

        gDb = SqlHelper(this)
        setContentView(R.layout.activity_select_lotto)

        refreshData()

        var gMaxSelLotto = 6
        var gMaxLottoNum = 46
        var gMaxCol = 7
        var gMaxRow = 7

        lottoGridLayout.columnCount = gMaxCol
        lottoGridLayout.rowCount = gMaxRow

        selLottoView.numColumns = 6
        saveBtn.text = "번호 저장"

        for (i in 1 until gMaxLottoNum) {
            var sIsClick = false
            var sBall = "ball_$i"
            var sUnBall = "un_ball_1" //버튼 하나만 만들어 놓았음 부림아 제발 해도
            val sCol : GridLayout.Spec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1F)
            val sRow : GridLayout.Spec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1F)
            var sGridParam :GridLayout.LayoutParams
            var sSelBtnAdapter : SelectButtonAdapter


            val sBtn = ImageButton(this).apply {
                this.setOnClickListener {
                    if(sIsClick) {
                        this.setBackgroundResource(
                            resources.getIdentifier(
                                sUnBall,
                                "drawable",
                                packageName
                            )
                        )
                        gSelLotto.remove(i)

                        gSelLotto.sort()

                        sSelBtnAdapter = SelectButtonAdapter(context, gSelLotto)
                        selLottoView.adapter = sSelBtnAdapter

                        sIsClick = false
                    } else {
                        if (gMaxSelLotto === gSelLotto.size) {
                            Toast.makeText(applicationContext, "어림없지 6개까지다 쒜끼야~", Toast.LENGTH_LONG).show();
                            return@setOnClickListener
                        }

                        this.setBackgroundResource(
                            resources.getIdentifier(
                                sBall,
                                "drawable",
                                packageName
                            )
                        )

                        gSelLotto.add(i.toInt())

                        gSelLotto.sort()

                        sSelBtnAdapter = SelectButtonAdapter(context, gSelLotto)
                        selLottoView.adapter = sSelBtnAdapter
                        sIsClick = true
                    }

                    Log.d("Select Lotto", gSelLotto.toString())
                }
            }

            sBtn.setBackgroundResource(resources.getIdentifier(sUnBall, "drawable", packageName))
            sBtn.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            sGridParam = GridLayout.LayoutParams(sRow, sCol)
            lottoGridLayout.addView(sBtn, sGridParam)

        }

        saveBtn.setOnClickListener {
            saveBtn()
        }
    }

    private fun saveBtn() {

        if (gSelLotto.size < 6) {
            Toast.makeText(applicationContext, "6개 번호를 선택해야 합니다.", Toast.LENGTH_SHORT).show();
            return
        }

        if (gLottoList.size >= 10) {
            Toast.makeText(applicationContext, "최대 10개까지 저장 가능합니다.", Toast.LENGTH_SHORT).show();
            return
        }

        gSelLotto.sort()

        val sStrArray = gSelLotto.map{ it.toString() }.toTypedArray()
        val sLottoNum = LottoNum(0, sStrArray.joinToString(","))

        gDb.addLottoNum(sLottoNum)
        Toast.makeText(applicationContext, "저장 되었습니다.", Toast.LENGTH_SHORT).show();
        refreshData()
    }

    private fun refreshData() {
        toolLottoLay.removeAllViews()
        gLottoList = gDb.gAllLottoNum
        val sDelBtn = Button(this)
        val sDelBtnPam =  LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        toolLottoLay.margin(20F, 0F, 20F, 10F)

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
            val sAddGridPram = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            var sStr = value.number

            sAddGridPram.height = dpToPx(42F)
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
        layoutParams<MarginLayoutParams> {
            left?.run { leftMargin = dpToPx(this) }
            top?.run { topMargin = dpToPx(this) }
            right?.run { rightMargin = dpToPx(this) }
            bottom?.run { bottomMargin = dpToPx(this) }
        }
    }

    inline fun <reified T : LayoutParams> View.layoutParams(block: T.() -> Unit) {
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