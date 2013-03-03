package com.juegos.infantiles.adivinaquees;

import java.util.ArrayList;
import java.util.HashMap;

public class Config {
	public static String DBLITE_NOMBRE="bd_juegoAdivina.sqlite";
	public static String DBLITE_RUTA="/data/data/com.juegos.infantiles.adivinaquees/databases/";
	
 	private static String[] animales={"Buho","Ballena","Ardilla","Caracol"};
 	private static String[] frutas={"Naranja","Pera","Uva","Plátano"};
	
	
	public static HashMap<String, String[]> camposConRegistros() {
		HashMap<String, String[]> camposRegistros=new HashMap<String, String[]>();
		
		camposRegistros.put("Frutas", frutas);
		camposRegistros.put("Animales", animales);
		
		return camposRegistros;
	}
	
	
	
}
