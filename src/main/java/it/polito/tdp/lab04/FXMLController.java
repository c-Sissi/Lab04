/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.lab04;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private List<Corso> corsi;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCercaCorsi"
    private Button btnCercaCorsi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaIscritti"
    private Button btnCercaIscritti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCheck"
    private Button btnCheck; // Value injected by FXMLLoader

    @FXML // fx:id="btnIscrivi"
    private Button btnIscrivi; // Value injected by FXMLLoader

    @FXML // fx:id="btnReset"
    private Button btnReset; // Value injected by FXMLLoader

    @FXML // fx:id="comboBox"
    private ComboBox<Corso> comboBox; // Value injected by FXMLLoader

    @FXML // fx:id="txtCognome"
    private TextField txtCognome; // Value injected by FXMLLoader

    @FXML // fx:id="txtMatricola"
    private TextField txtMatricola; // Value injected by FXMLLoader

    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML
    void doCercaCorsi(ActionEvent event) {
    	txtRisultato.clear();
    	
    	String matricola = txtMatricola.getText();
    	
    	if(matricola.equals("") || isValid(matricola) == false) {
    		txtRisultato.setText("INSERIRE UNA MATRICOLA VALIDA");
    		throw new NumberFormatException();
    		
    	}
    	else {
    		if(this.model.getStudente(matricola)!=null) {
	    		Studente s = this.model.getStudente(matricola);
	    		StringBuilder sb = new StringBuilder();
	    		for(Corso c: this.model.getCorsiPerStudente(s)) {
	    				sb.append(String.format("%-40s", c.getCodins()));
	    				sb.append(String.format("\t"+"%-30s", c.getCrediti()));
	    				sb.append(String.format("%-90s", c.getNome()));
	    				sb.append(String.format("\t"+"%-40s", c.getPd()));
	    				sb.append("\n");
	    			}
	    			txtRisultato.appendText(sb.toString());;
	    		
    		}
    		else {
    			txtRisultato.setText("LA MATRICOLA NON RISULTA REGISTRATA NEL DATABASE!");
    		}
    	}
    	
    }

    @FXML
    void doCercaIscritti(ActionEvent event) {
    	txtRisultato.clear();
    	Corso c = comboBox.getValue();
    	if(c!= null) {	
    		if(!txtMatricola.getText().equals("")) {
    			if(this.model.getStudente(txtMatricola.getText()) != null) {
	    			Studente s =  this.model.getStudente(txtMatricola.getText());
	    			if(this.model.isIscritto(s, c)== true) {
	    				txtRisultato.setText("LO STUDENTE RISULTA ISCRITTO A QUESTO CORSO");
	    			}
	    			else {
	    				txtRisultato.setText("LO STUDENTE NON RISULTA ISCRITTO A QUESTO CORSO");
	    			}
    			}
    			else {
    				txtRisultato.setText("LA MATRICOLA NON RISULTA REGISTRATA NEL DATABASE!");
    			}
    		}
    		else { 
	    		StringBuilder sb = new StringBuilder();
		    	for(Studente s: this.model.getStudentiIscrittiAlCorso(c)) {
		    		sb.append(String.format("%-10s",s.getMatricola()));
		    		sb.append(String.format("\t"+"%-30s",s.getCognome()));
		    		sb.append(String.format("\t"+"%-30s",s.getNome()));
		    		sb.append(String.format("\t"+"%-10s",s.getCDS()));
		    		sb.append("\n");
		    	}
	    		txtRisultato.appendText(sb.toString());
    		}
    	}
    	else {
    		txtRisultato.setText("SCEGLIERE UN CORSO!");
    	}
    }

    @FXML
    void doCheck(ActionEvent event) {
    	txtRisultato.clear();
    	String matricola = txtMatricola.getText();
    	
    	if(matricola.equals("") || isValid(matricola) == false) {
    		txtRisultato.setText("INSERIRE UNA MATRICOLA VALIDA");
    		throw new NumberFormatException();
    		
    	}
    	else {
    		if(this.model.getStudente(matricola)!=null) {
	    		Studente s = this.model.getStudente(matricola);
	    		txtNome.setText(s.getNome());
	    		txtCognome.setText(s.getCognome());
	    	}
    		else {
    			txtRisultato.setText("LA MATRICOLA NON RISULTA REGISTRATA NEL DATABASE!");
    		}
    	}
	    	
    }

    private boolean isValid(String matricola) {
		char matr[] = matricola.toCharArray();
		for(char c : matr) {
			if(Character.isDigit(c)) {
				return true;
			}
		}
		return false;
	}

	@FXML
    void doIscrivi(ActionEvent event) {
		txtRisultato.clear();
		
		String matricola = txtMatricola.getText();
		Corso c = comboBox.getValue();
		if(c!= null) {
			if(isValid(matricola) == true && this.model.getStudente(matricola) != null) {
				Studente s = this.model.getStudente(matricola);
				if(this.model.isIscritto(s, c) == true) {
					txtRisultato.setText("LO STUDENTE E' GIA' ISCRITTO A QUESTO CORSO!");
				}
				else {
					this.model.iscriviStudenteACorso(s, c);
					txtRisultato.setText("L'ISCRIZIONE E' AVVENUTA CON SUCCESSO!");
				}
			}
			else {
				txtRisultato.setText("MATRICOLA NON VALIDA");
				throw new NumberFormatException();
			}
		}
		else {
			txtRisultato.setText("SCEGLIERE UN CORSO A CUI ISCRIVERSI");
			throw new NullPointerException();
		}
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtNome.clear();
    	txtCognome.clear();
    	txtRisultato.clear();
    	txtMatricola.clear();
    	comboBox.setValue(null);

    }
    
    public void setComboItems() {
    	corsi = this.model.getTuttiCorsi();
    	Collections.sort(corsi);
    	comboBox.getItems().addAll(corsi);
    	comboBox.getItems().add(null);
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCheck != null : "fx:id=\"btnCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";
        assert comboBox != null : "fx:id=\"comboBox\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        
        
    }
    public void setModel(Model m) {
    	this.model = m;
    	setComboItems();
    }


}
