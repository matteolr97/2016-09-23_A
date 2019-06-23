package it.polito.tdp.gestionale.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Map<Integer,Studente> studentMap;
	List<StudenteCorso> risultato = new ArrayList<>();

	public Model() {
		dao = new DidatticaDAO();
		studentMap = new HashMap<>();
		

	}
	public void creaGrafo() {
				grafo = new SimpleGraph<Nodo, DefaultEdge>(DefaultEdge.class);

		 List<Nodo> nodi= new ArrayList<>();
		 nodi.addAll(dao.getTuttiStudenti(studentMap));
		 nodi.addAll(dao.getTuttiICorsi());
		 Graphs.addAllVertices(grafo, nodi);
		 System.out.println("VERTICI:"+grafo.vertexSet().size());
		 for(Corso c:dao.getTuttiICorsi()) {
			 dao.getStudentiIscrittiAlCorso(c, studentMap);
			 for(Studente s: c.getStudenti())
				 Graphs.addEdgeWithVertices(grafo, c, s)	;	 }
		 System.out.println("ARCHI:\n"+grafo.edgeSet().size());
	}
	public List<StudenteCorso> getFrequency(){
		for(Studente s: studentMap.values()) {
			risultato.add(new StudenteCorso(s,Graphs.neighborListOf(this.grafo, s).size()));
		}
		return risultato;
	}
	public Map<Integer,Integer> contaCorsi() {
		Map<Integer,Integer>contatore= new HashMap<Integer,Integer>();
		for(StudenteCorso sc:risultato)
			contatore.put(sc.getNumCorsi(), 0);
		for(StudenteCorso sc:risultato)
			contatore.put(sc.getNumCorsi(), contatore.get(sc.getNumCorsi())+1);
		return contatore;
	}
	
	
	
	
	
}
