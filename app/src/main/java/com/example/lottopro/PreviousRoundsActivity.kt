package com.example.lottopro

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.lottopro.Adapter.ButtonAdapter
import com.example.lottopro.DataBase.SqlHelper
import com.example.lottopro.Str.LottoNum
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.common.util.CollectionUtils.isEmpty
import kotlinx.android.synthetic.main.constellation.*
import kotlinx.android.synthetic.main.constellation.adView
import kotlinx.android.synthetic.main.header_lotto.*
import java.util.*

class PreviousRoundsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.previous_rounds)

    }
}


