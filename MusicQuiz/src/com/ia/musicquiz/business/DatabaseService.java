package com.ia.musicquiz.business;

import java.util.List;

import com.ia.musicquiz.persistence.SqliteManager;
import com.ia.musicquiz.persistence.dao.GenreDao;
import com.ia.musicquiz.persistence.dao.Song;
import com.ia.musicquiz.persistence.dao.SongDao;

import android.database.sqlite.SQLiteDatabase;

public class DatabaseService {
	
	private SQLiteDatabase bd;
	
	public DatabaseService() throws Exception {
		this.bd=SqliteManager.getDatabase();
	}

	public Song getRandomSong(String genre) {
		SongDao sd = new SongDao();
		sd.setDatabase(bd);
		return sd.getGenreRandomSong(genre);	
	}
	
	public List<String> getGenresList() {
		GenreDao gd = new GenreDao();
		gd.setDatabase(bd);
		return gd.getGenreList();
	}
}
