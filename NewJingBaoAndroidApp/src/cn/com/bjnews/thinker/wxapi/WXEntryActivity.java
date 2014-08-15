
package cn.com.bjnews.thinker.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("tag","share-WX===oncreate");
	}

	
	@Override
	public void onReq(BaseReq req) {
		Log.d("tag","share-WX===onReq"+req.openId);
	}


	@Override
	public void onResp(BaseResp arg0) {
		// TODO Auto-generated method stub
		Log.d("tag","share-WX===onResp"+arg0.errCode);
	}
	
	
	
}
