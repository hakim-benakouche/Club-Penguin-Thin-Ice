package models.impl;

import models.Item;

public class Path implements Item {
	// pv 0 : la glace est cassee
	// pv 1 : on peut marcher encore une fois sur la galce 
	// pv 2 : glace épaisse
	// ect
	private int pv;
		
	private int x;
	private int y;
	
	public Path(int pv, int x, int y) {
		if (pv < 0)
			throw new IllegalArgumentException("La vie de la glace ne peut pas <0");
		this.pv = pv;
		this.x = x;
		this.y = y;
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
	
	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}
	
	public String toString() {
		return this.getName() + " " + this.getChar() + " (" + this.getX() +", " + this.getY() +")";
	}
	
	
}
