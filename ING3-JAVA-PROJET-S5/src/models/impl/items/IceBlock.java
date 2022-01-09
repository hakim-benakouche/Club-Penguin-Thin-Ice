package models.impl.items;

import models.Item;
import models.Model;
import models.impl.Map.DIRECTION;

public class IceBlock extends MovableImpl {
	private Model model;
	
	public IceBlock(Model model, int x, int y) {
		super(x, y, 'I', "Ice block", 2, false, true);
		this.model = model;
	}
	
	@Override
	public boolean isPushable() {
		for (Item i : this.model.getItemsAround(this)) {
			if (i.isReacheable())
				return true;
		}
		return false;
	}

	@Override
	/**
	 * Tant que le bloc dans la direction to est "reacheable", le bloc de glace
	 * continue d'avancer. Similaire � la m�thode onPush de la Tondeuse (Mower)
	 */
	public void onPush(DIRECTION to) {
		Item item = this.model.getItem(this, to);
		boolean loop = true;
		while (loop) {
			if (!item.isReacheable()) {
				loop = false;
			} else {
				this.move(to);
			}
			item = this.model.getItem(this, to);
		}
		System.out.println("\nVous venez de pousser une brique de mur !");
	}

}
