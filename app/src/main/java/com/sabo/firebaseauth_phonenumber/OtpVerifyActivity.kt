package com.sabo.firebaseauth_phonenumber

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sabo.firebaseauth_phonenumber.databinding.ActivityOtpVerifyBinding
import java.util.concurrent.TimeUnit

class OtpVerifyActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PhoneAuthActivity"
        /** CountDown Timer */
        const val DELAY: Long = 60000
        const val INTERVAL: Long = 1000
    }

    private lateinit var binding: ActivityOtpVerifyBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mResendingToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var mVerificationId: String
    private lateinit var mPhoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextInput()

        mAuth = Firebase.auth
        mPhoneNumber = intent.getStringExtra("phone").toString()
        mVerificationId = intent.getStringExtra("verificationId").toString()
        mResendingToken = intent.getParcelableExtra("forceResendingToken")!!

        binding.tvMobile.text = mPhoneNumber


        /** countDownTimer */
        countDownTimer()


        binding.btnVerify.setOnClickListener {
            val c1 = binding.etC1.text.toString().trim()
            val c2 = binding.etC2.text.toString().trim()
            val c3 = binding.etC3.text.toString().trim()
            val c4 = binding.etC4.text.toString().trim()
            val c5 = binding.etC5.text.toString().trim()
            val c6 = binding.etC6.text.toString().trim()
            if (c1.isEmpty() || c2.isEmpty() || c3.isEmpty() || c4.isEmpty() || c5.isEmpty() || c6.isEmpty())
                Callback(this).onToast("OTP is not valid!")
            else {
                isVisible(true)
                if (mVerificationId != null) {
                    val smsCode = "$c1$c2$c3$c4$c5$c6"
                    val credential = PhoneAuthProvider.getCredential(mVerificationId, smsCode)

                    Firebase.auth.signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                isVisible(true)
                                Callback(this).onToast("Welcome .... ${task.result}")
                                startActivity(
                                    Intent(
                                        this,
                                        MainActivity::class.java
                                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                )
                            } else {
                                isVisible(false)
                                Callback(this).onToast("OTP is not valid!")
                            }
                        }
                }
            }
        }
    }

    private fun countDownTimer(){
        countDownTimer = object : CountDownTimer(DELAY, INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                binding.textView7.text =
                    Callback(this@OtpVerifyActivity).elapsedCountDownTimer(
                        millisUntilFinished.div(
                            INTERVAL
                        )
                    )
                binding.tvResendBtn.isVisible = false
            }

            override fun onFinish() {
                binding.textView7.text = "Don't get the OTP?"
                binding.tvResendBtn.isVisible = true
                binding.tvResendBtn.setTextColor(resources.getColor(R.color.red_400, theme))
                binding.tvResendBtn.setOnClickListener {
                    isVisible(true)
                    forceResendingToken()
                }
            }
        }
        countDownTimer.start()
    }

    private fun forceResendingToken() {
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted: ${credential.smsCode}")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                isVisible(false)
                Callback(this@OtpVerifyActivity).onToast(e.localizedMessage!!)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
//                super.onCodeSent(verificationId, token)
                isVisible(false)
                Callback(this@OtpVerifyActivity).onToast("OTP is successfully send.")
                mResendingToken = token
                mVerificationId = verificationId
            }
        }

        PhoneAuthProvider.verifyPhoneNumber(
            PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(mPhoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .setForceResendingToken(mResendingToken)
                .build()
        )
    }

    private fun isVisible(visible: Boolean) {
        binding.progressBarVerify.isVisible = visible
        binding.btnVerify.isVisible = !visible
    }

    private fun editTextInput() {
        binding.etC1.doOnTextChanged { _, _, _, _ ->
            binding.etC2.requestFocus()
        }
        binding.etC2.doOnTextChanged { _, _, _, _ ->
            binding.etC3.requestFocus()
        }
        binding.etC3.doOnTextChanged { _, _, _, _ ->
            binding.etC4.requestFocus()
        }
        binding.etC4.doOnTextChanged { _, _, _, _ ->
            binding.etC5.requestFocus()
        }
        binding.etC5.doOnTextChanged { _, _, _, _ ->
            binding.etC6.requestFocus()
        }
    }
}