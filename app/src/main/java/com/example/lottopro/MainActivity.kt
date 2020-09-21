package com.example.lottopro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setContentView(R.layout.activity_main)

        var sIntent = Intent();

        menuBtn1.setOnClickListener{
            sIntent = Intent(this@MainActivity, ConstellationActivity::class.java  )
            startActivity(sIntent);

        }

        menuBtn2.setOnClickListener{
//            val sIntent = Intent(this@MainActivity, ScrollingActivity::class.java  )
//            startActivity(sIntent);
        }
        menuBtn3.setOnClickListener{
//            val sIntent = Intent(this@MainActivity, ConstellationActivity::class.java  )
//            startActivity(sIntent);

        }
        menuBtn4.setOnClickListener{
            val intent = Intent(this@MainActivity, SelectLottoActivity::class.java  )
            startActivity(intent);

        }
    }

}