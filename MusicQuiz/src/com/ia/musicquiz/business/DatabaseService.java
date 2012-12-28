package com.ia.musicquiz.business;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import com.ia.musicquiz.persistence.SqliteManager;
import com.ia.musicquiz.persistence.dao.ArtistDao;
import com.ia.musicquiz.persistence.dao.GenreDao;
import com.ia.musicquiz.persistence.dao.Song;
import com.ia.musicquiz.persistence.dao.SongDao;

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
	
	public List<String> getArtistList() {
		ArtistDao ad = new ArtistDao();
		ad.setDatabase(bd);
		return ad.getArtistList();
	}
	
	public List<Song> getOtherGenreSongs(String genre, Song actual) {
		SongDao sd = new SongDao();
		sd.setDatabase(bd);
		return sd.getOtherGenreSongs(genre, actual);
	}
}
