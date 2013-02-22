package dani.funciones;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;

public class Basicas {
	static Random rand=new Random();
	
	
	public static ArrayList<Integer> escoger(int totalItems, int itemsAEscoger) {
		ArrayList<Integer> lista=new ArrayList<Integer>();
		int numEscogido, numsAnhadidos=1; 
		Boolean repetido=false;
 		
		//Log.i("Tamaño de la lista:", String.valueOf(totalItems));
		
		numEscogido=rand.nextInt(totalItems);
 		lista.add(numEscogido);
 		//Log.i("Número escogido:", String.valueOf(numEscogido));
 		
 		while (numsAnhadidos<itemsAEscoger) {
 			numEscogido=rand.nextInt(totalItems);
 			
 	 		for (Integer integer : lista) {
 				if(numEscogido==integer){
 					repetido=true;
 					break;
 				}
 			}
 	 		if(!repetido){
 	 			//Log.i("Número escogido:", String.valueOf(numEscogido));
 	 			lista.add(numEscogido);
 	 			numsAnhadidos++;
 	 		}
	 		repetido=false;
		}
 		
 		return lista;
	}
	
	public static ArrayList<Integer> desordenar(ArrayList<Integer> lista) {
		ArrayList<Integer> listaDefinitiva=new ArrayList<Integer>();
		int numeroItems=lista.size();
		int numero=rand.nextInt(numeroItems);
		
		//Log.i("Tamaño del Array a desordenar:", String.valueOf(numeroItems));
		
		for (int i=0;i<numeroItems;i++) {
			listaDefinitiva.add(lista.get(numero));
			if(lista.size()>1){
				lista.remove(listaDefinitiva.get(listaDefinitiva.size()-1));
				numero=rand.nextInt(lista.size());
				//Log.i("Tamaño de la lista original:", String.valueOf(lista.size()));
			}
			//Log.i("Tamaño de la lista definitiva:", String.valueOf(listaDefinitiva.size()));
		}
		
		return listaDefinitiva;
	}
	
}
