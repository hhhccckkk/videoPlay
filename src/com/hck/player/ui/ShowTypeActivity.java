package com.hck.player.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.waps.AppConnect;
import cn.waps.MiniAdView;

import com.hck.myplayer.R;
import com.hck.player.adpter.TypeAdpter;
import com.hck.player.bean.MovieBean;
import com.hck.player.utils.JsonUtil;
import com.wole56.sdk.Video;

public class ShowTypeActivity extends Activity {
	private TypeAdpter adpter;
	private ArrayList<MovieBean> beans;
	private ListView listView;
	private String id;
	private View pb;
	private int page;
	private boolean isRefsh;
	private View pView;
    private TextView nametTextView;
    private String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_one_type);
		nametTextView=(TextView) findViewById(R.id.show_type_id);
		pView = findViewById(R.id.pb);
		page = 0;
		listView = (ListView) findViewById(R.id.show_type_list);
		try {
			id = getIntent().getStringExtra("id").toString().toString();
			name=getIntent().getStringExtra("name");
			nametTextView.setText(name);
		} catch (Exception e) {
		}

		getDate("1");
		setlistener();
		showAds();
	}

	private void showAds() {
		AppConnect.getInstance(this).setAdBackColor(
				Color.WHITE);
		// 设置迷你⼲⼴广告⼲⼴广告语颜⾊色
		AppConnect.getInstance(this).setAdForeColor(Color.MAGENTA);
		// 若未设置以上两个颜⾊色，则默认为⿊黑底⽩白字
		LinearLayout miniLayout = (LinearLayout) findViewById(R.id.miniAdLinearLayout);
		new MiniAdView(this, miniLayout).DisplayAd(10); // 默认10秒切换⼀�?��⼲⼴广告
	}

	private void getDate(final String page) {

		new Thread() {
			public void run() {
				getDates(page);
				handler.sendEmptyMessage(1);
			};
		}.start();
	}

	private void getDates(String start) {
		beans = new ArrayList<MovieBean>();
		String string = Video.getChannelVideo(this, id, "15", start.trim())
				.toString();
		JsonUtil.getType(string, beans);

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			pView.setVisibility(View.GONE);
			setDate();
		};
	};

	private void setDate() {
		isRefsh = true;
		View view2 = getLayoutInflater().inflate(R.layout.progress_bar, null);
		listView.addFooterView(view2);
		if (adpter == null) {
			adpter = new TypeAdpter(this, beans);
			listView.setAdapter(adpter);
		} else {
			adpter.resh(beans);
		}

	}

	private void setlistener() {
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem + visibleItemCount == totalItemCount
						&& isRefsh && totalItemCount > 0) {
					page++;
					getDate(page + "");
					isRefsh = false;
				}

			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 >= adpter.beans.size()) {
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("movie", adpter.beans.get(arg2));
				intent.setClass(ShowTypeActivity.this,
						ShowMovieInfoActivity.class);
				startActivity(intent);

			}

		});
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppConnect.getInstance(this).close();
	}
}
