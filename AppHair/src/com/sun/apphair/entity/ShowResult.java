package com.sun.apphair.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.os.ParcelableCompat;

public class ShowResult implements Cloneable{

	 public int requestCode;
     /**ֵ*/
     public int resultCode;

     public List<? extends ShowResult> list = new ArrayList<ShowResult>();
     /***/
     public Map<String,Object> map = new HashMap<String,Object>();
}
