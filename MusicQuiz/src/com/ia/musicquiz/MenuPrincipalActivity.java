package com.ia.musicquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MenuPrincipalActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// FullSreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_menu_principal);
		
		Button boton = (Button) findViewById(R.id.btJugar);
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
