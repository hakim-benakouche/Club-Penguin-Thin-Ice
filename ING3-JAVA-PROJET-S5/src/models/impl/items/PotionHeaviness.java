package models.impl.items;

import models.Model;
import models.Player;
import models.Player.EFFECTS;

public class PotionHeaviness extends ItemImpl {
	private Model model;
	
	public PotionHeaviness(Model model, int x, int y) {
		super(x, y, 'H', "Heaviness Potion", 1, true, false);
		this.model = model;
	}
	
	@Override
	public void onEnter(Player player) {
		int duration = 6;
		player.setEffect(EFFECTS.HEAVINESS, duration);
		this.model.removeItem(this);
		System.out.println("\nPotion de lourdeur consommée : vous devenez lourd pendant les " +duration+ " prochains déplacements !");
	}
}