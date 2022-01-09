package models.impl.items;

import models.Player;
import models.impl.Map.DIRECTION;

public class Penguin extends MovableImpl implements Player {
	private EFFECTS currentEffect;
	private int currentEffectDuration;
	private boolean isAlive;
	
	public Penguin(int x, int y) {
		super(x, y, 'P', "Penguin", 3, false, false);
		this.isAlive = true;
	}
	
	
	@Override
	public boolean isAlive() {
		return this.isAlive;
	}
	
	@Override
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	/**
	 * Appel de la méthode par défaut "move" puis diminution d'un point
	 * l'effet (si le joueur a un effet).
	 */
	public void move(DIRECTION d) {
		super.move(d);
		
		if (this.currentEffectDuration > 0)
			this.currentEffectDuration--;
		
		if (this.currentEffectDuration == 0) 
			this.currentEffect = null;
	}

	@Override
	public EFFECTS getEffect() {
		return this.currentEffect;
	}

	@Override
	public void setEffect(EFFECTS e, int effectDuration) {
		this.currentEffect = e;
		this.currentEffectDuration = effectDuration;
	}
}
