package fr.uvsq.isty.gestionecole.controleurs;

import java.io.IOException;

import fr.uvsq.isty.gestionecole.modeles.Ecole;
import fr.uvsq.isty.gestionecole.modeles.Promotion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;

/**
 * Classe jouant le rôle de contrôleur pour la gestion des promotions
 * @author Joshua
 *
 */
public class PromotionTabController implements Controller {
	
	// Objet contenant l'ensemble des modèles
	private Ecole ecole;

	// Champ de saisie pour le nom de la promotion
	@FXML
	private TextField textFieldNom;
	// Champ de saisie pour l'année de la promotion
	@FXML
	private TextField textFieldAnnee;
	// Composant affichant les promotions existantes
	@FXML
	private ListView<HBox> listViewPromotion;
	
	/**
	 * Constructeur
	 * @param ecole: objet contenant l'ensemble des modèles
	 */
	public PromotionTabController(Ecole ecole) {
		this.ecole = ecole;
	}
	
	@FXML
	public void initialize() {
		this.ecole.getPromotions().forEach(p -> this.ajouterPromotionListe(p));
	}
	
	/**
	 * Méthode permettant de créer une promotion
	 * @param event : action déclenchant l'appel à cette méthode
	 */
	@Override
	@FXML
	public void creer(ActionEvent event) throws IOException  {
		// Récupération des valeurs saisies dans la vue
		String nomPromotion = textFieldNom.getText();
		String anneePromotion = textFieldAnnee.getText();
		
		if (nomPromotion == null || anneePromotion == null || !anneePromotion.matches("^[0-9]{4}$")) {
			// Affichage d'une boîte d'alerte en cas de champs non rempli
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Un champ est vide ou est mal écrit !");
			alert.showAndWait();
		}
		else {
			Promotion prom = new Promotion(Integer.parseInt(anneePromotion), nomPromotion);
			// Ajout de la nouvelle promotion au modèle
			this.ecole.addPromotion(prom);
			
			// Ajout de la nouvelle promotion à la vue
			this.ajouterPromotionListe(prom);
			
			// Réinitialisation des valeurs dans la vue
			this.textFieldNom.setText(null);
			this.textFieldAnnee.setText(null);
		}
	}
	
	/**
	 * Ajoute une promotion à la liste affichée
	 * @param p : promotion à ajouter
	 */
	public void ajouterPromotionListe(Promotion p) {
		// Création des composants de la vue permettant l'affichage
		HBox globalHBox = new HBox();
		globalHBox.setPadding(new Insets(5, 5, 5, 5));
		
		HBox ligne = new HBox();
		
		Label labelCreneau = new Label(p.toString());
		ligne.getChildren().add(labelCreneau);
		
		globalHBox.getChildren().add(ligne);
		// Ajout de la promotion dans la liste affichée
		this.listViewPromotion.getItems().add(globalHBox);
	}
	
	/**
	 * Méthode permettant de supprimer une promotion
	 * @param event : action déclenchant l'appel à cette méthode
	 */
	@Override
	@FXML
	public void supprimer(ActionEvent event) throws IOException  {
		// Récupération de l'élément sélectionné
		HBox box = this.listViewPromotion.getSelectionModel().getSelectedItem();
		
		if (box != null) {
			// Récupération de la promotion dans la sélection
			HBox selection = (HBox) box.getChildren().get(0);
			Label promotion = (Label) selection.getChildren().get(0);
			
			// Suppression de la promotion dans le modèle
			this.ecole.getPromotions().removeIf(p -> p.toString().equals(promotion.getText()));
			// Réinitialisation de la vue
			this.listViewPromotion.getItems().clear();
			this.ecole.getPromotions().forEach(p -> this.ajouterPromotionListe(p));
		}
		else {
			/* Affichage d'une boîte d'alerte en cas de tentative de suppression
			 * sans sélection de promotion
			 */
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Aucun item selectionné !");
			alert.showAndWait();
		}
	}
	
	@Override
	@FXML
	public void modifier(ActionEvent event) throws IOException {
	}

}
