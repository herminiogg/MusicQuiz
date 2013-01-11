package com.ia.musicquiz.business;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class IsServerOnlineTask extends AsyncTask<Void, Void, Boolean> {
	private final static String URLPRUEBA = "http://dl.dropbox.com/s/sdwfzu3zootz8ra/bd.sql";
	private final static int TIMEOUT = 5000;

	@Override
	protected Boolean doInBackground(Void... params) {
		HttpURLConnection conn = null;
	     try {
	    	URL url = new URL(URLPRUEBA);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(TIMEOUT);
			conn.connect();
			return conn.getResponseCode() == 200 ? true : false;
		} catch (IOException e) {
			return false;
		} finally {
			conn.disconnect();
		}
	}
}