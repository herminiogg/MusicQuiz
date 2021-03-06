package com.ia.musicquiz.persistence.dao;


import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


public class SongDao {
	
	private final static String GENRE_RANDOM_SONG_SQL = 
			"select titulo, nombre_artista, nombre_genero, uri " +
			"from cancion, artista, generocancion, genero " +
			"where cancion.id_artista=artista.id and " +
			"cancion.id=generocancion.id_cancion " +
			"and generocancion.id_genero=genero.id " +
			"and genero.nombre_genero=?	" +
			"order by random() limit 1";
	private final static String GENRE_OTHER_SONGS = "select titulo, nombre_artista, nombre_genero, uri " +
			"from cancion, artista, generocancion, genero " +
			"where cancion.id_artista=artista.id and " +
			"cancion.id=generocancion.id_cancion " +
			"and generocancion.id_genero=genero.id " +
			"and genero.nombre_genero=?	" +
			"and artista.nombre_artista!=?" +
			"order by random() limit 3";
	
	private SQLiteDatabase bd;
	private Cursor c;
	
	public void setDatabase(SQLiteDatabase bd) {
		this.bd=bd;
	}
	
	public Song getGenreRandomSong(String genre) {
		c = bd.rawQuery(GENRE_RANDOM_SONG_SQL, new String[] {genre});
		c.moveToFirst();
		Song song = new Song(c.getString(0),c.getString(1), 
				c.getString(2), Uri.parse(c.getString(3)));
		c.close();
		return song;
	}
	
	public List<Song> getOtherGenreSongs(String genre, Song actual) {
		List<Song> canciones = new ArrayList<Song>();
		c = bd.rawQuery(GENRE_OTHER_SONGS, new String[] {genre, actual.getArtista()});
		while(c.moveToNext()) {
			canciones.add(new Song(c.getString(0),c.getString(1), 
					c.getString(2), Uri.parse(c.getString(3))));
		}
		c.close();
		return canciones;
	}

}
