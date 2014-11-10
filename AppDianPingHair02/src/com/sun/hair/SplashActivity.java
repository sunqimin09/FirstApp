package com.sun.hair;

import net.youmi.android.AdManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.sun.hair.utils.MConstant;
import com.sun.jumi.JMPManager;

/**
 * 本程序仅限用作示�? */
public class SplashActivity extends Activity {

	 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
            	LocationApplication.getJmInstance().q();
            }
        };
        final Handler handler = new Handler();
        final JMPManager.CallbackListener listener = new JMPManager.CallbackListener() {

            /**
             * 广告打开成功
             */
            @Override
            public void onOpen() {
                // TODO Auto-generated method stub
                // 3 秒后跳转页面
                handler.postDelayed(runnable, 10000);
            }

            /**
             * 广告打开失败
             */
            @Override
            public void onFailed() {
                // TODO Auto-generated method stub
                jump();
            }

            @Override
            public void onClose() {
                // TODO Auto-generated method stub
                jump();
            }

            private void jump() {
                handler.removeCallbacks(runnable);

                Intent intent = new Intent(getApplicationContext(), HomeAct.class);//TestAct
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        };

        LocationApplication.getJmInstance().s(SplashActivity.this, listener);
        initYoumi();
    }

    private void initYoumi(){
    	AdManager.getInstance(this).init(MConstant.YOUMI_KEY, MConstant.YOUMI_SECRET, false);
    }
}
