package com.example.simplecountdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.simplecountdowntimer.UI.simpleTimerViewModel
import com.example.simplecountdowntimer.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    private lateinit var simpleTimer_ViewModel: simpleTimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)
        //

        simpleTimer_ViewModel = ViewModelProvider(this).get(simpleTimerViewModel::class.java)

        binding?.mainBtnStartTimer?.setOnClickListener {
            val seconds = 300L // 5 minutes
            simpleTimer_ViewModel.startTimer(seconds)
        }

        binding?.mainBtnStopTimer?.setOnClickListener {
            simpleTimer_ViewModel.stopTimer()
        }

        simpleTimer_ViewModel.remainingTime.observe(this) { remainingSeconds ->
            if(remainingSeconds != -1L) {
                binding?.mainTvShowing?.text = "The remaining seconds: $remainingSeconds"
            } else {
                binding?.mainTvShowing?.text = "Timer is cancelled!!"

            }
        }


    }
}