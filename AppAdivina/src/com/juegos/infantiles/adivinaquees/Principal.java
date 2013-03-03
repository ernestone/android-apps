package com.juegos.infantiles.adivinaquees;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Principal extends Activity implements OnClickListener{

	Button jugar, crear;
	Intent ventana=new Intent();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        
        this.jugar=(Button)this.findViewById(R.id.btnJugar);
        this.jugar.setOnClickListener(this);
        
        this.crear=(Button)this.findViewById(R.id.btnCrearNueva);
        this.crear.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_principal, menu);
        return true;
    }

	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btnJugar:
			this.ventana.setClass(getApplicationContext(), Inicio.class);
			startActivity(this.ventana);
			break;
		
		case R.id.btnCrearNueva:
			this.ventana.setClass(getApplicationContext(), GaleriaPersonalizada.class);
			startActivity(this.ventana);	

		default:
			break;
		}
	}

    
}
