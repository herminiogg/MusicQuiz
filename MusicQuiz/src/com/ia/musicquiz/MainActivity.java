package com.ia.musicquiz;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ia.musicquiz.persistence.SqliteManager;
import com.ia.musicquiz.persistence.dao.Song;
import com.ia.musicquiz.persistence.dao.SongDao;

public class MainActivity extends Activity {

	MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//FullSreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		TextView tx = (TextView) findViewById(R.id.texto);

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
			Toast.makeText(this, "Error al obtener una cancion",
					Toast.LENGTH_LONG).show();
		} finally {
			SqliteManager.closeBD();
		}
	}

	@Override
	protected void onPause() {
		mp.stop();
		finish();
		super.onPause();
	}
}
