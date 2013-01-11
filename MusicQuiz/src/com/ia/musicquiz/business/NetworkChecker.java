package com.ia.musicquiz.business;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

public class NetworkChecker {
	
	private Context context;
	
	public NetworkChecker(Context context) {
		this.context=context;
	}

	public boolean isNetWorkActive() {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork!=null;
	}
	
	public boolean isServerOnline() {
		AsyncTask<Void, Void, Boolean> isOnline = new IsServerOnlineTask().execute();
		try {
			return isOnline.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
