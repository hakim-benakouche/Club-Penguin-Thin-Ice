package models;

import models.impl.Map.DIRECTION;

public interface Movable extends Item {
	/**
	 * Permet de déplacer un item dans la direction souhaitée.
	 * Est utilisé par tous les items pouvant changer de place (ex. Player, Mower, Ennemy...)
	 * @param direction vers laquelle on souhaite déplacement l'item
	 */
	public void move(DIRECTION direction);
}
