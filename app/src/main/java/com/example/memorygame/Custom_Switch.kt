package com.example.memorygame

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.media.MediaPlayer
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import androidx.annotation.RequiresApi

@SuppressLint("CustomViewStyleable")
@RequiresApi(Build.VERSION_CODES.M)
class Custom_Switch @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {


    private val imageView: ImageView


    init {
        gravity=Gravity.CENTER
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.PersonView)
        val image = typedArray.getResourceId(R.styleable.PersonView_image, 0)
        typedArray.recycle()

        imageView = ImageView(context).apply {
            layoutParams = LayoutParams(70.dp, 50.dp)
            if (image != 0) setImageResource(image)

        }
        var ischeaked=true
        imageView.setBackgroundResource(R.drawable.baseline_volume_off_24)

        imageView.setOnClickListener {
            if (ischeaked){
                imageView.setBackgroundResource(R.drawable.baseline_volume_up_24)
                ischeaked=false
            }
            else{
                imageView.setBackgroundResource(R.drawable.baseline_volume_off_24)
                ischeaked=true

            }
        }
        addView(imageView)
    }
    fun setImage(resId: Int) {
        imageView.setImageResource(resId)
    }
    }




