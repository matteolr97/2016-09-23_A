package it.polito.tdp.gestionale.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.gestionale.db.DidatticaDAO;

public class Model {

	private List<Corso> corsi;
	private List<Studente> studenti;
	private DidatticaDAO dao;
	private Graph<Nodo, DefaultEdge> grafo;
	private Map<Integer, Studente> studentMap;
	List<StudenteCorso> risultato = new ArrayList<>();
	private List<Corso> best;
	private Set<Studente> mancanti ;

	private int bestCorsi;

	public Model() {
		dao = new DidatticaDAO();
		studentMap = new HashMap<>();

	}

	public void creaGrafo() {
		grafo = new SimpleGraph<Nodo, DefaultEdge>(DefaultEdge.class);

		List<Nodo> nodi = new ArrayList<>();
		corsi = new LinkedList<Corso>();

		nodi.addAll(dao.getTuttiStudenti(studentMap));
		corsi = dao.getTuttiICorsi();
		nodi.addAll(corsi);
		Graphs.addAllVertices(grafo, nodi);
		System.out.println("VERTICI:" + grafo.vertexSet().size());
		for (Corso c : dao.getTuttiICorsi()) {
			dao.getStudentiIscrittiAlCorso(c, studentMap);
			for (Studente s : c.getStudenti())
				Graphs.addEdgeWithVertices(grafo, c, s);
		}
		System.out.println("ARCHI:\n" + grafo.edgeSet().size());
	}

	public List<StudenteCorso> getFrequency() {
		for (Studente s : studentMap.values()) {
			risultato.add(new StudenteCorso(s, Graphs.neighborListOf(this.grafo, s).size()));
		}
		return risultato;
	}

	public Map<Integer, Integer> contaCorsi() {
		Map<Integer, Integer> contatore = new HashMap<Integer, Integer>();
		for (StudenteCorso sc : risultato)
			contatore.put(sc.getNumCorsi(), 0);
		for (StudenteCorso sc : risultato)
			contatore.put(sc.getNumCorsi(), contatore.get(sc.getNumCorsi()) + 1);
		return contatore;
	}

	public List<Corso> getMinimoCorsi() {
		best = new LinkedList<>();
		 mancanti = new HashSet<Studente>();
		this.creaGrafo();
		for (StudenteCorso sc : this.getFrequency()) {
			if (sc.getNumCorsi() > 0)
				mancanti.add(sc.getStudente());
		}
		List<Corso> parziale = new ArrayList<>();
		bestCorsi = Integer.MAX_VALUE;
		this.recursive(parziale) ;

		return best;
	}

	public void recursive(List<Corso> parziale) {
		if (parziale.size() > 0) {
			for (Studente s : (parziale.get(parziale.size() - 1)).getStudenti()) {
				if(mancanti.contains(s))
				mancanti.remove(s);

			}
		}
		if (mancanti.isEmpty()) {
			if (parziale.size() < bestCorsi) {
				bestCorsi = parziale.size();
				best = new ArrayList<Corso>(parziale);

			}
			
			for (Corso c : corsi) {
				if (!parziale.contains(c)) {
					parziale.add(c);
					recursive(parziale);
					parziale.remove(parziale.size() - 1);

				}
			}
		}

	}
}
