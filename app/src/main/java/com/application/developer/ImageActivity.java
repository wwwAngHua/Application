package com.application.developer;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.TextView;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import java.io.IOException;

public class ImageActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        try
        {
        ImageView image = (ImageView) findViewById(R.id.imageviewer);
        Intent intent = getIntent();
        Bitmap bitmap = BitmapFactory.decodeFile(intent.getStringExtra("ImagePath"));
        image.setImageBitmap(bitmap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
}
