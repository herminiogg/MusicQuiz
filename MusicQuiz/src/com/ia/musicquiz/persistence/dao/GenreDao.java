package com.ia.musicquiz.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GenreDao {
	
	private final static String GENRE_LIST_SQL = "select nombre_genero from genero";
	
	private SQLiteDatabase bd;
	private Cursor c;
	
	public void setDatabase(SQLiteDatabase bd) {
		this.bd=bd;
	}
	
	public List<String> getGenreList() {
		List<String> listaGeneros = new ArrayList<String>();
		c = bd.rawQuery(GENRE_LIST_SQL, null);
		while(c.moveToNext()) {
			listaGeneros.add(c.getString(0));
		}
		c.close();
		return listaGeneros;
	}

}
