package com.example.lottopro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}
        var  mAdView = adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        var sIntent = Intent();

        menuBtn1.setOnClickListener{
            sIntent = Intent(this@MainActivity, ConstellationActivity::class.java  )
            startActivity(sIntent);
        }
        menuBtn2.setOnClickListener{
            val sIntent = Intent(this@MainActivity, RandomLottoActivity::class.java  )
            startActivity(sIntent);
        }
        menuBtn3.setOnClickListener{
            val sIntent = Intent(this@MainActivity, PatternLottoActivity::class.java  )
            startActivity(sIntent);
        }
        menuBtn4.setOnClickListener{
            val intent = Intent(this@MainActivity, SelectLottoActivity::class.java  )
            startActivity(intent);
        }
    }

}