package com.example.simplecountdowntimer.utils

import com.example.simplecountdowntimer.ActivityThree
import com.example.simplecountdowntimer.R


import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat

//


// Notification ID.
val NOTIFICATION_ID = 1


public  fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    // Create the content intent for the notification, which launches this activity

    // >> create intent
    val contentIntent = Intent(applicationContext, ActivityThree::class.java)
    // >> create PendingIntent
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    // >> add style
    val LargeIconImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.pray_islam
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(LargeIconImage)
        .bigLargeIcon(null)

    val bigTextStyle = NotificationCompat.BigTextStyle()
        .bigText(messageBody)

    // >> get an instance of NotificationCompat.Builder
    // Build the notification

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.channel_ID_KEY))

        // >> set title, text and icon to builder
        .setSmallIcon(R.drawable.ic_alarm_access)
        .setContentTitle(applicationContext.getString(R.string.title_notification))
        .setContentText(messageBody)

        // >> set content intent
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)

        // >> add style to builder
        .setStyle(bigPicStyle)
        .setStyle(bigTextStyle)
        .setLargeIcon(LargeIconImage)


        // >> set priority
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    // >> call notify
    notify(NOTIFICATION_ID, builder.build())
}

public  fun NotificationManager.sendNotification_SAC0(messageBody: String, applicationContext: Context) {
    // Create the content intent for the notification, which launches this activity



    // >> add style
    val LargeIconImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.pray_islam
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(LargeIconImage)
        .bigLargeIcon(null)


    val bigTextStyle = NotificationCompat.BigTextStyle()
        .bigText(messageBody)

    // >> get an instance of NotificationCompat.Builder
    // Build the notification

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.channel_ID_KEY))

        // >> set title, text and icon to builder
        .setSmallIcon(R.drawable.ic_alarm_access)
        .setContentTitle(applicationContext.getString(R.string.title_notification))
        .setContentText(messageBody)

        // >> set content intent
        .setAutoCancel(false)

        // >> add style to builder
        .setStyle(bigPicStyle)
        .setStyle(bigTextStyle)
        .setLargeIcon(LargeIconImage)


        // >> set priority
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    // >> call notify
    notify(NOTIFICATION_ID, builder.build())
}
public fun NotificationManager.cancelNotifications() {
    cancelAll()
}