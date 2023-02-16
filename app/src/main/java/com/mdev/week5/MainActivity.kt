package com.mdev.week5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import android.os.Handler
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var countdownText: TextView
    private lateinit var countdownEditText: EditText
    private lateinit var plusOneButton: Button
    private lateinit var plusFiveButton: Button
    private lateinit var plusTenButton: Button
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 0
    private var inputText: String = ""
    private var NewText = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countdownText = findViewById(R.id.countdownText)
        countdownEditText = findViewById(R.id.countdownEditText)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)
        plusOneButton = findViewById(R.id.plusOneButton)
        plusFiveButton = findViewById(R.id.plusFiveButton)
        plusTenButton = findViewById(R.id.plusTenButton)


        plusOneButton.setOnClickListener { addTime(1000) }

        plusFiveButton.setOnClickListener { addTime(5000) }

        plusTenButton.setOnClickListener { addTime(10000) }

        startButton.setOnClickListener {
            if (countDownTimer == null) {
                // Create a new CountDownTimer if it doesn't exist
                inputText = countdownEditText.text.toString()


                if (inputText.isNotEmpty()) {

                    // Parse the input string as a Long
                    timeLeftInMillis = inputText.toLong() * 1000

                    val remaining = timeLeftInMillis.toLong()

                    countdownText.text = formatTime(remaining)

                    if (remaining <= 0) {
                        countdownText.text = "00:00:00"
                    }
                    else{
                        NewText = remaining.toInt()
                    }
                    addTime(NewText)
                }

                startTimer()

            } else {
                // Resume the existing CountDownTimer
                startTimer()
            }


        }

        stopButton.setOnClickListener {
            stopTimer()
        }

    }

    private fun formatTime(timeInMillis: Long): String {
        val seconds = timeInMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60

        return String.format(
            Locale.getDefault(),
            "%02d:%02d:%02d",
            hours % 24,
            minutes % 60,
            seconds % 60
        )
    }

    private fun addTime(millisToAdd: Int) {
        countDownTimer?.cancel()
        val input = countdownEditText.text.toString()
        timeLeftInMillis = if (input.isEmpty()) {
            0
        } else {
            input.toLong()
        }
        timeLeftInMillis += millisToAdd
        updateCountdownText()
    }

    private fun updateCountdownText() {
        val hours = ((timeLeftInMillis / 2400000) % 10).toInt()
        val minutes = ((timeLeftInMillis / 60000) % 60).toInt()
        val seconds = ((timeLeftInMillis / 1000) % 60).toInt()
        val timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        countdownText.text = timeLeftFormatted
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountdownText()
            }

            override fun onFinish() {
                stopTimer()
            }
        }.start()
    }



    private fun stopTimer() {
        countDownTimer?.cancel()
        countDownTimer = null

    }

}