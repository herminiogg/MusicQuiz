package com.ia.musicquiz;

import java.util.List;
import java.util.Random;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ia.musicquiz.business.Jugador;
import com.ia.musicquiz.business.PreguntaSingle;
import com.ia.musicquiz.business.TextoBoton;
import com.ia.musicquiz.business.TextoBotonArtista;
import com.ia.musicquiz.business.TextoBotonBandaSonora;
import com.ia.musicquiz.business.TextoBotonCancion;
import com.ia.musicquiz.persistence.dao.Song;

public class PreguntaSingleActivity extends ActivityFinishedOnPause implements
		OnClickListener, OnCompletionListener {

	private TextView tiempo;
	private TextView puntuacion;
	private TextView pregunta;
	private TextView textPregunta;
	private String genero;
	private AsyncTask<Void, Integer, Void> timer;
	private PreguntaSingle preguntaSingle;
	private List<Song> canciones;
	private Button[] botones;
	private Jugador jugador;
	private TextoBoton textoBoton;
	private int preguntaActual;
	private int npreguntas;
	private boolean acierto = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_pregunta_single);
		Typeface face = Typeface.createFromAsset(getAssets(),
	            "fonts/font.ttf");
	
		tiempo = (TextView) this.findViewById(R.id.textTiempo);
		tiempo.setTypeface(face);
		puntuacion = (TextView) this.findViewById(R.id.textPuntuacion);
		puntuacion.setTypeface(face);
		pregunta = (TextView) this.findViewById(R.id.textPregunta);
		textPregunta = (TextView) this.findViewById(R.id.textPreguntaActual);
        ImageButton img = new ImageButton(this);
		botones = new Button[4];
		botones[0] = (Button) findViewById(R.id.btOpcion1);
		botones[1] = (Button) findViewById(R.id.btOpcion2);
		botones[2] = (Button) findViewById(R.id.btOpcion3);
		botones[3] = (Button) findViewById(R.id.btOpcion4);
		iniciarJuego();
	}


	private void iniciarJuego() {
		randomizeSongTexts();
		genero = getIntent().getExtras().getString("genero");
		npreguntas = getIntent().getExtras().getInt("npreguntas");
		preguntaActual = getIntent().getExtras().getInt("preguntaActual");
		jugador = (Jugador) getIntent().getExtras().getSerializable("jugador");
		postPuntuacionToUI();
		postCurrentQuestionToUI();
		preguntaSingle = new PreguntaSingle(genero, this, this);
		canciones = preguntaSingle.getCanciones();
		asignarCancionesABotones(canciones);
		startMediaPlayer();
	}

	private void startMediaPlayer() {
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setTitle("Cargando...");
		pd.setMessage("Cargando canción...");
		pd.setCancelable(false);
		pd.show();
		new Thread() {
			public void run() {
				preguntaSingle.startPlayer();
				iniciarHiloTiempoRestante();
				pd.dismiss();	
			}
		}.start();
	}

	private void randomizeSongTexts() {
		if(genero != "Bandas Sonoras"){
			TextoBoton tb =  new TextoBotonBandaSonora(this);
			pregunta.setText(tb.getQuestionText());
			pregunta.setTextSize(25);
			textoBoton = tb;
		}
		else{
			TextoBoton[] tb = { new TextoBotonArtista(this),
					new TextoBotonCancion(this) };
			TextoBoton escogido = tb[new Random().nextInt(2)];
			pregunta.setText(escogido.getQuestionText());
			textoBoton = escogido;
		}
		
	}

	private void postPuntuacionToUI() {
		/*StringBuilder sb = new StringBuilder();
		sb.append(getResources().getText(R.string.puntuacion));
		sb.append(" ");
		sb.append(String.valueOf(jugador.getPuntuacion()));
		puntuacion.setText(sb.toString());*/
		puntuacion.setText(String.valueOf(jugador.getPuntuacion()));
	}

	private void asignarCancionesABotones(List<Song> canciones) {
		for (int i = 0; i < botones.length; i++) {
			botones[i].setText(textoBoton.getTexto(canciones, i));
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
		for (int i = 0; i < botones.length; i++) {
			if (botones[i].equals(source)) {
				if (preguntaSingle.isCorrectSong(canciones.get(i))) {
					jugador.addPreguntaAcertada(preguntaSingle
							.getTiempoRestante() / 1000);
					postPuntuacionToUI();
					botones[i].setBackgroundResource(R.drawable.botonacierto);
					acierto = true;
					nextSong();
				} else {
					jugador.addPreguntaFallada(preguntaSingle
							.getTiempoRestante() / 1000);
					postPuntuacionToUI();
					botones[i].setBackgroundResource(R.drawable.botonerror);

					
					Toast toast = Toast.makeText(getApplicationContext(),
							   "¡Oops! Vuelve a intentarlo", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							LinearLayout toastView = (LinearLayout) toast.getView();
							ImageView imageCodeProject = new ImageView(getApplicationContext());
							imageCodeProject.setImageResource(R.drawable.errorimage);
							toastView.addView(imageCodeProject, 0);
							toast.show();
				}
			}
		}
	}

	private void nextSong() {
		if (!acierto)
			botones[preguntaSingle.getIndexCancionCorrecta()].setBackgroundColor(Color.GREEN);
		postPuntuacionToUI();
		preguntaSingle.stopPlayer();
		timer.cancel(true);
		if(!tryToFinish()) {
			Intent i = new Intent(PreguntaSingleActivity.this, PreguntaSingleActivity.class);
			i.putExtra("jugador", jugador);
			i.putExtra("genero", genero);
			i.putExtra("npreguntas", npreguntas);
			i.putExtra("preguntaActual", preguntaActual); 
			startActivity(i);
		}
	}

	private boolean tryToFinish() {
		if (preguntaActual >= npreguntas) {
			Intent i = new Intent(PreguntaSingleActivity.this,
					LastActivity.class);
			i.putExtra("puntuacion", jugador.getPuntuacion());
			startActivity(i);
			return true;
		}
		preguntaActual++;
		return false;
	}

	private void postCurrentQuestionToUI() {
		StringBuilder sb = new StringBuilder();
		sb.append(getResources().getText(R.string.npregunta));
		sb.append(" ");
		sb.append(preguntaActual);
		sb.append(" ");
		sb.append(getResources().getText(R.string.de));
		sb.append(" ");
		sb.append(npreguntas);
		textPregunta.setText(sb.toString());
	}

	private void iniciarHiloTiempoRestante() {
		timer = new TiempoRestanteTask();
		timer.execute(null, null);
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		nextSong();
	}
	
	public class TiempoRestanteTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				while(preguntaSingle.getTiempoRestante()>0) {
					publishProgress(preguntaSingle.getTiempoRestante());
					Thread.sleep(1000);
				}

			} catch (InterruptedException e) {

			}
			return null;
		}

		protected void onProgressUpdate(Integer... progress) {
			tiempo.setText(formatearNumero(progress[0]/1000)+getResources().getText(R.string.segundos).toString());
	    }
		
		private String formatearNumero(int numero) {
			if (numero < 10)
				return ("0" + numero);
			else
				return String.valueOf(numero);
		}
	}

}
