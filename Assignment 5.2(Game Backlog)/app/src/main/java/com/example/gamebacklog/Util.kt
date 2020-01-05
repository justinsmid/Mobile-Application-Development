package com.example.gamebacklog

import android.widget.EditText

fun string(et: EditText) : String = et.text!!.toString().trim()

fun int(et: EditText) : Int = string(et).toInt()