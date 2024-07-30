    package com.paisalo.newinternalsourcingapp;

    import android.app.NotificationChannel;
    import android.app.NotificationManager;
    import android.app.PendingIntent;
    import android.content.Intent;
    import android.media.RingtoneManager;
    import android.net.Uri;
    import android.os.Build;
    import android.util.Log;
    import android.widget.RemoteViews;

    import androidx.core.app.NotificationCompat;

    import com.google.firebase.messaging.FirebaseMessagingService;
    import com.google.firebase.messaging.RemoteMessage;


    import java.util.Date;


    public class MyFirebaseMessagingService extends FirebaseMessagingService {
        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {
            Log.d("TAG", "onMessageReceived: "+remoteMessage.getNotification().getBody());
            Log.d("TAG", "onMessageReceived: "+remoteMessage.getNotification().getTitle());
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(),remoteMessage.getMessageId());

        }
        @Override
        public void onNewToken(String token) {
            Log.d("TAG", "Refreshed token: " + token);

            // You can send the token to your server if needed
            // sendRegistrationToServer(token);
        }
        private void sendNotification(String title, String messageBody, String messageId) {
            // Create notification channel for devices with Android Oreo and higher
            String channelId = "default_channel_id";
            NotificationManager notificationManager = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                notificationManager = getSystemService(NotificationManager.class);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "PDL";
                String description = "PDL";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(channelId, name, importance);
                channel.setDescription(description);
                notificationManager.createNotificationChannel(channel);
            }
            Intent intent = new Intent(this, NotificationActivity.class);
            intent.putExtra("notification", 2);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,  PendingIntent.FLAG_MUTABLE);
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.dialogqr_layout);

 // Create intent for opening app when notification is tapped
            // You can customize the intent to navigate to a specific activity
            Long time=new Date().getTime() + 60000;
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle(title)
                            .setContentText(messageBody)
                             .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

            // Show notification
            notificationManager.notify(2 /* ID of notification */, notificationBuilder.build());
        }
    }
