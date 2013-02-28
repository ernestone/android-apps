package com.juegos.infantiles.adivinaquees;

import android.os.Bundle;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CrearNueva extends Activity implements OnClickListener{

	EditText edtNueva;
	Button btnCrear;
	SQLiteDatabase db;
	CreacionBDSQLite creaBDSL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crear_nueva);
		
		this.edtNueva=(EditText) this.findViewById(R.id.edtNombre);
		
		this.btnCrear=(Button) this.findViewById(R.id.btnCrear);
		this.btnCrear.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_crear_nueva, menu);
		return true;
		
	}

	public void onClick(View v) {
		creaBDSL=new CreacionBDSQLite(this.getApplicationContext());
        db=creaBDSL.getWritableDatabase();
        
        db.execSQL("INSERT INTO tipos_personalizados (nombre) VALUES ('"+edtNueva.getText().toString()+"')");
        
        Toast toast = Toast.makeText(this, "Galería creada satisfactoriamente", Toast.LENGTH_SHORT);
        toast.show();
		
	}

}
