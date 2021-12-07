package models.impl.items;

import models.Model;
import models.Player;
import models.Player.EFFECTS;

public class PotionLightness extends ItemImpl {
	private Model model;
	
	public PotionLightness(Model model, int x, int y) {
		super(x, y, 'L', "Lightness Potion", 1, true, false);
		this.model = model;
	}
	
	@Override
	public void onEnter(Player player) {
		player.setEffect(EFFECTS.LIGHTNESS, 6);
		this.model.removeItem(this);
	}
}
