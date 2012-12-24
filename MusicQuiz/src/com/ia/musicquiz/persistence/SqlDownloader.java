package com.ia.musicquiz.persistence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;

public class SqlDownloader {

	private final static String URL = "http://petra.euitio.uniovi.es/~i1679771/musicquiz/bd.sql";
	private final static String FILENAME = "bd.sql";

	private Context context;

	public SqlDownloader(Context context) {
		this.context = context;
	}

	protected File downloadFile() {
		try {
			// primero especificaremos el origen de nuestro archivo a descargar
			// utilizando
			// la ruta completa
			URL url = new URL(URL);

			// establecemos la conexión con el destino
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();

			// establecemos el método jet para nuestra conexión
			// el método setdooutput es necesario para este tipo de conexiones
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);

			// por último establecemos nuestra conexión y cruzamos los dedos
			// <img
			// src="http://www.insdout.com/wp-includes/images/smilies/icon_razz.gif"
			// alt=":P" class="wp-smiley">
			urlConnection.connect();


			// utilizaremos un objeto del tipo fileoutputstream
			// para escribir el archivo que descargamos en el nuevo dentro de la
			// memoria interna de forma privada
			FileOutputStream fileOutput = context.openFileOutput(FILENAME,
					Context.MODE_PRIVATE);

			// leemos los datos desde la url
			InputStream inputStream = urlConnection.getInputStream();

			// obtendremos el tamaño del archivo y lo asociaremos a una
			// variable de tipo entero
			// int totalSize = urlConnection.getContentLength();
			// int downloadedSize = 0;

			// creamos un buffer y una variable para ir almacenando el
			// tamaño temporal de este
			byte[] buffer = new byte[1024];
			int bufferLength = 0;

			// ahora iremos recorriendo el buffer para escribir el archivo de
			// destino
			// siempre teniendo constancia de la cantidad descargada y el total
			// del tamaño
			// con esto podremos crear una barra de progreso
			while ((bufferLength = inputStream.read(buffer)) > 0) {

				fileOutput.write(buffer, 0, bufferLength);
				// downloadedSize += bufferLength;
				// descargado

			}
			// cerramos
			fileOutput.close();

			// devolvemos el fichero bajado
			return new File(context.getFilesDir(), FILENAME);
			// y gestionamos errores
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected File getFile() {
		return new File(context.getFilesDir(), FILENAME);
	}

}
