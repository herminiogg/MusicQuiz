package com.ia.musicquiz;

import com.ia.musicquiz.persistence.SqliteManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuPrincipalActivity extends ActivityFullScreen {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_menu_principal);
		
		Button botonUnJugador = (Button) findViewById(R.id.btUnJugador);
		botonUnJugador.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent lanzarJuego = new Intent(MenuPrincipalActivity.this, GenreListActivity.class);
				startActivity(lanzarJuego);
			}
		});
		
		
		Button botonVariosJugadores = (Button) findViewById(R.id.btVariosJugadores);
		
		botonVariosJugadores.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent lanzarJuego = new Intent(MenuPrincipalActivity.this, SeleccionJugadores.class);
				startActivity(lanzarJuego);
				
			}
		});

		botonUnJugador = (Button) findViewById(R.id.btSalir);
		botonUnJugador.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cerrarBD();
				finish();				
			}
		});
		
		
	}
	
	public void onBackPressed() {
		cerrarBD();
		super.onBackPressed();
	}
	
	private void cerrarBD() {
		SqliteManager.closeBD();
	}
}
