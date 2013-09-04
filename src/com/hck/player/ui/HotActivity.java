package com.hck.player.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;

import com.hck.myplayer.R;
import com.hck.player.adpter.HotAdpter;
import com.hck.player.bean.MovieBean;
import com.hck.player.date.Date;
import com.hck.player.date.LocalDate;
import com.hck.player.utils.AlertDialogUpdate;
import com.hck.player.utils.JsonUtil;
import com.hck.player.utils.SharedPreference;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wole56.sdk.Video;
/**
 * 
 * @author 老科
 * 展示热门视频用
 */
public class HotActivity extends Activity implements OnCheckedChangeListener , UpdatePointsNotifier{
	/**
	 * 热点 cid=2 女性 cid=11 娱乐 cid=1 母婴 cid=34 原创 cid=3 汽车 cid=28 动漫 cid=8 科教
	 * cid=10 音乐 cid=41 旅游 cid=27 体育 cid=14 搞笑 cid=4 游戏 cid=26
	 */
	private ListView listView; //展示数据的listview
	private HotAdpter adpter;  //绑定数据的适配器
	private String json;  //接收服务器端返回的json数据
	private ArrayList<MovieBean> beans;   //放置视频对象的集合
	private Map<Integer, HotAdpter> adpters;  //保存适配器的map集合，这个页面有多个频道。需要多个listview和adpter
	private static int post;  //记录当前点击的频道id
	private RadioGroup radioGroup;  //放置上面的各个频道按钮
	private LinearLayout layout;   //放置各个listview用
	private static String type; //类别
	private boolean isResh; //是否获取数据结束，防止listview滚动到最下面，重复获取数据
	private View pView; //转圈圈view
	private int[] location; //保存频道button的位置
	private int postion;
	private int page = 1;  
	private int width;
	private HorizontalScrollView scrollView; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("HotActivity", "onCreate");
		setContentView(R.layout.hot);
		adpters = new HashMap<Integer, HotAdpter>();
		beans = new ArrayList<MovieBean>();
		init();

	}
	public void getMoney() {
		AppConnect.getInstance(this).showOffers(this);
	}
	private void init() {
		scrollView = (HorizontalScrollView) findViewById(R.id.hscroll);
		location = new int[2];
		width = getWindowManager().getDefaultDisplay().getWidth();
		layout = (LinearLayout) findViewById(R.id.list_lin);
		radioGroup = (RadioGroup) findViewById(R.id.bar_rg);
		radioGroup.setOnCheckedChangeListener(this);
		pView = findViewById(R.id.pb);
		for (int i = 0; i < 13; i++) {
			listView = (ListView) getLayoutInflater().inflate(
					R.layout.listview_item, null);
			listView.setId(i + 1);
			layout.addView(listView);
			type = LocalDate.rd;
			if (i == 0) {
				listView.setVisibility(View.VISIBLE);
				post = 0;
				new Threads(LocalDate.rd, "1").start();
			} else {
				listView.setVisibility(View.GONE);
			}
			setListener(listView);
		}
	}

	private void setListener(ListView listView) {
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent();
				adpter = adpters.get(post);
				if (arg2>0) {
					arg2=arg2-1;
				}
				intent.putExtra("movie", adpter.beans.get(arg2));
				intent.setClass(HotActivity.this, ShowMovieInfoActivity.class);
				startActivity(intent);
			}
        	
		});
        listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {

			}
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2,
					int arg3) {

				if (arg1 + arg2 == arg3 && isResh && arg3 > 0) {
					page += 1;
					adpter = adpters.get(post);
					new Threads(type, page + "").start();
					isResh = false;
				}
			}
		});
	};

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			pView.setVisibility(View.GONE);
			if (beans.isEmpty()) {
				Toast.makeText(HotActivity.this, "获取数据失败", 4).show();
				return;
			}
			setDate(msg.what);
		};
	};

	private void setDate(int flag) {
		listView = (ListView) layout.getChildAt(flag);
		listView.setVisibility(View.VISIBLE);
		isResh = true;
		if (!adpters.containsKey(flag)) {
			adpter = new HotAdpter(this, beans);
			adpters.put(flag, adpter);
			View view = getLayoutInflater().inflate(R.layout.hot_title, null);
			ImageView imageView = (ImageView) view
					.findViewById(R.id.hot_title_img);
			TextView textView = (TextView) view.findViewById(R.id.hot_text);
			ImageLoader.getInstance().displayImage(
					adpter.beans.get(0).getBimg(), imageView);
			textView.setText(adpter.beans.get(0).getTitle());
			listView.addHeaderView(view);
			listView.setAdapter(adpter);
			View view2 = getLayoutInflater().inflate(R.layout.progress_bar,
					null);
			listView.addFooterView(view2);
		} else {
			adpters.get(flag).resh(beans);
		}
	}

	class Threads extends Thread {
		String id;
		String start;

		public Threads(String id, String start) {
			this.id = id;
			this.start = start;
		}

		@Override
		public void run() {
			Log.i("hck", " Threads run ");
			super.run();
			getDate(id, start);
			Message message = new Message();
			message.what = post;
			handler.sendMessage(message);
			start = null;
			id = null;
		}
	}

	private void getDate(String id, String start) {
		Log.i("hck", "HotActivity getDate start:" + start);
		beans = new ArrayList<MovieBean>();
		json = Video.getHotVideo(this, id, "15", start.trim()).toString();
		JsonUtil.getHot(json, beans);

	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {

		page = 1;
		switch (arg1) {
		case R.id.bar_rd1:
			setPostion(arg0.getChildAt(0));
			type = LocalDate.rd;
			post = 0;
			if (adpters.containsKey(post)) {
				show(0);
			} else {
				show(0);
				pView.setVisibility(View.VISIBLE);
				new Threads(LocalDate.rd, "1").start();
			}
			break;
		case R.id.bar_rd2:
			setPostion(arg0.getChildAt(1));
			post = 1;
			type = LocalDate.gx;
			if (adpters.containsKey(post)) {
				show(1);
			} else {
				show(1);
				pView.setVisibility(View.VISIBLE);
				new Threads(LocalDate.gx, "1").start();
			}
			break;
		case R.id.bar_rd3:
			setPostion(arg0.getChildAt(2));
			type = LocalDate.dm;
			post = 2;
			if (adpters.containsKey(post)) {
				show(2);
			} else {
				show(2);
				pView.setVisibility(View.VISIBLE);
				new Threads(LocalDate.dm, "1").start();
			}
			break;
		case R.id.bar_rd4:
			setPostion(arg0.getChildAt(3));
			type = LocalDate.yl;
			post = 3;
			if (adpters.containsKey(post)) {
				show(3);
			} else {
				beans = new ArrayList<MovieBean>();
				show(3);
				pView.setVisibility(View.VISIBLE);
				new Threads(LocalDate.yl, "1").start();
			}

			break;
		case R.id.bar_rd5:
			setPostion(arg0.getChildAt(4));
			type = LocalDate.yc;
			post = 4;
			if (adpters.containsKey(post)) {
				show(4);
			} else {
				beans = new ArrayList<MovieBean>();
				show(4);
				pView.setVisibility(View.VISIBLE);
				new Threads(LocalDate.yc, "1").start();
			}
			break;
		case R.id.bar_rd6:
			setPostion(arg0.getChildAt(5));
			type = LocalDate.ty;
			post = 5;
			if (adpters.containsKey(post)) {
				show(5);
			} else {
				beans = new ArrayList<MovieBean>();
				show(5);
				pView.setVisibility(View.VISIBLE);
				new Threads(LocalDate.ty, "1").start();
			}
			break;
		case R.id.bar_rd7:
			setPostion(arg0.getChildAt(6));
			type = LocalDate.yy;
			post = 6;
			if (adpters.containsKey(post)) {
				show(6);
			} else {
				beans = new ArrayList<MovieBean>();
				show(6);
				pView.setVisibility(View.VISIBLE);
				new Threads(LocalDate.yy, "1").start();
			}
			break;
		case R.id.bar_rd8:
			setPostion(arg0.getChildAt(7));
			type = LocalDate.nx;
			post = 7;
			if (adpters.containsKey(post)) {
				show(7);
			} else {
				beans = new ArrayList<MovieBean>();
				show(7);
				pView.setVisibility(View.VISIBLE);
				new Threads(LocalDate.nx, "1").start();
			}
			
			break;
		case R.id.bar_rd9:
			setPostion(arg0.getChildAt(8));
			type = LocalDate.my;
			post = 8;
			if (adpters.containsKey(post)) {
				show(8);
			} else {
				beans = new ArrayList<MovieBean>();
				show(8);
				pView.setVisibility(View.VISIBLE);
				new Threads(LocalDate.my, "1").start();
			}
			break;
		case R.id.bar_rd10:
			setPostion(arg0.getChildAt(9));
			type = LocalDate.ly;
			post = 9;
			if (adpters.containsKey(post)) {
				show(9);
			} else {
				beans = new ArrayList<MovieBean>();
				show(9);
				pView.setVisibility(View.VISIBLE);
				new Threads(LocalDate.ly, "1").start();
			}
			break;
		case R.id.bar_rd11:
			setPostion(arg0.getChildAt(10));
			type = LocalDate.qc;
			post = 10;
			if (adpters.containsKey(post)) {
				show(10);
			} else {
				beans = new ArrayList<MovieBean>();
				show(10);
				pView.setVisibility(View.VISIBLE);
				new Threads(LocalDate.qc, "1").start();
			}
			break;
		case R.id.bar_rd12:
			setPostion(arg0.getChildAt(11));
			type = LocalDate.yx;
			post = 11;
			if (adpters.containsKey(post)) {
				show(11);
			} else {
				beans = new ArrayList<MovieBean>();
				show(11);
				pView.setVisibility(View.VISIBLE);
				new Threads(LocalDate.yx, "1").start();
			}
			break;
		case R.id.bar_rd13:
			setPostion(arg0.getChildAt(12));
			type = LocalDate.kj;
			post = 12;
			if (adpters.containsKey(post)) {
				show(12);
			} else {
				beans = new ArrayList<MovieBean>();
				show(12);
				pView.setVisibility(View.VISIBLE);
				new Threads(LocalDate.kj, "1").start();
			}
			break;
		default:
			break;
		}
	}

	private void setPostion(View view) {
		view.getLocationInWindow(location);
		postion = location[0] - width / 2;
		Log.i("hck", "postion1: " + location[0]);
		Log.i("hck", "postion2: " + postion);
		if (postion != 0) {
			postion += 50;
			scrollView.smoothScrollBy(postion, 0);
		}
	}

	private void show(int id) {

		for (int i = 0; i < layout.getChildCount(); i++) {
			if (i == id) {
				layout.getChildAt(id).setVisibility(View.VISIBLE);
			} else {
				layout.getChildAt(i).setVisibility(View.GONE);
			}
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		AppConnect.getInstance(this).getPoints(this);
	}
	private void alert()
	{
		long time=SharedPreference.getTime();
			if (Date.money < 50 && System.currentTimeMillis()-time>=Date.timeOut) {
				new AlertDialogUpdate(this).alert("欢迎您使用蝌蚪视频，" +
						"获取蝌蚪币激活后 终身免费使用，蝌蚪币完全免费获取，点击激活，马上获取蝌蚪币 50蝌蚪币即可激活");
			} 
	}
	Handler handler2=new Handler()
	{
		public void handleMessage(Message msg) {
			alert();
		};
	};
	@Override
	public void getUpdatePoints(String arg0, int arg1) {
		Date.money=arg1;
		Log.i("hck", "getUpdatePoints: "+arg1);
		handler2.sendEmptyMessage(0);
	}
	@Override
	public void getUpdatePointsFailed(String arg0) {
		handler2.sendEmptyMessage(0);
	}
}
