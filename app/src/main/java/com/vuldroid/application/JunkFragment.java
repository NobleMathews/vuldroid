package com.vuldroid.application;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;


import java.util.Locale;

public class JunkFragment extends Fragment {
    private final JSInterface jsInterface = new JSInterface();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View wv = inflater.inflate(R.layout.frag_other, null);
        WebView myWebView = wv.findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(jsInterface, "LIGHT_SENSOR");
        myWebView.loadUrl(this.getActivity().getIntent().getDataString());
        return wv;
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
    }
}
