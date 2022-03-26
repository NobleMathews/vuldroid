package com.vuldroid.application;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import java.util.Locale;

public class MainActivity extends PreferenceActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected boolean isValidFragment(String fragmentName) {
        // BAD: any Fragment name can be provided.
        return true;
    }
}

