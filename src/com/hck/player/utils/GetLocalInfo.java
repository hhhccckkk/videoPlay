package com.hck.player.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;

import com.hck.player.adpter.LocalAdpter;
import com.hck.player.bean.OneMovieBean;

public class GetLocalInfo extends AsyncTask<Void, File, Void> {
	private ProgressDialog pd;
	private List<OneMovieBean> files = new ArrayList<OneMovieBean>();
	private OneMovieBean bean;
	private Context context;
	private ListView listView;

	public GetLocalInfo(Context context, ListView listView) {
		this.context = context;
		this.listView = listView;
		pd = new ProgressDialog(context);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pd.setMessage("正在扫描sdcard...");
		pd.show();
	}
	public void eachAllMedias(File f) {

		if (f != null && f.exists() && f.isDirectory()) {
			File[] files = f.listFiles();
			if (files != null) {
				
				for (File file : f.listFiles()) {
					if (file.isDirectory()) {
						eachAllMedias(file);
					} else if (file.exists() && file.canRead()
							&& FileUtils.isVideo(file)) {
						onProgressUpdate(file);
					}
				}
			}
		}
	}

	@Override
	protected void onPostExecute(Void result) {
		
		super.onPostExecute(result);
		super.onPostExecute(result);
		LocalAdpter adpter = new LocalAdpter(context,files);
		listView.setAdapter(adpter);
		try {
			pd.dismiss();
		} catch (Exception e) {
		}
		
	}
       @Override
    protected void onProgressUpdate(File... values) {
    	// TODO Auto-generated method stub
    	super.onProgressUpdate(values);
    	
		super.onProgressUpdate(values);
		File f = values[0];
		if (!f.getName().startsWith(".")) {
			bean = new OneMovieBean();
			bean.setTitle(f.getName());
			bean.setUrl(f.getPath());
			bean.setTotaltime(f.length()+"");
			files.add(bean);
		}
    }
	
	

	@Override
	protected Void doInBackground(Void... params) {
		eachAllMedias(Environment.getExternalStorageDirectory());
		return null;
	}

}
