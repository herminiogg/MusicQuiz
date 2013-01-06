package com.ia.musicquiz.business;

import java.util.List;

import com.ia.musicquiz.persistence.dao.Song;

public interface TextoBoton {
	
	String getTexto(List<Song> canciones, int index); 
	String getQuestionText();
}
