package com.ia.musicquiz.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class AlmacenPuntuaciones {

	SharedPreferences preferences;
	private List<Puntuacion> puntuaciones;
	private static AlmacenPuntuaciones instance;

	private AlmacenPuntuaciones(Context context) {
		preferences = context.getSharedPreferences("Puntuaciones",
				Context.MODE_PRIVATE);
		puntuaciones = new ArrayList<Puntuacion>();
		cargarPuntuaciones();
	}

	public static AlmacenPuntuaciones getInstance(Context context) {
		if (instance == null)
			instance = new AlmacenPuntuaciones(context);
		return instance;
	}

	@SuppressLint("DefaultLocale")
	public void guardarPuntuacion(String nombre, int puntuacion) {
		nombre = nombre.toUpperCase();
		SharedPreferences.Editor editor = preferences.edit();
		int puntos = preferences.getInt(nombre, -1);
		if (puntos != -1 && puntos < puntuacion) {
				editor.remove(nombre);
		}

		if (puntos < puntuacion) {
			editor.remove(nombre);
			editor.putInt(nombre, puntuacion);
			editor.commit();
			cargarPuntuaciones();
		}
	}

	public void cargarPuntuaciones() {
		puntuaciones.clear();
		for (Entry<String, ?> entrada : preferences.getAll().entrySet()) {
			Puntuacion puntuacion = new Puntuacion(entrada.getKey(),
					(Integer) entrada.getValue());
			puntuaciones.add(puntuacion);
		}
		if (!puntuaciones.isEmpty())
			ordenarLista(puntuaciones);
	}

	private void ordenarLista(List<Puntuacion> puntuaciones) {
		final int N = puntuaciones.size();
		quickSort(puntuaciones, 0, N - 1);
	}

	private void quickSort(List<Puntuacion> puntuaciones, int inicio, int fin) {
		if (inicio >= fin)
			return;
		int pivote = puntuaciones.get(inicio).getPuntos();
		int izq = inicio + 1;
		int der = fin;
		while (izq <= der) {
			while (izq <= fin && puntuaciones.get(izq).getPuntos() > pivote)
				izq++;
			while (der > inicio && puntuaciones.get(der).getPuntos() <= pivote)
				der--;
			if (izq < der) {
				Puntuacion tmp = puntuaciones.get(izq);
				puntuaciones.set(izq, puntuaciones.get(der));
				puntuaciones.set(der, tmp);
			}
		}
		if (der > inicio) {
			Puntuacion tmp = puntuaciones.get(inicio);
			puntuaciones.set(inicio, puntuaciones.get(der));
			puntuaciones.set(der, tmp);
		}
		quickSort(puntuaciones, inicio, der - 1);
		quickSort(puntuaciones, der + 1, fin);
	}

	public List<Puntuacion> getPuntuaciones() {
		return puntuaciones;
	}
}