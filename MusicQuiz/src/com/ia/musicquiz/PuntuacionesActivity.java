package com.ia.musicquiz;

import java.util.List;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ia.musicquiz.business.AlmacenPuntuaciones;
import com.ia.musicquiz.business.Puntuacion;

public class PuntuacionesActivity extends ActivityFinishedOnPause {

	private TableLayout tabla;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_puntuaciones);

		tabla = (TableLayout) findViewById(R.id.tableLayout);
		cargarPuntuaciones();
	}

	private void cargarPuntuaciones() {
		List<Puntuacion> puntuaciones = AlmacenPuntuaciones.getInstance(this)
				.getPuntuaciones();

		TableRow row;
		TextView puntos, nombre;

		for (Puntuacion puntuacion : puntuaciones) {
			row = new TableRow(this);

			puntos = new TextView(this);
			nombre = new TextView(this);

			puntos.setText(String.valueOf(puntuacion.getPuntos()));
			nombre.setText(puntuacion.getNombre());

			puntos.setTypeface(null, Typeface.BOLD);
			nombre.setTypeface(null, Typeface.BOLD);

			puntos.setTextSize(30);
			nombre.setTextSize(30);

			puntos.setGravity(Gravity.CENTER);

			row.addView(puntos);
			row.addView(nombre);

			tabla.addView(row);

		}
	}
}
