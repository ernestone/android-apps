package com.juegos.infantiles.adivinaquees;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;;

public class GaleriaPersonalizada extends Activity implements OnClickListener, OnItemClickListener {

	String galeriaSeleccionada;
	ListView lstGalerias;
	TextView txtAvisoGalNoCreada;
	Button btnNueva, btnEditar;
	Intent ventana=new Intent();
	SQLiteDatabase db;
	CreacionBDSQLite creaBDSL;
	ArrayList<String> lista=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.galeria_personalizada);
		
		this.btnNueva=(Button) this.findViewById(R.id.btnNueva);
		this.btnNueva.setOnClickListener(this);
		
		this.btnEditar=(Button) this.findViewById(R.id.editar);
		this.btnEditar.setOnClickListener(this);
		this.btnEditar.setEnabled(false);
		
		this.txtAvisoGalNoCreada=(TextView) this.findViewById(R.id.txtAvisoNoGalerias);
		this.txtAvisoGalNoCreada.setVisibility(View.GONE);
		
		this.lstGalerias=(ListView) this.findViewById(R.id.lstGalerias);
		this.lstGalerias.setVisibility(View.GONE);
		
		String[] cols=new String[] { "pkTipo", "nombre" };
		
		creaBDSL=new CreacionBDSQLite(this.getApplicationContext());
        db=creaBDSL.getWritableDatabase();
		
		Cursor result=db.query("tipos_personalizados", cols, null, null, null, null,null, null);
		
		if ((result.getCount() == 0)){
			this.txtAvisoGalNoCreada.setVisibility(View.VISIBLE);
			this.lstGalerias.setVisibility(View.GONE);
		}else{
			this.txtAvisoGalNoCreada.setVisibility(View.GONE);
			this.lstGalerias.setVisibility(View.VISIBLE);
			
			while(result.moveToNext()){
				lista.add(result.getString(result.getColumnIndex("nombre")));
			}
			this.lstGalerias.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lista));
			this.lstGalerias.setOnItemClickListener(this);
			
		}
		
		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.galeria_personalizada, menu);
		return true;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnNueva:
			this.ventana.setClass(getApplicationContext(), CrearNueva.class);
			startActivity(this.ventana);
			break;
		
		case R.id.editar:
			this.ventana.setClass(getApplicationContext(), EditarGaleria.class);
			this.ventana.putExtra("galeria", galeriaSeleccionada);
			startActivity(this.ventana);	

		default:
			break;
		}
		
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		this.btnEditar.setEnabled(true);
		
		galeriaSeleccionada=lista.get(arg2);
	}
	
	

}
