package com.ia.musicquiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ia.musicquiz.persistence.SqliteManager;
import com.ia.musicquiz.persistence.dao.Song;
import com.ia.musicquiz.persistence.dao.SongDao;

public class PreguntaSingleActivity extends ActivityFinishedOnPause implements OnClickListener {

	private MediaPlayer mp;
	private Song cancionElegida;
	private TextView tiempo;
	private Timer timer;
	private final static int DURACION = 30;
	private String genero;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_pregunta_single);
		
		tiempo = (TextView) this.findViewById(R.id.textTiempo);

		iniciarJuego();

	}

	private void iniciarJuego() {
		genero = getIntent().getExtras().getString("genero");
		List<Song> canciones = getCanciones();
		if (canciones != null) {
			cancionElegida = canciones.get(new Random().nextInt(3));
			asignarCancionesABotones(canciones);

			mp = MediaPlayer.create(this, cancionElegida.getUri());
			mp.start();
			iniciarHiloTiempoRestante();
				
		};
	}

	private List<Song> getCanciones() {
		try {
			SQLiteDatabase bd = SqliteManager.getDatabase();
			SongDao dao = new SongDao();
			dao.setDatabase(bd);

			List<Song> canciones = new ArrayList<Song>();

			while (canciones.size() < 4) {
				Song song = dao.getGenreRandomSong(genero);
				while (isArtistaRepetido(canciones, song.getArtista()))
					song = dao.getGenreRandomSong(genero);
				canciones.add(song);
			}
			return canciones;
		} catch (Exception e) {
			Toast.makeText(this, "Error al obtener una cancion",
					Toast.LENGTH_LONG).show();
		} finally {
			SqliteManager.closeBD();
		}
		return null;
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
	protected void onStop() {
		mp.reset();
		super.onStop();
	}

	@Override
	public void onClick(View v) {
		Button source = (Button) v;
		if (source.getText().toString().equals(cancionElegida.getArtista())) {
			Toast.makeText(this, "Bien, has acertado", Toast.LENGTH_SHORT)
					.show();
			reiniciarInterfaz();
		} else
			Toast.makeText(this, "Lo siento, te has confundido",
					Toast.LENGTH_SHORT).show();
	}

	private void reiniciarInterfaz() {
		mp.reset();
		timer.cancel();
		List<Song> canciones = getCanciones();
		if (canciones != null) {
			cancionElegida = canciones.get(new Random().nextInt(3));

			Button boton = (Button) findViewById(R.id.btOpcion1);
			boton.setText(canciones.get(0).getArtista());

			boton = (Button) findViewById(R.id.btOpcion2);
			boton.setText(canciones.get(1).getArtista());

			boton = (Button) findViewById(R.id.btOpcion3);
			boton.setText(canciones.get(2).getArtista());

			boton = (Button) findViewById(R.id.btOpcion4);
			boton.setText(canciones.get(3).getArtista());

			mp = MediaPlayer.create(this, cancionElegida.getUri());
			mp.start();
			iniciarHiloTiempoRestante();
			
		} else
			finish();
	}
	
	private void iniciarHiloTiempoRestante() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				final int tiempoRestante = DURACION*1000 - mp.getCurrentPosition();
				 PreguntaSingleActivity.this.runOnUiThread(new Runnable() {@Override public void run()
				 {
					 tiempo.setText(getResources().getText(R.string.tiempo_restante).toString()+
		    					tiempoRestante/1000+getResources().getText(R.string.segundos).toString());
				 }});
    			
			}
		}, 1000, 1000);
	}
	
	
}

