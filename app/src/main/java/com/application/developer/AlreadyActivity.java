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

public class AlreadyActivity extends Activity
{

    private File[] files;
    private ListView mListView;
    private File currentDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already);
        //获取项目下的文件目录，设置列表单击事件
        try
        {
        mListView = findViewById(R.id.alreadylist);
        mListView.setEmptyView(findViewById(R.id.onalready));
        initFileList(new File("/storage/emulated/0/"));
        mListView.setOnItemClickListener(new OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
                {
                    if (arg2 == 0)
                    {
                        File f = currentDirectory.getParentFile();
                        if(currentDirectory.getAbsolutePath().equals("/storage/emulated/0"))
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
                        else if(f.getName().endsWith(".sof"))
                        {
                            try
                            {
                                ZipUtils.UnZipFolder(f.getAbsolutePath(),"/storage/emulated/0/Application/Project");
                            }
                            catch (Exception e)
                            {}
                            Toast.makeText(AlreadyActivity.this,"导入成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(AlreadyActivity.this,"请导入sof格式文件",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
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
                SimpleAdapter sa = new SimpleAdapter(AlreadyActivity.this, list, R.layout.files_list, new String[] { "img", "text" }, new int[] { R.id.filesicon, R.id.filesname });
                mListView.setAdapter(sa);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        try
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
            try
            {
            // 这里我暴力解法了....
            if (file.equals("/storage/emulated"))
            {
                finish();
            }
            // 这里我暴力解法了....
            if (f != null)
            {
                if(currentDirectory.getAbsolutePath().equals("/storage/emulated"))
                {
                    finish();
                }
                else
                {
                    initFileList(currentDirectory.getParentFile());
                }
            }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        }
        catch (Exception e)
        {
            finish();
            e.printStackTrace();
        }
        return true;
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
        try
        {
        initFileList(currentDirectory.getAbsoluteFile());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
