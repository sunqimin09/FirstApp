package com.example.appcolleageentrance;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DataReceiver extends BroadcastReceiver{
	


@Override
public void onReceive(Context arg0, Intent arg1) {
	Log.d("tag","OnReceive-->");
	Toast.makeText(arg0, "OnReceive---", 10000).show();
}
	
	
}
