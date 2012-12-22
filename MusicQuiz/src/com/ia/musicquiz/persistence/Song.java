package com.ia.musicquiz.persistence;

import android.net.Uri;

public class Song {
	
	private String titulo;
	private String artista;
	private String genero;
	private Uri uri;
	
	public Song(String titulo, String artista, String genero, Uri uri) {
		this.titulo=titulo;
		this.artista=artista;
		this.genero=genero;
		this.uri=uri;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getArtista() {
		return artista;
	}

	public String getGenero() {
		return genero;
	}

	public Uri getUri() {
		return uri;
	}


}
