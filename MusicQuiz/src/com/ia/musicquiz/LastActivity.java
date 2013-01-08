package com.ia.musicquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LastActivity extends ActivityFullScreen {
	
	private int puntuacion;
	private Button btCompartir;
	private Button btVolver;
	private TextView textPuntuacion;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_last);
		
		puntuacion = getIntent().getExtras().getInt("puntuacion");
		btCompartir = (Button) this.findViewById(R.id.btCompartir);
		btVolver = (Button) this.findViewById(R.id.btVolverMenu);
		textPuntuacion = (TextView) this.findViewById(R.id.textPuntuacion);
		
		setPuntuacionText();
		loadButtonListeners();
		
	}

	private void setPuntuacionText() {
		textPuntuacion.setText(getResources().getText(R.string.puntuacion_obtenida).toString()+" "+puntuacion);
	}

	private void loadButtonListeners() {
		btCompartir.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StringBuilder resultado = new StringBuilder();
				resultado.append(getResources().getText(R.string.antes_compartir));
				resultado.append(" ");
				resultado.append(puntuacion);
				resultado.append(" ");
				resultado.append(getResources().getText(R.string.despues_compartir));
				
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT, resultado.toString());
				startActivity(Intent.createChooser(intent,(String) getResources().getText(R.string.compartir_en)));				
			}
		});
		
		btVolver.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
