package com.ia.musicquiz;

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
		
		Button boton = (Button) findViewById(R.id.btUnJugador);
		boton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent lanzarJuego = new Intent(MenuPrincipalActivity.this, GenreListActivity.class);
				startActivity(lanzarJuego);
			}
		});
		
		boton = (Button) findViewById(R.id.btSalir);
		boton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
	}
}
