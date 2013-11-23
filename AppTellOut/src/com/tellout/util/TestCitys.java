package com.tellout.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author sunqm E-mail:sunqimin09@163.com
 * @version 创建时间：2013-11-15 下午1:12:08
 * @description 
 */
public class TestCitys {
	
	String value = "values('";
	
	String value1 = "',";
	
	public void readFile(String filePath){
		File file = new File(filePath);
		try {
			InputStream in = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int hasRead = 0;
			
			while((hasRead = in.read(buffer))>0){
				String temp = new String(buffer);
//				if()
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
