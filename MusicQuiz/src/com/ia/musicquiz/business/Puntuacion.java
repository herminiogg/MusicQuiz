package com.ia.musicquiz.business;

public class Puntuacion implements Comparable<Puntuacion>{
	
	private String nombre;
	private Integer puntos;
	
	public Puntuacion(String nombre, int puntos) {
		this.nombre = nombre;
		this.puntos = puntos;
	}

	@Override
	public int compareTo(Puntuacion another) {
		return puntos.compareTo(another.getPuntos());
	}
	
	public int getPuntos(){
		return puntos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}

	@Override
	public String toString() {
		return nombre + ", " + puntos;
	}
	
	

}
