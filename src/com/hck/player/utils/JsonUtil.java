package com.hck.player.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.hck.player.bean.MovieBean;
import com.hck.player.bean.OneMovieBean;

public class JsonUtil {
/**
 * 	  * title string 视频标题 
		content string 视频描述 
		tag string 视频标签 
		bimg string 视频缩略图地址, 大图 
		user_id string 用户id 
		comments string 视频评论数 
		public_time string 视频公开时间 
		totaltime string 视频总时长 
 */
	
	private static JSONArray jArray;
	private static JSONObject object;
    private static MovieBean bean;
	public static void getHot(String json, ArrayList<MovieBean> beans) {
		
		if (json == null) {
			return;
		}
		try {
	      object=new JSONObject(json);
	      jArray=object.getJSONArray("list");
	      if (jArray==null) {
			return;
		}
	      for (int i = 0; i < jArray.length(); i++) {
			object=jArray.getJSONObject(i);
			if (object!=null) {
				bean=new MovieBean();
				bean.setVid(object.getString("vid"));
				bean.setTitle(object.getString("title"));
				bean.setBimg(object.getString("bimg"));
				bean.setMimg(object.getString("mimg"));
				bean.setSimg(object.getString("img"));
				bean.setAddTime(object.getString("public_time"));
				bean.setAllTime(object.getString("totaltime"));
				bean.setComments(object.getString("comments"));
				//bean.setContent(object.getString("content"));
				bean.setTag(object.getString("tag"));
				beans.add(bean);
			}
		}
	      
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public static void getInfo(String jsString,OneMovieBean bean)
	{
		try {
			object=new JSONObject(jsString);
			object=object.getJSONObject("0");
			bean.setContent(object.getString("desc"));
		} catch (Exception e) {
			
 		}
		
	}
	public static void getMovieinfo(String json, OneMovieBean bean) {
		Log.i("hck", "JsonUtil getMovieinfo:" +json);
		try {
			object=new JSONObject(json);
			JSONObject jObject=object.getJSONObject("info");
			if (jObject!=null) {
				bean.setHd(jObject.getString("hd"));
				bean.setImg(jObject.getString("img"));
				bean.setType("cid2");
				jArray=jObject.getJSONArray("rfiles");
				ArrayList<String> strings=new ArrayList<String>();
				for (int i = 0; i < jArray.length(); i++) {
					String string=jArray.getJSONObject(i).getString("url");
					strings.add(string);
				}
				bean.setUrls(strings);
			}
		} catch (Exception e) {
		}
		
	}
	/**
	 * "list":[
{
"id": "909",
"title": "十一周宝宝为爸爸伴奏",
"url": "http://www.56.com/u54/v_Njg1ODQ4Njc.html",
"pic": "http://s2.56img.com/images/index/1205/0526my2.jpg",
"settype": "1",
"vlink": "0",
"showname": "",
"showlink": "",
"add_admin": "qinwen",
"add_time": "2012-05-26 13:51:21",
"dateline": "2012-05-26 13:51:21",
"updatetime": "2012-09-27 18:36:42",
"imgicon": "0",
"playnum": "0",
"user_id": "musicguys",
"comment": "3",
"review": "3",
"baidupic": "0",
"nums": "35877",
"vid": "68584867",
"user_name": "musicguys",
"totaltime": "121550",
"isHD": "hd",
"link": ""
}]
	 * @param json
	 * @param beans
	 */
	public static void getTj(String json,ArrayList<MovieBean> beans)
	{
		Log.i("hck", "getTj "+json);
		if (json == null) {
			return;
		}
		try {
	      object=new JSONObject(json);
	      jArray=object.getJSONArray("list");
	      if (jArray==null) {
			return;
		}
	      for (int i = 0; i < jArray.length(); i++) {
			object=jArray.getJSONObject(i);
			if (object!=null) {
				bean=new MovieBean();
				bean.setVid(object.getString("vid"));
				bean.setTitle(object.getString("title"));
				bean.setSimg(object.getString("pic"));
				bean.setAddTime(object.getString("add_time"));
				bean.setAllTime(object.getString("totaltime"));
				bean.setComments(object.getString("comment"));
				beans.add(bean);
			}
		}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	/**
	 * vid	string	视频id
title	string	视频标题
content	string	视频描述
img	string	视频缩略图地址, 小图
	 * @param string
	 * @param beans
	 */
	public static void getType(String string,ArrayList<MovieBean> beans)
	{
		if (string == null) {
			return;
		}
		try {
	      object=new JSONObject(string);
	      jArray=object.getJSONArray("data");
	      if (jArray==null) {
			return;
		}
	      for (int i = 0; i < jArray.length(); i++) {
			object=jArray.getJSONObject(i);
			Log.i("hck", "type: "+object);
			if (object!=null) {
				bean=new MovieBean();
				bean.setVid(object.getString("vid"));
				bean.setTitle(object.getString("title"));
				bean.setSimg(object.getString("img"));
				//bean.setContent(object.getString("content"));
				beans.add(bean);
			}
		}
	      
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
