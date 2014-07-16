package cn.com.bjnews.thinker.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.bjnews.thinker.R;
import cn.com.bjnews.thinker.entity.NewsEntity;
import cn.com.bjnews.thinker.img.ImageLoader;


/**
 * 第一个页面的适配器
 * 
 * @author sunqm Create at: 2014-5-12 下午4:15:21 TODO
 */
@SuppressLint("ResourceAsColor")
public class FragmentFirstAdapter extends BaseAdapter {

	private List<NewsEntity> list = new ArrayList<NewsEntity>();

	private LayoutInflater inflater;

	// private ImageDownLoader imageLoader;

	private ImageLoader mImageLoader;

	private RelativeLayout.LayoutParams params;

	private ColorStateList defaultcolor;

	private ColorStateList selectedcolor;

	private Context context;

	private boolean busy = false;

	public FragmentFirstAdapter(List<NewsEntity> entitys, Context context) {
		this.list = entitys;
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// imageLoader = new ImageDownLoader(context,
		// R.drawable.default_img_small);
		mImageLoader = new ImageLoader(context, R.drawable.default_img_small,
				true);
		mImageLoader.setIsBg(false);
		Bitmap b = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.default_img_small);
		params = new RelativeLayout.LayoutParams(b.getWidth(), b.getHeight());
		b.recycle();
		b = null;
		Resources resource = (Resources) context.getResources();
		defaultcolor = (ColorStateList) resource
				.getColorStateList(R.color.listview_default);
		selectedcolor = (ColorStateList) resource
				.getColorStateList(R.color.listview_selected);

	}

	public void setData(List<NewsEntity> entitys) {
		this.list = entitys;
		this.notifyDataSetChanged();
	}

	public void setFlagBusy(boolean flag) {
		busy = flag;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.fragment_first_lv_item,
					null);
			holder.tv_title = (TextView) v
					.findViewById(R.id.fragment_first_item_title);
			holder.tv_description = (TextView) v
					.findViewById(R.id.fragment_first_item_content);
			holder.image = (ImageView) v
					.findViewById(R.id.fragment_first_item_icon);

			holder.image.setLayoutParams(params);
			holder.image.setBackgroundResource(R.drawable.default_img_small);
			v.setTag(holder);
			Log.d("tag","adapter--init>"+position);
		} else {
			Log.d("tag","adapter--init22>"+position);
			holder = (ViewHolder) v.getTag();
			holder.image.setImageResource(R.drawable.default_img_small);
		}
		holder.tv_title.setText(list.get(position).title);
		holder.tv_description.setText(list.get(position).description);
//		Log.d("tag", position+"<adapter--title-->" + list.get(position).title+"bitmap-->"+holder.image.getBackground().toString());
		if (list.get(position).state == 1) {
			
			holder.tv_title.setTextColor(selectedcolor);
		} else {
			holder.tv_title.setTextColor(defaultcolor);
		}
//		Log.d("tag",
//				"state-->" + list.get(position).state + "id->"
//						+ list.get(position).id);
//		
//		Log.d("tag", holder.image.getBackground().toString()+"adapter--url-->" + position);
//		holder.image.setBackgroundResource(R.drawable.default_img_small);
//		if (!busy){
//			Log.d("tag","setBitmap--->"+position);
			mImageLoader.DisplayImage(list.get(position).thumbnail,
					holder.image, false);
//		}
			
		return v;
	}

	static class ViewHolder {
		TextView tv_title;
		TextView tv_description;
		ImageView image;

	}
}