package com.ia.musicquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.ia.musicquiz.persistence.SqliteManager;

public class BootActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// FullSreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_boot);
	}

	// Lanza el metodo iniciarJuego cuando ya es visible la actividad.
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (hasFocus) {
			setVisible(true);
			iniciarJuego();
		}
	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}

	public void iniciarJuego() {
		SqliteManager.initBD(this);
		for (int i = 0; i < 100000; i++)
			Log.v("msg", "test");
		Intent ventanaInicial = new Intent(this, MainActivity.class);
		startActivity(ventanaInicial);
	}

}
