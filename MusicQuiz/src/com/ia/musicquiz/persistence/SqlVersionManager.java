package com.ia.musicquiz.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SqlVersionManager {
	
	private SqlDownloader downloader;
	
	public SqlVersionManager() {
		this.downloader = new SqlDownloader();
	}
	
	public boolean isNewVersion() {
		if(getOldVersion()==null) {
			getNewVersion();
			return true;
		}
		return !(getOldVersion().equals(getNewVersion()));
	}
	
	private String getNewVersion() {
		try {
			File file = downloader.downloadFile();
			return getVersionHeader(file);
		} catch(IOException e) {
			return null;
		}
	}


	private String getOldVersion() {
		try {
			File file = this.downloader.getFile();
			return getVersionHeader(file);	
		} catch(IOException e) {
			return null;
		}
	}

	private String getVersionHeader(File file) throws FileNotFoundException,
	IOException {
		SqlIterator si = new SqlIterator(file);
		String newVersion = si.next();
		si.close();
		return newVersion;
	}
}
