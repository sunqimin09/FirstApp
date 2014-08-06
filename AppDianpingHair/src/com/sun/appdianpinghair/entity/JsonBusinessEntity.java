package com.sun.appdianpinghair.entity;

import java.util.ArrayList;
import java.util.List;

public class JsonBusinessEntity implements JsonBaseInterface{
	
	public String status;
	
	public int totalCount;
	
	public int count;
	
	public List<BusinessEntity> list = new ArrayList<BusinessEntity>();
	
}
