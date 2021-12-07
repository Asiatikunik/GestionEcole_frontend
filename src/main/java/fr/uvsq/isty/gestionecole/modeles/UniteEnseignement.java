package fr.uvsq.isty.gestionecole.modeles;

/**
 * Une unite d'enseignement (ou matiere ou module).
 * Comporte un nom d'usage et un sigle officiel/administratif.
 * @author Simon
 *
 */
public class UniteEnseignement {

	String sigle; //le sigle
	String nom; //le nom
	
	/**
	 * Construit une UE
	 * @param sigle : le sigle
	 * @param nom : le nom
	 */
	public UniteEnseignement(String sigle, String nom) {
		this.sigle = sigle;
		this.nom = nom;
	}
	
	/**
	 * Construit une UE vide
	 */
	public UniteEnseignement() {
	}

	/**
	 * Getter pour le sigle
	 * @return le sigle
	 */
	public String getSigle() {
		return sigle;
	}

	/**
	 * Setter pour le sigle
	 * @param sigle : le nouveau sigle
	 */
	public void setSigle(String sigle) {
		this.sigle = sigle;
	}

	/**
	 * Getter pour le nom
	 * @return le nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter pour le nom
	 * @param nom : le nouveau nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * String pour l'affichage
	 * @return le nom de l'UE
	 */
	public String toDisplay() {
		return this.nom;
	}
	
	public String toString() {
		return "Sigle : " + sigle + " - Nom : " + nom;
	}
}
