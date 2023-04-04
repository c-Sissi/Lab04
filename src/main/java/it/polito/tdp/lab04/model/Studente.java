package it.polito.tdp.lab04.model;

import java.util.Objects;

import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Studente {
	private String matricola;
	private String cognome;
	private String nome;
	private String CDS;
	
	public Studente(String matricola, String cognome, String nome, String cDS) {
		this.matricola = matricola;
		this.cognome = cognome;
		this.nome = nome;
		CDS = cDS;
	}
	public String getMatricola() {
		return matricola;
	}
	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCDS() {
		return CDS;
	}
	public void setCDS(String cDS) {
		CDS = cDS;
	}
	@Override
	public int hashCode() {
		return Objects.hash(matricola);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Studente other = (Studente) obj;
		return Objects.equals(matricola, other.matricola);
	}
	@Override
	public String toString() {
		return matricola + "\t"+cognome + "\t" + nome + "\t" + CDS;
	}
	
}
