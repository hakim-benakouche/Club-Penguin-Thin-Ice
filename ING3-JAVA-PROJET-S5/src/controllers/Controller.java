package controllers;

import models.impl.Map.DIRECTION;

public interface Controller {
	/**
	 * Attends la saisie de l'utilisateur pour conna�tre la direction dans laquelle
	 * il souhaite se d�placer.
	 * @return Direction souhait�e par le joueur pour d�placer le pion.
	 */
	
	public DIRECTION waitForUserInput();
	
	/**
	 * Joue le mouvement du joueur.
	 * @return TRUE si le joueur est arriv� sur un item de fin (PathMapEnd), FALSE sinon.
	 */
	public boolean playUserMove();
	
	/**
	 * Permet de supprimer le controller (pour le controller swing, de supprimer la fenetre)
	 */
	public void destroy();
}
