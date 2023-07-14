package it.polito.tdp.genes.model;

public class Statistiche {
	private String target;
	private double pesoArco;
	public Statistiche(String target,double pesoArco) {
		super();
		this.target = target;
		this.pesoArco = pesoArco;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public double getPesoArco() {
		return pesoArco;
	}
	public void setPesoArco(int pesoArco) {
		this.pesoArco = pesoArco;
	}
	
	
}
