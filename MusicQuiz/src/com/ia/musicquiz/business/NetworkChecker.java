package com.ia.musicquiz.business;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChecker {
	
	private Context context;
	private final static String URLPRUEBA = "http://dl.dropbox.com/s/sdwfzu3zootz8ra/bd.sql"; 
	
	public NetworkChecker(Context context) {
		this.context=context;
	}

	public boolean isNetWorkActive() {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork!=null;
	}
	
	public boolean isServerOnline() {
		HttpURLConnection conn = null;
	     try {
	    	URL url = new URL(URLPRUEBA);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(2000);
			conn.connect();
			return conn.getResponseCode() == 200 ? true : false;
		} catch (IOException e) {
			return false;
		} finally {
			conn.disconnect();
		}
	}

}
