package com.example.lucifer.ayilaile_hz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Life_Detail_Activity extends AppCompatActivity {

    public static final String LIFE_NAME = "life_name";

    public static final String LIFE_IMAGE_ID = "life_image_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_detail);
        Intent intent = getIntent();
        String lifeName = intent.getStringExtra(LIFE_NAME);
        int lifeImageId = intent.getIntExtra(LIFE_IMAGE_ID,0);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        ImageView lifeImageView = (ImageView)findViewById(R.id.life_image_view);
        TextView lifeContentText = (TextView)findViewById(R.id.life_content_text);
        //setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(lifeName);
        Glide.with(this).load(lifeImageId).into(lifeImageView);
        String fruitContent = generateFruitContent(lifeName);
        lifeContentText.setText(fruitContent);
    }

    private String generateFruitContent(String fruitName) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            fruitContent.append(fruitName);
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
