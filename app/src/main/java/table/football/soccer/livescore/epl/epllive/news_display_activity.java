package table.football.soccer.livescore.epl.epllive;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import io.github.angebagui.mediumtextview.MediumTextView;
import table.football.soccer.livescore.epl.epllive.Utils.Helpers.InterAdsHelpers;

public class news_display_activity extends AppCompatActivity {

    private WebView mWebView;
    private ProgressBar progressBar;
    private Button Reload;
    private com.google.android.gms.ads.InterstitialAd Google_mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display_activity);

        new InterAdsHelpers("News_Ativity",getApplicationContext()).ShowInterAds();
        mWebView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progress_bar);
        Reload = findViewById(R.id.reload_button);

        final String url =  getIntent().getExtras().getString("News_Link");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new MyBrowser());


        Reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl(url);
            }
        });
//        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipe.setRefreshing(true);
//
//            }
//        });



    }


    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        if (mWebView.canGoBack()){
            mWebView.goBack();
        }else {
            if (isTaskRoot()) {
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                super.onBackPressed();
            }else {
                super.onBackPressed();
            }
        }
    }

    private class MyBrowser extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
           // Log.d("NewsDAa", "onReceivedError: " + url);
           // Toast.makeText(news_display_activity.this, "Swup", Toast.LENGTH_SHORT).show();
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            //swipe.setEnabled(true);

            progressBar.setVisibility(View.GONE);
            if(error.getErrorCode()==-2){
                Reload.setVisibility(View.VISIBLE);
            }
          Log.d("NewsDAa", "onReceivedError: " + error.getErrorCode() + " request: " + error.getDescription());
          //  Toast.makeText(news_display_activity.this, error.getDescription(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}
