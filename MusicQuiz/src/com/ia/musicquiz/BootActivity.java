package com.ia.musicquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ia.musicquiz.business.NetworkChecker;
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
		iniciarJuego();
	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}

	public void iniciarJuego() {
		if(checkConnection()) {
			SqliteManager.initBD(this);
			Thread splashThread = new Thread() {
				@Override
				public void run() {
					try {
						/*int waited = 0;
						while (waited < 3000) {
							sleep(100);
							waited += 100;
						}*/
						sleep(3000);
					} catch (InterruptedException e) {
					} finally {
						Intent ventanaInicial = new Intent(BootActivity.this, MenuPrincipalActivity.class);
						startActivity(ventanaInicial);
					}
				}
			};
			splashThread.start();
			
		}
	}
	
	private boolean checkConnection() {
		boolean estado = new NetworkChecker(this).isNetWorkActive();
		if(!estado) {
			Toast.makeText(this,
					this.getResources().getText(R.string.no_conexion),
					Toast.LENGTH_LONG).show();
			finish();
		} return estado;
	}

}
