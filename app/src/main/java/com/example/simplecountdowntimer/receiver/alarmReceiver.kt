package com.example.simplecountdowntimer.receiver

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.simplecountdowntimer.ActivityThree
import com.example.simplecountdowntimer.R
import com.example.simplecountdowntimer.UI.simpleCountDownTimerViewModel

class alarmReceiver() : BroadcastReceiver() {
     val requestCode1 = 1
     val notificationID = 1
     val channel_ID_KEY = "prayAlarmTimer"
    override fun onReceive(context: Context?, intent: Intent?) {
        val i = Intent(context, ActivityThree::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivities(context, requestCode1, arrayOf(i),
            PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context!!, channel_ID_KEY)
            .setSmallIcon(R.drawable.ic_alarm_access)
            .setContentTitle("منبه الصلاة")
            .setContentText("قال الله تعالى في القرآن الكريم: \"حَافِظُوا عَلَى الصَّلَوَاتِ وَالصَّلَاةِ الْوُسْطَىٰ وَقُومُوا لِلَّهِ قَانِتِينَ\"، مما يؤكد على أهمية أداء الصلاة.")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(notificationID, builder.build())
        }
    }
}
