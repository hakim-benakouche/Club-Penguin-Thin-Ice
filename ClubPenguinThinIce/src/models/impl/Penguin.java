package models.impl;

import models.Item;
import models.Player;
import models.impl.Map.DIRECTION;

public class Penguin implements Item, Player {
	private int x;
	private int y;
	
	public Penguin(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean isReacheable() {
		return false;
	}
	
	@Override
	public char getChar(){
		return 'P';
	}
	
	@Override
	public String getName(){
		return "Player";
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
	
	public void move(DIRECTION d) {
		if (d == DIRECTION.UP) {
			this.x--;	
		} else if (d == DIRECTION.LEFT) {
			this.y--;	
		} else if (d == DIRECTION.DOWN) {
			this.x++;	
		} else if (d == DIRECTION.RIGHT) {
			this.y++;	
		}
	}
	
	public String toString() {
		return this.getName() + " " + this.getChar() + " (" + this.getX() +", " + this.getY() +")";
	}
}
