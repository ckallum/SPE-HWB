package com.example.healthproject.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthproject.R;

public class InstagramFragment extends Fragment {


    public InstagramFragment(){

    }





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_instagram, container, false);



        WebView webView = v.findViewById(R.id.instPage);
        WebSettings settings = webView.getSettings();


        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.instagram.com/bristolunisport/?hl=en");

        settings.setDomStorageEnabled(true);

        return v;

    }




}




