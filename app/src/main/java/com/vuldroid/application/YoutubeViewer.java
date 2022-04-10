package com.vuldroid.application;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class YoutubeViewer extends AppCompatActivity {
    private final JSInterface jsInterface = new JSInterface();

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_youtubeviewer);
        String lods = "https://www.google.com";
        WebView vulnerable =(WebView) findViewById(R.id.vulweb);
        WebSettings webSettings = vulnerable.getSettings();
        webSettings.setAllowContentAccess(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        vulnerable.setWebChromeClient(new WebChromeClient());
        vulnerable.addJavascriptInterface(jsInterface, "LIGHT_SENSOR_1");
        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        vulnerable.setWebViewClient(webViewClient);
//        if ((getIntent() != null) || getIntent().hasExtra("intent_url")) {
//            vulnerable.loadUrl(getIntent().getStringExtra("intent_url"));
//        }
//        else{vulnerable.loadUrl(lods);}
        try{
            vulnerable.loadUrl(getIntent().getData().toString());
        } catch (Exception e){
            vulnerable.loadUrl(lods);
        }
    }
//    public class WebViewClientImpl extends WebViewClient {
//
//        private Activity activity = null;
//
//        public WebViewClientImpl(Activity activity) {
//            this.activity = activity;
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
//            return false;
//
//        }
//
//    }
    class WebViewClientImpl extends WebViewClient {

        private Activity activity = null;
        public WebViewClientImpl(Activity activity) {
            this.activity = activity;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            Uri uri = request.getUrl();
            if (uri.getPath().startsWith("/local_cache/")) {
                File cacheFile = new File(activity.getCacheDir(), uri.getLastPathSegment());
                if (cacheFile.exists()) {
                    InputStream inputStream;
                    try {
                        inputStream = new FileInputStream(cacheFile);
                    } catch (IOException e) {
                        return null;
                    }
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Access-Control-Allow-Origin", "*");
                    return new WebResourceResponse("text/html", "utf-8", 200, "OK", headers, inputStream);
                }
            }
            return super.shouldInterceptRequest(view, request);
        }

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
