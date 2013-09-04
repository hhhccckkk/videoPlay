package com.hck.player.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.waps.AdView;
import cn.waps.AppConnect;

import com.hck.myplayer.R;

public class DownActivity extends Activity implements OnClickListener {
	private RelativeLayout r1, r2, r3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		init();
		showAds();
	}

	private void showAds() {
		LinearLayout container =(LinearLayout)findViewById(R.id.AdLinearLayout);
		new AdView(this,container).DisplayAd();
	}

	public void init() {
		r1 = (RelativeLayout) findViewById(R.id.r1);
		r1.setOnClickListener(this);
		r2 = (RelativeLayout) findViewById(R.id.r2);
		r2.setOnClickListener(this);
		r3 = (RelativeLayout) findViewById(R.id.r3);
		r3.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.r1:
			AppConnect.getInstance(this).showAppOffers(this);

			break;
		case R.id.r2:
			AppConnect.getInstance(this).showTuanOffers(this);
			break;
		case R.id.r3:
			AppConnect.getInstance(this).showGameOffers(this);
			break;
		default:
			break;
		}
	}

}
