package com.application.developer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetHttps
{
    public static String getHtml(String url)
    {
        String result = null;
        try
        {
            HttpParser parser = new HttpParser(url);
            Thread thread = new Thread(parser);
            thread.start();
            thread.join(4000);
            result = parser.getResult();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    static class HttpParser implements Runnable
    {
        private String url;
        private String result = null;
        public HttpParser(String url)
        {
            this.url = url;
        }
        @Override
        public void run()
        {
            try
            {
                result = getHtml(url);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public synchronized String getResult()
        {
            return result;
        }

        public String getHtml(String path) throws Exception
        {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            InputStream inStream = conn.getInputStream();
            byte[] data = readInputStream(inStream);
            return new String(data, StandardCharsets.UTF_8);
        }

        public byte[] readInputStream(InputStream inStream) throws Exception
        {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inStream.read(buffer)) != -1)
            {
                outStream.write(buffer, 0, len);
            }
            inStream.close();
            return outStream.toByteArray();
        }

    }

}
