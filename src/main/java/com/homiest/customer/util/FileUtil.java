package com.homiest.customer.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class FileUtil {
	public static String readProperties(String propertiesFile, String propkey) {

		Properties prop = new Properties();
		try {
			propertiesFile = System.getProperty("user.dir") + "/" + propertiesFile;
			// 读取属性文件a.properties
			InputStream in = new BufferedInputStream(new FileInputStream(
					propertiesFile));
			prop.load(in); // /加载属性列表
			Iterator<String> it = prop.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				if (key.equals(propkey)) {
					return prop.getProperty(key);
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static  Properties loadProperties(String propertiesFile) {
		Properties prop = new Properties();
		try {
			propertiesFile = System.getProperty("user.dir") + "/" + propertiesFile;
			// 读取属性文件a.properties
			InputStream in = new BufferedInputStream(new FileInputStream(
					propertiesFile));
			prop.load(in); // /加载属性列表
			in.close();
			return prop;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public static String readMailContent(String path) {
		String result = "";
		byte[] buffer = null;
		FileInputStream file = null;
		BufferedInputStream fis = null;
		try {
			file = new FileInputStream(path);
			fis = new BufferedInputStream(file);
			buffer = new byte[fis.available()];
			fis.read(buffer);

			result = new String(buffer);
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fis.close();
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
}