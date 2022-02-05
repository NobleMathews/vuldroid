package com.vuldroid.application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.Map;

public class BlogsViewer extends AppCompatActivity {
    String gettoken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogsviewer);
        Bundle extras = getIntent().getExtras();
        if(extras == null){
             WebView vulnerable =(WebView) findViewById(R.id.loads);
            WebSettings webSettings = vulnerable.getSettings();
            webSettings.setJavaScriptEnabled(true);
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

    }}
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

