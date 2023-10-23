package com.example.memorygame

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Splash_Activity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val countDownTimer = object : CountDownTimer(800, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                val intent=Intent(this@Splash_Activity,MenuActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        countDownTimer.start()

    }
}