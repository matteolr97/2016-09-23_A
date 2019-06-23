package it.polito.tdp.gestionale.model;

public class StudenteCorso {
private Studente studente;
private int numCorsi;
public StudenteCorso(Studente studente, int numCorsi) {
	super();
	this.studente = studente;
	this.numCorsi = numCorsi;
}
public Studente getStudente() {
	return studente;
}
public void setStudente(Studente studente) {
	this.studente = studente;
}
public int getNumCorsi() {
	return numCorsi;
}
public void setNumCorsi(int numCorsi) {
	this.numCorsi = numCorsi;
}
@Override
public String toString() {
	return "Lo studente "+studente.getCognome()+" "+studente.getNome()+" è iscritto a "+ numCorsi+" corsi ";
}


}
