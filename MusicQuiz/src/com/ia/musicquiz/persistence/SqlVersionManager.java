package com.ia.musicquiz.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.util.Log;

public class SqlVersionManager {
	
	private SqlDownloader downloader;
	
	public SqlVersionManager() {
		this.downloader = new SqlDownloader();
	}
	
	public boolean isNewVersion() {
		try {
			return !(getOldVersion().equals(getNewVersion()));
		} catch (IOException e) {
			Log.e("SqlVersionManager", "No newer and/or older version find", e);
		}
		//Si no se encuentran los archivos por algun motivo, devuelve true
		// para que se actualice de todas formas, puede ser que no exista ningun
		// archivo en cuyo caso necesita crear la bd
		return true;
	}
	
	private String getNewVersion() throws IOException {
		File file = downloader.downloadFile();
		BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String newVersion = bf.readLine();
		bf.close();
		return newVersion;
	}

	private String getOldVersion() throws IOException {
		File file = this.downloader.getFile();
		BufferedReader bf = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));
		String oldVersion = bf.readLine();
		bf.close();
		return oldVersion;
	}

}
