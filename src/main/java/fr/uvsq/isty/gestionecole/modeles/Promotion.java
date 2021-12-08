package fr.uvsq.isty.gestionecole.modeles;

/**
 * Une promotion d'etudiants
 * Represente un ensemble d'etudiants qui seront diplomes la meme annee
 * Decrit par un nom de promotion, par exemple "IATIC5"
 * @author Simon
 *
 */
public class Promotion {
	//l'annee de remise des diplomes
	int anneeDiplome;
	//le nom de la promotion
	String nom;
	
	/**
	 * Construit une promotion
	 * @param anneeDiplome : l'annee de remise des diplomes
	 * @param nom : le nom de la promotion
	 */
	public Promotion(int anneeDiplome, String nom) {
		super();
		this.anneeDiplome = anneeDiplome;
		this.nom = nom;
	}

	/**
	 * Construit une promotion vide
	 */
	public Promotion() {
		super();
	}

	/**
	 * Getter pour l'annee de remise des diplomes
	 * @return l'annee de remise des diplomes
	 */
	public int getAnneeDiplome() {
		return anneeDiplome;
	}

	/**
	 * Setter pour l'annee de remise des diplomes
	 * @param anneeDiplome : la nouvelle annee de remise des diplomes
	 */
	public void setAnneeDiplome(int anneeDiplome) {
		this.anneeDiplome = anneeDiplome;
	}
	
	/**
	 * Getter pour le nom de la promotion
	 * @return le nom de la promotion
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Setter pour le nom de la promotion
	 * @param nom : le nouveau nom de la promotion
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String toString() {
		return "{\"anneeDiplome\":"+this.anneeDiplome+",\"nom\":\""+this.nom+"\"}";
	}
	
}
