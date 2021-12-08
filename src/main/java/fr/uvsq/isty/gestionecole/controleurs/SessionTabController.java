package fr.uvsq.isty.gestionecole.controleurs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.uvsq.isty.gestionecole.MainApp;
import fr.uvsq.isty.gestionecole.modeles.Creneau;
import fr.uvsq.isty.gestionecole.modeles.Ecole;
import fr.uvsq.isty.gestionecole.modeles.Promotion;
import fr.uvsq.isty.gestionecole.modeles.Session;
import fr.uvsq.isty.gestionecole.modeles.UniteEnseignement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Classe jouant le rôle de contrôleur pour la gestion des sessions
 * @author Lucien
 *
 */
public class SessionTabController implements Controller {
	
	// Objet contenant l'ensemble des modèles
	private Ecole ecole;
	
	@FXML
	AnchorPane pane;
	
	//Ajout des listes nécessaires à mettre à jour via le FXML
	@FXML
	ListView<Label> ListViewUE;
	
	@FXML
	ListView<Label> ListViewCreneau;
	
	@FXML
	ListView<Label> ListViewPromotion;
	
	@FXML
	ListView<Label> ListViewSession;
	
	@FXML
	ListView<Label> ListViewCreneauModify;
	
	// Constructeur par défaut
	public SessionTabController() {
    }
	
	/**
	 * Construit un objet Session avec le modèle
	 * @param ecole : le modèle à inclure dans l'objet Session
	 */
	public SessionTabController(Ecole ecole) {
		this.ecole = ecole;
	}

	/**
	 * Initialise la vue
	 */
	@FXML
	public void initialize() {
        this.ListViewCreneau.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.rafraichir();
    }
	
	/**
	 * Rafraichis les listes d'affichage
	 */
	@FXML
    private void rafraichir() {
		 //Rafraichis les unités enseignements dans la liste
		 Set<UniteEnseignement> ues = this.ecole.getUnitesEnseignement();
		 List<Label> ueLabels = new ArrayList<Label>();
		 for(UniteEnseignement ue : ues) {
			 ueLabels.add(new Label(ue.getNom()));			 
		 }
		 this.ListViewUE.getItems().setAll(ueLabels);
		 
		 //Rafraichis les créneaux dans la liste
		 Set<Creneau> crs = this.ecole.getCreneaux();
		 List<Label> crsLabels = new ArrayList<Label>();
		 for(Creneau cr : crs) {
			 crsLabels.add(new Label(cr.toString()));			 
		 }
		 this.ListViewCreneau.getItems().setAll(crsLabels);
		 
		 //Rafraichis les promotions dans la liste
		 Set<Promotion> promos = this.ecole.getPromotions();
		 List<Label> pLabels = new ArrayList<Label>();
		 for(Promotion p : promos) {
			 pLabels.add(new Label(p.getNom()));			 
		 }
		 this.ListViewPromotion.getItems().setAll(pLabels);
		 
		 //Rafraichis les créneaux à modifier dans la liste
		 Set<Creneau> nouveauCreneaux = this.ecole.getCreneaux();
		 List<Label> nvCrLabels = new ArrayList<Label>();
		 for(Creneau nvc : nouveauCreneaux) {
			 nvCrLabels.add(new Label(nvc.toString()));
		 }
		 this.ListViewCreneauModify.getItems().setAll(nvCrLabels);
		 
		 //Rafraichis les sessions dans la liste
		 Set<Session> sessions = this.ecole.getSessions();
		 List<Label> sLabels = new ArrayList<Label>();
		 for(Session ss : sessions) {
			 sLabels.add(new Label(ss.getCreneau().toString()));
		 }
		 this.ListViewSession.getItems().setAll(sLabels);
		 
	}
	
	/**
	 * Créé une session à partir d'une UE, un créneau et une promotion
	 * @param event : l'évènement déclenchant l'appel de cette méthode
	 */
	@Override
	@FXML
	public void creer(ActionEvent event) throws IOException, URISyntaxException, InterruptedException {
		//On récupère les textes de l'ue , du creneau et de la promotion sélectionner
		
		if(this.ListViewUE.getSelectionModel().getSelectedItem() == null)	{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Selectionner une unite d'enseignement, un creneau et une promotion!");
			alert.showAndWait();
		} else if(this.ListViewCreneau.getSelectionModel().getSelectedItem() == null){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Selectionner une unite d'enseignement, un creneau et une promotion!");
			alert.showAndWait();
		} else if(this.ListViewPromotion.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Selectionner une unite d'enseignement, un creneau et une promotion!");
			alert.showAndWait();
		} else {
			String ueNom = this.ListViewUE.getSelectionModel().getSelectedItem().getText();
			String promotion = this.ListViewPromotion.getSelectionModel().getSelectedItem().getText();
			List<Label> creneau = this.ListViewCreneau.getSelectionModel().getSelectedItems();

			//On retrouve l'objet ue en question
			UniteEnseignement unite = this.ecole.getUEByNom(ueNom);
			
			//On retrouve les objets créneaux
			List<Creneau> cre = new ArrayList<Creneau>();
			for (Label c : creneau) {	//Creneau existants
				cre.add(this.ecole.getCreneauByString(c.getText()));
			}
			//On retrouve l'objet promotion
			Promotion p = ecole.getPromotionByNom(promotion);
							
			//On créer autant de sessions que de créneau selectionner
			for(Creneau c : cre) {
				Session session = new Session(unite,p,c);
				this.ecole.addSession(session);	
				this.ListViewSession.getItems().add(
						new Label(session.getCreneau().toString())
						);
			}

			String lien = "http://localhost:8082/session";
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(lien))
					.headers("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(
							"{ " +
									"\"uniteEnseigment\": {" +
										"\"sigle\": \""+ unite.getSigle() +"\", " +
										"\"nom\": \""+ unite.getNom() + "\" " +
									"}, "+
									"\"promotion\": {" +
										"\"anneeDiplome\": \""+ p.getAnneeDiplome() +"\", " +
										"\"nom\": \""+ p.getNom() + "\" " +
									"}, "+
									"\"creneau\": {" +
										"\"date\": \""+ cre.get(cre.size()-1).getDate() +"\" , " +
										"\"debut\": \""+ cre.get(cre.size()-1).getDebut() +"\" , " +
										"\"fin\": \""+ cre.get(cre.size()-1).getFin() + "\" " +
									"} "+
							"}"
					))
					.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
		}
	}


	/**
	 * Supprime une session
	 * @param event : l'évènement déclenchant l'appel de cette méthode
	 */
	@Override
	@FXML
	public void supprimer(ActionEvent event) throws IOException  {
		Label label = this.ListViewSession.getSelectionModel().getSelectedItem();
		
		//Si on a bien selectionner une session
		if(label !=null) {
			Set<Session> sessions = this.ecole.getSessions();
			//On supprime la session
			sessions.removeIf(s -> s.getCreneau().toString().equals(label.getText()));
			this.ecole.setSessions(sessions);
			
			//On met à jour la liste des sessions
			List<Label> labels = new ArrayList<Label>();
			for(Session s : sessions) {
				labels.add(new Label(s.getCreneau().toString()));
			}
			this.ListViewSession.getItems().setAll(labels);
		} else { //Erreur car aucune session selectionner
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Aucun item selectionne !");
			alert.showAndWait();
		}
	}

	
	/**
	 * On modifie une session pour changer son creneau
	 * @param event : l'évènement déclenchant l'appel de cette méthode
	 */
	@Override
	public void modifier(ActionEvent event) throws IOException {
		// TODO Auto-generated method stub
		//Erreur car pas de sessions selectionner
		if(this.ListViewSession.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Aucun item selectionne !");
			alert.showAndWait();
		}
		//Erreur car pas de creneau selectionner
		else if(this.ListViewCreneauModify.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Aucun item selectionne !");
			alert.showAndWait();
		} else {
			//On prend la session a modifier et on indique qu'elle sera a modifer
			String sessionText = this.ListViewSession.getSelectionModel().getSelectedItem().getText();
			Session session = ecole.getSessionByString(sessionText);
			
			//On recupere la session et le creneau a modifier
			Label textCreneauSelected = this.ListViewCreneauModify.getSelectionModel().getSelectedItem();
		    Creneau creneauSelected = this.ecole.getCreneauByString(textCreneauSelected.getText());
		    Set<Session> sessionsExistantes = this.ecole.getSessions();
		    
		    //On modifie la session avec le creneau selectionner
			session.setCreneau(creneauSelected);

		}
		rafraichir();
	}
}

