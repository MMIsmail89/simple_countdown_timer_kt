package com.example.simplecountdowntimer.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.simplecountdowntimer.ActivityThree
import com.example.simplecountdowntimer.R
import com.example.simplecountdowntimer.UI.simpleCountDownTimerViewModel
import com.example.simplecountdowntimer.utils.sendNotification

class alarmReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationManager = context?.let {
            ContextCompat.getSystemService(
                it,
                NotificationManager::class.java
            )
        } as NotificationManager

        notificationManager.cancelAll()

        val hour = intent?.getIntExtra("hour", 1) ?: 0
        val minutes = intent?.getIntExtra("minute", 22) ?: 0
        val isAM = intent?.getBooleanExtra("isAM", false) ?: true

//        val activityThree= ActivityThree()
//
//        val hour = activityThree.settingTime.getHr() // 24-hour format
//        val minutes = activityThree.settingTime.getMin()
//        val isAM =  activityThree.settingTime.isAM()

        val message_notification = "Setting Time: $hour:$minutes, ${if(isAM){"AM"} else {"PM"}}" +
                "\n" + context.getString(R.string.message_notification)

        notificationManager.sendNotification(
            message_notification,
            context)


    }
}
