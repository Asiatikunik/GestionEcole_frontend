package fr.uvsq.isty.gestionecole.modeles;

import java.util.HashSet;
import java.util.Set;

/**
 * Contient tous les objets manipules par l'application.
 * Expose des methodes permettant de les manipuler.
 * @author Simon
 *
 */
public class Ecole {
	//Ensemble des promotions de l'ecole
	private Set<Promotion> promotions;
	//Ensemble des sessions de l'ecole
	private Set<Session> sessions;
	//Ensemble des UE de l'ecole
	private Set<UniteEnseignement> unitesEnseignement;
	//Ensemble des creneaux temporels
	private Set<Creneau> creneaux;
	
	/**
	 * Construit une ecole avec des ensembles vides
	 */
	public Ecole(){
		//Tous les attributs sont initialises a vide
		this.promotions = new HashSet<Promotion>();
		this.sessions = new HashSet<Session>();
		this.unitesEnseignement = new HashSet<UniteEnseignement>();
		this.creneaux = new HashSet<Creneau>();
	}
	
	/**
	 * Cherche une promotion via son nom
	 * @param nom : le nom a rechercher
	 * @return La derniere promotion dont le nom correspond a nom ou une promotion vide si aucune ne correspond
	 */
	public Promotion getPromotionByNom(String nom) {
		Promotion retour = new Promotion();
		for(Promotion p : this.getPromotions()) {
			if(nom.equals(p.getNom())) {
				retour = p;			}
		}
		return retour;
	}
	
	/**
	 * Cherche une unite d'enseignemement via son nom
	 * @param nom : le nom a rechercher
	 * @return La derniere unite d'enseignement dont le nom correspond ou une unite d'enseignement vide si aucune ne correspond
	 */
	public UniteEnseignement getUEByNom(String nom) {
		UniteEnseignement retour = new UniteEnseignement();
		for(UniteEnseignement ue : this.getUnitesEnseignement()) {
			if(nom.equals(ue.getNom())){
				retour = ue;
			}
		}
		return retour;
	}
	
	/**
	 * Cherche un creneau par le resultat de sa methode toString()
	 * Utile car c'est ce qu'affiche l'IHM et ce que retourne la selection dans l'IHM
	 * @param string : le retour de la methode toString du creneau que l'on cherche
	 * @return le dernier creneau dont le toString() correspond ou un creneau vide si aucun ne correspond
	 */
//	@GetMapping(/getCreneauByString)
	public Creneau getCreneauByString(String string) {
		Creneau retour = new Creneau();
		for(Creneau cr : this.getCreneaux()) {
			if(string.equals(cr.toString())) {
				retour = cr;
			}
		}
		return retour;
	}
	
	
	/**
	 * Cherche une session par le resultat de la methode toString de son creneau
	 * fonctionnement identique a getCreneauByString 
	 * @param string : le retour de la methode toString du creneau de la session que l'on cherche
	 * @return la derniere session dont le toString() du creneau correspond ou une session vide si aucune ne correspond
	 */
	public Session getSessionByString(String string) {
		Session retour = new Session();
		for(Session s : this.getSessions()) {
			if(string.equals(s.getCreneau().toString())) {
				retour = s;
			}
		}
		return retour;
	}
	
	/**
	 * Getter pour promotions
	 * @return l'ensemble des promotions
	 */
	public Set<Promotion> getPromotions() {
		return promotions;
	}

	/**
	 * Setter pour promotions
	 * @param promotions : le nouvel ensemble des promotions
	 */
	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}
	
	/**
	 * Ajoute une promotion a l'ensemble des promotions
	 * @param promotion : promotion a ajouter
	 */
	public void addPromotion(Promotion promotion) {
		this.promotions.add(promotion);
	}
	
	/**
	 * Retire une promotion de l'ensemble des promotions
	 * @param promotion : promotion a retirer
	 */
	public void removePromotion(Promotion promotion) {
		this.promotions.remove(promotion);
	}

	/**
	 * Getter pour sessions
	 * @return l'ensemble des sessions
	 */
	public Set<Session> getSessions() {
		return sessions;
	}

	/**
	 * Setter pour sessions
	 * @param sessions : le nouvel ensemble des sessions
	 */
	public void setSessions(Set<Session> sessions) {
		this.sessions = sessions;
	}
	
	/**
	 * Ajoute une session a l'ensemble des sessions
	 * @param session : session a ajouter
	 */
	public void addSession(Session session) {
		this.sessions.add(session);
	}
	
	/**
	 * Retire une sessions de l'ensemble des sessions
	 * @param session : session a retirer
	 */
	public void removeSession(Session session) {
		this.sessions.remove(session);
	}

	/**
	 * Getter pour l'ensemble des UE
	 * @return l'ensemble des UE
	 */
	public Set<UniteEnseignement> getUnitesEnseignement() {
		return unitesEnseignement;
	}

	/**
	 * Setter pour l'ensemble des UE
	 * @param unitesEnseignement : le nouvel ensemble des UE
	 */
	public void setUnitesEnseignement(Set<UniteEnseignement> unitesEnseignement) {
		this.unitesEnseignement = unitesEnseignement;
	}
	
	/**
	 * Ajoute une UE a l'ensemble des UE
	 * @param ue : UE a ajouter a l'ensemble des UE
	 */
	public void addUniteEnseignement(UniteEnseignement ue) {
		this.unitesEnseignement.add(ue);
	}
	
	/**
	 * Retire une UE de l'ensemble des UE
	 * @param ue : UE a retirer de l'ensemble des UE
	 */
	public void removeUniteEnseignement(UniteEnseignement ue) {
		this.unitesEnseignement.remove(ue);
	}

	/**
	 * Getter pour l'ensemble des creneaux
	 * @return l'ensemble des creneaux
	 */
	public Set<Creneau> getCreneaux() {
		return creneaux;
	}

	/**
	 * Setter pour l'ensemble des creneaux
	 * @param creneaux : le nouvel ensemble des creneaux
	 */
	public void setCreneaux(Set<Creneau> creneaux) {
		this.creneaux = creneaux;
	}
	
	/**
	 * Ajoute un creneau a l'ensemble des creneaux
	 * @param creneau : creneau a ajouter a l'ensemble des creneaux
	 */
	public void addCreneau(Creneau creneau) {
		this.creneaux.add(creneau);
	}
	
	/**
	 * Retire un creneau de l'ensemble des creneaux
	 * @param creneau : creneau a retirer de l'ensemble des creneaux
	 */
	public void removeCreneau(Creneau creneau) {
		this.creneaux.remove(creneau);
	}

	public String toString() {
		String s = "";
		
		s = s + "Promotions\n------\n";
		for (Promotion p : this.promotions) {
			s = s + p.toString() + "\n";
		}
		s = s + "\n";
		
		s = s + "Sessions\n------\n";
		for (Session ue : this.sessions) {
			s = s + ue.toString() + "\n";
		}
		s = s + "\n";

		s = s + "Unites enseignement\n------\n";
		for (UniteEnseignement ue : this.unitesEnseignement) {
			s = s + ue.toString() + "\n";
		}
		s = s + "\n";
		
		s = s + "Creneaux\n------\n";
		for (Creneau c : this.creneaux) {
			s = s + c.toString() + "\n";
		}
		s = s + "\n";

		return s;
	}
}
