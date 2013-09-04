package com.hck.player.utils;
import android.app.Activity;
public class Exit {
	public static void exit() {
            Activity activity = null;
       		for (int i = 0; i < activityManagers.activitys.size(); i++) {
       			activity = (Activity) activityManagers.activitys.get(i);
       			if (null != activity) {
       				activity.finish();
       			}
       		}
       		activityManagers.activitys.clear();
       		System.exit(0);

			
	}
}
