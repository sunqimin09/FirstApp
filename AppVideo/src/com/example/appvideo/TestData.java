package com.example.appvideo;

import java.util.ArrayList;
import java.util.List;

public class TestData {
	
	private String[] names = {"空山鸟语1","空山鸟语2","空山鸟语3"};
	//http://js.tudouui.com/bin/lingtong/PortalPlayer_117.swf
	public String[] urls = {"http://www.tudou.com/albumplay/DYgjgFkKOAE/WnnmO1YVOtg.html",
			"http://www.tudou.com/albumplay/DYgjgFkKOAE/qJaxeUUvDvw.html",
			"http://www.tudou.com/albumplay/DYgjgFkKOAE/MI2Qo5fhmoA.html"};
	
	/**
	 * 123
	 */
	private String[] mp4 = {"http://112.25.61.184/youku/6567AD4D743A76F2DEC13B98/03102001005374C8C5E524050170578ED1FFCC-CC1A-5214-1664-A6623FBF3263.mp4"
						   ,"http://117.135.153.97/youku/6977EC60FD94A8285E56B12490/0310200100539B4A3FB826050170571EA8CC0C-94B5-60C7-F7F9-2F33438FF7E9.mp4",
						   "http://112.25.47.76/youku/656734870545784654443871/031020010053C63C33201D0501705794B0AABB-94C4-6E8C-1A4C-827C3A1AA1AE.mp4"
	};
	
	public List<InforEntity> getData(){
		List<InforEntity> list = new ArrayList<InforEntity>();
		InforEntity entity = null;
		for(int i = 0;i<3;i++){
			entity = new InforEntity();
			entity.id = i;
			entity.name = names[i];
			entity.url = mp4[i];
			list.add(entity);
		}
		return list;
	}
	
}
