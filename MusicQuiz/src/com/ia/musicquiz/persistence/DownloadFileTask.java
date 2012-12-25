package com.ia.musicquiz.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.os.AsyncTask;

public class DownloadFileTask extends AsyncTask<Object, Integer, File> {

	@Override
	protected File doInBackground(Object... params) {
		try {
			
			URL url = (URL) params[0];
			String filename = (String) params[1];
			Context context = (Context) params[2];

			FileOutputStream fileOutput = context.openFileOutput(filename, Context.MODE_PRIVATE);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					fileOutput));

			URLConnection conection = url.openConnection();
			InputStream inputStream = conection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));

			String linea = reader.readLine();
			while (linea != null) {
				writer.append(linea + "\n");
				linea = reader.readLine();
			}

			writer.close();
			reader.close();
			
			File files = context.getFilesDir();
			
			File file = new File(files, filename);
			
			return file;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
