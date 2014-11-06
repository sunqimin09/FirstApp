package com.sun.hair.utils;

import android.widget.EditText;

public class CheckInput {
	
	/**
	 * ������Ƿ�Ϊ��
	 * @param et
	 * @return
	 */
	public static boolean isNull(EditText et){
		if(et!=null&&et.getText()!=null&&et.getText().toString().trim().equals("")){
			return true;
		}
		return false;
	}
	
	
	
}
