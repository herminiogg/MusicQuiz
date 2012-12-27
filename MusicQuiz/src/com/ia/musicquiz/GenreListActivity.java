package com.ia.musicquiz;

import java.util.List;

import com.ia.musicquiz.business.DatabaseService;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class GenreListActivity extends ListActivity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_genre_list);
	
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, cargarGenerosBD());
		
		setListAdapter(adapter);
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String seleccionado = (String) getListView().getItemAtPosition(position);
		Intent i = new Intent(GenreListActivity.this, PreguntaSingleActivity.class);
		i.putExtra("genero", seleccionado);
		startActivity(i);
	}
	
	
	private List<String> cargarGenerosBD() {
		try {
			return new DatabaseService().getGenresList();
		} catch (Exception e) {
			Toast.makeText(this, this.getResources().
					getString(R.string.errorbd), Toast.LENGTH_LONG).show();
			finish();
		}
		return null;
	}

}
