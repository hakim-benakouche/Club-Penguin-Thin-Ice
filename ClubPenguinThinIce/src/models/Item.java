package models;

public interface Item {
	public boolean isReacheable();
	public char getChar();
	public String getName();
	
	public void onEnter();
	public void onLeave();
	
	public int getX();
	public int getY();
	public void setX(int x);
	public void setY(int y);
	
}
