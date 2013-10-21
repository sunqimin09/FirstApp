package com.example.util;

import org.apache.http.HttpStatus;

import com.example.apptellout.BaseActivity;

import android.os.Handler;
import android.os.Message;

public class ResultHandler extends Handler{

	private BaseActivity activity;
	
	public ResultHandler(BaseActivity activity){
		this.activity = activity;
	}
	
	@Override
	public void handleMessage(Message msg) {
		switch(msg.what){
		case HttpStatus.SC_OK://数据正确
			activity.showResult(msg.obj.toString());
			break;
		default:
				
		}
	}
	
	
	
}	
