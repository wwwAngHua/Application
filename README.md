# Application

Application是我在2年前使用Java开发的一款开发安卓应用程序的App。至于为什么叫Application，是因为我想了好几天也没想出一个合适的名字，哈哈哈，所以就叫Application了，Application意为应用程序的意思。

Application中所使用的Soft语言其实本质是也是Java，只是对Java的代码进行了封装。开源这个应用程序的原因，主要因为我个人之前一直没有时间开发新版本，所以这个软件只开发了当前这一个版本；其次也是因为我有了更新的想法，在未来将开发一个全新的Application，使用H5（Vue3、Vant，JavaScript等）的开发方式来开发安卓软件，同时后期面向全平台，就像微信小程序、Uni-App一样，一套代码打包多端平台的应用程序（欢迎大家加群交流）；再接下来的原因当然就是开源精神了，因为圈子里很多朋友想让我开源，有很多的朋友想要研究如何编译安卓应用程序，所以借此，也就给大家开源了，希望对大家有所帮助！

开发者：王华
联系QQ：422584084
联系邮箱：wwwanghua@outlook.com
Application官方QQ交流群：737444923

大家觉得有用的话，麻烦点个Start收藏一下，感谢！！！

**一、编译原理**

```java
1.第一步，使用aapt编译资源文件
2.第二步，使用ecj编译java源代码
3.第三步，使用dx生成dex文件
4.第四步，使用sdklib打包生成未签名的apk
5.第五步，使用zipsigner签名apk
6.完成
```

二、示例

***1.aapt***

```java
public static boolean aapt(String res,String gen,String assets,String androidmanifest,String android_jar,String ap_)
{
    String[] args = 
    {
        //aapt文件路径
        "data/data/com.application.developer/files/aapt",
        //执行aapt编译资源
        "package","-v","-f","-m",
        //res文件夹路径
        "-S",res,
        //gen文件夹路径
        "-J",gen,
        //assets文件夹路径，如果没有会导致编译失败
        "-A",assets,
        //AndroidManifest.xml文件路径
        "-M",androidmanifest,
        //android.jar文件路径
        "-I",android_jar,
        //输出.ap_文件路径
        "-F",ap_
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
```

***2.ecj***

```java
public static boolean ecj(String libs,String android_jar,String java,String r_java,String classs,String mainactivity)
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
        "-extdirs",libs,
        //android.jar文件路径
        "-bootclasspath",android_jar,
        //java文件存放路径
        "-classpath",java+":"+
        //r.java文件存放路径
        r_java+":"+
        //第三方jar文件存放路径，如果没有使用第三方jar那就不用添加，它们之间用冒号隔开
        libs,
        "-1.6",
        "-target","1.6",
        "-proc:none",
        //class文件存放路径
        "-d",classs,
        //第一个被执行的java文件
        mainactivity
    };
    //执行编译并返回结果
    boolean b = main.compile(args);
    //如果失败请打印此信息
    //获得编译信息字符串
    String s1 = baos1.toString();
    //获得错误信息字符串
    String s2 = baos2.toString();
    Log.e("ecj",s2);
    return b;
}
```

***3.dx***

```java
public static boolean dex(String dex,String classs,String libs)
{
    String[] args =
    {
        "--verbose",
        //核心数
        "--num-threads="+Runtime.getRuntime().availableProcessors(),
        //classes.dex文件输出路径
        "--output="+dex,
        //class文件存放路径
        classs,
        //如果使用了第三方jar请添加存放路径
        libs
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
```

***4.sdklib***

```java
public static boolean sdklib(String ap_,String resources,String dex)
{
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try
    {
        ApkBuilder builder = new ApkBuilder(
            //未签名的apk输出路径
            new File(ap_),
            //aapt生成的文件路径
            new File(resources),
            //dx生成的文件
            new File(dex),
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
```

***5.zipsigner***

```java
public static boolean zipSigner(String key,String ap_,String apk)
{
    try
    {
        ZipSigner zipSigner = new ZipSigner();
        zipSigner.setKeymode(key);
        zipSigner.signZip(
            //未签名的apk文件
            ap_,
            //签名输出apk文件
            apk
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
```

