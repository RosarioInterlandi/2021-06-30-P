package it.polito.tdp.genes.model;

public class Interaction {
	private String GeneID1;
	private String GeneID2;
	private String tipo;
	public Interaction(String geneID1, String geneID2, String tipo) {
		super();
		GeneID1 = geneID1;
		GeneID2 = geneID2;
		this.tipo = tipo;
	}
	public String getGeneID1() {
		return GeneID1;
	}
	public void setGeneID1(String geneID1) {
		GeneID1 = geneID1;
	}
	public String getGeneID2() {
		return GeneID2;
	}
	public void setGeneID2(String geneID2) {
		GeneID2 = geneID2;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
