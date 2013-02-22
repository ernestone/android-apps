package com.juegos.infantiles.adivinaquees;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CreacionBDSQLite extends SQLiteOpenHelper {
	 
   

	//Nombre de la base de datos
    public static final String NOMBREBD = Config.DBLITE_NOMBRE;
    //Versión de la base de datos
    public static final int VERSION = 1;
    //Nombre de la tabla (puede haber tantas como necesitemos)
    public static final String NOMBRE_TABLA1 = "tipos";
    public static final String NOMBRE_TABLA2 = "imagenes";
    //Campo 1
    public static final String ID1 = "pkTipo";
    public static final String ID2 = "pkImagenes";
    //Campo 2 (también puede haber tantos campos como queramos)
    public static final String NOMBRE1 = "nombre";
    public static final String NOMBRE2 = "nombre";
 
    //Constructor
    public CreacionBDSQLite(Context context) {
        //Aquí le pasamos el contexto, el nombre de la base de datos, el cursor que no lo necesitamos y la version de la base de datos.
        super(context, NOMBREBD, null, VERSION);
    }
 
    //Aquí crearemos la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*En la siguiente linea lo que estamos haciendo es crear la base de datos 
         * con una tabla llamada tablaprueba
         * dos campos uno llamado id que almacenará un número entero, que será clave primaria con autoincremento y que no podrá ser null
         * y otro campo llamado nombre que será de tipo texto  
         */
    	//Log.i("Base de datos", "Estoy dentro!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        db.execSQL("create table " + NOMBRE_TABLA1 + "(" + ID1 + " integer primary key autoincrement not null, " + NOMBRE1 + " text);");
        db.execSQL("create table " + NOMBRE_TABLA2 + "(" + ID2 + " integer primary key autoincrement not null, " + NOMBRE2 + " text, fkTipo numeric);");
        db.execSQL("INSERT INTO tipos (nombre) VALUES ('Frutas')");
        db.execSQL("INSERT INTO tipos (nombre) VALUES ('Animales')");
        db.execSQL("INSERT INTO imagenes (nombre,fkTipo) VALUES ('banana',1)");
        db.execSQL("INSERT INTO imagenes (nombre,fkTipo) VALUES ('naranja',1)");
        db.execSQL("INSERT INTO imagenes (nombre,fkTipo) VALUES ('pera',1)");
        db.execSQL("INSERT INTO imagenes (nombre,fkTipo) VALUES ('uva',1)");
        db.execSQL("INSERT INTO imagenes (nombre,fkTipo) VALUES ('conejo',2)");
        db.execSQL("INSERT INTO imagenes (nombre,fkTipo) VALUES ('elefante',2)");
        db.execSQL("INSERT INTO imagenes (nombre,fkTipo) VALUES ('tigre',2)");
        db.execSQL("INSERT INTO imagenes (nombre,fkTipo) VALUES ('perro',2)");
        db.execSQL("INSERT INTO imagenes (nombre,fkTipo) VALUES ('caballo',2)");
        db.execSQL("INSERT INTO imagenes (nombre,fkTipo) VALUES ('leon',2)");
        db.execSQL("INSERT INTO imagenes (nombre,fkTipo) VALUES ('lobo',2)");
        db.execSQL("INSERT INTO imagenes (nombre,fkTipo) VALUES ('gato',2)");
        
        /*String[] campos=new String[]{"pkTipo,nombre"};
        String[] args=new String[]{""};
        int frutas;
        int animales;
        
        Cursor c=db.query("tipos",campos,"",args,null,null,null,null);
        
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
            	if(c.getColumnName(1)=="Frutas"){
            		frutas=c.getInt(0);
            	}else{
            		animales=c.getInt(0);
            	}
            } while(c.moveToNext());
       }*/
        
        
        
        
        
        //dentro de los paréntesis también podría ir perfectamente "create table tablaprueba(id integer primary key autoincrement not null, nombre text);" esto y lo anterior es lo mismo
    }
 
    //Aquí se actualizará la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {    
    }

    private void creacionCamposConRegistros(){
    	HashMap<String, String[]> camposRegistros=Config.camposConRegistros();
    	
    	
    }
	

	
}