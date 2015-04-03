package com.deckerhead.tictactoe;

public class TTTSpacePosition {

	private int positionX;
	private int positionY;
	
	public TTTSpacePosition(int x, int y) {
		positionX = x;
		positionY = y;
	}
	
	public void setPosition(int x, int y) {
		positionX = x;
		positionY = y;
	}

	public int getPositionX() {
		return positionX;
	}
	
	public int getPositionY() {
		return positionY;
	}
}
