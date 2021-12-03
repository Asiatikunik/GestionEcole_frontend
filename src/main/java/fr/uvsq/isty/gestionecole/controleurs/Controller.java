package fr.uvsq.isty.gestionecole.controleurs;

import java.io.IOException;

import javafx.event.ActionEvent;

/**
 * Interface exposant les méthodes implémentées par
 * les contrôleurs
 * @author Josh
 *
 */
public interface Controller {
	public void creer(ActionEvent event) throws IOException;
	public void supprimer(ActionEvent event) throws IOException;
	public void modifier(ActionEvent event) throws IOException;
}