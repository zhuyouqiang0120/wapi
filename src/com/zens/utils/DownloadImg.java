package com.zens.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;



public class DownloadImg {
	public String Setimg(String titlepic) {
		//titlepic = titlepic.replace("www.whjd.sh.cn", "139.196.2.87");
System.out.println(titlepic);
		String picCacheDir = ResourceBundle.getBundle("url").getString("picCacheDir");
		String picCachePath = ResourceBundle.getBundle("url").getString("picCachePath");
		//String path = this.getClass().getClassLoader().getResource("/").getPath().concat("media/imgcache/CultureCloud_JD/");
		String imgName = titlepic.substring(titlepic.lastIndexOf("/") + 1);
		//String savepath = path .replace("/WEB-INF/classes/", "/").substring(1)+imgName;
// System.out.println(path);
 //System.out.println(savepath);
		File file = new File(picCacheDir + imgName);
		//File file = new File(savepath);
		if(!file.exists()){
			System.out.println("下载图片！");
			OutputStream os = null;
			String str = titlepic;
			InputStream is = null;
			HttpURLConnection connection = null;
			URL userver = null;

			try {
				userver = new URL(str);
				connection = (HttpURLConnection) userver.openConnection();
				connection.connect();
				is = connection.getInputStream();
				os = new FileOutputStream(file);
				int b = is.read();
				while (b != -1) {
					os.write(b);
					b = is.read();
				}
				is.close();
				os.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		
	}
		String imgurl = picCachePath + imgName ;
		return imgurl;
	}

}
