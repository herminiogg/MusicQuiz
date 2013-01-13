package com.ia.musicquiz;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ia.musicquiz.business.DatabaseService;
import com.ia.musicquiz.business.Jugador;

public class GenreListActivity extends ListActivity {
	
	private final static int NPREGUNTAS = 10;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// FullSreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.activity_genre_list);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, cargarGenerosBD());

		setListAdapter(adapter);
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		String seleccionado = (String) getListView()
				.getItemAtPosition(position);
		Intent i = new Intent(GenreListActivity.this,
				PreguntaSingleActivity.class);
		i.putExtra("genero", seleccionado);
		i.putExtra("npreguntas", NPREGUNTAS); //hardcoded, habrá que cambiarlo cuando se presenten más niveles 
		i.putExtra("jugador", new Jugador());
		i.putExtra("preguntaActual", 1);
		startActivity(i);
	}

	private List<String> cargarGenerosBD() {
		try {
			return new DatabaseService().getGenresList();
		} catch (Exception e) {
			Toast.makeText(this,
					this.getResources().getString(R.string.errorbd),
					Toast.LENGTH_LONG).show();
			finish();
		}
		return null;
	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}

}
