package com.ia.musicquiz.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SqliteManager {
	
	private static SqliteManager singleton;
	private static MySqliteOpenHelper openHelper; 
		
	private SqliteManager(Context context) {
		openHelper = new MySqliteOpenHelper(context, "musicBD", null, 1);
		if(new SqlVersionManager().isNewVersion())
			openHelper.upgrade(openHelper.getWritableDatabase());
	}
	
	public static void initBD(Context context) {
		singleton = new SqliteManager(context);
	}
	
	public static void closeBD() {
		openHelper.close();
	}
	
	public static SQLiteDatabase getDatabase() throws Exception {
		if(singleton!=null)
			return openHelper.getReadableDatabase();
		throw new Exception("No init database");
		
	}
}
