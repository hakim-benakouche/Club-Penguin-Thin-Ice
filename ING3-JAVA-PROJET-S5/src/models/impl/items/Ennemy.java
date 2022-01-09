package models.impl.items;

import models.Item;
import models.Model;
import models.Player;
import models.impl.Map.DIRECTION;

public class Ennemy extends MovableImpl {
	
	private Model model;
	private DIRECTION direction;
	
	
	public Ennemy(Model model, int x, int y, DIRECTION direction) {
		super(x, y, 'E', "Ennemy", 2, false, false);
		
		this.model = model;
		this.direction = direction;
	}
	
	/**
	 * Si l'item dans la direction du mob est reacheable : il avance dans cette direction.
	 * Sinon : on lui fait changer de direction pour la direction opposée à la sienne.
	 * 
	 * Après le déplacement : on vérifie si le joueur est côté. 
	 * Si oui : on le tue et la carte est finie.
	 */
	public void moveDefault() {
		if (this.model.getItem(this, this.direction).isReacheable()) {
			this.move(direction);
		} else {
			if (this.direction == DIRECTION.UP)
				this.direction = DIRECTION.DOWN;
			else if (this.direction == DIRECTION.DOWN)
				this.direction = DIRECTION.UP;
			else if (this.direction == DIRECTION.RIGHT)
				this.direction = DIRECTION.LEFT;
			else if (this.direction == DIRECTION.LEFT)
				this.direction = DIRECTION.RIGHT;
		}
		
		if (this.getX() == this.model.getPlayer().getX() &&
				this.getY() == this.model.getPlayer().getY())
			this.model.getPlayer().setAlive(false);
		
		for (Item item : this.model.getItemsAround(this)) {
			if (item instanceof Player) {
				((Player) item).setAlive(false);
			}
		}
	}

}
