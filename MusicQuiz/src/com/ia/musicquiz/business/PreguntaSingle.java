package com.ia.musicquiz.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

import com.ia.musicquiz.PreguntaSingleActivity;
import com.ia.musicquiz.persistence.dao.Song;

public class PreguntaSingle {

	private String genero;
	private Song correcta;
	private List<Song> incorrectas;
	private MediaPlayer mp;
	private final static int DURACION = 30;
	
	private Context context;
	private OnCompletionListener listener;
	
	public PreguntaSingle(String genero, Context context, PreguntaSingleActivity psa) {
		this.context=context;
		this.genero=genero;
		this.listener = psa;
		initCanciones();
	}
	
	private void initCanciones() {
		DatabaseService ds;
		try {
			ds = new DatabaseService();
			correcta = ds.getRandomSong(genero);
			incorrectas = ds.getOtherGenreSongs(genero, correcta);
		} catch (Exception e) {
			Log.e("Database", "Error al obtener cancion de la base de datos");
		}
	}
	
	public void startPlayer() {
		mp = MediaPlayer.create(context, correcta.getUri());
		mp.setOnCompletionListener(listener);
		mp.start();
	}
	
	public void stopPlayer() {
		mp.stop();
	}
	
	public int getTiempoRestante() {
		if(mp.isPlaying()) 
			return DURACION*1000 - mp.getCurrentPosition();
		else return 0;
	}
	
	public boolean isCorrectSong(Song song) {
		return correcta.equals(song);
	}
	
	public List<Song> getCanciones() {
		List<Song> todasCanciones = new ArrayList<Song>(); 
		for(Song s : incorrectas) 
			todasCanciones.add(s);
		todasCanciones.add(new Random().nextInt(todasCanciones.size()+1), correcta);
		return todasCanciones;
	}
	
	

}
