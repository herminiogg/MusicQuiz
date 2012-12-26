package com.ia.musicquiz.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ArtistDao {
	
private final static String ARTIST_LIST_SQL = "select nombre_artista from artista";
	
	private SQLiteDatabase bd;
	private Cursor c;
	
	public void setDatabase(SQLiteDatabase bd) {
		this.bd=bd;
	}
	
	public List<String> getArtistList() {
		List<String> listaArtistas = new ArrayList<String>();
		c = bd.rawQuery(ARTIST_LIST_SQL, null);
		while(c.moveToNext()) {
			listaArtistas.add(c.getString(0));
		}
		c.close();
		return listaArtistas;
	}

}
