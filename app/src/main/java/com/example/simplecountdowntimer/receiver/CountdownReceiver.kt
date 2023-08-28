package com.example.simplecountdowntimer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.simplecountdowntimer.UI.simpleCountDownTimerViewModel

/*
class CountdownReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Handle countdown logic here
        val viewModel = simpleAlarmManagerViewModel(context!!.applicationContext as Application)

        val remainingTime = viewModel.remainingTime.value ?: 0L


        if (remainingTime > 0) {
            viewModel.updateRemainingTime(remainingTime - 1)
        } else {
            // Stop the countdown and do other necessary tasks
            viewModel.stopTimer()
        }


    }
}


 */

class CountdownReceiver(private val viewModel: simpleCountDownTimerViewModel) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val remainingTime = viewModel.remainingTime.value ?: 0L

        if (remainingTime > 0) {
            viewModel.updateRemainingTime(remainingTime - 1)
        } else {
            // Stop the countdown and do other necessary tasks
            viewModel.stopTimer()
        }
    }
}
