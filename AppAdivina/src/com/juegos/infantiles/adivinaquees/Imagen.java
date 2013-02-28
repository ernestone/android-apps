package com.juegos.infantiles.adivinaquees;

public class Imagen {
	
	private int IDResource, IDTextViewAsociado,idAnimacion;
	private String nombre;
	private boolean correcto=false;
	
	public Imagen() {
		super();
	}
	public Imagen(String nombre) {
		super();
		this.nombre = nombre;
	}
	public boolean isCorrecto() {
		return correcto;
	}
	public void setCorrecto(boolean correcto) {
		this.correcto = correcto;
	}
	public String getNombre() {
		return nombre;
	}
	public int getIDResource() {
		return IDResource;
	}
	public void setIDResource(int iDResource) {
		IDResource = iDResource;
	}
	public int getIDTextViewAsociado() {
		return IDTextViewAsociado;
	}
	public void setIDTextViewAsociado(int iDTextViewAsociado) {
		IDTextViewAsociado = iDTextViewAsociado;
	}
	public int getIdAnimacion() {
		return idAnimacion;
	}
	public void setIdAnimacion(int idAnimacion) {
		this.idAnimacion = idAnimacion;
	}
	
	
	
}
