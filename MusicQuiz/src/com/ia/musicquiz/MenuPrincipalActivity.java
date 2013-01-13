package com.ia.musicquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.ia.musicquiz.persistence.SqliteManager;

public class MenuPrincipalActivity extends ActivityFullScreen {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_menu_principal);
		
		
		ImageButton botonJugar = (ImageButton) findViewById(R.id.btUnJugador);
		botonJugar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent lanzarJuego = new Intent(MenuPrincipalActivity.this, GenreListActivity.class);
				startActivity(lanzarJuego);
			}
		});
		
		ImageButton botonSalir = (ImageButton) findViewById(R.id.btSalir);
		botonSalir.setOnClickListener(new OnClickListener() {
			
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
