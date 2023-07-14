package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	private Graph<String, DefaultWeightedEdge> grafo;
	private GenesDao dao;
	private Map<String, Interaction> allInteractions;
	private String source;
	private List<String> bestPath;
	private int bestScore;

	public Model() {
		this.dao = new GenesDao();
		this.allInteractions = new HashMap<>(this.dao.getInteractions());

	}

	public List<String> getVertici() {
		List<String> result = new ArrayList<>(this.grafo.vertexSet());
		Collections.sort(result);
		return result;
	}

	public void BuildGrafo() {
		this.grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		// Creazione vertici
		Graphs.addAllVertices(this.grafo, this.dao.getVertici());

//		for (String loc1 : this.grafo.vertexSet()) {
//			for (String loc2 : this.grafo.vertexSet()) {
//				if (!loc1.equals(loc2)) {
//					Set<String> tipi1 = new HashSet<>();
//					List<String> allGenes1 = new ArrayList<>(this.dao.getListaGeniLocalizzazione(loc1));
//					List<String> allGenes2 = new ArrayList<>(this.dao.getListaGeniLocalizzazione(loc2));
//					for (String gene1 : allGenes1) {
//						for (String gene2 : allGenes2) {
//							String key1 = gene1 + "-" + gene2;
//							String key2 = gene2 + "-" + gene1;
//							if (allInteractions.containsKey(key1)) {
//								if (!tipi1.contains(allInteractions.get(key1).getTipo()))
//									tipi1.add(allInteractions.get(key1).getTipo());
//							}
//							if (allInteractions.containsKey(key2)) {
//								if (!tipi1.contains(allInteractions.get(key2).getTipo()))
//									tipi1.add(allInteractions.get(key2).getTipo());
//							}
//						}
//					}
//					if (tipi1.size()!=0)
//						Graphs.addEdge(this.grafo, loc1, loc2, tipi1.size());
//				}
//			}
//		}
		List<EdgeModel> listaArchi = this.dao.getArchi();
		for (EdgeModel e : listaArchi) {
			Graphs.addEdgeWithVertices(this.grafo, e.getLocalizzazione1(), e.getLocalizzazione2(), e.getPesoArco());
		}

	}

	public List<String> getPath() {
		this.bestPath = new ArrayList<>();
		this.bestScore = 0;
		List<String> parziale = new ArrayList<>();
		parziale.add(this.source);
		ricorsione(parziale, this.source);
		return this.bestPath;

	}

	private void ricorsione(List<String> parziale, String source) {
		String current = parziale.get(parziale.size() - 1);

		// Termine uscite
		if (getScore(parziale, source) > this.bestScore) {
			this.bestScore = getScore(parziale, source);
			this.bestPath = new ArrayList<>(parziale);
			return;

		}
		// Aggiungo elementi
		List<String> successori = Graphs.successorListOf(this.grafo, current);
		for (String s : successori) {
			if (!parziale.contains(s)) {
				parziale.add(s);
				ricorsione(parziale, source);
				parziale.remove(s);
			}
		}
	}

	public int getScore(List<String> parziale, String source) {
		int score = 0;
		String s_1 = source;
		for (String s : parziale) {
			if (s.equals(source))
				continue;
			score += this.grafo.getEdgeWeight(this.grafo.getEdge(s_1, s));
			s_1 = s;
		}
		return score;
	}

	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}

	public List<Statistiche> getStatistiche(String root) {
		this.source = root;
		Set<String> connesso = Graphs.neighborSetOf(this.grafo, root);
		List<Statistiche> result = new ArrayList<>();
		for (String connessione : connesso) {
			result.add(new Statistiche(connessione, this.grafo.getEdgeWeight(this.grafo.getEdge(root, connessione))));
		}
		return result;
	}
}