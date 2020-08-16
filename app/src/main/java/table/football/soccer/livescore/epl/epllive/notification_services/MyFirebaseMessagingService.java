package table.football.soccer.livescore.epl.epllive.notification_services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Objects;
import java.util.Random;

import table.football.soccer.livescore.epl.epllive.MainActivity;
import table.football.soccer.livescore.epl.epllive.Match_Activity;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Team_comparision_activity;
import table.football.soccer.livescore.epl.epllive.Utils.Pref_fav_tem_settings;
import table.football.soccer.livescore.epl.epllive.Utils.Pref_notification_setting;
import table.football.soccer.livescore.epl.epllive.Utils.Pref_subscirbe_match_setting;
import table.football.soccer.livescore.epl.epllive.activity_yt_player;
import table.football.soccer.livescore.epl.epllive.news_display_activity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String TAG = "FirebaseNotificaation";



    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
    }



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

      //  Log.d(TAG,"Message data payload: " + remoteMessage);
        if(remoteMessage.getData().size()>0){


           // Log.d(TAG,"Message data payload: " +  remoteMessage.getFrom());





            try {
                if (Objects.equals(remoteMessage.getFrom(), "/topics/videos")){
                    // VideoNotification("\uD83C\uDFA5 EPL Videos" + " \uD83C\uDFA5","\uD83D\uDE00 New Videos Added Watch Them \uD83D\uDC53");
                 //   Log.d(TAG,"Message data payload Data: " +  remoteMessage.getData());
                    JSONObject obj = new JSONObject(remoteMessage.getData());
                    String ClickAction =   obj.getString("ClickAction");
                    String ImageLink   =   obj.getString("imageLink");
                    String videoId     =   obj.getString("videID");
                    String VideoTitle  =   obj.getString("VideoTitle");
                    String ChannelID   =   obj.getString("channelId");


                    FutureTarget<Bitmap> futureTarget =
                            Glide.with(getApplicationContext())
                                    .asBitmap()
                                    .load(ImageLink)
                                    .submit(512, 256);
                    // 512x256 image sieze guie
                    Bitmap bitmap = futureTarget.get();

                    VideoNotification("EPL Videos",VideoTitle,bitmap,videoId,ChannelID,ClickAction);
                }else if (Objects.equals(remoteMessage.getFrom(), "/topics/app")){
                    updateApp("\uD83C\uDF1FUpdate Now,New Version\uD83C\uDF1F","Please Click to Update your app");
                }



                //New_Activity

                JSONObject obj = new JSONObject(remoteMessage.getData());

                String ClickAction = obj.getString("ClickAction");
                if (Objects.equals(ClickAction, "Match_Activity")){
                    String NotificationFor = obj.getString("Notification_for_Team");
                    String HomeTeamName =  obj.getString("HomTeam");
                    String AwayTeamName=  obj.getString("AwayTeam");
                    String HomTeamID =  obj.getString("HomTeamID");
                    String AwayTeamID =  obj.getString("AwayTeamID");
                    String title = obj.getString("Title");
                    String body = obj.getString("body");
                    int MatchID = obj.getInt("MatchID");
                    int EmojiCode = obj.getInt("EmojiCode");
                    if (isMatchSubscribed(String.valueOf(MatchID)) || isTeamNotificationIsEnable(NotificationFor) || isFavTeam(Integer.parseInt(NotificationFor))){
//                        String HomeTeam
//                    ,String AwayTeam,String HomeTeamID,String AwayTeamID
                        sendNotificationforLiveMatch(title,body,getEmojiByUnicode(EmojiCode),HomeTeamName,AwayTeamName,HomTeamID,AwayTeamID,String.valueOf(MatchID));
//                    Log.d(TAG, "onMessageReceived: " + "NotificationFor:" +NotificationFor +" HomeTeamName:" + HomeTeamName
//                            +" AwayTeamName:"+AwayTeamName+" HomeTeamID:"+HomTeamID+" AwayTeamID:"+AwayTeamID+" title:"+title
//                            +" body:"+body + " ClickAction"+ClickAction+" MatchID:"+MatchID+" EmojiCode:"+EmojiCode );

                    }
                }
                else if (Objects.equals(ClickAction, "New_Activity")){
                    Log.d(TAG,"Message data payload: " + remoteMessage.getData());
                    String thumlink =  obj.getString("ThubNail");
                    String NewsHeadLine =  obj.getString("Headline");
                    String NewLink =  obj.getString("NewsLink");


                    FutureTarget<Bitmap> futureTarget =
                            Glide.with(getApplicationContext())
                                    .asBitmap()
                                    .load(thumlink)
                                    .submit(512, 256);
                    // 512x256 image sieze guie
                    Bitmap bitmap = futureTarget.get();
                    displayNotification("EPL News",NewsHeadLine,bitmap,NewLink);


                }
                else if (Objects.equals(ClickAction, "Team_comparision_activity")){
                    displayCompareTeamNotification("⚽ Compare Teams","See How your Team is Performing?");
                }




            } catch (Exception e) {
                Log.d(TAG, "onMessageReceived: " + e.getMessage());
            }




        }

    }


    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }




    private static int getNotificationId() {
        Random rnd = new Random();
        return 100 + rnd.nextInt(9000);
    }

    //For team notification only
    private Boolean isTeamNotificationIsEnable(String TeamID){
        /*
         if(new Pref_fav_tem_settings(getContext()).getFavTeam()!=0){
            CloseButton.setVisibility(View.VISIBLE);
        }
         */
        return new Pref_notification_setting(getApplicationContext()).isNotificationEnable(TeamID);
    }

    //For fav Team
    // if(new Pref_fav_tem_settings(getContext()).getFavTeam()!=0)
    private Boolean isFavTeam(int TeamID){
        if(TeamID ==new Pref_fav_tem_settings(getApplicationContext()).getFavTeam())
            return true;
    return false;
    }

    private Boolean isMatchSubscribed(String MatchID){
        return new Pref_subscirbe_match_setting(getApplicationContext()).isNotificationEnable(MatchID);
    }




     //------Notificaion Method Starts---------
    //notification of events match
    private void sendNotificationforLiveMatch(String title, String messageBody,String EmojiCode,String HomeTeam
                    ,String AwayTeam,String HomeTeamID,String AwayTeamID,String MatchID) {
        String channelId = getString(R.string.default_notification_channel_id);
        //Match_Activity
        Intent intent = null;
        intent = new Intent(this, Match_Activity.class);

        intent.putExtra("HomeTeam",HomeTeam);
        intent.putExtra("AwayTeam",AwayTeam);
        intent.putExtra("HomeTeamID",HomeTeamID);
        intent.putExtra("AwayTeamID",AwayTeamID);
        intent.putExtra("MatchId",MatchID);


        intent.setAction(Long.toString(System.currentTimeMillis()));
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(intent);
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
    //           | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //PendingIntent pendingIntent = stackBuilder.getActivity(this, 0 /* Request code */, intent,
        //        PendingIntent.FLAG_ONE_SHOT);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
   /*
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
      */


//.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_attach_money_black_24dp))
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(EmojiCode+title)
                .setContentText(messageBody)
                .setPriority(Notification.PRIORITY_MAX).setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Match Live Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = notificationBuilder.build();

        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(path);

        //  notification.flags    |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;



        assert notificationManager != null;
        notificationManager.notify(getNotificationId(),notification);


    }


    //Notification in news
    private void displayNotification(String title, String message,Bitmap image,String newsLink) {


        Intent     intent = new Intent(this, news_display_activity.class);
        intent.putExtra("News_Link",newsLink);
      //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("NewsNotification", "Daily News Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "NewsNotification")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(image)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(image)
                        .bigLargeIcon(null))
                .setAutoCancel(true).setContentIntent(pendingIntent);
        Notification notification = notificationBuilder.build();

        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(path);

        //  notification.flags    |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        notificationManager.notify(getNotificationId(), notification);
    }


    //Notification in Video videos
    private void VideoNotification(String title,String messageVideoTitle,Bitmap image,String VideoID,String VideChannelID,String ClickAction) {

      //  VideoId = Objects.requireNonNull(getIntent().getExtras()).getString("Video");
       // String ChanelId = Objects.requireNonNull(getIntent().getExtras()).getString("chanID");


        Intent     intent = new Intent(this, activity_yt_player.class);
        intent.putExtra("Video",VideoID);
        intent.putExtra("chanID",VideChannelID);
        //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("VideoNotification", "Daily Video Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "VideoNotification")
                .setContentTitle(title)
                .setContentText(messageVideoTitle)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(image)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(image)
                        .bigLargeIcon(null))
                .setAutoCancel(true).setContentIntent(pendingIntent);
        Notification notification = notificationBuilder.build();

        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(path);

        //  notification.flags    |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        notificationManager.notify(getNotificationId(), notification);











//
//
//
//
//
//        Intent     intent = new Intent(this, MainActivity.class);
//    //    intent.putExtra("News_Link",newsLink);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//
//        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("videoupdate", "Video New Notification", NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "videoupdate")
//                .setContentTitle(title)
//                .setContentText(message)
//                .setSmallIcon(R.drawable.ic_launcher_background)
//
//                .setAutoCancel(true).setContentIntent(pendingIntent);
//        Notification notification = notificationBuilder.build();
//
//        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        notificationBuilder.setSound(path);
//
//        //  notification.flags    |= Notification.FLAG_AUTO_CANCEL;
//        notification.defaults |= Notification.DEFAULT_SOUND;
//        notification.defaults |= Notification.DEFAULT_VIBRATE;
//
//        notificationManager.notify(getNotificationId(), notification);
    }


    //Update App Notificaation apps request code 3
    private void updateApp(String title,String message){

        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        PendingIntent pendingIntent;
        try {
             //  startActivity(goToMarket);
             pendingIntent = PendingIntent.getActivity(this, 3 /* Request code */, goToMarket,
                    PendingIntent.FLAG_ONE_SHOT);
        } catch (ActivityNotFoundException e) {

             pendingIntent = PendingIntent.getActivity(this, 3 /* Request code */, new Intent(Intent.ACTION_VIEW,
                             Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())),
                    PendingIntent.FLAG_ONE_SHOT);
        }







        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("UpdateApp", "Update App Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "UpdateApp")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true).setContentIntent(pendingIntent);
        Notification notification = notificationBuilder.build();

        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(path);

        //  notification.flags    |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        notificationManager.notify(getNotificationId(), notification);

    }



    //Notifcation Compare your Team events
    private void displayCompareTeamNotification(String title,String message){
        Intent     intent = new Intent(this, Team_comparision_activity.class);
        //    intent.putExtra("News_Link",newsLink);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("CompateTeam", "Compate Team Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "CompateTeam")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_background)

                .setAutoCancel(true).setContentIntent(pendingIntent);
        Notification notification = notificationBuilder.build();

        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(path);

        //  notification.flags    |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        notificationManager.notify(getNotificationId(), notification);

    }









}



