/**
 * 
 */
package com.example.msalary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msalary.R;
import com.example.msalary.entity.ResponseResult;
import com.example.msalary.internet.IRequestCallBack;

/**
 * 项目名称：营销移动智能工作平台 <br>
 * 文件名：TerminalDetailsFragment.java <br>
 * 作者：@沈潇    <br>
 * 创建时间：2013/11/24 <br>
 * 功能描述: 终端详情 <br>
 * 版本 V 1.0 <br>               
 * 修改履历 <br>
 * 日期      原因  BUG号    修改人 修改版本 <br>
 */
public class BaseActivity extends Activity implements IRequestCallBack{

	protected TextView tv_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	}

	/**
	 * 初始化控件
	 */
	protected void initView(){
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		tv_title = (TextView) findViewById(R.id.title_tv);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            share();
        }
        return true;
    }

	
	/**分享*/
	private void share(){
		Intent intent=new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");//intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "好友推荐");
		intent.putExtra(Intent.EXTRA_TEXT, "嗨，我正在使用查工资，你也来查查吧！");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(intent, getTitle()));

	}
	
	
	/* (non-Javadoc)
	 * @see com.example.msalary.internet.IRequestCallBack#requestSuccess(com.example.msalary.entity.ResponseResult)
	 */
	@Override
	public void requestSuccess(ResponseResult responseResult) {
		
	}

	/* (non-Javadoc)
	 * @see com.example.msalary.internet.IRequestCallBack#requestFailedStr(java.lang.String)
	 */
	@Override
	public void requestFailedStr(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	
	public void Toast(String str){
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	
}
