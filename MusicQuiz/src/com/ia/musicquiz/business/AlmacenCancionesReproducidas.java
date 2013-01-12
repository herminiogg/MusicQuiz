package com.ia.musicquiz.business;

import java.util.HashSet;
import java.util.Set;

import com.ia.musicquiz.persistence.dao.Song;

public class AlmacenCancionesReproducidas {
	
	public Set<Song> canciones;
	private static AlmacenCancionesReproducidas instance = null;
	
	private AlmacenCancionesReproducidas() {
		canciones = new HashSet<Song>();
	}
	
	public static AlmacenCancionesReproducidas getInstance() {
		if (instance == null)
			instance = new AlmacenCancionesReproducidas();
		return instance;
	}
	
	public void addCancion(Song cancion) {
		canciones.add(cancion);
	}
	
	public void removeCancion(Song cancion) {
		canciones.remove(cancion);
	}
	
	public void vaciarAlmacen() {
		canciones.clear();
	}
	
	public boolean contiene(Song cancion) {
		return canciones.contains(cancion);
	}

}
