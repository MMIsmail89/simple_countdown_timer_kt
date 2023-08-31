package com.example.simplecountdowntimer

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat

import com.example.simplecountdowntimer.databinding.ActivityThreeBinding
import com.example.simplecountdowntimer.receiver.alarmReceiver
import com.example.simplecountdowntimer.utils.sendNotification
import com.example.simplecountdowntimer.utils.sendNotification_SAC0
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class ActivityThree : AppCompatActivity() {
    private var binding: ActivityThreeBinding? = null
    private lateinit var picker:MaterialTimePicker
    private lateinit var calendar : Calendar
    private lateinit var alarm_manager: AlarmManager

    private lateinit var intent : Intent
    private lateinit var pendingIntent : PendingIntent
    val requestCode2 = 2

    private var settingTime : getHrMin = getHrMin(0, 0, true)

    private  var hour = 0
    private var minutes = 0
    private var seconds = 0
    private var milliseconds = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_three)
        binding = ActivityThreeBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)
        //
        createNotificationChannel()

        currentTime()

        binding?.actThreeTvCurrentTime?.setOnClickListener {
            currentTime()
        }

        //

        binding?.actThreeBtnBack?.setOnClickListener {
            finish()
        }
         //
        binding?.actThreeBtnSelectTime?.setOnClickListener {
            showTimePicker()
        }

        binding?.actThreeBtnSetAlarm?.setOnClickListener {
            setAlarm()
        }

        binding?.actThreeBtnCancelAlarm?.setOnClickListener {
            cancelAlarm()
        }
        //


    }

    private fun currentTime() {
//        Hour: hr
//        Minutes: min
//        Seconds: sec
//        Milliseconds: ms

        calendar = Calendar.getInstance()

        // Get hour, minutes, seconds, and milliseconds

         hour = calendar.get(Calendar.HOUR_OF_DAY) // 24-hour format
         minutes = calendar.get(Calendar.MINUTE)
         seconds = calendar.get(Calendar.SECOND)
         milliseconds = calendar.get(Calendar.MILLISECOND)

        binding?.actThreeTvCurrentTime?.text = if(hour > 12) {
            String.format("%2d", hour-12) +
                    " hr : " +
                    String.format("%2d", minutes) +
                    " min : " +
                    String.format("%2d", seconds) +
                    " sec : " +
                    String.format("%3d", milliseconds) +
                    " ms, PM"
        } else{
            String.format("%2d", hour) +
                    " hr : " +
                    String.format("%2d", minutes) +
                    " min : " +
                    String.format("%2d", seconds) +
                    " sec : " +
                    String.format("%3d", milliseconds) +
                    " ms, AM"
        }

        //
        binding?.actThreeBtnCancelAlarm?.isEnabled = false
        binding?.actThreeBtnSetAlarm?.isEnabled = false

        //
        settingTime.setHr(hour)
        settingTime.setMin(minutes)

        if(hour<12){
            settingTime.setAM()
        } else {
            settingTime.reSetAM()
        }
    }



    private fun setAlarm() {
        alarm_manager = getSystemService(ALARM_SERVICE) as AlarmManager

        intent = Intent(this, alarmReceiver::class.java)

        intent.putExtra("hour", settingTime.getHr())
        intent.putExtra("minute", settingTime.getMin())
        intent.putExtra("isAM", settingTime.isAM())

        pendingIntent = PendingIntent.getBroadcast(this, requestCode2,
            intent, PendingIntent.FLAG_MUTABLE)



        alarm_manager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent)

        Toast.makeText(this, "Alarm set successfully", Toast.LENGTH_LONG).show()


        val hour1 = settingTime.getHr() // 24-hour format
        val minutes1 = settingTime.getMin()
        val AM1 =  settingTime.isAM()

        val message_notification = "Setting Time: $hour1:$minutes1, ${if(AM1 == true){"AM"} else {"PM"}}"



        val notificationManager = this?.let {
            ContextCompat.getSystemService(
                it,
                NotificationManager::class.java
            )
        } as NotificationManager

        notificationManager.sendNotification_SAC0(
            message_notification,
            this@ActivityThree)


    }

    private fun showTimePicker() {

        hour = calendar.get(Calendar.HOUR_OF_DAY) // 24-hour format
        minutes = calendar.get(Calendar.MINUTE)


        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(hour)
            .setMinute(minutes)
            .setTitleText("Select Alarm Time")
            .build()

        picker.show(supportFragmentManager, getString(R.string.channel_ID_KEY))
        // picker.show(supportFragmentManager, "Picker for timer")

        picker.addOnPositiveButtonClickListener {
            binding?.actThreeTvCurrentTime?.text = if(picker.hour > 12) {
                String.format("%2d", picker.hour-12) +
                        " : " +
                        String.format("%2d", picker.minute) +
                        ", PM"
            } else{
                String.format("%2d", picker.hour) +
                        " : " +
                        String.format("%2d", picker.minute) +
                        ", AM"
            }

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            //
            settingTime.setHr(picker.hour)
            settingTime.setMin(picker.minute)

            if(picker.hour<12){
                settingTime.setAM()
            } else {
                settingTime.reSetAM()
            }
            //
            binding?.actThreeBtnCancelAlarm?.isEnabled = true
            binding?.actThreeBtnSetAlarm?.isEnabled = true

        }




    }

    private fun cancelAlarm() {
        alarm_manager = getSystemService(ALARM_SERVICE) as AlarmManager

        intent = Intent(this, alarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this, requestCode2,
            intent, PendingIntent.FLAG_MUTABLE)
        alarm_manager.cancel(pendingIntent)

        Toast.makeText(this, "Alarm is cancelled!!", Toast.LENGTH_LONG).show()

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name : CharSequence = "Pray memorize"
            val description = "Pray first to do"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(getString(R.string.channel_ID_KEY), name, importance)
            channel.description = description
            val notification_manager = getSystemService(NotificationManager::class.java)

            notification_manager.createNotificationChannel(channel)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding  = null
    }
}