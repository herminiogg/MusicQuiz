package com.ia.musicquiz.business;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

}
