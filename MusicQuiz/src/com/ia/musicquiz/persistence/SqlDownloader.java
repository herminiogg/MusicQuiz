package com.ia.musicquiz.persistence;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.os.AsyncTask;

public class SqlDownloader {
	
	private final static String URL = "http://musicquiz.tk/song/bd.sql";
	private final static String FILENAME = "bd.sql";

	private Context context;

	public SqlDownloader(Context context) {
		this.context = context;
	}
	
	protected File downloadFile()  {
		try {
			URL url = new URL(URL);
			AsyncTask<Object, Integer, File> file = new DownloadFileTask().execute(url, FILENAME, context);
			return file.get();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	protected File getFile() {
		return new File(context.getFilesDir(), FILENAME);
	}

}

