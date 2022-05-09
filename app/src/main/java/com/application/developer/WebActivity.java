package com.application.developer;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.content.Intent;

public class WebActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        WebView webview = (WebView) findViewById(R.id.manual);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("Title"));
        webview.loadUrl(intent.getStringExtra("Url"));
    }
    
}
