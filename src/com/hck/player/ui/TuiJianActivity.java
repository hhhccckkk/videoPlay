package com.hck.player.ui;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;
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
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.hck.myplayer.R;
import com.hck.player.adpter.TJAdpter;
import com.hck.player.bean.MovieBean;
import com.hck.player.date.LocalDate;
import com.hck.player.utils.JsonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wole56.sdk.Video;
public class TuiJianActivity extends Activity implements android.widget.RadioGroup.OnCheckedChangeListener{
		private GridView gView;
		private TJAdpter adpter;
		private String json;
		private ArrayList<MovieBean> beans;
		private Map<Integer, TJAdpter> adpters;
		private static int post;
		private RadioGroup radioGroup;
		private LinearLayout layout;
		private static String type;
		private boolean isResh;
		private View pView;
		private int[] location;
		private int postion;
		private int page = 1;
		private int width;
		private HorizontalScrollView scrollView;
		@SuppressLint("UseSparseArrays")
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Log.i("HotActivity", "onCreate");
			setContentView(R.layout.tj);
			beans = new ArrayList<MovieBean>();
			adpters=new HashMap<Integer, TJAdpter>();
			init();
		}
		private void init() {
			scrollView = (HorizontalScrollView) findViewById(R.id.hscroll);
			location = new int[2];
			width = getWindowManager().getDefaultDisplay().getWidth();
			layout = (LinearLayout) findViewById(R.id.grid_lin);
			radioGroup = (RadioGroup) findViewById(R.id.bar_rg);
			radioGroup.setOnCheckedChangeListener(this);
			pView = findViewById(R.id.pb);
			for (int i = 0; i < 12; i++) {
				gView = (GridView) getLayoutInflater().inflate(
						R.layout.gridview, null);
				gView.setId(i + 1);
				layout.addView(gView);
				type = LocalDate.rd;
				if (i == 0) {
					gView.setVisibility(View.VISIBLE);
					post = 0;
					new Threads(LocalDate.tj_rd, "1").start();
				} else {
					gView.setVisibility(View.GONE);
				}
				setListener(gView);
			}
		}

		private void setListener(GridView gView) {
	        gView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					Intent intent=new Intent();
					adpter = adpters.get(post);
					if (arg2>0) {
						arg2=arg2-1;
					}
					intent.putExtra("movie", adpter.beans.get(arg2));
					intent.setClass(TuiJianActivity.this, ShowMovieInfoActivity.class);
					startActivity(intent);
				}
	        	
			});
	        gView.setOnScrollListener(new OnScrollListener() {
				@Override
				public void onScrollStateChanged(AbsListView arg0, int arg1) {

				}
				@Override
				public void onScroll(AbsListView arg0, int arg1, int arg2,
						int arg3) {

					if (arg1 + arg2 == arg3 && isResh && arg3 > 0) {

						page += 1;
						adpter = adpters.get(post);
						//new Threads(type, page + "").start();
						isResh = false;
					}
				}
			});
		};
		Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				pView.setVisibility(View.GONE);
				if (beans.isEmpty()) {
					Toast.makeText(TuiJianActivity.this, "获取数据失败", 4).show();
					return;
				}
				setDate(msg.what);
			};
		};

		private void setDate(int flag) {
			gView = (GridView) layout.getChildAt(flag);
			gView.setVisibility(View.VISIBLE);
			isResh = true;
			if (!adpters.containsKey(flag)) {
				adpter = new TJAdpter(this, beans);
				adpters.put(flag, adpter);
				View view = getLayoutInflater().inflate(R.layout.hot_title, null);
				ImageView imageView = (ImageView) view
						.findViewById(R.id.hot_title_img);
				TextView textView = (TextView) view.findViewById(R.id.hot_text);
				ImageLoader.getInstance().displayImage(
						adpter.beans.get(0).getSimg(), imageView);
				textView.setText(adpter.beans.get(0).getTitle());
				//gView.addView(view);
				gView.setAdapter(adpter);
				View view2 = getLayoutInflater().inflate(R.layout.progress_bar,
						null);
				//gView.addView(view2,gView.getChildCount()-1);
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
			Log.i("hck", "TuiJianActivity getDate start"+start);
			beans = new ArrayList<MovieBean>();
			json = Video.getRecommendVideo(this, id, "30", start.trim()).toString();
			JsonUtil.getTj(json, beans);
		}
		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {

			page = 1;
			switch (arg1) {
			case R.id.tj_rd:
				setPostion(arg0.getChildAt(0));
				type = LocalDate.tj_rd;
				post = 0;
				if (adpters.containsKey(post)) {
					show(0);
				} else {
					show(0);
					pView.setVisibility(View.VISIBLE);
					new Threads(LocalDate.tj_rd, "1").start();
				}
				break;
			case R.id.tj_gx:
				setPostion(arg0.getChildAt(1));
				post = 1;
				type = LocalDate.tj_gx;
				if (adpters.containsKey(post)) {
					show(1);
				} else {
					show(1);
					pView.setVisibility(View.VISIBLE);
					new Threads(LocalDate.tj_gx, "1").start();
				}
				break;
			case R.id.tj_mv:
				setPostion(arg0.getChildAt(2));
				type = LocalDate.tj_mv;
				post = 2;
				if (adpters.containsKey(post)) {
					show(2);
				} else {
					show(2);
					pView.setVisibility(View.VISIBLE);
					new Threads(LocalDate.tj_mv, "1").start();
				}
				break;
			case R.id.tj_pk:
				setPostion(arg0.getChildAt(3));
				type = LocalDate.tj_pk;
				post = 3;
				if (adpters.containsKey(post)) {
					show(3);
				} else {
					beans = new ArrayList<MovieBean>();
					show(3);
					pView.setVisibility(View.VISIBLE);
					new Threads(LocalDate.tj_pk, "1").start();
				}

				break;
			case R.id.tj_yl:
				setPostion(arg0.getChildAt(4));
				type = LocalDate.tj_yl;
				post = 4;
				if (adpters.containsKey(post)) {
					show(4);
				} else {
					beans = new ArrayList<MovieBean>();
					show(4);
					pView.setVisibility(View.VISIBLE);
					new Threads(LocalDate.tj_yl, "1").start();
				}
				break;
			case R.id.tj_zy:
				setPostion(arg0.getChildAt(5));
				type = LocalDate.tj_zy;
				post = 5;
				if (adpters.containsKey(post)) {
					show(5);
				} else {
					beans = new ArrayList<MovieBean>();
					show(5);
					pView.setVisibility(View.VISIBLE);
					new Threads(LocalDate.tj_zy, "1").start();
				}
				break;
			case R.id.tj_dy:
				setPostion(arg0.getChildAt(6));
				type = LocalDate.tj_dy;
				post = 6;
				if (adpters.containsKey(post)) {
					show(6);
				} else {
					beans = new ArrayList<MovieBean>();
					show(6);
					pView.setVisibility(View.VISIBLE);
					new Threads(LocalDate.tj_dy, "1").start();
				}
				break;
			case R.id.tj_dsj:
				setPostion(arg0.getChildAt(7));
				type = LocalDate.tj_dsj;
				post = 7;
				if (adpters.containsKey(post)) {
					show(7);
				} else {
					beans = new ArrayList<MovieBean>();
					show(7);
					pView.setVisibility(View.VISIBLE);
					new Threads(LocalDate.tj_dsj, "1").start();
				}
				
				break;
			case R.id.tj_hr:
				setPostion(arg0.getChildAt(8));
				type = LocalDate.tj_hr;
				post = 8;
				if (adpters.containsKey(post)) {
					show(8);
				} else {
					beans = new ArrayList<MovieBean>();
					show(8);
					pView.setVisibility(View.VISIBLE);
					new Threads(LocalDate.tj_hr, "1").start();
				}
				break;
			case R.id.tj_zb:
				setPostion(arg0.getChildAt(9));
				type = LocalDate.tj_zb;
				post = 9;
				if (adpters.containsKey(post)) {
					show(9);
				} else {
					beans = new ArrayList<MovieBean>();
					show(9);
					pView.setVisibility(View.VISIBLE);
					new Threads(LocalDate.tj_zb, "1").start();
				}
				break;
			case R.id.tj_nx:
				setPostion(arg0.getChildAt(10));
				type = LocalDate.tj_nx;
				post = 10;
				if (adpters.containsKey(post)) {
					show(10);
				} else {
					beans = new ArrayList<MovieBean>();
					show(10);
					pView.setVisibility(View.VISIBLE);
					new Threads(LocalDate.tj_nx, "1").start();
				}
				break;
			case R.id.tj_yx:
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
			
			default:
				break;
			}
		}
		private void setPostion(View view) {
			view.getLocationInWindow(location);
			postion = location[0] - width / 2;
			
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

	
	


}
