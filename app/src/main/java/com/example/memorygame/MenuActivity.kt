package com.example.memorygame

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.memorygame.activityes.Hard
import com.example.memorygame.activityes.Medium
import com.example.memorygame.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {


    private var player:MediaPlayer?=null

    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        player=MediaPlayer.create(this,R.raw.ring)

        binding.easy.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("level","Easy")
            startActivity(intent)
            player!!.start()
            finish()
        }
        binding.medium.setOnClickListener{
            val intent= Intent(this,Medium::class.java)
            intent.putExtra("level","Medium")
            startActivity(intent)
            player!!.start()
            finish()
        }

        binding.hard.setOnClickListener{
            val intent= Intent(this,Hard::class.java)
            intent.putExtra("level","Hard")
            startActivity(intent)
            player!!.start()
            finish()
        }
    }
}