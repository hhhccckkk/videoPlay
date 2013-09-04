package com.hck.player.adpter;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hck.myplayer.R;
import com.hck.player.bean.MovieBean;
import com.hck.player.utils.MyTools;
import com.nostra13.universalimageloader.core.ImageLoader;
public class HotAdpter extends BaseAdapter {
	private Context context;
	public ArrayList<MovieBean> beans;
	public HotAdpter(Context context, ArrayList<MovieBean> beans) {
		this.context = context;
		this.beans = beans;
	}

	@Override
	public int getCount() {
		return beans.size();
	}

	@Override
	public Object getItem(int arg0) {
		return beans.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = arg1;
		GetView getView;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.home_list_item, null);
			getView = new GetView();
			getView.imageView = (ImageView) view.findViewById(R.id.hot_mv_img);
			getView.nameView = (TextView) view
					.findViewById(R.id.hot_mv_name_id);
			getView.timView = (TextView) view.findViewById(R.id.hot_tag);
			getView.textView=(TextView) view.findViewById(R.id.hot_time);
			view.setTag(getView);
		} else {
			getView = (GetView) view.getTag();
		}
		getView.nameView.setText(beans.get(arg0).getTitle());
		getView.timView.setText(beans.get(arg0).getTag());
		if (beans.get(arg0).getAllTime()!=null) {
			getView.textView.setText(MyTools.changeTime2(Long.parseLong(beans.get(arg0).getAllTime())));
		}
		ImageLoader.getInstance().displayImage(beans.get(arg0).getSimg(),
				getView.imageView);
		return view;
	}

	static class GetView {
		ImageView imageView;
		TextView nameView;
		TextView timView;
		TextView textView;
	}
	public void resh(ArrayList<MovieBean> beans)
	{
		this.beans.addAll(beans);
		this.notifyDataSetChanged();
	}

}
