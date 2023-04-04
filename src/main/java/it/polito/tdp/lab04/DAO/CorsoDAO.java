package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
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

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				//System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				
				Corso c = new Corso(codins,numeroCrediti,nome,periodoDidattico);
				corsi.add(c);
			}

			conn.close();
			st.close();
			rs.close();
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(Corso corso) {
		
		String sql = "select * "
				+ "from corso c " 
				+ "where codins = ?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();

			if(rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"),rs.getString("nome"),rs.getInt("pd"));
				return c;
			}

			conn.close();
			st.close();
	
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		return null;
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		List<Studente> studentiIscritti = new ArrayList<Studente>();
		String sql = "select * "
				+ "from iscrizione i, studente s "
				+ "where i.matricola = s.matricola and i.codins = ?";
		
		// TODO
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Studente s = new Studente (rs.getString("matricola"), rs.getString("cognome"),rs.getString("nome"),rs.getString("CDS"));
				studentiIscritti.add(s);
			}

			conn.close();
			st.close();
	
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		return studentiIscritti;
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean iscriviStudenteACorso(Studente studente, Corso corso) {
		// TODO
		// ritorna true se l'iscrizione e' avvenuta con successo
		boolean iscrizione = false;
		String sql = "INSERT INTO iscritticorsi.iscrizione "
				+ "(matricola,codins) VALUES (?,?)";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,studente.getMatricola());
			st.setString(2, corso.getCodins());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				iscrizione = true;
			}

			conn.close();
			st.close();
	
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		return iscrizione;
	}

}
