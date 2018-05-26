package com.col.commo.fightrats_time;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists dict(_id integer primary key autoincrement," +
				"username text,passwd text,phone text,admin text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//如果传入的版本号大于当前版本，则调用该方法  int version这个是版本号
		db.execSQL("drop table if exists dict");
		onCreate(db);
	}

}
