package com.application.developer;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.Layout;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import java.util.List;
import java.io.IOException;
import android.app.Dialog;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.content.DialogInterface;
import android.content.Context;
import android.text.TextUtils;
import android.os.Build;
import android.net.Uri;
import androidx.core.content.FileProvider;

public class DatailsActivity extends Activity
{
    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datails);
        Intent intent = getIntent();
        ImageView icon = (ImageView) findViewById(R.id.datailsicon);
        TextView name = (TextView) findViewById(R.id.datailsname);
        TextView packages = (TextView) findViewById(R.id.datailspackage);
        TextView path = (TextView) findViewById(R.id.datailspath);
        Bitmap bitmap = BitmapFactory.decodeFile(intent.getStringExtra("Icon"));
        icon.setImageBitmap(bitmap);
        name.setText("项目名："+intent.getStringExtra("Name"));
        packages.setText("包名："+intent.getStringExtra("Package"));
        path.setText("项目路径："+intent.getStringExtra("Path"));
        LinearLayout compile = (LinearLayout) findViewById(R.id.compileandpackage);
        LinearLayout installation = (LinearLayout) findViewById(R.id.installation);
        LinearLayout backup = (LinearLayout) findViewById(R.id.backup);
        LinearLayout delete = (LinearLayout) findViewById(R.id.delete);
        compile.setOnClickListener(new OnClickListener()
        {
                @Override
                public void onClick(View p1)
                {
                    //处理soft文件以及java文件
                    Intent intent = getIntent();
                    List<String> list = CompileUtils.getFiles(intent.getStringExtra("Path")+"/src/");
                    for (String i : list)
                    {
                        if (i.contains(".java"))
                        {
                            String j = i.replace("src", "build/java");
                            try
                            {
                                CompileUtils.copyFile(new File(i), new File(j));
                            }
                            catch (IOException e)
                            {}
                        }
                        else if (i.contains(".soft"))
                        {
                            String j = i.replace("src", "build/java").replace(".soft", ".java");
                            try
                            {
                                CompileUtils.writeFile(getSoft(i), j);
                            }
                            catch (Exception e)
                            {}
                        }
                    }
                    if(CompileUtils.aapt(intent.getStringExtra("Path")+"/res/",intent.getStringExtra("Path")+"/build/gen/",intent.getStringExtra("Path")+"/assets/",intent.getStringExtra("Path")+"/AndroidManifest.xml","/data/data/com.application.developer/files/android.jar",intent.getStringExtra("Path")+"/build/bin/resources.ap_"))
                    {
                    //使用aapt编译资源
                    //res文件夹路径，gen文件夹路径，assets文件夹路径，AndroidManifest.xml文件路径，android.jar文件路径，ap_输出路径
                    if(CompileUtils.ecj(intent.getStringExtra("Path")+"/libs/","/data/data/com.application.developer/files/android.jar",intent.getStringExtra("Path")+"/build/java/",intent.getStringExtra("Path")+"/build/gen/",intent.getStringExtra("Path")+"/build/bin/classes/",intent.getStringExtra("Path")+"/build/java/Main.java"))
                    {
                    //使用ecj编译java
                    //libs文件夹路径，android.jar文件路径，java文件存放路径，r.java存放路径，class文件存放路径，第一个被执行的java文件
                    if(CompileUtils.dex(intent.getStringExtra("Path")+"/build/bin/classes.dex",intent.getStringExtra("Path")+"/build/bin/classes/",intent.getStringExtra("Path")+"/libs/"))
                    {
                    //使用dx生成dex文件
                    //classes.dex文件输出路径，class文件存放路径，libs文件夹路径
                    if(CompileUtils.sdklib(intent.getStringExtra("Path")+"/build/bin/app.ap_",intent.getStringExtra("Path")+"/build/bin/resources.ap_",intent.getStringExtra("Path")+"/build/bin/classes.dex"))
                    {
                    //使用sdklib打包生成未签名的apk
                    //未签名的apk文件输出路径，aapt生成的文件输出路径，dx生成的文件路径
                    if(CompileUtils.zipSigner("testkey",intent.getStringExtra("Path")+"/build/bin/app.ap_",intent.getStringExtra("Path")+"/build/bin/app.apk"))
                    {
                    //使用zipSigner签名apk
                    //key文件，未签名的apk文件路径，签名后的apk文件输出路径
                    //installApk(DatailsActivity.this,intent.getStringExtra("Path")+"/build/bin/app.apk");
                        File javapath = new File(intent.getStringExtra("Path")+"/build/java/");
                        FileUtils.deleteAllFiles(javapath);
                        installApk(intent.getStringExtra("Path")+"/build/bin/app.apk");
                        Toast.makeText(DatailsActivity.this,"编译成功",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(DatailsActivity.this,"apk签名失败",Toast.LENGTH_SHORT).show();
                    }
                    }
                    else
                    {
                        Toast.makeText(DatailsActivity.this,"apk打包失败",Toast.LENGTH_SHORT).show();
                    }
                    }
                    else
                    {
                        Toast.makeText(DatailsActivity.this,"dex编译失败",Toast.LENGTH_SHORT).show();
                    }
                    }
                    else
                    {
                        Toast.makeText(DatailsActivity.this,"编译Java失败，请检查代码中是否含有错误！",Toast.LENGTH_SHORT).show();
                    }
                    }
                    else
                    {
                        Toast.makeText(DatailsActivity.this,"apk资源编译失败",Toast.LENGTH_SHORT).show();
                    }
                }
                private LayoutInflater getLayoutInflater()
                {
                    return null;
                }
            });
        installation.setOnClickListener(new OnClickListener()
        {
                @Override
                public void onClick(View p1)
                {
                    Intent intent = getIntent();
                    File app = new File(intent.getStringExtra("Path")+"/build/bin/app.apk");
                    if (app.exists())
                    {
                        installApk(intent.getStringExtra("Path")+"/build/bin/app.apk");
                    }
                    else
                    {
                        Toast.makeText(DatailsActivity.this,"请先编译打包",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        backup.setOnClickListener(new OnClickListener()
        {
                @Override
                public void onClick(View p1)
                {
                    Intent intent = getIntent();
                    try
                    {
                        ZipUtils.ZipFolder(intent.getStringExtra("Path"),"/storage/emulated/0/Application/"+intent.getStringExtra("Name")+".sof");
                    }
                    catch (Exception e)
                    {}
                    Toast.makeText(DatailsActivity.this, "项目已备份至：/storage/emulated/0/Application/"+intent.getStringExtra("Name")+".sof", Toast.LENGTH_SHORT).show();
                }
            });
        delete.setOnClickListener(new OnClickListener()
        {
                @Override
                public void onClick(View p1)
                {
                    AlertDialog.Builder build = new AlertDialog.Builder(DatailsActivity.this);
                    build.setTitle("警告");
                    build.setMessage("确定要删除该项目吗？删除后无法找回！");
                    build.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                            @Override
                            public void onClick(DialogInterface p1, int p2)
                            {
                                Intent intent = getIntent();
                                File files = new File(intent.getStringExtra("Path"));
                                FileUtils.deleteFolder(files);
                                finish();
                                Toast.makeText(DatailsActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
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
            });
    }
    
    private static String getSoft(String path)
    {
        String data = CompileUtils.readFile(path);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CompileUtils.getPackage(data));
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        stringBuilder.append(CompileUtils.getImport(data));
        stringBuilder.append("\n");
        stringBuilder.append("public class ");
        stringBuilder.append(CompileUtils.getClass(data));
        stringBuilder.append(" extends Activity");
        stringBuilder.append("\n");
        stringBuilder.append("{");
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("@Override");
        stringBuilder.append("\n");
        stringBuilder.append("protected void onCreate(Bundle savedInstanceState)");
        stringBuilder.append("\n");
        stringBuilder.append("{");
        stringBuilder.append("\n");
        stringBuilder.append("super.onCreate(savedInstanceState);");
        stringBuilder.append("\n");
        stringBuilder.append(CompileUtils.getOnCreate(data));
        stringBuilder.append("\n");
        stringBuilder.append("}");
        stringBuilder.append("\n");
        stringBuilder.append(CompileUtils.getOther(data));
        stringBuilder.append("\n");
        stringBuilder.append("}");
        stringBuilder.append("\n");
        System.out.println(CompileUtils.getOther(data));
        return stringBuilder.toString();
	}
    
    public void installApk(String apkPath)
    {
        File file = new File(apkPath);
        String type = "application/vnd.android.package-archive";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri apkUri = FileProvider.getUriForFile(getApplicationContext(),"com.application.developer.fileprovider",file);
            intent.setDataAndType(apkUri,type);
        }
        else
        {
            intent.setDataAndType(Uri.fromFile(file), type);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
    }
    
    
}
