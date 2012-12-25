package com.ia.musicquiz;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.ia.musicquiz.persistence.Song;
import com.ia.musicquiz.persistence.SqliteManager;
import com.ia.musicquiz.persistence.dao.SongDao;

public class MainActivity extends Activity {

	MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView tx = (TextView) findViewById(R.id.texto);
		
		SqliteManager.initBD(this);
		
		SQLiteDatabase bd;
		try {
			bd = SqliteManager.getDatabase();
			SongDao dao = new SongDao();
			dao.setDatabase(bd);
			Song song = dao.getGenreRandomSong("Rock");
			tx.setText("Titulo: " + song.getTitulo() + "\nArtista: "
					+ song.getArtista() + "\nGenero: " + song.getGenero()
					+ "\nUri: " + song.getUri());
			mp = MediaPlayer.create(this, song.getUri());
			mp.start();
		} catch (Exception e) {
			Toast.makeText(this, "Error al obtener una cancion", Toast.LENGTH_LONG).show();
		} finally {			
			SqliteManager.closeBD();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		mp.stop();
		finish();
		super.onPause();
	}
}
