package com.application.developer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.io.RandomAccessFile;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.UnsupportedEncodingException;
import java.io.FileReader;

class FileUtils
{
    private static final String TAG = "FileUtils";
    /**
     * 创建文件
     *
     * @param filePath 文件地址
     * @param fileName 文件名
     * @return
     */
    public static boolean createFile(String filePath, String fileName)
    {
        String strFilePath = filePath + fileName;
        File file = new File(filePath);
        if (!file.exists())
        {
            /**  注意这里是 mkdirs()方法  可以创建多个文件夹 */
            file.mkdirs();
        }
        File subfile = new File(strFilePath);
        if (!subfile.exists())
        {
            try
            {
                boolean b = subfile.createNewFile();
                return b;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            return true;
        }
        return false;
    }

    /**
     * 遍历文件夹下的文件
     *
     * @param file 地址
     */
    public static List<File> getFile(File file)
    {
        List<File> list = new ArrayList<>();
        File[] fileArray = file.listFiles();
        if (fileArray == null)
        {
            return null;
        }
        else
        {
            for (File f : fileArray)
            {
                if (f.isFile())
                {
                    list.add(0, f);
                }
                else
                {
                    getFile(f);
                }
            }
        }
        return list;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件地址
     * @return
     */
    public static boolean deleteFiles(String filePath)
    {
        List<File> files = getFile(new File(filePath));
        if (files.size() != 0) {
            for (int i = 0; i < files.size(); i++)
            {
                File file = files.get(i);
                /**  如果是文件则删除  如果都删除可不必判断  */
                if (file.isFile())
                {
                    file.delete();
                }

            }
        }
        return true;
    }
    
    //flie：要删除的文件夹的所在位置
    public static void deleteFolder(File file)
    {
        if (file.isDirectory())
        {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++)
            {
                File f = files[i];
                deleteFolder(f);
            }
            file.delete();
            //如要保留文件夹，只删除文件，请注释这行
        }
        else if (file.exists())
        {
            file.delete();
        }
    }
    
    
    /**
     * 向文件中添加内容
     *
     * @param strcontent 内容
     * @param filePath   地址
     * @param fileName   文件名
     */
    public static void writeToFile(String strcontent, String filePath, String fileName)
    {
        //生成文件夹之后，再生成文件，不然会出错
        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        File subfile = new File(strFilePath);
        RandomAccessFile raf = null;
        try
        {
            /**   构造函数 第二个是读写方式    */
            raf = new RandomAccessFile(subfile, "rw");
            /**  将记录指针移动到该文件的最后  */
            raf.seek(subfile.length());
            /** 向文件末尾追加内容  */
            raf.write(strcontent.getBytes());
            raf.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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

    /**
     * 读取文件内容
     *
     * @param filePath 地址
     * @param filename 名称
     * @return 返回内容
     */
    public static String getString(String filePath, String filename)
    {
        FileInputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(new File(filePath + filename));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
        }
        catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
                sb.append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 重命名文件
     *
     * @param oldPath 原来的文件地址
     * @param newPath 新的文件地址
     */
    public static void renameFile(String oldPath, String newPath)
    {
        File oleFile = new File(oldPath);
        File newFile = new File(newPath);
        //执行重命名
        oleFile.renameTo(newFile);
    }


    /**
     * 复制文件
     *
     * @param fromFile 要复制的文件目录
     * @param toFile   要粘贴的文件目录
     * @return 是否复制成功
     */
    public static boolean copy(String fromFile, String toFile)
    {
        //要复制的文件目录
        File[] currentFiles;
        File root = new File(fromFile);
        //如同判断SD卡是否存在或者文件是否存在
        //如果不存在则 return出去
        if (!root.exists())
        {
            return false;
        }
        //如果存在则获取当前目录下的全部文件 填充数组
        currentFiles = root.listFiles();
        //目标目录
        File targetDir = new File(toFile);
        //创建目录
        if (!targetDir.exists())
        {
            targetDir.mkdirs();
        }
        //遍历要复制该目录下的全部文件
        for (int i = 0; i < currentFiles.length; i++)
        {
            if (currentFiles[i].isDirectory())//如果当前项为子目录 进行递归
            {
                copy(currentFiles[i].getPath() + "/", toFile + currentFiles[i].getName() + "/");

            } else//如果当前项为文件则进行文件拷贝
            {
                CopySdcardFile(currentFiles[i].getPath(), toFile + currentFiles[i].getName());
            }
        }
        return true;
    }


    //文件拷贝
    //要复制的目录下的所有非子目录(文件夹)文件拷贝
    public static boolean CopySdcardFile(String fromFile, String toFile)
    {
        try
        {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0)
            {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return true;

        }
        catch (Exception ex)
        {
            return false;
        }
    }
    
    //以下是加载App列表所用到的并非FileUitls封装的
    
    /**
     * 读取文件夹
     *
     * @param dir 要读取的文件夹路径
     */
    public static List<String> readFolders(File dir)
    {
        List<String> list = new ArrayList<>();
        File[] dirFiles = dir.listFiles();
        if (dirFiles != null)
        {
            for (File temp : dirFiles)
            {
                if (!temp.isFile())
                {
                    list.add(temp.getAbsolutePath());
                }
            }
        }
        return list;
    }
    
    /**
     * 判断项目 AndroidManifest.xml 是否存在
     *
     * @param dir 要读取的文件夹路径
     * @return 存在 true 不存在false
     */
    public static boolean isProjectPackage(File dir)
    {
        return new File(dir + "/AndroidManifest.xml").exists();
    }
    
    /**
     * 读取文件内容
     *
     * @param file 要读取的文件路径
     * @return StringBuilder 文件内容
     */
    public static String readFile(File file) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        while (br.ready())
        {
            sb.append(br.readLine());
        }
        return sb.toString();
    }

    /**
     * 读取文件内容
     *
     * @param file 要读取的文件路径
     * @return content 文件内容
     */
    public static String readFileContent(File file)
    {
        String content = null;
        try
        {
            content = readFile(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return content;
    }
    
    /**
    * String 截取信息
    *
    * @param text  要截取的内容
    * @param left  截取的左边
    * @param right 截取的右边
    * @return 截取内容
    */
    public static String getSubString(String text, String left, String right)
    {
        String result = "";
        int i;
        if (left == null || left.isEmpty())
        {
            i = 0;
        }
        else
        {
            i = text.indexOf(left);
            if (i > -1)
            {
                i += left.length();
            }
            else
            {
                i = 0;
            }
        }
        int indexOf = text.indexOf(right, i);
        if (indexOf < 0 || right.isEmpty())
        {
            indexOf = text.length();
        }
        result = text.substring(i, indexOf);
        return result;
    }
    
    //删除文件夹下面所有文件
    public static void deleteAllFiles(File root)
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
    

}
