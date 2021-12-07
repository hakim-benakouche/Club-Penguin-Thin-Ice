package controllers;

import models.impl.Map.DIRECTION;

public interface Controller {
	/**
	 * Attends la saisie de l'utilisateur pour connaître la direction dans laquelle
	 * il souhaite se déplacer.
	 * @return Direction souhaitée par le joueur pour déplacer le pion.
	 */
	
	public DIRECTION waitForUserInput();
	
	/**
	 * Joue le mouvement du joueur.
	 * @return TRUE si le joueur est arrivé sur un item de fin (PathMapEnd), FALSE sinon.
	 */
	public boolean playUserMove();
	
	/**
	 * Permet de supprimer le controller (pour le controller swing, de supprimer la fenetre)
	 */
	public void destroy();
}
