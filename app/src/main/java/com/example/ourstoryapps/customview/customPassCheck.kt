package com.example.ourstoryapps.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.ourstoryapps.R

class customPassCheck:ConstraintLayout {
    private lateinit var editText: EditText
    private lateinit var showHideButton: ImageButton

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

    }

//    private init {
//        LayoutInflater.from(context).inflate(R.layout.activity_main, this, true)
//        editText = findViewById(R.id.)
//    }
}