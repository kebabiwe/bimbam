package com.example.bimbam

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log

import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService:FirebaseMessagingService() {
   companion object{
       private const val TAG = "FCM Notification"
       const val DEFAULT_NOTIFICATION_ID = 0
   }

    override fun onNewToken(token: String) {
        Log.i(TAG,"new FCM token created: $token")
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        initNotificationChannel(notificationManager)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        var notificationBuilder = if(Build.VERSION_CODES.O <= Build.VERSION.SDK_INT){
            NotificationCompat.Builder(applicationContext,"1")
        }else{
            NotificationCompat.Builder(applicationContext)
        }
        notificationBuilder = notificationBuilder
            .setSmallIcon(R.drawable.ic_icon_app)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
        initNotificationChannel(notificationManager)
        notificationManager.notify(DEFAULT_NOTIFICATION_ID, notificationBuilder.build())
    }
    private fun initNotificationChannel(notificationManager: NotificationManager) {
        if(Build.VERSION_CODES.O == Build.VERSION.SDK_INT){
            notificationManager.createNotificationChannelIfNotExist(
                channelId = "1",
                channelName = "Default"
            )
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun NotificationManager.createNotificationChannelIfNotExist(
    channelId : String,
    channelName: String,
    importancee:Int = NotificationManager.IMPORTANCE_DEFAULT)
 {
    var channel = this.getNotificationChannel(channelId)
     if(channel == null){
        channel = NotificationChannel(channelId, channelName, importancee)
         this.createNotificationChannel(channel)
     }
}