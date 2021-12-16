package com.sabo.firebaseauth_phonenumber

import android.content.Context
import android.text.format.DateUtils
import android.widget.Toast

class Callback(private val context: Context) {

    fun onToast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    fun elapsedCountDownTimer(time: Long): String {
        return "Code verification resend in... " + DateUtils.formatElapsedTime(time).replace(".", ":")
    }
}