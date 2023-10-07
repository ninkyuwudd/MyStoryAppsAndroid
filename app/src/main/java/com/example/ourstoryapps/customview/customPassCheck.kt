package com.example.ourstoryapps.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.ourstoryapps.R

class customPassCheck: AppCompatEditText{

    private var showPassIconDraw : Drawable? = null
    private var hidePassIconDraw : Drawable? = null
    private var passVisible  = false


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun init(){
//        showPassIconDraw = ContextCompat.getDrawable(context, R.drawable.baseline_remove_red_eye_24)
//        hidePassIconDraw = ContextCompat.getDrawable(context, R.drawable.ic_hidden)
//
//        setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,showPassIconDraw,null)

//        setOnTouchListener { _, event ->
//            if (event.action == MotionEvent.ACTION_UP) {
//                if (event.rawX >= right - compoundDrawables[2].bounds.width()) {
//                    passwordVisibleToggle()
//                    return@setOnTouchListener true
//                }
//            }
//            false
//        }

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                passwordCheking(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

    }

//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        hint = "Masukkan nama Anda"
//        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
//    }

    private fun passwordCheking(password: String){
        if(password.length < 8){
            setError("Password tidak boleh kurang dari 8 karakter", null)
        }else{
            error = null
        }
    }

    private fun passwordVisibleToggle(){
        passVisible = !passVisible

        val drawableIc = if(passVisible) hidePassIconDraw else showPassIconDraw
        setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,drawableIc,null)

        transformationMethod = if (passVisible) null else PasswordTransformationMethod.getInstance()
    }


}