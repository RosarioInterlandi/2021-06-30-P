package it.polito.tdp.genes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Model;
import it.polito.tdp.genes.model.Statistiche;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnStatistiche;

    @FXML
    private Button btnRicerca;

    @FXML
    private ComboBox<String> boxLocalizzazione;

    @FXML
    private TextArea txtResult;

    @FXML
    void doRicerca(ActionEvent event) {
    	txtResult.setText("Cammino massimo trovato: \n");
    	List<String> bestPath = this.model.getPath();
    	for (String s: bestPath) {
    		txtResult.appendText(s+"\n");
    	}
    }

    @FXML
    void doStatistiche(ActionEvent event) {
    	if (this.boxLocalizzazione.getValue()==null) {
    		txtResult.setText("Scegli una localizzazione");
    		return;
    	}
    	List<Statistiche> result = this.model.getStatistiche(this.boxLocalizzazione.getValue());
    	txtResult.setText("Adiacenti a :"+boxLocalizzazione.getValue()+"\n");
    	for (Statistiche s : result) {
    		txtResult.appendText(s.getTarget()+"     "+ s.getPesoArco()+"\n");
    	}
    }

    @FXML
    void initialize() {
        assert btnStatistiche != null : "fx:id=\"btnStatistiche\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxLocalizzazione != null : "fx:id=\"boxLocalizzazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.model.BuildGrafo();
		txtResult.setText(model.getVertici().size()+"--"+this.model.getNArchi());
		this.boxLocalizzazione.getItems().addAll(this.model.getVertici());
	}
	
}
