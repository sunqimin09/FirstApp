package com.example.appvideo.webplay;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;

public class FileHelper {
	
	public String write(String fileName,String str){
		
		File file = new File(getPath(),fileName);
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			out.write(str.getBytes());
			out.flush();
			out.close();
			return getPath()+"/"+fileName;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	private String getPath(){
		if(Environment.isExternalStorageRemovable()){//不存在
			return Environment.getRootDirectory()+"/QinShiMingYue";
		}else{//存在
			return Environment.getExternalStorageDirectory()+"/QinShiMingYue";
		}
		
	}
	
}
