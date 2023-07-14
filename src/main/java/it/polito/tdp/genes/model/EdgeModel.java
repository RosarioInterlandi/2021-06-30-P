package it.polito.tdp.genes.model;

public class EdgeModel {
	private String Localizzazione1;
	private String Localizzazione2;
	private int pesoArco;
	
	public EdgeModel(String localizzazione1, String localizzazione2, int pesoArco) {
		super();
		Localizzazione1 = localizzazione1;
		Localizzazione2 = localizzazione2;
		this.pesoArco = pesoArco;
	}

	public String getLocalizzazione1() {
		return Localizzazione1;
	}

	public void setLocalizzazione1(String localizzazione1) {
		Localizzazione1 = localizzazione1;
	}

	public String getLocalizzazione2() {
		return Localizzazione2;
	}

	public void setLocalizzazione2(String localizzazione2) {
		Localizzazione2 = localizzazione2;
	}

	public int getPesoArco() {
		return pesoArco;
	}

	public void setPesoArco(int pesoArco) {
		this.pesoArco = pesoArco;
	}
	
	
}
