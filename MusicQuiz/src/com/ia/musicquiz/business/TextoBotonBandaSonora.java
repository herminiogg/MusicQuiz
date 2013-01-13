package com.ia.musicquiz.business;

import java.util.List;

import android.content.Context;

import com.ia.musicquiz.R;
import com.ia.musicquiz.persistence.dao.Song;

public class TextoBotonBandaSonora implements TextoBoton {
	
	private Context context;
	
	public TextoBotonBandaSonora(Context context) {
		this.context = context;
	}

	@Override
	public String getTexto(List<Song> canciones, int i) {
		return canciones.get(i).getTitulo();
	}
	
	public String getQuestionText() {
		return context.getResources().getText(R.string.textoBandaSonora).toString();
	}
}
