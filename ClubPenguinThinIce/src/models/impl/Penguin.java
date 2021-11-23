package models.impl;

import models.Item;
import models.Player;

public class Penguin implements Item, Player {
	private int posLine;
	private int posColumn;
	
	public Penguin(int posLine, int posColumn) {
		this.posLine = posLine;
		this.posColumn = posColumn;
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
	public int getPosLine() {
		return this.posLine;
	}

	@Override
	public int getPosColumn() {
		return this.posColumn;
	}

	@Override
	public void onEnter() {
		
	}

	@Override
	public void onLeave() {
		
	}

	@Override
	public void setPosLine(int newPosLine) {
		this.posLine = newPosLine;
	}

	@Override
	public void setPosColumn(int newPosColumn) {
		this.posColumn = newPosColumn;
	}

	@Override
	public void addPosLine(int offsetPosLine) {
		this.posLine += offsetPosLine;
	}

	@Override
	public void addPosColumn(int offsetPosColumn) {
		this.posColumn += offsetPosColumn;
	}




}
