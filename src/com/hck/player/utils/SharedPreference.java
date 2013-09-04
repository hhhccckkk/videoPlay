package com.hck.player.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreference {
	private static SharedPreferences pref;
	private static Editor editor;

	public SharedPreference(Context context) {

		pref = context.getSharedPreferences(Context.ACTIVITY_SERVICE,
				Context.MODE_APPEND);
	}

	public static void saveTime(long time, Context context) { // 保存昵称
		editor = pref.edit();
		editor.putLong("time", time);
		editor.commit();
	}

	public static long getTime() {
		return pref.getLong("time", 0);
	}
	public static void saveIsFirst()
	{
		editor = pref.edit();
		editor.putBoolean("first", false);
		editor.commit();
	}
	public static boolean getIsFirst()
	{
		return pref.getBoolean("first", true);
	}

}
