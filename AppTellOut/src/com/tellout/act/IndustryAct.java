package com.tellout.act;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

/**
 * 行业选择
 * 
 * @author sunqm Create at: 2013-11-13 下午11:14:36 TODO
 */
public class IndustryAct extends BaseActivity {

	private ListView listview;

	private EditText etInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.industry_act);
		initView();
	}

	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Log.d("tag","input->"+etInput.getText().toString());
			if(etInput.getText().toString().equals("")){
				showDialog();
				return false;
			}else{
				int selectedId = listview.getSelectedItemPosition()==-1?0:listview.getSelectedItemPosition();
				Intent data = new Intent(IndustryAct.this,EditMyInforAct.class);
				data.putExtra("industryId", selectedId);
				data.putExtra("industryName", industry[selectedId]);
				data.putExtra("detail", etInput.getText().toString());
				setResult(-101, data);
				finish();
			}
			
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showDialog(){
		Dialog alertDialog = new AlertDialog.Builder(this)
		.setIcon(R.drawable.ic_launcher)
		.setTitle("友情提示")
		.setMessage("职业信息不能为空")
		.setIcon(R.drawable.ic_launcher)
		.setPositiveButton("取消",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.dismiss();
					}
				})
		.create();

alertDialog.show();
	}

	/**
	 * 
	 */
	private void initView() {
		String detail = getIntent().getStringExtra("detail");
		int industryId = getIntent().getIntExtra("industryId", 0);
		listview = (ListView) findViewById(R.id.listView1);
		etInput = (EditText) findViewById(R.id.industry_input);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, industry);
		listview.setAdapter(adapter);
		listview.setItemsCanFocus(false);
		listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		etInput.setText(detail);
		listview.setItemChecked(industryId, true);
	}

	private String[] industry = { "计算机/互联网/通信", "生产/工艺/制造", "商业/服务业/个体经营",
			"金融/银行/投资/保险", "文化/广告/传媒", "娱乐/艺术/表演", "医疗/护理/制药", "律师/法务",
			"教育/培训", "公务员/事业单位", "学生", "其他" };
}
