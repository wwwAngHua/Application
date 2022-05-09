package com.application.developer;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.view.View.OnClickListener;

public class FeedActivity extends Activity
{

    private EditText text,contact;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        text = findViewById(R.id.feedbacktext);
        contact = findViewById(R.id.contact);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new OnClickListener()
        {
                @Override
                public void onClick(View p1)
                {
                    if (text.getText().toString().equals(""))
                    {
                        Toast.makeText(FeedActivity.this,"请填写反馈内容",Toast.LENGTH_SHORT).show();
                    }
                    else if (contact.getText().toString().equals(""))
                    {
                        Toast.makeText(FeedActivity.this,"请填写联系方式",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        try
                        {
                        String versions = GetHttps.getHtml("http://app.huayi-w.cn/application/input.php?text="+text.getText().toString()+"&cin="+contact.getText().toString());
                        Toast.makeText(FeedActivity.this,"反馈成功，感谢支持 (-^〇^-)",Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            });
    }
}
