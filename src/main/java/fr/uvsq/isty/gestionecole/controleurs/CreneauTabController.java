package fr.uvsq.isty.gestionecole.controleurs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import fr.uvsq.isty.gestionecole.modeles.Creneau;
import fr.uvsq.isty.gestionecole.modeles.Ecole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;

/**
 * Classe jouant le rôle de contrôleur pour la gestion des créneaux
 * @author Joshua
 *
 */
public class CreneauTabController implements Controller {
	
	// Objet contenant l'ensemble des modèles
	private Ecole ecole;
	
	// Composant permettant de sélectionner une date
	@FXML
	private DatePicker date;
	// Composant permettant de choisir l'heure de début parmi une liste
	@FXML
	private ChoiceBox<String> heureDebut;
	// Composant permettant de choisir l'heure de fin parmi une liste
	@FXML
	private ChoiceBox<String> heureFin;
	// Composant affichant les créneaux existants
	@FXML
	private ListView<HBox> listViewCreneau;
	
	/**
	 * Constructeur
	 * @param ecole: objet contenant l'ensemble des modèles
	 */
	public CreneauTabController(Ecole ecole) {
		this.ecole = ecole;
	}
	
	/**
	 * Initialise la vue
	 */
	@FXML
	public void initialize() {
		this.ecole.getCreneaux().forEach(c -> this.ajouterCreneauListe(c));
	}

	/**
	 * Méthode permettant de créer un créneau
	 * @param event : action déclenchant l'appel à cette méthode
	 */
	@Override
	@FXML
	public void creer(ActionEvent event) throws IOException, InterruptedException, URISyntaxException {
		// Récupération des valeurs saisies dans la vue
		LocalDate dateCreneau = this.date.getValue();
		String hDebut = this.heureDebut.getValue();
		String hFin = this.heureFin.getValue();
		
		if (dateCreneau == null || hDebut == null || hFin == null) {
			// Affichage d'une boîte d'alerte en cas de champs non rempli
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Un champ est vide !");
			alert.showAndWait();
		}
		else {
			// Création des objets LocalTime en fonction des valeurs saisies
			LocalTime debut = LocalTime.parse(hDebut);
			LocalTime fin = LocalTime.parse(hFin);
			
			Creneau c = new Creneau(dateCreneau, debut, fin);

			String lien = "http://localhost:8082/creneau";
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(lien))
					.headers("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(
							"{ " +
									"\"date\": \""+ dateCreneau +"\" , " +
									"\"debut\": \""+ hDebut +"\" , " +
									"\"fin\": \""+ hFin +
									"\"}"
					))
					.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());


			// Ajout du nouveau créneau dans le modèle
			this.ecole.addCreneau(c);
			
			// Réinitialisation des valeurs dans la vue
			this.date.setValue(null);
			this.heureDebut.setValue(null);
			this.heureFin.setValue(null);
			
			// Ajout du créneau dans la liste affichée
			this.ajouterCreneauListe(c);
		}
	}
	
	
	/**
	 * Ajoute un créneau à la liste affichée
	 * @param c : le créneau à ajouter
	 * dans la vue
	 */
	public void ajouterCreneauListe(Creneau c) {
		// Création des composants de la vue permettant l'affichage
		HBox globalHBox = new HBox();
		globalHBox.setPadding(new Insets(5, 5, 5, 5));
		
		HBox ligne = new HBox();
		
		Label labelCreneau = new Label(c.toString());
		ligne.getChildren().add(labelCreneau);
		
		globalHBox.getChildren().add(ligne);
		// Ajout du créneau dans la liste affichée
		this.listViewCreneau.getItems().add(globalHBox);
	}
	
	
	/**
	 * Méthode permettant de supprimer un créneau
	 * @param event : action déclenchant l'appel à cette méthode
	 */
	@Override
	@FXML
	public void supprimer(ActionEvent event) throws IOException, URISyntaxException, InterruptedException {
		// Récupération de l'élément sélectionné
		HBox box = this.listViewCreneau.getSelectionModel().getSelectedItem();
		
		if (box != null) {
			// Récupération du créneau dans la sélection
			HBox selection = (HBox) box.getChildren().get(0);
			Label creneau = (Label) selection.getChildren().get(0);
			
			// Suppression du créneau dans le modèle
			this.ecole.getCreneaux().removeIf(c -> c.toString().equals(creneau.getText()));
			
			// Réinitialisation de la vue
			this.listViewCreneau.getItems().clear();
			this.ecole.getCreneaux().forEach(c -> this.ajouterCreneauListe(c));

			String lien = "http://localhost:8082/creneau";
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(lien))
					.headers("Content-Type", "application/json")
					.method("DELETE",HttpRequest.BodyPublishers.ofString(creneau.getText()))
					.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
		}
		else {
			/* Affichage d'une boîte d'alerte en cas de tentative de suppression
			 * sans sélection de créneau
			 */
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Aucun item selectionné !");
			alert.showAndWait();
		}
		
	}

	@Override
	public void modifier(ActionEvent event) throws IOException {}
	

}
