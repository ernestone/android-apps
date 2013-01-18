package com.juegos.infantiles.adivinaquees;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
	
	private boolean Copiada=false;
	 
	private SQLiteDatabase db;
	 
	private final Context myContext;
	 
	/**
	* Constructor
	* Toma referencia hacia el contexto de la aplicaci�n que lo invoca para poder acceder a los 'assets' y 'resources' de la aplicaci�n.
	* Crea un objeto DBOpenHelper que nos permitir� controlar la apertura de la base de datos.
	* @param context
	*/
	public DBHelper(Context context) {
	 
	super(context, Config.DBLITE_NOMBRE, null, 1);
	this.myContext = context;
	 
	}
	 
	/**
	* Crea una base de datos vac�a en el sistema y la reescribe con nuestro fichero de base de datos.
	* */
	public void createDataBase() throws IOException{
	 
	boolean dbExist = checkDataBase();
	 
	if(dbExist){
	//la base de datos existe y no hacemos nada.
	}else{
	//Llamando a este m�todo se crea la base de datos vac�a en la ruta por defecto del sistema
	//de nuestra aplicaci�n por lo que podremos sobreescribirla con nuestra base de datos.
	this.getReadableDatabase();
	 
	try {
	 
	copyDataBase();
	 
	} catch (IOException e) {
	throw new Error("Error copiando Base de Datos");
	}
	}
	}
	 
	/**
	* Comprueba si la base de datos existe para evitar copiar siempre el fichero cada vez que se abra la aplicaci�n.
	* @return true si existe, false si no existe
	*/
	private boolean checkDataBase(){
	 
	SQLiteDatabase checkDB = null;
	 
	try{
	 
	String myPath = Config.DBLITE_RUTA + Config.DBLITE_NOMBRE;
	Log.i("Mensaje", "Estoy en Checkdatabase ANTES de openDatabase");
	checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
	Log.i("Mensaje", "Estoy en Checkdatabase DESPUES de openDatabase");
	}catch(SQLiteException e){
	 
	//si llegamos aqui es porque la base de datos no existe todav�a.
	 
	}
	if(checkDB != null){
	 
	checkDB.close();
	 
	}
	return checkDB != null ? true : false;
	}
	 
	/**
	* Copia nuestra base de datos desde la carpeta assets a la reci�n creada
	* base de datos en la carpeta de sistema, desde d�nde podremos acceder a ella.
	* Esto se hace con bytestream.
	* */
	private void copyDataBase() throws IOException{
	 
	//Abrimos el fichero de base de datos como entrada
	InputStream myInput = myContext.getAssets().open(Config.DBLITE_NOMBRE);
	 
	//Ruta a la base de datos vac�a reci�n creada
	String outFileName = Config.DBLITE_RUTA + Config.DBLITE_NOMBRE;
	 
	//Abrimos la base de datos vac�a como salida
	OutputStream myOutput = new FileOutputStream(outFileName);
	 
	//Transferimos los bytes desde el fichero de entrada al de salida
	byte[] buffer = new byte[1024];
	int length;
	while ((length = myInput.read(buffer))>0){
	myOutput.write(buffer, 0, length);
	}
	 
	//Liberamos los streams
	myOutput.flush();
	myOutput.close();
	myInput.close();
	 
	}
	 
	public void open() throws SQLException{
	 
	//Abre la base de datos
	/*try {
	createDataBase();
	} catch (IOException e) {
	throw new Error("Ha sido imposible crear la Base de Datos");
	}*/
	 
	String myPath = "/data/data/com.juegos.infantiles.adivinaquees/databases/bd_juegoAdivina";
	db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
	 
	}
	 
	@Override
	public synchronized void close() {
	if(db != null)
		db.close();
	super.close();
	}
	 
	@Override
	public void onCreate(SQLiteDatabase db) {
	 
	}
	 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	 
	}
	
	
	/**
	* INSERTAR NUEVA ALARMA
	* */
	/*public long insertAlarma(Integer id, Integer alarma, Integer evento) {
	ContentValues newValues = new ContentValues();
	newValues.put(KEY_ID, id);
	newValues.put(KEY_COL1, alarma);
	newValues.put(KEY_COL2, evento);
	return db.insert(DATABASE_TABLE, null, newValues);
	}
	 
	/**
	* BORRAR ALARMA CON _id = _rowIndex
	* */
	/*public boolean removeAlarma(long _rowIndex) {
	return db.delete(DATABASE_TABLE, KEY_ID + "=" + _rowIndex, null) > 0;
	}
	 
	/**
	* ACTUALIZAR ALARMA _id = _rowIndex
	* */
	/*public boolean updateAlarma(Integer _rowIndex, Integer alarma, Integer evento) {
	ContentValues newValues = new ContentValues();
	newValues.put(KEY_COL1,alarma);
	newValues.put(KEY_COL2, evento);
	return db.update(DATABASE_TABLE, newValues, KEY_ID + "=" + _rowIndex, null) > 0;
	}*/
	
	
	
	
	
	public Tipo getTipo(String cadenaTipo) {
		Tipo tipo=new Tipo();
		
		String[] cols=new String[] { "pkTipo", "nombre" };
		
		Cursor result = db.query(true, "tipos",
		cols,
		"nombre='" + cadenaTipo+"'", null, null, null,
		null, null);
		if ((result.getCount() == 0) || !result.moveToFirst()) {
		//Si la alarma no existe, devuelve una alarma con valores -1 y -1
			//tipo = new tipo(-1,-1);
			
		 
		} else {
		if (result.moveToFirst()) {
			tipo = new Tipo(
		result.getInt(result.getColumnIndex("pkTipo")),
		result.getString(result.getColumnIndex("nombre"))
		);
		}
		}
		return tipo;
		}
		 
		public ArrayList<Imagen> getImagenes(int fkTipo) {
		ArrayList<Imagen> imagenes = new ArrayList<Imagen>();
		String[] cols=new String[] { "nombre" };
		
		
		Cursor result = db.query("imagenes",
		cols, "fkTipo=" + fkTipo, null, null, null, null);
		if (result.moveToFirst())
		do {
		imagenes.add(new Imagen(
		result.getString(result.getColumnIndex("nombre"))
		)
		);
		} while(result.moveToNext());
		return imagenes;
		}
}
