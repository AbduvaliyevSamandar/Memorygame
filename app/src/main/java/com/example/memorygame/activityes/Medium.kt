package com.example.memorygame.activityes

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.memorygame.MemoryCard
import com.example.memorygame.MenuActivity
import com.example.memorygame.R
import com.example.memorygame.databinding.ActivityMediumBinding

class Medium :AppCompatActivity() {

    private lateinit var binding: ActivityMediumBinding
    private lateinit var buttons:List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexofSelectedCard:Int?=null

    private lateinit var clockThread:Thread

    private  var player: MediaPlayer?=null

    var minutes = 0
    var seconds = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMediumBinding.inflate(layoutInflater)
        setContentView(binding.root)



        onBackPressedDispatcher.addCallback(this@Medium,object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent=Intent(this@Medium,MenuActivity::class.java)

                val alertDialog=AlertDialog.Builder(this@Medium)
                alertDialog.setTitle("Ha albatta !!!")
                alertDialog.setMessage("Chiqsangiz oyinni davom ettira olmaysiz !!!")


                alertDialog.setPositiveButton("Ok") { dialog, l ->

                    startActivity(intent)
                    dialog.dismiss()
                    finish()

                }
                alertDialog.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                alertDialog.show()
            }
        } )


        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        }

        val images= mutableListOf(R.drawable.baseline_favorite_24,R.drawable.flash
            ,R.drawable.flight,R.drawable.baseline_insert_emoticon_24,
            R.drawable.baseline_person_24,R.drawable.baseline_phone_24)

        images.addAll(images)
        images.shuffle()


        binding.apply {


            var ischeaked=true
            switchmedium.setOnClickListener {
                if (ischeaked){
                    switchmedium.setBackgroundResource(R.drawable.baseline_volume_up_24)
                    ischeaked=false
                    if (player==null){
                        player=MediaPlayer.create(this@Medium,R.raw.ring)
                        player!!.start()
                    }

                }
                else{
                    switchmedium.setBackgroundResource(R.drawable.baseline_volume_off_24)
                    ischeaked=true
                    player=null

                }
            }

            clockThread = Thread(){
                while(true){

                    // 1 sekund kutish
                    Thread.sleep(1000)

                    // sekund oshirish
                    seconds++

                    // agar sekund 60ga teng bo'lsa
                    if(seconds == 60){
                        // minut oshirish
                        minutes++

                        // sekundni 0 qilish
                        seconds = 0
                    }

                    // textView ni yanglash
                    runOnUiThread {
                        if (minutes<10){
                            if (seconds<10){
                                timemedium.text="0${minutes}:0${seconds}"
                            }
                            else timemedium.text="0${minutes}:${seconds}"
                        }
                        else  timemedium.text = "${minutes}:${seconds}"
                    }
                }
            }
            clockThread.start()


            buttons= listOf(imageButton9,imageButton10,imageButton11,imageButton12
            ,imageButton13,imageButton14,imageButton15,imageButton16,imageButton17,imageButton18,imageButton19,imageButton20)

            arrow.setOnClickListener {
                val player2=MediaPlayer.create(this@Medium,R.raw.lose)
                player2.start()
                val intent= Intent(this@Medium, MenuActivity::class.java)
                startActivity(intent)

                if (player!=null){
                    player!!.start()
                }
                finish()
            }

            level.text=intent?.getStringExtra("level");
        }

        cards= buttons.indices.map { indeks->
            MemoryCard(images[indeks])
        }

        buttons.forEachIndexed{index, button ->
            button.setOnClickListener {
                updateModels(index)
                updateViews()
                if (player!=null){
                    player!!.start()
                }
            }
        }
    }

    private fun updateViews() {
        var ischeak=true
        for (card in cards){
            if (!card.isFaceUp){
                ischeak=false
            }
        }
        if (ischeak){

            val player1=MediaPlayer.create(this,R.raw.winner)
            player1.start()
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setView(R.layout.dialog_win)

            alertDialog.setPositiveButton("Menu window") { _, _ ->
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                player1.stop()
                finish()
            }
            val dialog=alertDialog.create()
            val animview=dialog.findViewById<LottieAnimationView>(R.id.lottie)

            animview?.setAnimation(R.raw.winner_anim)
            dialog.show()
            animview?.playAnimation()

        }

        cards.forEachIndexed { index, card ->
            val button=buttons[index]
            if (card.isMatched){
                val countDownTimer = object : CountDownTimer(500, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                    }
                    override fun onFinish() {
                        button.visibility=View.INVISIBLE
                    }
                }
                countDownTimer.start()
            }
//            if (!card.isFaceUp)
//            button.setImageResource(R.drawable.img)
        }
    }
    private fun updateModels(position:Int) {
        val card=cards[position]
        val button=buttons[position]

        if (card.isFaceUp){
            Toast.makeText(this,"Invalid move", Toast.LENGTH_SHORT).show()
            return
        }
        if (indexofSelectedCard==null){
            restorecards()
            indexofSelectedCard=position
        }
        else{
            cheakForMatch(indexofSelectedCard!!,position)
            indexofSelectedCard=null
        }
        card.isFaceUp=!card.isFaceUp

        val animation= AnimationUtils.loadAnimation(this,R.anim.scale1)
        button.startAnimation(animation)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }
            override fun onAnimationEnd(animation: Animation?) {
                button.setImageResource(card.identifier)
                val animation1= AnimationUtils.loadAnimation(this@Medium,R.anim.scale2)
                button.startAnimation(animation1)
            }
            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
    }
    private fun restorecards() {
        for (card in cards){
            if (!card.isMatched){

                card.isFaceUp=false
            }
        }
    }
    private fun cheakForMatch(position1: Int,position2: Int) {
        if (cards[position1].identifier==cards[position2].identifier){
            cards[position1].isMatched=true
            cards[position2].isMatched=true
        }
        else{
            val countDownTimer = object : CountDownTimer(500, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                }
                override fun onFinish() {
                    buttons[position1].setImageResource(R.drawable.img)
                    buttons[position2].setImageResource(R.drawable.img)
                    cards[position2].isFaceUp=false
                    cards[position1].isFaceUp=false
                }
            }
            countDownTimer.start()
        }
    }



}