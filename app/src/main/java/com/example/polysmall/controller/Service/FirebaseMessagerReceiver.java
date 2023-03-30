//package com.example.polysmall.controller.Service;
//
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.os.Build;
//import android.widget.RemoteViews;
//
//import androidx.core.app.NotificationCompat;
//
//import com.example.polysmall.MainActivity;
//
//public class FirebaseMessagerReceiver extends FirebaseMessagingService {
//
//
//    @Override
//    public void onMessageReceived(@NonNull RemoteMessage message) {
//        if (message.getNotification() != null){
//            showNotification(message.getNotification().getTitle(),message.getNotification().getBody());
//        }
//        super.onMessageReceived(message);
//    }
//
//    private void showNotification(String title, String body) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        String channelId = "noti";
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),channelId)
//                .setSmallIcon(R.drawable.notifications)
//                .setAutoCancel(true)
//                .setVibrate(new long[]{1000,1000,1000,1000})
//                .setOnlyAlertOnce(true)
//                .setContentIntent(pendingIntent);
//        builder = builder.setContent(customView(title,body));
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//            NotificationChannel notificationChannel = new NotificationChannel(channelId,"web_app",NotificationManager.IMPORTANCE_HIGH);
//            notificationManager.createNotificationChannel(notificationChannel);
//        }
//        notificationManager.notify(0, builder.build());
//    }
//
//    private RemoteViews customView(String title, String body){
//        RemoteViews remoteView=new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
//        remoteView.setTextViewText(R.id.title_noti,title);
//        remoteView.setTextViewText(R.id.body_notifi,body);
//        remoteView.setImageViewResource(R.id.imgnoti,R.drawable.notifications);
//        return remoteView;
//    }
//}
