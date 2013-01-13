package com.ia.musicquiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ia.musicquiz.business.NetworkChecker;
import com.ia.musicquiz.persistence.SqliteManager;

public class BootActivity extends ActivityFinishedOnPause {

	private Toast actualizacion;

	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_boot);
		actualizacion = Toast.makeText(BootActivity.this,
				this.getResources().getText(R.string.aviso_actualizacion), Toast.LENGTH_SHORT);
		iniciarJuego();
	}

	public void iniciarJuego() {
		if (checkConnection()) {
			Thread splashThread = new Thread() {
				@Override
				public void run() {
					try {
						if (SqliteManager.initBD(BootActivity.this))
							actualizacion.show();
						sleep(3000);
					} catch (InterruptedException e) {
					} finally {
						Intent ventanaInicial = new Intent(BootActivity.this,
								MenuPrincipalActivity.class);
						startActivity(ventanaInicial);
					}
				}
			};
			splashThread.start();
		}
	}

	private boolean checkConnection() {
		NetworkChecker nt = new NetworkChecker(this);
		boolean conexion = nt.isNetWorkActive();
		boolean servidor = nt.isServerOnline();
		if (!conexion) {
			Toast.makeText(this,
					this.getResources().getText(R.string.no_conexion),
					Toast.LENGTH_LONG).show();
			finish();
		} else if (!servidor) {
			Toast.makeText(this,
					this.getResources().getText(R.string.no_servidor),
					Toast.LENGTH_LONG).show();
			finish();
		}
		return conexion && servidor;
	}

}
