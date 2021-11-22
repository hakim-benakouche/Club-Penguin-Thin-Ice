package models.impl;

import models.Item;

public class Wall implements Item {

	@Override
	public boolean isReacheable() {
		return false;
	}

	@Override
	public char getChar() {
		return '#';
	}

	@Override
	public String getName() {
		return "Wall";
	}

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeave() {
		// TODO Auto-generated method stub
		
	}

}
