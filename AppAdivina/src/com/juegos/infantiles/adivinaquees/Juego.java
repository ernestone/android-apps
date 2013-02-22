package com.juegos.infantiles.adivinaquees;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import dani.funciones.Basicas;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class Juego extends Activity implements OnClickListener,TextToSpeech.OnInitListener{

	ArrayList<Imagen> imagenes;
	int idResource;
	TextView txtPalabraClave, img1,img2,img3,img4;
	SQLiteDatabase db;
	CreacionBDSQLite creaBDSL;
	Random rand = new Random();
	private TextToSpeech mTts;
    // This code can be any value you want, its just a checksum.
    private static final int MY_DATA_CHECK_CODE = 1234;
    Context contexto=this;
    TextToSpeech.OnInitListener listener=this;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.juego);
        
        String tipo=getIntent().getStringExtra("Tipo");
        
        Log.i("Tipo", tipo);
        
        this.txtPalabraClave=(TextView) this.findViewById(R.id.txtPalabraClave);
        this.img1=(TextView) this.findViewById(R.id.img1);
        this.img2=(TextView) this.findViewById(R.id.img2);
        this.img3=(TextView) this.findViewById(R.id.img3);
        this.img4=(TextView) this.findViewById(R.id.img4);
        
        
        Log.i("Aviso", "hola");
        
        
        creaBDSL=new CreacionBDSQLite(this.getApplicationContext());
        
        
        db=creaBDSL.getWritableDatabase();
        
        
        Tipo pkTipo=this.getTipo(tipo);
        
        imagenes=this.getImagenes(pkTipo.getPkTipo());
        
        db.close();
        
        for (Imagen imagen : imagenes) {
        	this.idResource=Juego.this.getResources().getIdentifier("drawable/" + imagen.getNombre(), null, Juego.this.getPackageName());
        	imagen.setIDResource(this.idResource);
		}
        
        //instanciamos el arrayList con los números escogidos de toda la lista de imagenes
        ArrayList<Integer> ordenImagenes=Basicas.escoger(imagenes.size(),4);
        
        //y luego, lo desordenamos
        ordenImagenes=Basicas.desordenar(ordenImagenes);
        
        //despues, creamos el arrayList que contendrán las imagenes escogidas y en el orden final
        ArrayList<Imagen> imagenesFinales=new ArrayList<Imagen>();
        
        //y le añadimos las imagenes utilizando el arrayList de los numeros de imagen con el orden
        for (int num : ordenImagenes) {
			imagenesFinales.add(imagenes.get(num));
		}
        
        
        //ArrayList<Imagen> imagenesFinales=imagenesAMostrar(4);
       
        //Con esta funci�n, escogemos la imagen para acertar.
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
        
        
        TimerTask timerTask = new TimerTask() 
        { 
            public void run()  
            { 
            	mTts = new TextToSpeech(contexto, listener); 
            } 
        }; 

         // Aquí se pone en marcha el timer cada segundo. 
        Timer timer = new Timer(); 
        // Dentro de 0 milisegundos avísame cada 1000 milisegundos 
        timer.scheduleAtFixedRate(timerTask, 1500, 5000);
        
        
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

	public void onInit(int status) {
		//Log.i("Lengua del móvil",mTts.getLanguage().toString());
		//mTts.setPitch((float) 0.6);
		mTts.setSpeechRate((float) 0.5);
		mTts.speak(txtPalabraClave.getText().toString(),
                TextToSpeech.QUEUE_FLUSH,  // Drop all pending entries in the playback queue.
                null);
		
	}
}
