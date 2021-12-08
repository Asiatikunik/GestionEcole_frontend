package fr.uvsq.isty.gestionecole.controleurs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import fr.uvsq.isty.gestionecole.modeles.Ecole;
import fr.uvsq.isty.gestionecole.modeles.UniteEnseignement;

/**
 * Classe jouant le rôle de contrôleur pour la gestion
 * des unités d'enseignement
 * @author Lucien
 *
 */
public class UETabController implements Controller {
	
	private Ecole ecole;
	
	//Ajout des listes nécessaires à mettre à jour via le FXML
	@FXML
	ListView<Label> listViewUE;
	@FXML
	TextField textFieldNom;
	@FXML
	TextField textFieldSigle;


	/**
	 * Construit une instance d'UniteEnseignement avec le modèle
	 * @param ecole : le modèle à inclure dans la nouvelle instance
	 */
	public UETabController(Ecole ecole) {
		this.ecole = ecole;
	}
	
	/**
	 * Initialise la vue
	 */
	@FXML
	public void initialize() {
		this.ecole.getUnitesEnseignement().forEach(ue -> {
		this.listViewUE.getItems().add(
				new Label(ue.toDisplay())
			);
		});
	}

	/**
	 * Créé une unité d'enseignement
	 * @param event : l'évènement déclenchant l'appel de cette méthode
	 */
	@Override
	@FXML
	public void creer(ActionEvent event) throws IOException, URISyntaxException, InterruptedException {
		//on recupere le nom et sigle entree par l'utilisateur
		String sigle = textFieldSigle.getText();
		String nom = textFieldNom.getText();
		UniteEnseignement ue = new UniteEnseignement(sigle, nom);
		
		//Si un des champs est vide : Erreur!
		if (sigle.isEmpty() || nom.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Un champ est vide !");
			alert.showAndWait();
			//Sinon on creer l'UE et on l'ajoute a l'ecole puis on actualise
		} else {

			String lien = "http://localhost:8082/ue";
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(lien))
					.headers("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString("{ \"sigle\": \""+ sigle +"\" , \"nom\": \""+ nom +"\"}"))
					.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());

			this.ecole.addUniteEnseignement(ue);
			this.textFieldSigle.setText("");
			this.textFieldNom.setText("");
			this.listViewUE.getItems().add(
					new Label(ue.toDisplay())
			);
		}
	}

	/**
	 * Supprime une unité d'enseignement
	 * @param event : l'évènement déclenchant l'appel de cette méthode
	 */
	@Override
	@FXML
	public void supprimer(ActionEvent event) throws IOException, URISyntaxException, InterruptedException {
		Label label = this.listViewUE.getSelectionModel().getSelectedItem();
		//On prend les UE et on compare avec la sélection pour supprimer
		if (label != null) {
			Set<UniteEnseignement> ues = this.ecole.getUnitesEnseignement();
			ues.removeIf(s -> s.getNom().equals(label.getText()));
			this.ecole.setUnitesEnseignement(ues);
			
			//On actualise de nouveau la liste
			List<Label> labels = new ArrayList<Label>();
			for (UniteEnseignement ue : ues) {
				labels.add(new Label(ue.toDisplay()));
			}
			this.listViewUE.getItems().setAll(labels);

			System.out.println("label: " + label.getText());

			String lien = "http://localhost:8082/ue/" + label.getText();
			System.out.println(lien);
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(lien))
					.headers("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString("{ \"sigle\": \""+  label.getText() +"\"}"))
					.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());

		} else {
			// Boite d'alerte si aucun élément n'est sélectionné
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Aucun item selectionne !");
			alert.showAndWait();
		}
	}

	@Override
	public void modifier(ActionEvent event) throws IOException {}

}
