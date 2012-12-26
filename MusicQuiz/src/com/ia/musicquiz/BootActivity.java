package com.ia.musicquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
		if (hasFocus)
			iniciarJuego();
	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}

	public void iniciarJuego() {
		SqliteManager.initBD(this);
	     Thread splashThread = new Thread() {
	         @Override
	         public void run() {
	            try {
	               int waited = 0;
	               while (waited < 3000) {
	                  sleep(100);
	                  waited += 100;
	               }
	            } catch (InterruptedException e) {
	               ;// do nothing
	            } finally {
	               finish();
	               Intent ventanaInicial = new Intent(BootActivity.this, MenuPrincipalActivity.class);
	               startActivity(ventanaInicial);
	               
	            }
	         }
	      };
	      splashThread.start();
	}

}
