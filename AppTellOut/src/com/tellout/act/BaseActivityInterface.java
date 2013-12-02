package com.tellout.act;

import com.tellout.entity.RequestEntity;
import com.tellout.entity.ResponseEntity;

public interface BaseActivityInterface {
	
	public void request(RequestEntity requestEntity);
	
	public void showResult(ResponseEntity result);
	
}
