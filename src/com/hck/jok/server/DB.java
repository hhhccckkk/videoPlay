package com.hck.jok.server;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper{

	private static final String dataBaseName="movie";
    private static final int version=1;
	public DB(Context context) {
		super(context, dataBaseName, null, version);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table point( id Integer primary key autoincrement,money Integer)");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		
	}

}
