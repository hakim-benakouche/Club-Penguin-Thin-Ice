package models.impl;

import models.Item;

public class Empty implements Item {
	
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

}
