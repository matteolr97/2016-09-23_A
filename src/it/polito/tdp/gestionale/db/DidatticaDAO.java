package it.polito.tdp.gestionale.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.gestionale.model.Corso;
import it.polito.tdp.gestionale.model.Studente;
import it.polito.tdp.gestionale.model.StudenteCorso;

public class DidatticaDAO {

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso, Map<Integer, Studente> mapStudenti) {
		final String sql = "SELECT studente.matricola FROM iscrizione, studente WHERE iscrizione.matricola=studente.matricola AND codins=?";

		List<Studente> studentiIscrittiAlCorso = new ArrayList<Studente>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int matricola = rs.getInt("matricola");
				Studente studente = mapStudenti.get(matricola);
				if (studente != null) {
					studentiIscrittiAlCorso.add(studente);
				} else {
					System.out.println("ERRORE! Lo studente non è presente!");
				}
			}

			corso.setStudenti(studentiIscrittiAlCorso);

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		return studentiIscrittiAlCorso;
	}

	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Corso s = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				corsi.add(s);
			}

			return corsi;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Ottengo tutti gli studenti salvati nel Db
	 */
	public List<Studente> getTuttiStudenti(Map<Integer,Studente> map) {

		final String sql = "SELECT * FROM studente";

		List<Studente> studenti = new LinkedList<Studente>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
				studenti.add(s);
				map.put(s.getMatricola(), s);
			}

			return studenti;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	public List<StudenteCorso> getFrequenza(Map<Integer,Studente>idMap, Studente studente){

		final String sql = "SELECT count(*) as cntFROM iscrizione WHERE matricola=?";

		List<StudenteCorso> studenti = new LinkedList<StudenteCorso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

//				studenti.add(new StudenteCorso(rs.getInt(studente), rs.getInt("cnt")));
			}

			return studenti;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

}
