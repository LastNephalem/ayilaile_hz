package com.example.lucifer.ayilaile_hz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

public class search_Activity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton search_Left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        WebView webView = (WebView)findViewById(R.id.web_view);
        search_Left = (ImageButton)findViewById(R.id.btn_search_left);
        search_Left.setOnClickListener(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();
        String icon = intent.getStringExtra("item");
        switch (icon){
            case "1":
                webView.loadUrl("https://www.baidu.com/s?wd=%E6%89%93%E5%BC%80%E6%89%8B%E6%9C%BA%E6%89%AB%E4%B8%80%E6%89%AB");
                break;
            case "2":
                webView.loadUrl("https://www.baidu.com/s?wd=%E7%94%9F%E6%B4%BB%E5%B0%8F%E6%8A%80%E5%B7%A7");
                break;
            case "3":
                webView.loadUrl("https://www.baidu.com/s?wd=%E5%AD%A9%E5%AD%90%E6%88%90%E9%95%BF%E4%B8%AD%E7%9A%84%E7%83%A6%E6%81%BC");
                break;
            case "4":
                webView.loadUrl("https://www.baidu.com/s?wd=%E8%90%8C%E5%AE%A0%E5%A4%A7%E4%BD%9C%E6%88%98");
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search_left:
                finish();
                break;
            default:
                break;
        }
    }
}
