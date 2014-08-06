
package cn.com.bjnews.thinker.wxapi;

import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity  implements IWXAPIEventHandler{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResp(BaseResp resp) {
		switch(resp.errCode){
		case BaseResp.ErrCode.ERR_OK:
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
			break;
		}
		super.onResp(resp);
	}
	
	
	
}
