package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	//VEDO SE UNO STUDENTE E' ISCRITTO AL CORSO
	public boolean isIscritto(Studente s, Corso c) {
		boolean iscritto = false;
		String sql = "select * "
				+"from iscrizione i "
				+"where i.matricola = ? and i.codins = ?";
	
	
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,s.getMatricola());
			st.setString(2, c.getCodins());
			ResultSet rs = st.executeQuery();
	//SE RS NON HA UN SUCCESSORE VUOL DIRE CHE LO STUDENTE NON E' ISCRITTO
			if(rs.next()) {
				iscritto = true;
			}
	
			conn.close();
			st.close();
			rs.close();
			
	
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		return iscritto;
	}
	
	public Studente getStudente(String matricola) {
		Studente studente = null;
		String sql = "select * "
				+ "from studente s "
				+"where s.matricola = ?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,matricola);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				studente = new Studente(rs.getString("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
			}
			rs.close();
			st.close();
			conn.close();
		}
		catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		return studente;
	}
	
	public List<Corso> getCorsiPerStudente(Studente s){
		List<Corso> elencoCorsi = new ArrayList<Corso>();
		String sql = "select * "
				+ "from iscrizione i, corso c "
				+ "where i.codins = c.codins and i.matricola = ?";
		
		// TODO
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, s.getMatricola());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"),rs.getString("nome"),rs.getInt("pd"));
				elencoCorsi.add(c);
			}

			conn.close();
			st.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		return elencoCorsi;
	}
}
