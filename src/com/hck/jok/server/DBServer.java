package com.hck.jok.server;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBServer {
	private SQLiteDatabase sDatabase;
	private DB db;

	public DBServer(Context context) {
		db = new DB(context);
		sDatabase = db.getWritableDatabase();
	}

	public int getPoint() {
		Cursor cursor = null;
		String sql = "select * from point";
		cursor = sDatabase.rawQuery(sql, null);
		if (cursor != null && cursor.moveToNext()) {
			return cursor.getInt(0);
		}
		return 0;

	}
   
	public boolean savePoint(int point) {
		try {
			sDatabase.execSQL("insert into point values(?)",
					new Object[] { new int[] { point } });
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public void closeDB() {
		sDatabase.close();
	}

}
