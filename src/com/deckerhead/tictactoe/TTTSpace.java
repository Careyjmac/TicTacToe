/*
 * The purpose of this class is to 
 * provide objects for each space
 * in the tic-tac-toe grid.
 */
package com.deckerhead.tictactoe;

public class TTTSpace {

	/*
	 * Static values for the various weapons.
	 * Note that the values are hexadecimal.
	 * This is derived from the first letter of
	 * the weapon. 
	 * i.e. O is the fifteenth letter of the
	 * alphabet, 15 in hexadecimal is F.
	 */
	public static final int UNOCCUPIED = 0x0;
	public static final int WEAPON_X = 0x18;
	public static final int WEAPON_O = 0xF;

	private final TTTSpacePosition position;
	// The position object.

	private int occupancy = UNOCCUPIED;
	// The occupancy.

	public TTTSpace(int x, int y) {
		position = new TTTSpacePosition(x, y);
	}
	// Constructor, with two integers for position.

	public TTTSpace(TTTSpacePosition pos) {
		position = pos;
	}
	// Constructor, with position object for position.

	public int getOccupancy() {
		/*
		 * Returns the occupancy.
		 */
		return occupancy;
	}

	public void setOccupancy(int occupied) {
		/*
		 * Sets the occupancy.
		 */
		occupancy = occupied;
	}

	public TTTSpacePosition getPosition() {
		/*
		 * Returns the position as an object.
		 */
		return position;
	}
}
