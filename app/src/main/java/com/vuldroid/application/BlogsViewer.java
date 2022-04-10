package com.vuldroid.application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.JavascriptInterface;

import java.util.Locale;

public class BlogsViewer extends AppCompatActivity {
    String gettoken;
    private final JSInterface jsInterface = new JSInterface();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogsviewer);
        Bundle extras = getIntent().getExtras();
        if(extras == null){
             WebView vulnerable =(WebView) findViewById(R.id.loads);
            WebSettings webSettings = vulnerable.getSettings();
            webSettings.setJavaScriptEnabled(true);
            vulnerable.addJavascriptInterface(jsInterface, "LIGHT_SENSOR_2");
            webSettings.setAllowFileAccessFromFileURLs(true);
            vulnerable.setWebChromeClient(new WebChromeClient());
            WebViewClientImpl webViewClient = new WebViewClientImpl(this);
            vulnerable.setWebViewClient(webViewClient);
            vulnerable.loadUrl("https://medium.com"); }

            else{gettoken=getIntent().getData().getQueryParameter("url");
            WebView vulnerable =(WebView) findViewById(R.id.loads);
            WebSettings webSettings = vulnerable.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            vulnerable.setWebChromeClient(new WebChromeClient());
            WebViewClientImpl webViewClient = new WebViewClientImpl(this);
            vulnerable.setWebViewClient(webViewClient);
            vulnerable.loadUrl(gettoken);}

    }

    private static class JSInterface {
        float lux = 0.0f;

        private void updateLux(float lux) {
            this.lux = lux;
        }

        @JavascriptInterface
        public String getLux() {
            return (String.format(Locale.US, "{\"lux\": %f}", lux));
        }

        @JavascriptInterface
        public Boolean getLuxer() {
            return true;
        }
    }
}

class WebViewClientImpl extends WebViewClient {

    private Activity activity = null;

    public WebViewClientImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        return false;

    }

}

