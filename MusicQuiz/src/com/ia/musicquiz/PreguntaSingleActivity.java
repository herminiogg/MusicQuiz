package com.ia.musicquiz;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ia.musicquiz.business.Jugador;
import com.ia.musicquiz.business.PreguntaSingle;
import com.ia.musicquiz.persistence.dao.Song;

public class PreguntaSingleActivity extends ActivityFinishedOnPause implements OnClickListener, OnCompletionListener {

	private TextView tiempo;
	private TextView puntuacion;
	private Timer timer;
	private String genero;
	private PreguntaSingle preguntaSingle;
	private List<Song> canciones;
	private Button[] botones;
	private Jugador jugador;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_pregunta_single);
		
		tiempo = (TextView) this.findViewById(R.id.textTiempo);
		puntuacion = (TextView) this.findViewById(R.id.textPuntuacion);
		
		botones = new Button[4];
		botones[0] = (Button) findViewById(R.id.btOpcion1);
		botones[1] = (Button) findViewById(R.id.btOpcion2);
		botones[2] = (Button) findViewById(R.id.btOpcion3);
		botones[3] = (Button) findViewById(R.id.btOpcion4);

		iniciarJuego();

	}

	private void iniciarJuego() {
		genero = getIntent().getExtras().getString("genero");
		jugador = new Jugador();
		postPuntuacionToUI();
		preguntaSingle = new PreguntaSingle(genero, this, this);
		canciones = preguntaSingle.getCanciones();
		asignarCancionesABotones(canciones);
		preguntaSingle.startPlayer();
		iniciarHiloTiempoRestante();
	}

	private void postPuntuacionToUI() {
		StringBuilder sb = new StringBuilder();
		sb.append(getResources().getText(R.string.puntuacion));
		sb.append(" ");
		sb.append(String.valueOf(jugador.getPuntuacion()));
		puntuacion.setText(sb.toString());
	}

	private void asignarCancionesABotones(List<Song> canciones) {
		for(int i=0;i<botones.length;i++) {
			botones[i].setText(canciones.get(i).getArtista());
			botones[i].setOnClickListener(this);			
		}
	}
	
	@Override
	protected void onStop() {
		preguntaSingle.stopPlayer();
		super.onStop();
	}

	@Override
	public void onClick(View v) {
		Button source = (Button) v;
		for(int i=0; i<botones.length;i++) {
			if(botones[i].equals(source)) {
				if (preguntaSingle.isCorrectSong(canciones.get(i))) {
					jugador.addPreguntaAcertada(preguntaSingle.getTiempoRestante()/1000);
					postPuntuacionToUI();
					Toast.makeText(this, "Bien, has acertado", Toast.LENGTH_SHORT)
					.show();
					reiniciarInterfaz();
				} else {
					jugador.addPreguntaFallada(preguntaSingle.getTiempoRestante()/1000);
					postPuntuacionToUI();
					Toast.makeText(this, "Lo siento, te has confundido",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	private void reiniciarInterfaz() {
		postPuntuacionToUI();
		preguntaSingle.stopPlayer();
		timer.cancel();
		preguntaSingle = new PreguntaSingle(genero, this, this);
		canciones = preguntaSingle.getCanciones();
		asignarCancionesABotones(canciones);
		preguntaSingle.startPlayer();
		iniciarHiloTiempoRestante();
	}
	
	private void iniciarHiloTiempoRestante() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				final int tiempoRestante = preguntaSingle.getTiempoRestante();
				 PreguntaSingleActivity.this.runOnUiThread(new Runnable() {@Override public void run()
				 {
					 tiempo.setText(getResources().getText(R.string.tiempo_restante).toString()+ " " +
		    					tiempoRestante/1000+getResources().getText(R.string.segundos).toString());
				 }});
    			
			}
		}, 1000, 1000);
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		reiniciarInterfaz();
	}
	
	
}

