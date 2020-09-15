package com.example.lottopro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.view.View
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setContentView(R.layout.activity_main)

        val sPageMoveBtn = findViewById<Button>(R.id.moveBtn) as Button

        sPageMoveBtn.setOnClickListener{
            val sNextIntent = Intent(this@MainActivity, SelectNumActivity::class.java)
            startActivity(sNextIntent)
        }
    }
}