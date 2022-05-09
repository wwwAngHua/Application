package com.application.developer.activity;

import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.appcompat.app.*;
import androidx.appcompat.widget.*;
import com.application.developer.*;
import com.application.developer.editor.*;
import com.application.developer.view.*;
import androidx.appcompat.widget.Toolbar;
import android.app.Activity;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import android.util.Log;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.List;
import android.net.Uri;
import androidx.core.content.FileProvider;

public class EditCodeActivity extends AppCompatActivity
{
    private TextEditor content_edit;
    //内容编辑框
    private Toolbar toolbar;
    private SymbolView sv;

	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editcode);
        content_edit = findViewById(R.id.textEditor);
        toolbar = findViewById(R.id.fragment_editor_Toolbar);
        //初始化Toolbar
        initToolbar();
        //初始化编辑器
        initEditor();
	}

    private void initToolbar()
    {
        Intent intent = getIntent();
        toolbar.setTitle(intent.getStringExtra("FilesName"));
        setSupportActionBar(toolbar);
    }

    private void initEditor()
    {
        Intent intent = getIntent();
        String path = intent.getStringExtra("FilesPath");
        String text = ReadTxtFile(path);
        //把焦点放到editor
        content_edit.requestFocus();
        content_edit.setText(text);
        View rootView = getWindow().getDecorView();
        sv = new SymbolView(this, rootView);
        sv.setVisible(true);
        sv.setOnSymbolViewClick(new SymbolView.OnSymbolViewClick()
        {
				@Override
				public void onClick(View view, String text)
                {
					if (text.equals("→"))
                    {
						content_edit.paste("\t");
					}
                    else
                    {
						content_edit.paste(text);
					}
				}
			});
    }

    //菜单事件监听
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        //获取编辑框内容
        String content = content_edit.getText().toString();
        switch (item.getItemId())
        {
            case R.id.compilerun:
                Intent intent = getIntent();
                modifyFile(intent.getStringExtra("FilesPath"),content_edit.getText().toString(),false);
                //处理soft文件以及java文件
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
                                    deleteAllFiles(javapath);
                                    installApk(intent.getStringExtra("Path")+"/build/bin/app.apk");
                                    Toast.makeText(EditCodeActivity.this,"编译成功",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(EditCodeActivity.this,"apk签名失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(EditCodeActivity.this,"apk打包失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(EditCodeActivity.this,"dex编译失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(EditCodeActivity.this,"编译Java失败，请检查代码中是否含有错误！",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(EditCodeActivity.this,"apk资源编译失败",Toast.LENGTH_SHORT).show();
                }
                break;
				//撤销
            case R.id.undo:
                content_edit.undo();
                break;
				//重做
            case R.id.redo:
                content_edit.redo();
                break;
                //保存
            case R.id.savecode:
                Intent intents = getIntent();
                modifyFile(intents.getStringExtra("FilesPath"),content_edit.getText().toString(),false);
                Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                break;
				//复制代码片段
            case R.id.copycode:
                if (content.isEmpty())
                {
					Toast.makeText(this, "代码为空", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    copyData(content);
                    //复制代码
					Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //复制到剪切板
    private void copyData(String str)
    {
        //获取剪切板管理器
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        //创建普通字符型ClipData
        ClipData cd = ClipData.newPlainText("title", str);
        //将ClipData内容复制到剪切板
        cm.setPrimaryClip(cd);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
    {
		getMenuInflater().inflate(R.menu.menu_editcode_action, menu);
		return true;
	}

    //读取文本文件中的内容
    public static String ReadTxtFile(String strFilePath)
    {
        String path = strFilePath;
        String content = "";
        //文件内容字符串
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory())
        {
            Log.d("TestFile", "The File doesn't not exist.");
        }
        else
        {
            try
            {
                InputStream instream = new FileInputStream(file); 
                if (instream != null) 
                {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while (( line = buffreader.readLine()) != null)
                    {
                        content += line + "\n";
                    }                
                    instream.close();
                }
            }
            catch (java.io.FileNotFoundException e) 
            {
                Log.d("TestFile", "The File doesn't not exist.");
            } 
            catch (IOException e) 
            {
                Log.d("TestFile", e.getMessage());
            }
        }
        return content;
    }

    
    /**
     * 修改文件内容（覆盖或者添加）
     *
     * @param path    文件地址
     * @param content 覆盖内容
     * @param append  指定了写入的方式，是覆盖写还是追加写(true=追加)(false=覆盖)
     */
    public static void modifyFile(String path, String content, boolean append)
    {
        try
        {
            FileWriter fileWriter = new FileWriter(path, append);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.append(content);
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    //删除文件夹下面所有文件
    private static void deleteAllFiles(File root)
    {
        File files[] = root.listFiles();  
        if (files != null)  
            for (File f : files)
            {
                if (f.isDirectory())
                {
                    // 判断是否为文件夹  
                    deleteAllFiles(f);  
                    try
                    {
                        f.delete();  
                    }
                    catch (Exception e)
                    {
                    }  
                }
                else
                {
                    if (f.exists())
                    {
                        // 判断是否存在
                        deleteAllFiles(f);
                        try
                        {
                            f.delete();  
                        }
                        catch (Exception e)
                        {
                        }  
                    }  
                }  
            }  
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
