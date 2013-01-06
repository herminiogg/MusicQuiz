package com.ia.musicquiz.business;

import java.util.List;

import com.ia.musicquiz.R;
import android.content.Context;

import com.ia.musicquiz.persistence.dao.Song;

public class TextoBotonArtista implements TextoBoton {
	
	private Context context;
	
	public TextoBotonArtista(Context context) {
		this.context = context;
	}

	@Override
	public String getTexto(List<Song> canciones, int i) {
		return canciones.get(i).getArtista();
	}
	
	public String getQuestionText() {
		return context.getResources().getText(R.string.pregunta_artista).toString();
	}

}
