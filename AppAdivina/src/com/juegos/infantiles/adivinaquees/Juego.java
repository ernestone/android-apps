package com.juegos.infantiles.adivinaquees;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class Juego extends Activity implements OnClickListener{

	ArrayList<Imagen> imagenes;
	int idResource;
	TextView txtPalabraClave, img1,img2,img3,img4;
	SQLiteDatabase db;
	CreacionBDSQLite creaBDSL=new CreacionBDSQLite(this.getApplicationContext());;
	Random rand = new Random();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        
        String tipo=getIntent().getStringExtra("Tipo");
        
        this.txtPalabraClave=(TextView) this.findViewById(R.id.txtPalabraClave);
        this.img1=(TextView) this.findViewById(R.id.img1);
        this.img2=(TextView) this.findViewById(R.id.img2);
        this.img3=(TextView) this.findViewById(R.id.img3);
        this.img4=(TextView) this.findViewById(R.id.img4);
        
        Log.i("Aviso", "hola");
        
        db=creaBDSL.getWritableDatabase();
        
        
        Tipo pkTipo=this.getTipo(tipo);
        
        imagenes=this.getImagenes(pkTipo.getPkTipo());
        
        db.close();
        
        for (Imagen imagen : imagenes) {
        	this.idResource=Juego.this.getResources().getIdentifier("drawable/" + imagen.getNombre(), null, Juego.this.getPackageName());
        	imagen.setIDResource(this.idResource);
		}

        //Con esta funci�n, escogemos la imagen para acertar.
        ArrayList<Imagen> imagenesFinales=imagenesAMostrar(4);
        
        imagenCorrecta(imagenesFinales);
        
        
        

        
        this.img1.setBackgroundResource(imagenesFinales.get(0).getIDResource());
        imagenesFinales.get(0).setIDTextViewAsociado(R.id.img1);
        this.img1.setOnClickListener(this);
        this.img2.setBackgroundResource(imagenesFinales.get(1).getIDResource());
        imagenesFinales.get(1).setIDTextViewAsociado(R.id.img2);
        this.img2.setOnClickListener(this);
        this.img3.setBackgroundResource(imagenesFinales.get(2).getIDResource());
        imagenesFinales.get(2).setIDTextViewAsociado(R.id.img3);
        this.img3.setOnClickListener(this);
        this.img4.setBackgroundResource(imagenesFinales.get(3).getIDResource());
        imagenesFinales.get(3).setIDTextViewAsociado(R.id.img4);
        this.img4.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_juego, menu);
        return true;
    }

	public void onClick(View v) {
		for (Imagen img : imagenes) {
			if(v.getId()==img.getIDTextViewAsociado()){
				if(img.isCorrecto()){
					Toast toast = Toast.makeText(this, "Muy bien!! Has acertado!!", Toast.LENGTH_SHORT);
			        toast.show();
			        finish();
				}else{
					Toast toast = Toast.makeText(this, "Mal. Vuelve a intentarlo!!", Toast.LENGTH_SHORT);
			        toast.show();
				}
				break;
			}
		}
	}
	
	public void imagenCorrecta(ArrayList<Imagen> imagenes){
		Log.i("Imagenes finales",String.valueOf(imagenes.size()));
		int index=(this.rand.nextInt(imagenes.size()));
        
        imagenes.get(index).setCorrecto(true);
        this.txtPalabraClave.setText(imagenes.get(index).getNombre());
	}

    public ArrayList<Imagen> imagenesAMostrar(int numImagenes){
    	ArrayList<Imagen> imagenesDefinitivas=new ArrayList<Imagen>();
    	int imagenAMostrar;
    	boolean repetido=false;
    	int imagenesAnhadidas=0;
    	
    	while(imagenesAnhadidas<numImagenes){
    		repetido=false;
    		imagenAMostrar=(this.rand.nextInt(imagenes.size()));
    		if(imagenesDefinitivas.size()!=0){
	    		for(Imagen img: imagenesDefinitivas){
	    			if(img.getIDResource()==imagenes.get(imagenAMostrar).getIDResource()){
	    				repetido=true;
	    				break;
	    			}
	    		}
	    		if(!repetido) {
	    			imagenesDefinitivas.add(imagenes.get(imagenAMostrar));
	    			imagenesAnhadidas++;
	    		}
    		}else{
    			imagenesDefinitivas.add(imagenes.get(imagenAMostrar));
    			imagenesAnhadidas++;
    		}
		}
    	
    	return imagenesDefinitivas;
    }
    
    public Tipo getTipo(String cadenaTipo) {
		Tipo tipo=new Tipo();
		
		String[] cols=new String[] { "pkTipo", "nombre" };
		
		Cursor result = db.query(true, "tipos",cols,"nombre='" + cadenaTipo+"'", null, null, null,null, null);
		
		if ((result.getCount() == 0) || !result.moveToFirst()) {
		//Si la alarma no existe, devuelve una alarma con valores -1 y -1
			//tipo = new tipo(-1,-1);
		} else {
			if (result.moveToFirst()) {
				tipo = new Tipo(
				result.getInt(result.getColumnIndex("pkTipo")),
				result.getString(result.getColumnIndex("nombre")));
			}
		}
		return tipo;
	}
		 
	public ArrayList<Imagen> getImagenes(int fkTipo) {
		ArrayList<Imagen> imagenes = new ArrayList<Imagen>();
		
		String[] cols=new String[] { "nombre" };
		
		Cursor result = db.query("imagenes",cols, "fkTipo=" + fkTipo, null, null, null, null);
		
		if (result.moveToFirst())
			do {
					imagenes.add(new Imagen(
					result.getString(result.getColumnIndex("nombre"))));
			} while(result.moveToNext());
			return imagenes;
	}
}
