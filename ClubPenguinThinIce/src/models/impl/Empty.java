package models.impl;

import models.Item;

public class Empty implements Item {
	private int x;
	private int y;
	
	public Empty(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean isReacheable() {
		return false;
	}

	@Override
	public char getChar() {
		return '.';
	}

	@Override
	public String getName() {
		return "Empty";
	}

	@Override
	public void onEnter() {
	}

	@Override
	public void onLeave() {
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
