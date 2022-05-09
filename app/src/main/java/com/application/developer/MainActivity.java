package com.application.developer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.sdklib.build.ApkBuilder;
import com.android.sdklib.build.ApkCreationException;
import com.android.sdklib.build.SealedApkException;
import com.application.developer.SettingActivity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import kellinwood.security.zipsigner.ZipSigner;
import org.eclipse.jdt.internal.compiler.batch.Main;
import android.util.Log;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import java.util.List;
import android.widget.ListView;
import java.util.LinkedList;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import com.android.dx.dex.code.PositionList;
import android.content.DialogInterface;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.KeyEvent;
import com.tendcloud.tenddata.TCAgent;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.net.Uri;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;

public class MainActivity extends Activity
{
    public static final String TAG = "MainActivity.Compile";
    private List<AppList> mData = null;
    private Context mContext;
    private AppAdapter mAdapter = null;
    private ListView list_animal;
    Dialog dialog,dialog1,dialog2;
    private String gxdz;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        //aapt文件路径
        File file = new File("/data/data/com.application.developer/files/aapt");
        File files = new File("/data/data/com.application.developer/files/android.jar");
        //判断aapt文件是否存在，如果不存在就复制aapt到私有目录
        if (!file.exists() && !files.exists())
        {
            try
            {
                InputStream input = getResources().getAssets().open("aapt");
                FileOutputStream out = this.openFileOutput("aapt",Context.MODE_PRIVATE);
                byte[] b = new byte[input.available()];
                InputStream inputs = getResources().getAssets().open("android.jar");
                FileOutputStream outs = this.openFileOutput("android.jar",Context.MODE_PRIVATE);
                byte[] bs = new byte[inputs.available()];
                inputs.read(bs);
                outs.write(bs);
                inputs.close();
                outs.close();
                input.read(b);
                out.write(b);
                input.close();
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            //设置可执行
            file.setExecutable(true);
            files.setExecutable(true);
            //Dialog弹窗
            AlertDialog.Builder build = new AlertDialog.Builder(this);
            build.setTitle("欢迎");
            build.setMessage("欢迎来到Application！\n\nApplication是一个集成开发环境（IDE），直接在Android设备上开发真正的Android应用程序，让你在没有电脑的情况下，一步一步成为一个专家应用程序开发人员。\n\nApplication支持Soft&XML和Android SDK开发应用程序，用Java&XML和Android SDK开发应用程序，不仅如此，Application还支持Soft与Java进行混编交互。\n\nApplication设计的目的是构建一个Soft语言的开发环境，让人人都能开发应用程序，Soft语言简化了Java繁琐的代码并保留了Java原有的特点，让小白也能够快速上手开发应用程序。\n\nApplication使用的主要编程语言是Soft语言，如需了解学习Soft语言请阅读Soft快速开发手册。\n\nApplication当前软件版本为1.0版本，功能尚未完善，如果您在使用过程中遇到什么问题或者有什么好的建议欢迎反馈给我们，让我们更好的完善Application，同时欢迎各位有兴趣有志向的小伙伴一起交流一同发展。\n\n开发者：呆瓜Dusk\n联系QQ：422584084\n联系邮箱：duskmail@qq.com\nApplication官方QQ交流群：737444923");
            build.setCancelable(false);
            build.setPositiveButton("确定", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface p1, int p2)
                {
                    try
                    {
                        //获取project目录下的项目列表
                        mContext = MainActivity.this;
                        list_animal = (ListView) findViewById(R.id.applistview);
                        list_animal.setEmptyView(findViewById(R.id.appnoproject));
                        mData = new LinkedList<AppList>();
                        List<String> lists = FileUtils.readFolders(new File("/storage/emulated/0/Application/Project"));
                        for (String s : lists)
                        {
                            if (FileUtils.isProjectPackage(new File(s)))
                            {
                                String temp1 = "/AndroidManifest.xml";
                                String temp2 = "/res/values/strings.xml";
                                String temp3 = "/res/drawable/ic_launcher.png";
                                String apppackage = FileUtils.readFileContent(new File(s + temp1));
                                apppackage = FileUtils.getSubString(apppackage, "package=\"", "\"");
                                String appname = FileUtils.readFileContent(new File(s + temp2));
                                appname = FileUtils. getSubString(appname, "name=\"app_name\">", "</string>");
                                temp3 = s+temp3;
                                mData.add(new AppList(appname, apppackage, temp3, s));
                            }
                        }
                        mAdapter = new AppAdapter((LinkedList<AppList>) mData, mContext);
                        list_animal.setAdapter(mAdapter);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            });
            dialog = build.create();
            dialog.show();
            //Toast.makeText(this,"Welcome to Application!",Toast.LENGTH_SHORT).show();
        }
        
        try
        {
        //获取project目录下的项目列表
        mContext = MainActivity.this;
        list_animal = (ListView) findViewById(R.id.applistview);
        list_animal.setEmptyView(findViewById(R.id.appnoproject));
        mData = new LinkedList<AppList>();
        List<String> lists = FileUtils.readFolders(new File("/storage/emulated/0/Application/Project"));
        for (String s : lists)
        {
            if (FileUtils.isProjectPackage(new File(s)))
            {
                String temp1 = "/AndroidManifest.xml";
                String temp2 = "/res/values/strings.xml";
                String temp3 = "/res/drawable/ic_launcher.png";
                String apppackage = FileUtils.readFileContent(new File(s + temp1));
                apppackage = FileUtils.getSubString(apppackage, "package=\"", "\"");
                String appname = FileUtils.readFileContent(new File(s + temp2));
                appname = FileUtils. getSubString(appname, "name=\"app_name\">", "</string>");
                temp3 = s+temp3;
                mData.add(new AppList(appname, apppackage, temp3, s));
            }
        }
        mAdapter = new AppAdapter((LinkedList<AppList>) mData, mContext);
        list_animal.setAdapter(mAdapter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        /*加载application列表
        mContext = MainActivity.this;
        list_animal = (ListView) findViewById(R.id.applistview);
        mData = new LinkedList<AppList>();
        mData.add(new AppList("MyApp", "com.application.my", R.drawable.ic_launcher));
        mData.add(new AppList("MyApp1", "com.application.my1", R.drawable.ic_launcher));
        mData.add(new AppList("MyApp2", "com.application.my2", R.drawable.ic_launcher));
        mData.add(new AppList("MyApp3", "com.application.my3", R.drawable.ic_launcher));
        mData.add(new AppList("MyApp4", "com.application.my4", R.drawable.ic_launcher));
        mAdapter = new AppAdapter((LinkedList<AppList>) mData, mContext);
        list_animal.setAdapter(mAdapter);
        */
        
        //ListView单击事件
        list_animal.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
                @Override
                public void onItemClick(AdapterView<?> p1,View p2,int p3,long p4)
                {
                    AppList appname = mData.get(p3);
                    Intent intent = new Intent(MainActivity.this,FilesActivity.class);
                    intent.putExtra("AppName",appname.getappName());
                    intent.putExtra("Path",appname.getappPath());
                    startActivity(intent);
                }
            });
            
        //ListView长按事件
        list_animal.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
                @Override
                public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4)
                {
                    AppList datails = mData.get(p3);
                    Intent intent = new Intent(MainActivity.this,DatailsActivity.class);
                    intent.putExtra("Icon",datails.getappIcon());
                    intent.putExtra("Name",datails.getappName());
                    intent.putExtra("Package",datails.getappPackage());
                    intent.putExtra("Path",datails.getappPath());
                    startActivity(intent);
                    return true;
                }
            });
            
        //初始化TalkingData
        TCAgent.LOG_ON=true;
        TCAgent.init(this,"881EAF8519DF4165B2C110FF54E11C20","Android");
        TCAgent.setReportUncaughtExceptions(true);
        
        //检测版本更新以及获取公告
        PackageManager ver = getPackageManager();
        try
        {
            PackageInfo sion = ver.getPackageInfo(getPackageName(),0);
            String version = sion.versionName;
            String versions = GetHttps.getHtml("http://app.huayi-w.cn/application/config.txt");
            String bbh = FileUtils.getSubString(versions, "<version>", "</version>");
            String qzgx = FileUtils.getSubString(versions, "<forceupdate>", "</forceupdate>");
            String qzgg = FileUtils.getSubString(versions, "<display>", "</display>");
            String ggnr = FileUtils.getSubString(versions, "<bulletintext>", "</bulletintext>");
            String gxnr = FileUtils.getSubString(versions, "<updatetext>", "</updatetext>");
            gxdz = FileUtils.getSubString(versions, "<updateurl>", "</updateurl>");
            String gxnr1 = gxnr.replace("€","\n");
            String ggnr1 = ggnr.replace("€","\n");
            if (Double.parseDouble(version) < Double.parseDouble(bbh))
            {
                if (qzgx.equals("true"))
                {
                    AlertDialog.Builder build1 = new AlertDialog.Builder(MainActivity.this);
                    build1.setTitle("发现新版本");
                    build1.setMessage(gxnr1);
                    build1.setCancelable(false);
                    build1.setPositiveButton("前往更新", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface p1, int p2)
                            {
                                finish();
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
                    AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this);
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
                if (qzgg.equals("true"))
                {
                AlertDialog.Builder build1 = new AlertDialog.Builder(MainActivity.this);
                build1.setTitle("公告");
                build1.setMessage(ggnr1);
                build1.setCancelable(false);
                build1.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface p1, int p2)
                        {
                            dialog2.dismiss();
                        }
                    });
                dialog2 = build1.create();
                dialog2.show();
                }
            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        /*
        Button compile = (Button) findViewById(R.id.compile);
        compile.setOnClickListener(new OnClickListener()
        {
                @Override
                public void onClick(View p1)
                {
                    if (aapt())
                    {
                        if (ecj())
                        {
                            if (dex())
                            {
                                if (sdklib())
                                {
                                    if (zipSigner())
                                    {
                                        Toast.makeText(MainActivity.this,"编译成功",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(MainActivity.this,"签名失败",Toast.LENGTH_SHORT).show();
                                        Log.e(TAG,"zipSigner失败");
                                }
                                else
                                    Toast.makeText(MainActivity.this,"打包apk失败",Toast.LENGTH_SHORT).show();
                                    Log.e(TAG,"sdklib失败");
                            }
                            else
                                Toast.makeText(MainActivity.this,"dex编译失败",Toast.LENGTH_SHORT).show();
                                Log.e(TAG,"dex失败");
                        }
                        else
                            Toast.makeText(MainActivity.this,"Java编译失败",Toast.LENGTH_SHORT).show();
                            Log.e(TAG,"ecj失败");
                    }
                    else
                        Toast.makeText(MainActivity.this,"资源编译失败",Toast.LENGTH_SHORT).show();
                        Log.e(TAG,"aapt失败");
                }
            });
        */
    }
    
    //使用aapt编译资源
    public boolean aapt()
    {
        String[] args = 
        {
            //aapt文件路径
            "data/data/com.application.developer/files/aapt",
            //执行aapt编译资源
            "package","-v","-f","-m",
            //res文件夹路径
            "-S","/storage/emulated/0/AppProjects/Test/app/src/main/res/",
            //gen文件夹路径
            "-J","/storage/emulated/0/AppProjects/Test/app/build/gen/",
            //assets文件夹路径，如果没有会导致编译失败
            "-A","/storage/emulated/0/AppProjects/Test/app/src/main/assets/",
            //AndroidManifest.xml文件路径
            "-M","/storage/emulated/0/AppProjects/Test/app/src/main/AndroidManifest.xml",
            //android.jar文件路径
            "-I","/storage/emulated/0/.aide/android.jar",
            //输出.ap_文件路径
            "-F","/storage/emulated/0/AppProjects/Test/app/build/bin/resources.ap_"
        };
        try
        {
            Process process = Runtime.getRuntime().exec(args);
            int code = process.waitFor();
            /*
            //如果失败请打印此信息
            InputStream input=process.getInputStream(); 
            //获得执行信息
            InputStream input=process.getErrorStream(); 
            //获得错误信息
            */
            if (code!=0)
                return false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    //使用ecj编译java
    public boolean ecj()
    {
        //编译信息
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        //错误信息
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        Main main = new Main(new PrintWriter(baos1),new PrintWriter(baos2),false,null,null);
        //像下面这样可以直接打印信息
        //Main main=new Main(new PrintWriter(System.out),new PrintWriter(System.err),false,null,null);
        String[] args =
        {
            "-verbose",
            //第三方jar文件存放路径
            "-extdirs","/storage/emulated/0/AppProjects/Test/app/libs/",
            //android.jar文件路径
            "-bootclasspath","/storage/emulated/0/.aide/android.jar",
            //java文件存放路径
            "-classpath","/storage/emulated/0/AppProjects/Test/app/src/main/java/"+":"+
            //r.java文件存放路径
            "/storage/emulated/0/AppProjects/Test/app/build/gen/"+":"+
            //第三方jar文件存放路径，如果没有使用第三方jar那就不用添加，它们之间用冒号隔开
            "/storage/emulated/0/AppProjects/Test/app/libs/",
            "-1.6",
            "-target","1.6",
            "-proc:none",
            //class文件存放路径
            "-d","/storage/emulated/0/AppProjects/Test/app/build/bin/classes/",
            //第一个被执行的java文件
            "/storage/emulated/0/AppProjects/Test/app/src/main/java/com/huayi/test/MainActivity.java"
        };
        //执行编译并返回结果
        boolean b = main.compile(args);
        //如果失败请打印此信息
        //获得编译信息字符串
        String s1 = baos1.toString();
        //获得错误信息字符串
        String s2 = baos2.toString();
        return b;
    }
    
    //使用dx生成dex文件
    public boolean dex()
    {
        String[] args =
        {
            "--verbose",
            //核心数
            "--num-threads="+Runtime.getRuntime().availableProcessors(),
            //classes.dex文件输出路径
            "--output="+"/storage/emulated/0/AppProjects/Test/app/build/bin/classes.dex",
            //class文件存放路径
            "/storage/emulated/0/AppProjects/Test/app/build/bin/classes/",
            //如果使用了第三方jar请添加存放路径
            "/storage/emulated/0/AppProjects/Test/app/libs/"
        };
        com.android.dx.command.dexer.Main.Arguments arguments = new com.android.dx.command.dexer.Main.Arguments();
        arguments.parse(args);
        try
        {
            int code = com.android.dx.command.dexer.Main.run(arguments);
            if (code!=0)
                return false;
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    //使用sdklib打包生成未签名的apk
    public boolean sdklib()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            ApkBuilder builder = new ApkBuilder(
                //未签名的apk输出路径
                new File("/storage/emulated/0/AppProjects/Test/app/build/bin/app.ap_"),
                //aapt生成的文件路径
                new File("/storage/emulated/0/AppProjects/Test/app/build/bin/resources.ap_"),
                //dx生成的文件
                new File("/storage/emulated/0/AppProjects/Test/app/build/bin/classes.dex"),
                null,
                new PrintStream(baos)
            );
            builder.sealApk();
            //如果失败请打印此信息
            String s = baos.toString();
        }
        catch (ApkCreationException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (SealedApkException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //使用zipsigner签名apk
    public boolean zipSigner()
    {
        try
        {
            ZipSigner zipSigner = new ZipSigner();
            zipSigner.setKeymode("testkey");
            zipSigner.signZip(
                //未签名的apk文件
                "/storage/emulated/0/AppProjects/Test/app/build/bin/app.ap_",
                //签名输出apk文件
                "/storage/emulated/0/AppProjects/Test/app/build/bin/app.apk"
            );
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (GeneralSecurityException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public void requestPermission()
    {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ||
            ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
            ActivityCompat.requestPermissions(this,new String[]
            {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            },100);
        }
    }
    
    //创建Menu菜单 menu_main.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }
    
    //设置Menu菜单单击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.cation:
                //dialog弹窗
                AlertDialog.Builder build = new AlertDialog.Builder(this);
                build.setTitle("创建项目");
                LayoutInflater lay = this.getLayoutInflater();
                View v = lay.inflate(R.layout.new_project,null);
                build.setView(v);
                final TextView name = (TextView) v.findViewById(R.id.newprojectname);
                final TextView pack = (TextView) v.findViewById(R.id.newprojectpackage);
                build.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface p1, int p2)
                        {
                            if(name.getText().toString().equals(""))
                            {
                                Toast.makeText(MainActivity.this,"项目名不能为空",Toast.LENGTH_SHORT).show();
                            }
                            else if(pack.getText().toString().equals(""))
                            {
                                Toast.makeText(MainActivity.this,"包名不能为空",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                            try
                            {
                            //从assets目录下解压zip文件
                            String path = "/storage/emulated/0/Application/Project/"+name.getText().toString();
                            ZipUtils.UnZipAssetsFolder(MainActivity.this,"MySoft.sof",path);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            try
                            {
                            //修改项目AndroidManifest.xml包名和strings.xml软件名
                            String manifest = FileUtils.getString("/storage/emulated/0/Application/Project/"+name.getText().toString()+"/","AndroidManifest.xml");
                            String strings = FileUtils.getString("/storage/emulated/0/Application/Project/"+name.getText().toString()+"/res/values/","strings.xml");
                            String main = FileUtils.getString("/storage/emulated/0/Application/Project/"+name.getText().toString()+"/src/","Main.soft");
                            String manifest1 = manifest.replace("$APP_PACKAGE$",pack.getText().toString());
                            String strings1 = strings.replace("$APP_NAME$",name.getText().toString());
                            String main1 = main.replace("$APP_PACKAGE$",pack.getText().toString());
                            FileUtils.modifyFile("/storage/emulated/0/Application/Project/"+name.getText().toString()+"/AndroidManifest.xml",manifest1,false);
                            FileUtils.modifyFile("/storage/emulated/0/Application/Project/"+name.getText().toString()+"/res/values/strings.xml",strings1,false);
                            FileUtils.modifyFile("/storage/emulated/0/Application/Project/"+name.getText().toString()+"/src/Main.soft",main1,false);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                                try
                                {
                                //获取project目录下的项目列表
                                mContext = MainActivity.this;
                                list_animal = (ListView) findViewById(R.id.applistview);
                                list_animal.setEmptyView(findViewById(R.id.appnoproject));
                                mData = new LinkedList<AppList>();
                                List<String> lists = FileUtils.readFolders(new File("/storage/emulated/0/Application/Project"));
                                for (String s : lists)
                                {
                                    if (FileUtils.isProjectPackage(new File(s)))
                                    {
                                        String temp1 = "/AndroidManifest.xml";
                                        String temp2 = "/res/values/strings.xml";
                                        String temp3 = "/res/drawable/ic_launcher.png";
                                        String apppackage = FileUtils.readFileContent(new File(s + temp1));
                                        apppackage = FileUtils.getSubString(apppackage, "package=\"", "\"");
                                        String appname = FileUtils.readFileContent(new File(s + temp2));
                                        appname = FileUtils. getSubString(appname, "name=\"app_name\">", "</string>");
                                        temp3 = s+temp3;
                                        mData.add(new AppList(appname, apppackage, temp3, s));
                                    }
                               }
                               mAdapter = new AppAdapter((LinkedList<AppList>) mData, mContext);
                               list_animal.setAdapter(mAdapter);
                               Toast.makeText(MainActivity.this,"创建成功",Toast.LENGTH_SHORT).show();
                               }
                               catch (Exception e)
                               {
                                   e.printStackTrace();
                               }
                            }
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
                return true;
            case R.id.sort:
                Intent sort = new Intent();
                sort.setClass(this,AlreadyActivity.class);
                startActivity(sort);
                return true;
            case R.id.setting:
                Intent setting = new Intent();
                setting.setClass(this,SettingActivity.class);
                startActivity(setting);
                return true;
            case R.id.exit:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
	}
    
    //onRestart()重新开始事件
    @Override
    protected void onRestart()
    {
        super.onRestart();
        try
        {
        //获取project目录下的项目列表
        mContext = MainActivity.this;
        list_animal = (ListView) findViewById(R.id.applistview);
        list_animal.setEmptyView(findViewById(R.id.appnoproject));
        mData = new LinkedList<AppList>();
        List<String> lists = FileUtils.readFolders(new File("/storage/emulated/0/Application/Project"));
        for (String s : lists)
        {
            if (FileUtils.isProjectPackage(new File(s)))
            {
                String temp1 = "/AndroidManifest.xml";
                String temp2 = "/res/values/strings.xml";
                String temp3 = "/res/drawable/ic_launcher.png";
                String apppackage = FileUtils.readFileContent(new File(s + temp1));
                apppackage = FileUtils.getSubString(apppackage, "package=\"", "\"");
                String appname = FileUtils.readFileContent(new File(s + temp2));
                appname = FileUtils. getSubString(appname, "name=\"app_name\">", "</string>");
                temp3 = s+temp3;
                mData.add(new AppList(appname, apppackage, temp3, s));
            }
        }
        mAdapter = new AppAdapter((LinkedList<AppList>) mData, mContext);
        list_animal.setAdapter(mAdapter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /*
    //双击退出软件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
            {
            if (System.currentTimeMillis() - firstTime > 2000)
            {
                Toast.makeText(MainActivity.this, "再按一次退出软件",Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }
            else
            {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    */
    
}
