package models.impl.items;

import models.Player;
import models.Player.EFFECTS;

public class Path extends ItemImpl {
	// pv 0 : la glace est cassee
	// pv 1 : on peut marcher encore une fois sur la galce 
	// pv 2 : glace épaisse
	// ect
	private int pv;
		
	public Path(int pv, int x, int y) {
		super(x, y, '0', "", 0, false, false);
		if (pv < 0)
			throw new IllegalArgumentException("La vie de la glace ne peut pas <0");
		this.pv = pv;
	}
	
	public int getPV() {
		return this.pv;
	}
	
	@Override
	public boolean isReacheable() {
		return this.pv > 0;
	}

	@Override
	public char getChar() {
		if (this.pv == 0) 
			return 'O';
		else if (this.pv == 1)
			return 'x';
		else
			return 'X';
	}

	@Override
	public String getName() {
		return "Ice " + this.pv + "HP";
	}

	@Override
	public void onLeave(Player player) {
		if (!(player.getEffect() == EFFECTS.LIGHTNESS))
			this.pv -= 1;
	}
	
	public void destroy() {
		this.pv = 0;
	}
}
