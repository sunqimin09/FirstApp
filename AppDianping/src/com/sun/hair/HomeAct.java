package com.sun.hair;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.sun.hair.R;
import com.sun.hair.menu.SlidingMenu;
import com.sun.hair.menu.SlidingMenu.OnCloseListener;
import com.sun.hair.menu.SlidingMenu.OnClosedListener;
import com.sun.hair.menu.SlidingMenu.OnOpenListener;
import com.sun.hair.menu.SlidingMenu.OnOpenedListener;

public class HomeAct extends FragmentActivity{

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
		// menu.setMode(SlidingMenu.RIGHT);//ÍùÓÒ»¬
		// menu.setMode(SlidingMenu.LEFT);//Íù×ó»¬

		menu.setMode(SlidingMenu.LEFT_RIGHT);// Íù×óÓÒ»¬

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
				.replace(R.id.menu_frame, new SampleListFragment()).commit();

		// ×óÓÒ¶¼¿ÉÒÔ»¬¶¯Ê±Òª´Ë¶Î
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
	public void onBackPressed() {
		if (menu.isMenuShowing()) {
			menu.showContent();
		} else {
			super.onBackPressed();
		}
	}
}
