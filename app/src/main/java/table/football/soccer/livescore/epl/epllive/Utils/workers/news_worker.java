package table.football.soccer.livescore.epl.epllive.Utils.workers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.ExecutionException;

import androidx.work.Result;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Team_comparision_activity;


import static android.support.v4.content.ContextCompat.getSystemService;

/**
 * Created by Ran on 06-Dec-18.
 */

public class news_worker extends Worker {


    private static final String TAG ="news_worker" ;

    public news_worker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
       // Log.d(TAG, "doWork: ");
//        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
      //  new ShowNewsNotification().execute();
//        MakeNotification(new WebView(getApplicationContext()).getSettings().getUserAgentString());
        return Result.success();
    }


    private class ShowNewsNotification extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            Document document;
            String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";

            try{
                document = Jsoup.connect("https://www.sportskeeda.com/go/epl?page=1").userAgent(USER_AGENT).get();
                Elements NewsDataList = document.select(".story-wrapper");//team home
                String link =      NewsDataList.select("a").attr("abs:href");
                final String headline =  NewsDataList.select("a").text();
                String thumblink = NewsDataList.select(".in-block-img").attr("data-lazy");
                String NewsID =    NewsDataList.select(".list-story-link").attr("data-id");

                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(thumblink)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                //imageView.setImageBitmap(resource);
                              //  displayNotification("EPL News",headline,resource);
                            }
                        });

            }catch(Exception e){
                System.out.println(e);
            }
            return null;
        }

    }


    private void MakeNotification(String UserAgent){
        Document document;
        //String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";

        try{
            document = Jsoup.connect("https://www.sportskeeda.com/go/epl?page=1").userAgent(UserAgent).referrer("https://www.sportskeeda.com").get();
            Elements NewsDataList = document.select(".story-wrapper");//team home
            String link =      NewsDataList.select("a").attr("abs:href");
            final String headline =  NewsDataList.select("a").text();
            String thumblink = NewsDataList.select(".in-block-img").attr("data-lazy");
            String NewsID =    NewsDataList.select(".list-story-link").attr("data-id");
          //  Log.d(TAG, "MakeNotification: "  + link + " " + thumblink );
          //  displayNotification("EPL News",headline,thumblink);


            FutureTarget<Bitmap> futureTarget =
                    Glide.with(getApplicationContext())
                            .asBitmap()
                            .load(thumblink)
                            .submit(512, 256);
// 512x256 image sieze guie
//            if (futureTarget.isDone()){
//
//            }
            Bitmap bitmap = futureTarget.get();
            displayNotification("EPL News",headline,bitmap);


//            Glide.with(getApplicationContext())
//                    .asBitmap()
//                    .load(thumblink)
//                    .into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                            //imageView.setImageBitmap(resource);
//
//                            displayNotification("EPL News",headline,resource);
//                        }
//                    });

        }catch(Exception e){
            System.out.println(e);
            Log.d(TAG, "MakeNotification:Error " + e.getMessage());
        }


    }

    private void displayNotification(String title, String message,Bitmap image) {

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("LatestNewa", "Epl News Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "LatestNewa")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(image)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(image)
                        .bigLargeIcon(null));


        notificationManager.notify(1, notification.build());
    }
}
