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
import com.nostra13.universalimageloader.core.ImageLoader;

public class TJAdpter extends BaseAdapter{
	private Context context;
	public ArrayList<MovieBean> beans;
	public TJAdpter(Context context, ArrayList<MovieBean> beans) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=convertView;
		GetView getView=null;
		if (view==null) {
			getView=new GetView();
			view=LayoutInflater.from(context).inflate(R.layout.gridview_item, null);
			getView.imageView=(ImageView) view.findViewById(R.id.tj_item_image);
			getView.nameView=(TextView) view.findViewById(R.id.tj_item_text);
			view.setTag(getView);
		}
		else {
			getView=(GetView) view.getTag();
		}
		ImageLoader.getInstance().displayImage(beans.get(position).getSimg(),getView.imageView);
		getView.nameView.setText(beans.get(position).getTitle());
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
