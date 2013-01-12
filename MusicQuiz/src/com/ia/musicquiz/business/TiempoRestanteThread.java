package com.ia.musicquiz.business;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class TiempoRestanteThread extends Thread {

	private PreguntaSingle preguntaSingle;
	private Handler manejador;
	private boolean isRunning;
	private boolean isPaused;
	private String textoInicial;
	private String textoFinal;

	public TiempoRestanteThread(String textoInicial, TextView tiempoView, String textoFinal) {
		this.manejador = new Manejador(tiempoView);
		this.isRunning = true;
		this.isPaused = true;
		this.textoInicial = textoInicial;
		this.textoFinal = textoFinal;
	}

	@Override
	public void run() {
		while (isRunning) {
			while (!isPaused) {
				Message msg = new Message();
				msg.obj = new String(textoInicial + " " + formatearNumero(preguntaSingle.getTiempoRestante()) + " " + textoFinal);
				manejador.sendMessage(msg);
			}
		}
	}

	public void start(PreguntaSingle preguntaSingle) {
		this.preguntaSingle = preguntaSingle;
		this.isPaused = false;;
	}

	public void pausar() {
		this.isPaused = true;
	}
	
	public void isRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	//Pasa el tiempo a segundos
	private String formatearNumero(int numero) {
		numero = 1 + (numero / 1000);
		if (numero < 10)
			return ("0" + numero);
		else
			return String.valueOf(numero);
	}

	@SuppressLint("HandlerLeak")
	private class Manejador extends Handler {
		TextView textView;

		public Manejador(TextView textView) {
			this.textView = textView;
		}

		public void handleMessage(Message msg) {
			textView.setText((String) msg.obj);
		}
	}

}
