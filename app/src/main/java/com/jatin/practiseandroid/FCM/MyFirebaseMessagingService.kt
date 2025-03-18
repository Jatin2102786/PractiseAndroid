package com.jatin.practiseandroid.FCM

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jatin.practiseandroid.R

//You have to extend your service class with "FirebaseMessagingService"
class MyFirebaseMessagingService: FirebaseMessagingService() {

    private val TAG = "FireBaseServices"
    var firebase = ""

    val channelDescription = "FCM channel used for messaging"
    val channelID = "FCM Channel"
//    val notificationManager: NotificationManager by lazy {
//        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//    }

    private lateinit var notificationManager: NotificationManager

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)




        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notificationData= message.notification




        print("notification title ${notificationData?.title}")
        print("notification body ${notificationData?.body}")
        println("type ${notificationData}")
        print("notification data ${message.data}")
        Log.e(TAG, "onMessageReceived: ${notificationData?.title} ${message.data}", )
        firebase = message.data.toString()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = channelDescription
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channelId = channelID

            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }

        generateNotification(message)
    }




    private fun generateNotification(notificationData: RemoteMessage, bitmap: Bitmap?= null) {

//        Intent
        var intent = Intent(this, FCMActivity::class.java)
        intent.putExtra("data", firebase)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP )


//        Pending Intent
        var pendingIntent = PendingIntent.getActivity(this, 1, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)


//        Building Notification
        var builder = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notificationData?.notification?.title?:"")
            .setContentText(notificationData?.notification?.body?:"")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)
            .setContentIntent(pendingIntent)

        if(bitmap != null){
            builder.setLargeIcon(bitmap)
        }

        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())

    }
}