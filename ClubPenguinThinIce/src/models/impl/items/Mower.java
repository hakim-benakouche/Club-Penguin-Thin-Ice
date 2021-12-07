package models.impl.items;

import models.Item;
import models.Model;
import models.impl.Map.DIRECTION;

public class Mower extends MovableImpl {
	private Model model;
	
	public Mower(Model model, int x, int y) {
		super(x, y, 'M', "Mower", 1, false, true);
		this.model = model;
	}

	@Override
	/**
	 * Tant que la tondeuse peut avancer (item recheable) dans la même direction selon laquelle 
	 * le joueur l'a poussée : elle avance.
	 * Si elle passe par dessus de la glace : elle la détruit (en définisant les PV à 0).
	 */
	public void onPush(DIRECTION to) {
		Item item = this.model.getItem(this, to);
		boolean loop = true;
		while (loop) {
			if (item instanceof Path) {
				((Path) item).destroy();
				this.move(to);
			} else if (!item.isReacheable()) {
				loop = false;
				this.model.removeItem(this);
			} else {
				this.move(to);
			}
			item = this.model.getItem(this, to);
		}
	}

}
