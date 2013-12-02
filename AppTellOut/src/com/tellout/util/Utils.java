package com.tellout.util;

import android.content.Context;
import android.content.Intent;

import com.tellout.constant.MConstant;

public class Utils {
	
	public static void share(Context context ,String shareStr){
		Intent shareInt = new Intent(Intent.ACTION_SEND);
		shareInt.setType("text/plain");
		shareInt.putExtra(Intent.EXTRA_SUBJECT, "选择分享方式");
		shareInt.putExtra(Intent.EXTRA_TEXT, shareStr+MConstant.DOWNLOAD_URL);
		shareInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(shareInt);
	}
	
}
