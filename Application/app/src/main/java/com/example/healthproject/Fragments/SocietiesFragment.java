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

public class SocietiesFragment extends Fragment {


    public SocietiesFragment(){

    }





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_twitter, container, false);



        WebView webView = v.findViewById(R.id.twitterPage);
        WebSettings settings = webView.getSettings();


        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.bristolsu.org.uk/sports-clubs-societies");

        settings.setDomStorageEnabled(true);

        return v;

    }




}




