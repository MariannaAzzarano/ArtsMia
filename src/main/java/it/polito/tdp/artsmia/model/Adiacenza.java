package it.polito.tdp.artsmia.model;

public class Adiacenza {  //mappa i dati che estraiamo dalla query delle adiacenze
	
	private Integer id1;
	private Integer id2;
	private int peso;
	
	
	public Adiacenza(Integer id1, Integer id2, int peso) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.peso = peso;
	}
	
	
	public Integer getId1() {
		return id1;
	}
	public void setId1(Integer id1) {
		this.id1 = id1;
	}
	public Integer getId2() {
		return id2;
	}
	public void setId2(Integer id2) {
		this.id2 = id2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	
	//in questo caso  non mi servono metodi come HashCode e Equals perchè so che non andrò ad inserire le adiacenze un una HashMap o una HashTable
	

}
