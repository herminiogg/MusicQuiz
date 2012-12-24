package com.ia.musicquiz.persistence;

import java.io.FileNotFoundException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqliteOpenHelper extends SQLiteOpenHelper {
	
	private Context context;

	public MySqliteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("PRAGMA foreign_keys=ON;"); //activa las foreing keys
		execSqlScript(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		upgrade(db);
	}
	
	public void upgrade(SQLiteDatabase db) {
		execSqlScript(db);
	}
	
	private void execSqlScript(SQLiteDatabase db) {
		SqlIterator iterator;
		try {
			iterator = new SqlIterator(new SqlDownloader(context).getFile());
			iterator.next(); // salta la linea de version
			while(iterator.hasNext()) {
				db.execSQL(iterator.next());
			}
		} catch (FileNotFoundException e) {
			Log.e("MySqliteOpenHelper", "No file found");
		}
	}
	

}
