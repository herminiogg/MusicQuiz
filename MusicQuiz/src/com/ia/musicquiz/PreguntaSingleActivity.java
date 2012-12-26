package com.ia.musicquiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.ia.musicquiz.persistence.Song;
import com.ia.musicquiz.persistence.SqliteManager;
import com.ia.musicquiz.persistence.dao.SongDao;

public class PreguntaSingleActivity extends Activity implements OnClickListener {

	private MediaPlayer mp;
	private Song cancionElegida;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// FullSreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_pregunta_single);

		try {
			List<Song> canciones = getCanciones();
			cancionElegida = canciones.get(new Random().nextInt(3));
			asignarCancionesABotones(canciones);

			mp = MediaPlayer.create(this, cancionElegida.getUri());
			mp.start();
		} catch (Exception e) {
			Toast.makeText(this, "Error al obtener una cancion",
					Toast.LENGTH_LONG).show();
		} finally {
			SqliteManager.closeBD();
		}
	}

	private List<Song> getCanciones() throws Exception {
		SQLiteDatabase bd = SqliteManager.getDatabase();
		SongDao dao = new SongDao();
		dao.setDatabase(bd);
		
		List<Song> canciones = new ArrayList<Song>();

		while (canciones.size() < 4) {
			Song song = dao.getGenreRandomSong("Rock");
			while (isArtistaRepetido(canciones, song.getArtista()))
				song = dao.getGenreRandomSong("Rock");
			canciones.add(song);
		}

		return canciones;
	}
	
	private boolean isArtistaRepetido(List<Song> canciones, String artista) {
		for (Song cancion : canciones) {
			if (cancion.getArtista().equals(artista))
				return true;
		}
		return false;
	}


	private void asignarCancionesABotones(List<Song> canciones) {
		
		Button boton = (Button) findViewById(R.id.btOpcion1);
		boton.setText(canciones.get(0).getArtista());
		boton.setOnClickListener(this);

		boton = (Button) findViewById(R.id.btOpcion2);
		boton.setText(canciones.get(1).getArtista());
		boton.setOnClickListener(this);

		boton = (Button) findViewById(R.id.btOpcion3);
		boton.setText(canciones.get(2).getArtista());
		boton.setOnClickListener(this);

		boton = (Button) findViewById(R.id.btOpcion4);
		boton.setText(canciones.get(3).getArtista());
		boton.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		mp.stop();
		finish();
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		Button source = (Button) v;
		if (source.getText().toString().equals(cancionElegida.getArtista())) {
			Toast.makeText(this, "Bien, has acertado", Toast.LENGTH_SHORT)
					.show();
			Intent avanzar = new Intent(this, PreguntaSingleActivity.class);
			startActivity(avanzar);
		}
		else 
			Toast.makeText(this, "Lo siento, te has confundido",
					Toast.LENGTH_SHORT).show();
	}
}
