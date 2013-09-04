package com.hck.player.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;

import com.hck.myplayer.R;
import com.hck.player.date.Date;
import com.hck.player.date.LocalDate;
import com.hck.player.utils.SharedPreference;
import com.wole56.sdk.APP;

public class LodingActivity extends Activity  {
	private ImageView imageView; // 显示loding页面图片
	private Animation animation; // 渐变动画
    private View view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loding);
		init();
		new SharedPreference(this); // 初始化，用于保存一些信息
		boolean b = SharedPreference.getIsFirst(); // 看用户是否是第一次使用
		if (b) { // 说明第一次进入
			SharedPreference.saveIsFirst(); // 保存数据，里面有个firstkey，把他设置为false，表明不是第一次进入应用了
			SharedPreference.saveTime(System.currentTimeMillis(), this); // 保存用户使用时候的时间，用于后面弹积分墙广告用(当用户使用一定时间后，才弹)
		}
		 initAd(); //初始化广告
		imageView = (ImageView) findViewById(R.id.img);
		animation = AnimationUtils.loadAnimation(this, R.anim.loding);
		imageView.setAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
			@Override
			public void onAnimationEnd(Animation animation) {
				
			}
		});
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			view.setVisibility(View.GONE);
           startMainActivity();
		};
	};
	private void init() {
		view=findViewById(R.id.pb);
		new Thread() {
			@Override
			public void run() {
				super.run();
				APP.init(getApplicationContext(), LocalDate.app_id,
						LocalDate.app_key).toString();
				handler.sendEmptyMessage(0);
			}
		}.start();
	}

	private void initAd() {
		AppConnect.getInstance(this);// 初始化广告，万普广告平台帮助文档有相关说明
		AppConnect.getInstance(this).initPopAd(this);
	}

	private void startMainActivity() {
		startActivity(new Intent(LodingActivity.this, MainActivity.class)); // 用intent，启动主界面
		this.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppConnect.getInstance(this).close(); // 接触广告绑定
	}

	
}
