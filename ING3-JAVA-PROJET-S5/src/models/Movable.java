package models;

import models.impl.Map.DIRECTION;

public interface Movable extends Item {
	/**
	 * Permet de d�placer un item dans la direction souhait�e.
	 * Est utilis� par tous les items pouvant changer de place (ex. Player, Mower, Ennemy...)
	 * @param direction vers laquelle on souhaite d�placement l'item
	 */
	public void move(DIRECTION direction);
}
