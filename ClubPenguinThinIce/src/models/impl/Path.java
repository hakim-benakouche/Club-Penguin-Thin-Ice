package models.impl;

import models.Item;

public class Path implements Item {
	// pv 0 : la glace est cass�e
	// pv 1 : on peut marcher encore une fois sur la galce 
	// ect
	private int pv;
	
	public Path(int pv) {
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
	public void onEnter() {
		//this.pv -= 1;
		
	}

	@Override
	public void onLeave() {
		this.pv -= 1;
	}

}
