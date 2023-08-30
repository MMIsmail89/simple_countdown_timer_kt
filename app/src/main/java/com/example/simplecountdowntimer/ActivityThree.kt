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
import android.widget.Toast
import com.example.simplecountdowntimer.UI.simpleTimerViewModel

import com.example.simplecountdowntimer.databinding.ActivityThreeBinding
import com.example.simplecountdowntimer.receiver.alarmReceiver
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
    }

    private fun cancelAlarm() {
        alarm_manager = getSystemService(ALARM_SERVICE) as AlarmManager

        intent = Intent(this, alarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this, requestCode2,
            intent, PendingIntent.FLAG_MUTABLE)
        alarm_manager.cancel(pendingIntent)

        Toast.makeText(this, "Alarm is cancelled!!", Toast.LENGTH_LONG).show()

    }

    private fun setAlarm() {
        alarm_manager = getSystemService(ALARM_SERVICE) as AlarmManager
        intent = Intent(this, alarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this, requestCode2,
            intent, PendingIntent.FLAG_MUTABLE)

        alarm_manager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        Toast.makeText(this, "Alarm set successfully", Toast.LENGTH_LONG).show()

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

        val  alarm_receiver = alarmReceiver()

        picker.show(supportFragmentManager, alarm_receiver.channel_ID_KEY)
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
        }


    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name : CharSequence = "Pray memorize"
            val description = "Pray first to do"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val  alarm_receiver = alarmReceiver()
            val channel = NotificationChannel(alarm_receiver.channel_ID_KEY, name, importance)
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