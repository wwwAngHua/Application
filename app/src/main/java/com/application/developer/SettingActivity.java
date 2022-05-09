package com.application.developer;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.text.Layout;
import android.view.View.OnClickListener;
import android.service.autofill.OnClickAction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.DialogInterface;
import android.net.Uri;

public class SettingActivity extends Activity
{
    private String gxdz;
    private Dialog dialog,dialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        LinearLayout appupdate = (LinearLayout) findViewById(R.id.appupdate);
        LinearLayout bugfeed = (LinearLayout) findViewById(R.id.bugfeed);
        LinearLayout softhelp = (LinearLayout) findViewById(R.id.softhelp);
        LinearLayout softabout = (LinearLayout) findViewById(R.id.softabout);
        TextView version = (TextView) findViewById(R.id.version);
        PackageManager pm = getPackageManager();
        try
        {
            PackageInfo pi = pm.getPackageInfo(getPackageName(),0);
            String name = pi.versionName;
            version.setText("当前软件版本v"+name);
        }
        catch (PackageManager.NameNotFoundException e)
        {}
        appupdate.setOnClickListener(new OnClickListener()
        {
                @Override
                public void onClick(View p1)
                {
                    PackageManager ver = getPackageManager();
                    try
                    {
                        PackageInfo sion = ver.getPackageInfo(getPackageName(),0);
                        String version = sion.versionName;
                        String versions = GetHttps.getHtml("http://app.huayi-w.cn/application/config.txt");
                        String bbh = FileUtils.getSubString(versions, "<version>", "</version>");
                        String qzgx = FileUtils.getSubString(versions, "<forceupdate>", "</forceupdate>");
                        String gxnr = FileUtils.getSubString(versions, "<updatetext>", "</updatetext>");
                        gxdz = FileUtils.getSubString(versions, "<updateurl>", "</updateurl>");
                        String gxnr1 = gxnr.replace("€","\n");
                        if (Double.parseDouble(version) < Double.parseDouble(bbh))
                        {
                            if (qzgx.equals("true"))
                            {
                                AlertDialog.Builder build1 = new AlertDialog.Builder(SettingActivity.this);
                                build1.setTitle("发现新版本");
                                build1.setMessage(gxnr1);
                                build1.setCancelable(false);
                                build1.setPositiveButton("前往更新", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface p1, int p2)
                                        {
                                            Intent intent_d= new Intent(); 
                                            intent_d.setAction("android.intent.action.VIEW"); 
                                            Uri content_url = Uri.parse(gxdz); 
                                            intent_d.setData(content_url); startActivity(intent_d);
                                        }
                                    });
                                dialog1 = build1.create();
                                dialog1.show();
                            }
                            else
                            {
                            AlertDialog.Builder build = new AlertDialog.Builder(SettingActivity.this);
                            build.setTitle("发现新版本");
                            build.setMessage(gxnr1);
                            build.setPositiveButton("前往更新", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface p1, int p2)
                                {
                                    Intent intent_d= new Intent(); 
                                    intent_d.setAction("android.intent.action.VIEW"); 
                                    Uri content_url = Uri.parse(gxdz); 
                                    intent_d.setData(content_url); startActivity(intent_d);
                                }
                            });
                            build.setNegativeButton("取消", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface p1, int p2)
                                {
                                    dialog.dismiss();
                                }
                            });
                            dialog = build.create();
                            dialog.show();
                            }
                        }
                        else
                        {
                            Toast.makeText(SettingActivity.this,"已是最新版",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (PackageManager.NameNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        bugfeed.setOnClickListener(new OnClickListener()
        {
                @Override
                public void onClick(View p1)
                {
                    Intent sort = new Intent();
                    sort.setClass(SettingActivity.this,FeedActivity.class);
                    startActivity(sort);
                }
            });
        softhelp.setOnClickListener(new OnClickListener()
        {
                @Override
                public void onClick(View p1)
                {
                    Intent intent = new Intent(SettingActivity.this,WebActivity.class);
                    intent.putExtra("Title","Soft手册");
                    intent.putExtra("Url","file:///android_asset/Soft.txt");
                    startActivity(intent);
                }
            });
        softabout.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View p1)
                {
                    Intent intent = new Intent(SettingActivity.this,WebActivity.class);
                    intent.putExtra("Title","关于");
                    intent.putExtra("Url","file:///android_asset/About.txt");
                    startActivity(intent);
                }
            });
    }
    
}
