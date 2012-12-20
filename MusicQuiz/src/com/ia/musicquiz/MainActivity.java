package com.ia.musicquiz;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.ia.musicquiz.persistence.SqliteManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView tx = (TextView) findViewById(R.id.texto);
		try {
			SqliteManager.initBD(this);
			SQLiteDatabase bd = SqliteManager.getDatabase();
			Cursor c = bd.rawQuery("SELECT titulo,uri FROM cancion", null);

			if (c.moveToFirst()) {
				do {
					String titulo = c.getString(0);
					String uri = c.getString(1);
					tx.setText(tx.getText().toString() + "\n" + titulo + "\n" + uri);
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
