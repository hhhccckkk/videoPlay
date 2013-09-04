package com.hck.player.utils;

import java.util.List;

import com.hck.player.bean.OneMovieBean;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class VideoUtil {
	private static OneMovieBean bean;
	public static void getMyVideo(Context context,List<OneMovieBean> beans)//获取所有歌曲信息
	{
		Cursor cursor=null;
	    cursor=context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,null, null, null, null);
	    while (cursor.moveToNext()) { // 遍历数据，把音乐保存在几个里面
			bean=new OneMovieBean();
			bean.setTotaltime(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE)));
			bean.setUrl(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
			bean.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE)));
			beans.add(bean);
		}
	    
	}
  
}
