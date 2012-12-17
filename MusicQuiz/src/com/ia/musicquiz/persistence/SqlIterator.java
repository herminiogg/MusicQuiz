package com.ia.musicquiz.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import android.util.Log;

public class SqlIterator implements Iterator<String> {
	
	private BufferedReader bf;
	
	public SqlIterator(File file) throws FileNotFoundException {
		this.bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	}
	

	@Override
	public boolean hasNext() {
		try {
			return bf.ready();
		} catch (IOException e) {
			Log.e("SqlIterator", "Error en el hashNext");
		}
		return false;
	}

	@Override
	public String next() {
		try {
			return bf.readLine();
		} catch (IOException e) {
			Log.e("SqlIterator", "Error en el next");
		}
		return null;
	}

	@Override
	public void remove() {
		; //opcional 
	}
	
	public void close() throws IOException {
		bf.close();
	}
}
