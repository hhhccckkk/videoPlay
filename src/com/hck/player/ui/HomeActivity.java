package com.hck.player.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;

import com.hck.myplayer.R;
import com.hck.player.adpter.AllAdpter;
import com.hck.player.date.Date;
import com.hck.player.utils.AlertDialogUpdate;

public class HomeActivity extends Activity{
	private List<String> fatherList;
	private List<List<String>> childerlList;
	private List<String> list;
	String string[];
	private AllAdpter adpter;
	private ExpandableListView view;
	private int id[] = { R.array.chiled_gaoxiao, R.array.chiled_yc,
			R.array.chiled_yl, R.array.chiled_zx, R.array.chiled_qc,
			R.array.chiled_ty, R.array.chiled_yx };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.all);
		view = (ExpandableListView) findViewById(R.id.all);
		fatherList = new ArrayList<String>();
		childerlList = new ArrayList<List<String>>();
		initDate();
		adpter = new AllAdpter(fatherList, childerlList, this);
		view.setAdapter(adpter);
		setListener();

	}
	
	private void initDate() {
		string = getResources().getStringArray(R.array.all_name);
		for (int i = 0; i < string.length; i++) {
			fatherList.add(string[i]);
		}
		for (int i = 0; i < id.length; i++) {
			initChild(id[i]);
		}
	}

	private void initChild(int id) {
		string = getResources().getStringArray(id);

		list = new ArrayList<String>();
		for (int i = 0; i < string.length; i++) {
			list.add(string[i]);
		}
		childerlList.add(list);
	}
	private void setListener() {
       view.setOnChildClickListener(new OnChildClickListener() {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			Log.i("hck", "HomeActivity "+groupPosition);
			  return false;
		}
	});
       
      
	}

	@Override
	protected void onResume() {
		super.onResume();
		
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
