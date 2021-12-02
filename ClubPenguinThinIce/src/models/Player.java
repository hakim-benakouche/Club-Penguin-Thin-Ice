package models;

import models.impl.Map.DIRECTION;

public interface Player extends Item {
//	public int getPosLine();
//	public int getPosColumn();
//	public void setPosLine(int newPosLine);
//	public void setPosColumn(int newPosColumn);
//	public void addPosLine(int offsetPosLine);
//	public void addPosColumn(int offsetPosColumn);
	
	public void move(DIRECTION d);
}
