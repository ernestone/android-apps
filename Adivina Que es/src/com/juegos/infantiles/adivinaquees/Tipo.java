package com.juegos.infantiles.adivinaquees;

public class Tipo {
	private int pkTipo;
	private String nombre;
	
	
	
	public Tipo() {
		super();
	}


	public Tipo(int pkTipo, String nombre) {
		super();
		this.pkTipo = pkTipo;
		this.nombre = nombre;
	}


	public int getPkTipo() {
		return pkTipo;
	}


	public void setPkTipo(int pkTipo) {
		this.pkTipo = pkTipo;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
