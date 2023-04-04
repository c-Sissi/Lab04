package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;

	public Model() {
		this.corsoDao = new CorsoDAO();
		this.studenteDao = new StudenteDAO();
	}
	
	public List<Corso> getTuttiCorsi(){
		return this.corsoDao.getTuttiICorsi();
	}
	public Corso getCorso(Corso c) {
		return this.corsoDao.getCorso(c);
	}
	
	public Studente getStudente(String matricola) {
		return this.studenteDao.getStudente(matricola);
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(Corso c) {
		return this.corsoDao.getStudentiIscrittiAlCorso(c);
	}
	public List<Corso> getCorsiPerStudente(Studente s){
		return this.studenteDao.getCorsiPerStudente(s);
	}
	
	public boolean isIscritto(Studente s, Corso c) {
		return this.studenteDao.isIscritto(s, c);
	}
	public boolean iscriviStudenteACorso(Studente s, Corso c) {
		return this.corsoDao.iscriviStudenteACorso(s, c);
	}
}
