package com.ia.musicquiz;

import com.ia.musicquiz.business.AlmacenPuntuaciones;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LastActivity extends ActivityFullScreen {
	
	private int puntuacion;
	private Button btCompartir;
	private Button btVolver;
	private TextView textPuntuacion;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_last);
		
		Typeface face = Typeface.createFromAsset(getAssets(),
	            "fonts/font.ttf");
		
		puntuacion = getIntent().getExtras().getInt("puntuacion");
		btCompartir = (Button) this.findViewById(R.id.btCompartir);
		btVolver = (Button) this.findViewById(R.id.btVolverMenu);
		textPuntuacion = (TextView) this.findViewById(R.id.textPuntuacion);
		textPuntuacion.setTypeface(face);
		textPuntuacion.setText(String.valueOf(puntuacion));
		mostrarDialogoNombre();
		loadButtonListeners();
	}
	
	private void mostrarDialogoNombre() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Nombre"); 
		alert.setMessage("Introduce tu nombre"); 
		final EditText input = new EditText(this); 
		alert.setView(input); 
		alert.setPositiveButton("Aceptar", new  DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
		    	guardarPreferencias(input.getText().toString());
		    }
		});
		alert.show();
	}

	private void guardarPreferencias(String nombre) {
		AlmacenPuntuaciones.getInstance(this).guardarPuntuacion(nombre, puntuacion);
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
		
		Button botonPuntuaciones = (Button) findViewById(R.id.btPuntuaciones);
		botonPuntuaciones.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent lanzarJuego = new Intent(LastActivity.this, PuntuacionesActivity.class);
				startActivity(lanzarJuego);			
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
