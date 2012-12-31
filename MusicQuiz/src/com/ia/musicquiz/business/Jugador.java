package com.ia.musicquiz.business;


public class Jugador {

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
