package com.ia.musicquiz.business;

import java.io.Serializable;


public class Jugador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int puntuacion;
	private final static int DURACION = 30;
	
	public Jugador() {
		this.puntuacion=0;
	}
	
	public void addPreguntaAcertada(int tiempoRestante) {
		puntuacion+=tiempoRestante;
	}
	
	public void addPreguntaFallada(int tiempoRestante) {
		puntuacion-=(DURACION-tiempoRestante)/2;
	}

	public int getPuntuacion() {
		return puntuacion;
	}
	
}
