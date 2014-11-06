package com.sun.hair;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.spot.SpotManager;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sun.hair.menu.SlidingMenu;
import com.sun.hair.menu.SlidingMenu.OnCloseListener;
import com.sun.hair.menu.SlidingMenu.OnClosedListener;
import com.sun.hair.menu.SlidingMenu.OnOpenListener;
import com.sun.hair.menu.SlidingMenu.OnOpenedListener;
import com.sun.jumi.JMPManager;

public class HomeAct extends FragmentActivity {

	private SlidingMenu menu;

	public SlidingMenu getMenu() {
		return menu;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.attach);
		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, new ContentFragment()).commit();

		// configure the SlidingMenu
		menu = new SlidingMenu(this);
//		menu.setMode(SlidingMenu.RIGHT);// ���һ�
		// menu.setMode(SlidingMenu.LEFT);//����

		 menu.setMode(SlidingMenu.LEFT_RIGHT);// �����һ�

		menu.setOnOpenedListener(new OnOpenedListener() {

			@Override
			public void onOpened() {
				Log.d("callback", "onOpened");
			}
		});
		menu.setOnOpenListener(new OnOpenListener() {

			@Override
			public void onOpen() {
				Log.d("callback", "onOpen");

			}
		});
		menu.setOnClosedListener(new OnClosedListener() {

			@Override
			public void onClosed() {
				Log.d("callback", "onClosed");

			}
		});
		menu.setOnCloseListener(new OnCloseListener() {

			@Override
			public void onClose() {
				Log.d("callback", "onClose");

			}
		});
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);

		menu.setMenu(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new LeftListFragment()).commit();

		// ���Ҷ����Ի���ʱҪ�˶�
		menu.setSecondaryMenu(R.layout.menu_frame_two);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame_two, new SampleListFragment())
				.commit();
		menu.setSecondaryShadowDrawable(R.drawable.shadowright);
		menu.setShadowDrawable(R.drawable.shadow);

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
//		Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			LocationApplication.getJmInstance().e(this,
//					new JMPManager.CallbackListener() {
//
//						@Override
//						public void onOpen() {
//							// TODO Auto-generated method stub
//
//						}
//
//						@Override
//						public void onFailed() {
//							
//							HomeAct.this.finish();
//						}
//
//						@Override
//						public void onClose() {
//							// TODO Auto-generated method stub
//
//						}
//					});
			showExitDialog();
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}
	
	private void showExitDialog(){
		final Dialog d = new Dialog(this);
		View layout = View.inflate(this, R.layout.dialog_exit, null);
		// ��ȡҪǶ�������Ĳ���
		LinearLayout adLayout = (LinearLayout) layout.findViewById(R.id.adLayout);
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		layout.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HomeAct.this.finish();
			}
		});
		layout.findViewById(R.id.not).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				d.dismiss();
			}
		});
		
		// ����������뵽������
		adLayout.addView(adView);
		d.setContentView(layout);
		d.setTitle("确定要退出吗?");
		
		d.show();
	}
	
	@Override
	protected void onDestroy() {
		SpotManager.getInstance(this).unregisterSceenReceiver();
		super.onDestroy();
	}
	

	@Override
	public void onBackPressed() {
		if (menu.isMenuShowing()) {
			menu.showContent();
		} else {
			super.onBackPressed();
		}
	}

	public void switchFragment(int id) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragments[id]).commit();
		onBackPressed();
	}

	private Fragment[] fragments = { new ContentFragment(),
			new AboutUsFragment() ,new FamousFragment(),new FragmentMyInfor()};
}
