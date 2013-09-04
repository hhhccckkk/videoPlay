package com.hck.player.ui;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hck.myplayer.R;
import com.hck.player.adpter.LocalAdpter;
import com.hck.player.bean.OneMovieBean;
import com.hck.player.utils.GetLocalInfo;

public class LocalActivity extends Activity {
	private ListView listView;
    private ArrayList<String> urlList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local);
		init();
		setListener();
	}

	private void init() {
		urlList=new ArrayList<String>();
		listView = (ListView) findViewById(R.id.show_local_list);
		new GetLocalInfo(this, listView).execute();
		
		
	}

	private void setListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent();
				urlList.clear();
				urlList.add(LocalAdpter.beans.get(arg2).getUrl());
				intent.putExtra("name", LocalAdpter.beans.get(arg2).getTitle());
				intent.putExtra("url", urlList);
				intent.setClass(LocalActivity.this, PlayActivity.class);
				startActivity(intent);
			}

		});
	}

}
