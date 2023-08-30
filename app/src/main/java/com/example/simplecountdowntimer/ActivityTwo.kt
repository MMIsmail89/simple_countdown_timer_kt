package com.example.simplecountdowntimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.simplecountdowntimer.UI.simpleCountDownTimerViewModel
import com.example.simplecountdowntimer.databinding.ActivityTwoBinding

class ActivityTwo : AppCompatActivity() {
    private var binding: ActivityTwoBinding? = null
    private lateinit var simpleCountDownTimer_ViewModel: simpleCountDownTimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_two)
        binding = ActivityTwoBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)
        //

        /*
        Absolutely, you can use the AlarmManager class instead of the Timer class,
        for scheduling tasks like the countdown timer.
        The AlarmManager is better suited for such tasks,
         because it can wake up the device if it's in a low-power state,
          and it's designed to handle scheduling operations,
          even when your app is not actively running.
         */

        simpleCountDownTimer_ViewModel = ViewModelProvider(this).get(simpleCountDownTimerViewModel::class.java)

        //
        simpleCountDownTimer_ViewModel.elapsedTime.observe(this) { remainingSeconds ->
            if(remainingSeconds == 0L){
                binding?.actTwoTvShowing?.text = "Hit below button to start!!"
            } else if(remainingSeconds != -1L) {
                binding?.actTwoTvShowing?.text = "The remaining seconds:" +
                        " ${remainingSeconds/simpleCountDownTimer_ViewModel.second}"
            } else {
                binding?.actTwoTvShowing?.text = "Timer is cancelled!!"
            }
        }

        binding?.actTwoBtnStartTimer?.setOnClickListener {
            val selectedInterval = 60L // 5 minutes
            simpleCountDownTimer_ViewModel.startTimer(selectedInterval)
        }

        binding?.actTwoBtnStopTimer?.setOnClickListener {
            simpleCountDownTimer_ViewModel.stopTimer()
        }

        binding?.actTwoTvTitle?.text = "ActivityTwo in " +
                "${simpleCountDownTimer_ViewModel.getAppName().toString().uppercase()}"


        //
        binding?.actTwoBtnBack?.setOnClickListener {
            finish()
        }
        binding?.actTwoBtnNextActivity?.setOnClickListener {
            Intent(this@ActivityTwo, ActivityThree::class.java).also {
                startActivity(it)
            }
        }

        //



    }

    override fun onDestroy() {
        super.onDestroy()
        binding  = null
    }
}