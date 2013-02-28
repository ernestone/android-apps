package com.juegos.infantiles.adivinaquees;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class EditarGaleria extends Activity {

	TextView txtNombreGaleria;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editar_galeria);
		
		this.txtNombreGaleria=(TextView) this.findViewById(R.id.txtNombreGaleria);
		
		this.txtNombreGaleria.setText(getIntent().getStringExtra("galeria"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editar_galeria, menu);
		return true;
	}

}
