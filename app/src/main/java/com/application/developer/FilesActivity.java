package com.application.developer;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.content.Intent;
import java.util.List;
import java.util.LinkedList;
import java.io.File;
import android.widget.AdapterView.OnItemClickListener;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.io.IOException;
import java.util.Comparator;
import com.application.developer.activity.EditCodeActivity;
import android.content.DialogInterface;
import android.view.View.OnLongClickListener;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.ClipData;

public class FilesActivity extends Activity
{
    private File[] files;
    private ListView mListView;
    private File currentDirectory;
    Dialog dialog,dialogs,dialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("AppName"));
        String Path = intent.getStringExtra("Path");
        //获取项目下的文件目录，设置列表单击事件
        mListView = findViewById(R.id.fileslist);
        mListView.setEmptyView(findViewById(R.id.onfiles));
        initFileList(new File(Path+"/"));
        mListView.setOnItemClickListener(new OnItemClickListener()
        {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
                {
                    if (arg2 == 0)
                    {
                        Intent intent = getIntent();
                        if(currentDirectory.getAbsolutePath().equals(intent.getStringExtra("Path")))
                        {
                            finish();
                        }
                        else
                        {
                            initFileList(currentDirectory.getParentFile());
                        }
                    }
                    else
                    {
                        File f = files[arg2 - 1];
                        if (f.isDirectory())
                        {
                            initFileList(f);
                        }
                        else if(f.getName().endsWith(".java"))
                        {
                            Intent inte = getIntent();
                            Intent intent = new Intent(FilesActivity.this,EditCodeActivity.class);
                            intent.putExtra("Path",inte.getStringExtra("Path"));
                            intent.putExtra("FilesName",f.getName());
                            intent.putExtra("FilesPath",f.getAbsolutePath());
                            startActivity(intent);
                        }
                        else if(f.getName().endsWith(".soft"))
                        {
                            Intent inte = getIntent();
                            Intent intent = new Intent(FilesActivity.this,EditCodeActivity.class);
                            intent.putExtra("Path",inte.getStringExtra("Path"));
                            intent.putExtra("FilesName",f.getName());
                            intent.putExtra("FilesPath",f.getAbsolutePath());
                            startActivity(intent);
                        }
                        else if(f.getName().endsWith(".xml"))
                        {
                            Intent inte = getIntent();
                            Intent intent = new Intent(FilesActivity.this,EditCodeActivity.class);
                            intent.putExtra("Path",inte.getStringExtra("Path"));
                            intent.putExtra("FilesName",f.getName());
                            intent.putExtra("FilesPath",f.getAbsolutePath());
                            startActivity(intent);
                        }
                        else if(f.getName().endsWith(".gradle"))
                        {
                            Intent inte = getIntent();
                            Intent intent = new Intent(FilesActivity.this,EditCodeActivity.class);
                            intent.putExtra("Path",inte.getStringExtra("Path"));
                            intent.putExtra("FilesName",f.getName());
                            intent.putExtra("FilesPath",f.getAbsolutePath());
                            startActivity(intent);
                        }
                        else if(f.getName().endsWith(".html"))
                        {
                            Intent inte = getIntent();
                            Intent intent = new Intent(FilesActivity.this,EditCodeActivity.class);
                            intent.putExtra("Path",inte.getStringExtra("Path"));
                            intent.putExtra("FilesName",f.getName());
                            intent.putExtra("FilesPath",f.getAbsolutePath());
                            startActivity(intent);
                        }
                        else if(f.getName().endsWith(".php"))
                        {
                            Intent inte = getIntent();
                            Intent intent = new Intent(FilesActivity.this,EditCodeActivity.class);
                            intent.putExtra("Path",inte.getStringExtra("Path"));
                            intent.putExtra("FilesName",f.getName());
                            intent.putExtra("FilesPath",f.getAbsolutePath());
                            startActivity(intent);
                        }
                        else if(f.getName().endsWith(".css"))
                        {
                            Intent inte = getIntent();
                            Intent intent = new Intent(FilesActivity.this,EditCodeActivity.class);
                            intent.putExtra("Path",inte.getStringExtra("Path"));
                            intent.putExtra("FilesName",f.getName());
                            intent.putExtra("FilesPath",f.getAbsolutePath());
                            startActivity(intent);
                        }
                        else if(f.getName().endsWith(".js"))
                        {
                            Intent inte = getIntent();
                            Intent intent = new Intent(FilesActivity.this,EditCodeActivity.class);
                            intent.putExtra("Path",inte.getStringExtra("Path"));
                            intent.putExtra("FilesName",f.getName());
                            intent.putExtra("FilesPath",f.getAbsolutePath());
                            startActivity(intent);
                        }
                        else if(f.getName().endsWith(".sh"))
                        {
                            Intent inte = getIntent();
                            Intent intent = new Intent(FilesActivity.this,EditCodeActivity.class);
                            intent.putExtra("Path",inte.getStringExtra("Path"));
                            intent.putExtra("FilesName",f.getName());
                            intent.putExtra("FilesPath",f.getAbsolutePath());
                            startActivity(intent);
                        }
                        else if(f.getName().endsWith(".json"))
                        {
                            Intent inte = getIntent();
                            Intent intent = new Intent(FilesActivity.this,EditCodeActivity.class);
                            intent.putExtra("Path",inte.getStringExtra("Path"));
                            intent.putExtra("FilesName",f.getName());
                            intent.putExtra("FilesPath",f.getAbsolutePath());
                            startActivity(intent);
                        }
                        else if(f.getName().endsWith(".lua"))
                        {
                            Intent inte = getIntent();
                            Intent intent = new Intent(FilesActivity.this,EditCodeActivity.class);
                            intent.putExtra("Path",inte.getStringExtra("Path"));
                            intent.putExtra("FilesName",f.getName());
                            intent.putExtra("FilesPath",f.getAbsolutePath());
                            startActivity(intent);
                        }
                        else if(f.getName().endsWith(".txt"))
                        {
                            Intent inte = getIntent();
                            Intent intent = new Intent(FilesActivity.this,EditCodeActivity.class);
                            intent.putExtra("Path",inte.getStringExtra("Path"));
                            intent.putExtra("FilesName",f.getName());
                            intent.putExtra("FilesPath",f.getAbsolutePath());
                            startActivity(intent);
                        }
                        else if(f.getName().endsWith(".png"))
                        {
                            Intent intent = new Intent(FilesActivity.this,ImageActivity.class);
                            intent.putExtra("ImagePath",f.getAbsolutePath());
                            startActivity(intent);
                        }
                        else if(f.getName().endsWith(".jpg"))
                        {
                            Intent intent = new Intent(FilesActivity.this,ImageActivity.class);
                            intent.putExtra("ImagePath",f.getAbsolutePath());
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(FilesActivity.this,"无法打开该类型文件",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            
        //ListView长按事件
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
            {
                @Override
                public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4)
                {
                    File f = files[p3 - 1];
                    myPopupMenu(p2,f.getName());
                    return true;
                }
            });
            
    }

    private boolean checkEndsWithInStringArray(String checkItsEnd, String[] fileEndings)
    {
        for (String aEnd : fileEndings)
        {
            if (checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }

    private void initFileList(File f)
    {
        if (f.isDirectory())
        {
            if (f.canRead())
            {
                currentDirectory = f;
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Map<String, Object> map;
                map = new HashMap<String, Object>();
                map.put("text", "..");
                map.put("img", R.drawable.le_folder_bin);
                list.add(map);
                files = f.listFiles();
                //Arrays.sort(files);
                Arrays.sort(files, new Comparator<File>()
                {
                        @Override
                        public int compare(File p1, File p2)
                        {
                            if(p1.isDirectory() && p2.isFile())
                                return -1;
                            if(p1.isFile() && p2.isDirectory())
                                return 1;
                            return p1.getName().compareTo(p2.getName());
                        }
                    });
                for (File file : files)
                {
                    map = new HashMap<String, Object>();
                    map.put("text", file.getName());
                    if (file.isDirectory())
                    {
                        map.put("img", R.drawable.le_folder_bin);
                    }
                    else if (file.getName().endsWith(".java"))
                    {
                        map.put("img", R.drawable.ic_java);
                    } 
                    else if (file.getName().endsWith(".soft"))
                    {
                        map.put("img", R.drawable.op_run_script);
                    } 
                    else if (file.getName().endsWith(".xml"))
                    {
                        map.put("img", R.drawable.ic_xml);
                    } 
                    else if (file.getName().endsWith(".zip"))
                    {
                        map.put("img", R.drawable.ic_zip);
                    } 
                    else if (file.getName().endsWith(".jar"))
                    {
                        map.put("img", R.drawable.ic_zip);
                    } 
                    else if (file.getName().endsWith(".aar"))
                    {
                        map.put("img", R.drawable.ic_zip);
                    } 
                    else if (file.getName().endsWith(".png"))
                    {
                        map.put("img", R.drawable.picture);
                    } 
                    else if (file.getName().endsWith(".jpg"))
                    {
                        map.put("img", R.drawable.picture);
                    } 
                    else if (file.getName().endsWith(".txt"))
                    {
                        map.put("img", R.drawable.fs_text);
                    } 
                    else if (file.getName().endsWith(".html"))
                    {
                        map.put("img", R.drawable.ic_html);
                    } 
                    else if (file.getName().endsWith(".mp3"))
                    {
                        map.put("img", R.drawable.ic_audio);
                    } 
                    else if (file.getName().endsWith(".mp4"))
                    {
                        map.put("img", R.drawable.ic_video);
                    } 
                    else if (file.getName().endsWith(".apk"))
                    {
                        map.put("img", R.drawable.ic_launchers);
                    } 
                    else if (file.getName().endsWith(".sof"))
                    {
                        map.put("img", R.drawable.no_image);
                    } 
                    else if (checkEndsWithInStringArray(file.getName(),getResources().getStringArray(R.array.file)))
                    {
                        map.put("img", R.drawable.fs_xml);
                    }
                    else if (file.isFile())
                    {
                        map.put("img", R.drawable.ic_wenhao);
                    }
                    list.add(map);
                }
                SimpleAdapter sa = new SimpleAdapter(FilesActivity.this, list, R.layout.files_list, new String[] { "img", "text" }, new int[] { R.id.filesicon, R.id.filesname });
                mListView.setAdapter(sa);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            // 这里我暴力解法了....
            File f = currentDirectory.getParentFile();
            String file = null;
            // 这里我暴力解法了....
            try
            {
                file = f.getCanonicalPath();
            }
            catch (IOException e)
            {
                e.getStackTrace();
            }
            // 这里我暴力解法了....
            if (file.equals("/storage/emulated/0/Application/Project"))
            {
                finish();
            }
            // 这里我暴力解法了....
            if (f != null)
            {
                Intent intent = getIntent();
                if(currentDirectory.getAbsolutePath().equals(intent.getStringExtra("Path")))
                {
                    finish();
                }
                else
                {
                    initFileList(currentDirectory.getParentFile());
                }
            }
        }
        return true;
	}
    
    //ListView长按弹出PopupMenu菜单
    private void myPopupMenu(View v,final String name)
    {
        //定义PopupMenu对象
        PopupMenu popupMenu = new PopupMenu(FilesActivity.this, v);
        //设置PopupMenu对象的布局
        popupMenu.getMenuInflater().inflate(R.menu.menu_fileoperating, popupMenu.getMenu());
        //设置PopupMenu的点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
                @Override
                public boolean onMenuItemClick(MenuItem item)
                {
                    switch (item.getItemId())
                    {
                        case R.id.dormids:
                            //dialog弹窗
                            AlertDialog.Builder builds = new AlertDialog.Builder(FilesActivity.this);
                            builds.setTitle("重命名");
                            LayoutInflater lays = FilesActivity.this.getLayoutInflater();
                            View vs = lays.inflate(R.layout.new_files,null);
                            builds.setView(vs);
                            final TextView names = (TextView) vs.findViewById(R.id.newfilesname);
                            names.setText(name);
                            builds.setPositiveButton("确定", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface p1, int p2)
                                    {
                                        if(names.getText().toString().equals(""))
                                        {
                                            Toast.makeText(FilesActivity.this,"文件名不能为空",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            String filename = names.getText().toString();
                                            FileUtils.renameFile(currentDirectory.getAbsolutePath()+"/"+name+"/",currentDirectory.getAbsolutePath()+"/"+filename);
                                            initFileList(currentDirectory.getAbsoluteFile());
                                            Toast.makeText(FilesActivity.this,"重命名成功",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            builds.setNegativeButton("取消", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface p1, int p2)
                                    {
                                        dialog1.dismiss();
                                    }
                                });
                            dialog1 = builds.create();
                            dialog1.show();
                            return true;
                        case R.id.detect:
                            File scpath = new File(currentDirectory.getAbsolutePath()+"/"+name);
                            FileUtils.deleteFolder(scpath);
                            initFileList(currentDirectory.getAbsoluteFile());
                            Toast.makeText(FilesActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.copypath:
                            String copypath = currentDirectory.getAbsolutePath()+"/"+name;
                            copyData(copypath);
                            Toast.makeText(FilesActivity.this,"复制成功",Toast.LENGTH_SHORT).show();
                            return true;
                    }
                    return true;
                }
            });
        //显示菜单
        popupMenu.show();
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
        
    //创建Menu菜单 menu_main.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_files,menu);
        return true;
    }
    
    //设置Menu菜单单击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.newfiles:
                //dialog弹窗
                AlertDialog.Builder build = new AlertDialog.Builder(this);
                build.setTitle("新建文件");
                LayoutInflater lay = this.getLayoutInflater();
                View v = lay.inflate(R.layout.new_files,null);
                build.setView(v);
                final TextView name = (TextView) v.findViewById(R.id.newfilesname);
                build.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface p1, int p2)
                        {
                            if(name.getText().toString().equals(""))
                            {
                                Toast.makeText(FilesActivity.this,"文件名不能为空",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String filename = name.getText().toString();
                                FileUtils.createFile(currentDirectory.getAbsolutePath()+"/",filename);
                                initFileList(currentDirectory.getAbsoluteFile());
                                Toast.makeText(FilesActivity.this,"创建成功",Toast.LENGTH_SHORT).show();
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
            case R.id.newfolder:
                //dialog弹窗
                AlertDialog.Builder builds = new AlertDialog.Builder(this);
                builds.setTitle("新建文件夹");
                LayoutInflater lays = this.getLayoutInflater();
                View vs = lays.inflate(R.layout.new_files,null);
                builds.setView(vs);
                final TextView names = (TextView) vs.findViewById(R.id.newfilesname);
                builds.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface p1, int p2)
                        {
                            if(names.getText().toString().equals(""))
                            {
                                Toast.makeText(FilesActivity.this,"文件名不能为空",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String filename = names.getText().toString();
                                FileUtils.createFile(currentDirectory.getAbsolutePath()+"/"+filename+"/","");
                                initFileList(currentDirectory.getAbsoluteFile());
                                Toast.makeText(FilesActivity.this,"创建成功",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                builds.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface p1, int p2)
                        {
                            dialogs.dismiss();
                        }
                    });
                dialogs = builds.create();
                dialogs.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
     }
        
        /*
        //获取项目下的文件目录
        mContext = FilesActivity.this;
        list_animal = (ListView) findViewById(R.id.fileslist);
        list_animal.setEmptyView(findViewById(R.id.onfiles));
        fData = new LinkedList<FilesList>();
        List<String> lists = FileUtils.readFolders(new File(Path));
        for(String file : lists)
        {
        fData.add(new FilesList(R.drawable.file_folder,file));
        mAdapter = new FilesAdapter((LinkedList<FilesList>) fData, mContext);
        list_animal.setAdapter(mAdapter);
        }
        */
        
    //onRestart()重新开始事件
    @Override
    protected void onRestart()
    {
        super.onRestart();
        initFileList(currentDirectory.getAbsoluteFile());
    }

}
