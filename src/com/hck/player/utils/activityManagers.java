package com.hck.player.utils;

import java.util.ArrayList;
import java.util.List;

public class activityManagers {
	public static List<Object> activitys = new ArrayList<Object>();
	// 添加新創建的activity
	public static void addActivity(Object object) {
		activitys.add(object);
	}
}