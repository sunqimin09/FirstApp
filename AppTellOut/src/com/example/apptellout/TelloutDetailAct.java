package com.example.apptellout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adapter.CommentAdapter;
import com.example.entity.BaseEntity;
import com.example.entity.CommentEntity;
import com.example.entity.RequestEntity;
import com.example.entity.TellOutEntity;
import com.sun.constant.DbConstant;
import com.sun.constant.MConstant;

/**
 * 吐槽详细
 * @author sunqm
 *
 */
public class TelloutDetailAct extends BaseActivity{

	private ListView listView = null;
	/**当前吐槽的id*/
	private int TellOutId = 0;
	
	private int pageIndext =1;
	
	private CommentAdapter adapter = null;
	
	private List<CommentEntity> list = new ArrayList<CommentEntity>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tellout_detail_act);
		initView();
	}

	private void initView() {
		TellOutEntity entity = (TellOutEntity) getIntent().getSerializableExtra("tellout");
		listView = (ListView) findViewById(R.id.tellout_detail_listview);
		View headView = View.inflate(this, R.layout.tellout_item, null);
		TextView tvAuthor = (TextView) headView.findViewById(R.id.tellout_item_author_tv);
		TextView tvContent = (TextView) headView.findViewById(R.id.tellout_item_content_tv);
		TextView tvOk = (TextView) headView.findViewById(R.id.tellout_item_ok_tv);
		TextView tvNo = (TextView) headView.findViewById(R.id.tellout_item_no_tv);
		TextView tvComment = (TextView) headView.findViewById(R.id.tellout_item_comment_tv);
		listView.addHeaderView(headView);
		adapter = new CommentAdapter(this, list);
		TellOutId = entity.getTellOutId();
		tvAuthor.setText(entity.getAuthorName());
		tvContent.setText(entity.getContent());
		tvOk.setText(entity.getOkNum()+"");
		tvNo.setText(entity.getNoNum()+"");
		tvComment.setText(entity.getCommentNum()+"");
	}
	
	
	
	public void request(){
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setPost(false);
		requestEntity.setRequestType(MConstant.REQUEST_CODE_COMMENTS);
		Map<String,String> map = new HashMap<String,String>();
		map.put(DbConstant.DB_COMMENT_TELLOUT_ID,""+TellOutId);
		//当前页码
		map.put(MConstant.OTHER_PAGE_INDEXT, ""+pageIndext);
		requestEntity.setParams(map);
		request(requestEntity);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void showResult(int type, BaseEntity baseEntity) {
		super.showResult(type, baseEntity);
		list = (List<CommentEntity>) baseEntity.getList();
		adapter.addData(list);
	}
	
	
	
}
