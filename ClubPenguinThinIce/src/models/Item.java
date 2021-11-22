package models;

public interface Item {
	public boolean isReacheable();
	public char getChar();
	public String getName();
	
	public void onEnter();
	public void onLeave();
}
