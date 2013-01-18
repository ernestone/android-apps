package com.juegos.infantiles.adivinaquees;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.support.v4.app.NavUtils;

public class Principal extends Activity implements OnClickListener{

	Button jugar;
	Intent ventana=new Intent();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        
        this.jugar=(Button)this.findViewById(R.id.btnJugar);
        this.jugar.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_principal, menu);
        return true;
    }

	public void onClick(View v) {
		this.ventana.setClass(getApplicationContext(), Inicio.class);
		startActivity(this.ventana);
		
	}

    
}
