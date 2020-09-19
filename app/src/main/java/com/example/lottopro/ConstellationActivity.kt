package com.example.lottopro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class ConstellationActivity : AppCompatActivity() {
    var clickCount = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constellation)

        Toast.makeText(applicationContext, "광고수입 호로록", Toast.LENGTH_LONG).show();
    }
}