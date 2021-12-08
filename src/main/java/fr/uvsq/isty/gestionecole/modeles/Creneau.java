package fr.uvsq.isty.gestionecole.modeles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import fr.uvsq.isty.gestionecole.controleurs.CreneauTabController;

/**
 * Un creneau temporel qui represente une periode avec une heure de debut et une heure de fin
 * a une date specifique.
 * @author Simon
 *
 */
public class Creneau {
	//Date du creneau
	LocalDate date;
	//Horaire de debut du creneau
	LocalTime debut;
	//Horaire de fin du creneau
	LocalTime fin;
	
	/**
	 * Construit un creneau temporel
	 * @param date : date du creneau
	 * @param debut : horaire de debut du creneau
	 * @param fin : horaire de fin du creneau
	 */
	public Creneau(LocalDate date, LocalTime debut, LocalTime fin) {
		super();
		this.date = date;
		this.debut = debut;
		this.fin = fin;
	}
	
	/**
	 * Construit un creneau temporel vide
	 */
	public Creneau() {
		super();
	}
	
	/**
	 * Getter pour la date
	 * @return la date
	 */
	public LocalDate getDate() {
		return date;
	}
	
	/**
	 * Setter pour la date
	 * @param date : nouvelle date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * Getter pour l'horaire de debut
	 * @return l'horaire de debut
	 */
	public LocalTime getDebut() {
		return debut;
	}

	/**
	 * Setter pour l'horaire de debut
	 * @param debut : le nouvel horaire de debut
	 */
	public void setDebut(LocalTime debut) {
		this.debut = debut;
	}

	/**
	 * Getter pour l'horaire de fin
	 * @return l'horaire de fin
	 */
	public LocalTime getFin() {
		return fin;
	}

	/**
	 * Setter pour l'horaire de fin
	 * @param fin : le nouvel horaire de fin
	 */
	public void setFin(LocalTime fin) {
		this.fin = fin;
	}
	
	public String toString() {
		return "{\"date\":\""+this.date.toString()+"\",\"debut\": \""+ this.debut.toString()+"\",\"fin\": \""+ this.fin.toString() +"\"}";
	}
	

}
