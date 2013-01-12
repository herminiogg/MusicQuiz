package com.ia.musicquiz.business;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.ia.musicquiz.PreguntaSingleActivity;

public class InicializarCancionesTask extends AsyncTask<Object, Integer, List<PreguntaSingle>> {
	
	private Context context;
	private String genero;
	private int npreguntas;
	private ProgressDialog pd;
	
	public InicializarCancionesTask(Context context, String genero, int npreguntas, ProgressDialog pd) {
		this.context = context;
		this.genero = genero;
		this.npreguntas = npreguntas;
		this.pd = pd;
	}

	@Override
	protected List<PreguntaSingle> doInBackground(Object... params) {
		List<PreguntaSingle> preguntasSingle = new ArrayList<PreguntaSingle>();
		
		for (int i = 0; i < npreguntas; i++) {
			preguntasSingle.add(new PreguntaSingle(genero, context, (PreguntaSingleActivity)params[0]));
			publishProgress(i+1);
		}
		pd.dismiss();
		return preguntasSingle;		
	}
	
	protected void onProgressUpdate(Integer... progress) {
		pd.setMessage("Se están cargando las canciones. Cargadas: " + progress[0] + " de " + npreguntas);
	}
}
