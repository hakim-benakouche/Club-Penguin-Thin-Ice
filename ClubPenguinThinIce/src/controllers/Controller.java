package controllers;

import models.impl.Map.DIRECTION;

public interface Controller {
	public DIRECTION waitForUserInput();
	public boolean playUserMove();
}
