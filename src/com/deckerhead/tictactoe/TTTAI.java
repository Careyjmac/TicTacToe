package com.deckerhead.tictactoe;

import java.util.Random;
import java.util.ArrayList;
//import java.lang.Math;

/**
 * @author Harley Fioretti, Carey MacDonald
 * @company DeckerHead Developers
 * 
 */

public class TTTAI {

	private final int weapon;
	/*
	 * Difficulty will be as follows: Hardest = 0, hard = 1, normal = 2, easy = 3, easiest = 4.
	 * Hardest is picking one of the highest priority spot ONLY
	 * Hard is picking between the highest priority spots and the second highest priority spots
	 * Normal would change from move to move, sometimes operating as easy and others as hardest (Randomly)
	 * Easy is dumbAI
	 * Easiest is picking the worst priority spots ONLY
	 * */
	private int difficulty, choice;
	Random ran;
	private boolean checkHappened = false; // used for Diag. check and algorithm below.
	private boolean algorithm_Is_Irrelevant = false; 
	int occupancy[][] = new int[3][3]; //Used to define whether each space is occupied and with what.


	/**
	 * the AI is always considered to be player two even if it goes first.
	 * the reference variable (ie. TTTAI test = new TTTAI(*weapon of choice*);)
	 * should be instantiated before any moves are made in either the
	 * single player or the campaign. Harley F.
	 * */

	/**
	 * When making a move, the class TTTAI.java will be called in the following fashion: 
	 * ---> TTTAI test =new TTTAI(weapon, difficulty); <---
	 * ---> test.makeMoveSP(); <---
	 * ---> test.makeMoveCP(); <---
	 * the "SP" following the words "makeMove" is to reference the single player
	 * activity that will be used. omnia idem for campaign ---> "CP"
	 * */
	
	public TTTAI(int Weapon) {
		weapon = Weapon;

	}

	//single-player method
	public void makeMoveSP(int Difficulty) {
		TTTSpacePosition placeMarker = new TTTSpacePosition(3,3);
		int playerOneWeapon=TicTacToeSinglePlayerActivity.getPlayerOneWeapon();
		int[][] weight = new int[3][3];
		difficulty = Difficulty;
		ran = new Random();

		for (int n = 0; n < 3; n++) 
			for (int j = 0; j < 3; j++)
				occupancy[n][j]=TicTacToeSinglePlayerActivity.grid[n][j].getOccupancy();

		if (isBoardEmpty())
		{
			placeMarker = dumbAI(occupancy);
		}
		else
		{

			//get difficulty from singleplayer activity
			//Hardest
			if(difficulty==0)
			{
				//If a win is possible with the current Occupancy of the grid, then loc[] will equal that 2d position.
				placeMarker = checkWin(occupancy, weapon);

				checkHappened = false;

				//If a win is not possible, the program will check if it is possible to block the human player from a win.
				if (!checkWorked(placeMarker))
				{
					placeMarker = checkBlock(occupancy, playerOneWeapon);
					checkHappened = false;
					//If a win or a block is not possible, then an algorithm will choose the best placement. 
					if (!checkWorked(placeMarker))
					{
						weight = algorithm(occupancy);
						checkHappened = false;
					}
				}
				placeMarker=Hardest(weight);
			}
			//Hard
			else if(difficulty==1)
			{
				//If a win is possible with the current Occupancy of the grid, then loc[] will equal that 2d position.
				placeMarker = checkWin(occupancy, weapon);

				checkHappened = false;

				//If a win is not possible, the program will check if it is possible to block the human player from a win.
				if (!checkWorked(placeMarker))
				{
					placeMarker = checkBlock(occupancy, playerOneWeapon);
					checkHappened = false;
					//If a win or a block is not possible, then an algorithm will choose the best placement. 
					if (!checkWorked(placeMarker))
					{
						weight = algorithm(occupancy);
						checkHappened = false;
					}
				}
				placeMarker=Hard(weight);
			}
			//Normal
			else if(difficulty==2)
			{
				int choice=(int)(Math.random());
				//If a win is possible with the current Occupancy of the grid, then loc[] will equal that 2d position.
				placeMarker = checkWin(occupancy, weapon);

				checkHappened = false;

				//If a win is not possible, the program will check if it is possible to block the human player from a win.
				if (!checkWorked(placeMarker))
				{
					placeMarker = checkBlock(occupancy, playerOneWeapon);
					checkHappened = false;
					//If a win or a block is not possible, then an algorithm will choose the best placement. 
					if (!checkWorked(placeMarker))
					{
						if(choice==0)
						{
							weight = algorithm(occupancy);
							checkHappened = false;
							placeMarker=Hardest(weight);
						}
						else
						{
							placeMarker=dumbAI(occupancy);
						}
					}
				}
			}
			//Easy
			else if(difficulty==3)
			{
				placeMarker = dumbAI(occupancy);
			}
			//Easiest
			else
			{
				weight = algorithm(occupancy);
				checkHappened = false;
				placeMarker = Easiest(weight);
			}
			/*
			 * If there are no possible moves that would result in the hindrance of the human player
			 * or the aid of the computer, then the algorithm is irrelevant, and a cell to occupy is
			 * Chosen by random via the dumbAI method
			 * */
			if (algorithm_Is_Irrelevant)
			{
				placeMarker = dumbAI(occupancy);
				/**
				 * If there are no empty cells available, then the human player wins the game. -Harley F.
				 */
				if (!checkWorked(placeMarker)){
					//Add a victory routine -Harley F.
					return;
				}
			}
		}
		//TicTacToeSinglePlayerActivity.grid[ placeMarker.getPositionX() ][ placeMarker.getPositionY() ].setOccupancy(weapon);
		if (TicTacToeSinglePlayerActivity.getTurn() == TicTacToeSinglePlayerActivity.PLAYER_ONE) {
			// The case of player one being X.
			if (TicTacToeSinglePlayerActivity.getPlayerOneWeapon() == TTTSpace.WEAPON_X) {
				// Change spot to an X.
				TicTacToeSinglePlayerActivity.locations[ placeMarker.getPositionX() ][ placeMarker.getPositionY() ].setImageResource(R.drawable.x);

				// Set the spot to X.
				TicTacToeSinglePlayerActivity.grid[ placeMarker.getPositionX() ][ placeMarker.getPositionY() ].setOccupancy(TTTSpace.WEAPON_X);
			}
			// The case of player one being O.
			else {
				// Change spot to an O.
				TicTacToeSinglePlayerActivity.locations[ placeMarker.getPositionX() ][ placeMarker.getPositionY() ].setImageResource(R.drawable.o);
				// Set the spot to O.
				TicTacToeSinglePlayerActivity.grid[ placeMarker.getPositionX() ][ placeMarker.getPositionY() ].setOccupancy(TTTSpace.WEAPON_O);
			}
		}
		// If it is player two's turn.
		else {
			// The case that player two is playing X.
			if (TicTacToeSinglePlayerActivity.getPlayerTwoWeapon() == TTTSpace.WEAPON_X) {
				// Change spot to an X.
				TicTacToeSinglePlayerActivity.locations[ placeMarker.getPositionX() ][ placeMarker.getPositionY() ].setImageResource(R.drawable.x);
				// Set the spot to X.
				TicTacToeSinglePlayerActivity.grid[ placeMarker.getPositionX() ][ placeMarker.getPositionY() ].setOccupancy(TTTSpace.WEAPON_X);
			}
			// The case that player two is playing O.
			else {
				// Change spot to an O.
				TicTacToeSinglePlayerActivity.locations[ placeMarker.getPositionX() ][ placeMarker.getPositionY() ].setImageResource(R.drawable.o);
				// Set the spot to O.
				TicTacToeSinglePlayerActivity.grid[ placeMarker.getPositionX() ][ placeMarker.getPositionY() ].setOccupancy(TTTSpace.WEAPON_O);
			}
		}
		TicTacToeSinglePlayerActivity.setTurn(!TicTacToeSinglePlayerActivity.getTurn());
	} // end single-player method

	//campaign method
	void makeMoveCP() {

	}

	private boolean checkWorked(TTTSpacePosition placeMarker) {
		// TODO Auto-generated method stub
		if(placeMarker.getPositionX() != 3){
			return true;
		}
		else
			return false;
	}

	private int[][] algorithm(int[][] occupancy) {
		//DEFINE VARIABLES
		int weight[][]=new int[3][3]; //use occupancy to find this. the higher the weight the higher the priority.
		TTTSpacePosition loc = new TTTSpacePosition(3,3);
		//Priority will be increased for a spot if the spot is empty and if there is a friendly or enemy block
		//horizontal, vertical, or diagonal from the spot we are checking.

		//VERTICAL CHECK
		for(int i = 0; i < 3; i++)
		{
			int one = occupancy[i][0];
			int two = occupancy[i][1];
			int three = occupancy[i][2];

			if(one == weapon && two != 0x0)
			{
				for (int j = 0; j < 3; j++)
					weight[i][j] = 0;
			}
			else if(one == weapon && three != 0x0)
			{
				for (int j = 0; j < 3; j++)
					weight[i][j] = 0;
			}
			else if(two == weapon && one != 0x0)
			{
				for (int j = 0; j < 3; j++)
					weight[i][j] = 0;
			}
			else if(two == weapon && three != 0x0)
			{
				for (int j = 0; j < 3; j++)
					weight[i][j] = 0;
			}
			else if(three == weapon && one != 0x0)
			{
				for (int j = 0; j < 3; j++)
					weight[i][j] = 0;
			}
			else if(three == weapon && two != 0x0)
			{
				for (int j = 0; j < 3; j++)
					weight[i][j] = 0;
			}
			else if( one > 0x0)
			{
				weight[i][1]++;
				weight[i][2]++;
			}
			else if(two > 0x0)
			{
				weight[i][0]++;
				weight[i][2]++;
			}
			else if(three > 0x0)
			{
				weight[i][0]++;
				weight[i][1]++;
			}
		}
		//HORIZONTAL CHECK
		for(int i = 0; i < 3; i++)
		{
			int one = occupancy[0][i];
			int two = occupancy[1][i];
			int three = occupancy[2][i];

			if(one == weapon && two != 0x0)
			{
				for (int j = 0; j < 3; j++)
					weight[j][i] = 0;
			}
			else if(one == weapon && three != 0x0)
			{
				for (int j = 0; j < 3; j++)
					weight[j][i] = 0;
			}
			else if(two == weapon && one != 0x0)
			{
				for (int j = 0; j < 3; j++)
					weight[j][i] = 0;
			}
			else if(two == weapon && three != 0x0)
			{
				for (int j = 0; j < 3; j++)
					weight[j][i] = 0;
			}
			else if(three == weapon && one != 0x0)
			{
				for (int j = 0; j < 3; j++)
					weight[j][i] = 0;
			}
			else if(three == weapon && two != 0x0)
			{
				for (int j = 0; j < 3; j++)
					weight[j][i] = 0;
			}
			else if( one > 0x0)
			{
				weight[1][i]++;
				weight[2][i]++;
			}
			else if(two > 0x0)
			{
				weight[0][i]++;
				weight[2][i]++;
			}
			else if(three > 0x0)
			{
				weight[0][i]++;
				weight[1][i]++;
			}
		}
		for(int i = 0;i < 2; i++ ) //Diag. check
		{
			int one, two, three;
			if(!checkHappened)
			{
				one=occupancy[0][0];
				two=occupancy[1][1];
				three=occupancy[2][2];
				if(one == weapon && two != 0x0)
				{
					weight[0][0] = 0;
					weight[1][1]=0;
					weight[2][2]=0;
				}
				else if(one == weapon && three != 0x0)
				{
					weight[0][0] = 0;
					weight[1][1]=0;
					weight[2][2]=0;
				}
				else if(two == weapon && one != 0x0)
				{
					weight[0][0] = 0;
					weight[1][1]=0;
					weight[2][2]=0;
				}
				else if(two == weapon && three != 0x0)
				{
					weight[0][0] = 0;
					weight[1][1]=0;
					weight[2][2]=0;
				}
				else if(three == weapon && one != 0x0)
				{
					weight[0][0] = 0;
					weight[1][1]=0;
					weight[2][2]=0;
				}
				else if(three == weapon && two != 0x0)
				{
					weight[0][0] = 0;
					weight[1][1]=0;
					weight[2][2]=0;
				}
				else if( one > 0x0)
				{
					weight[1][1]++;
					weight[2][2]++;
				}
				else if(two > 0x0)
				{
					weight[0][0]++;
					weight[2][2]++;
				}
				else if(three > 0x0)
				{
					weight[0][0]++;
					weight[1][1]++;
				}
			}
			else
			{
				one=occupancy[2][0];
				two=occupancy[1][1];
				three=occupancy[0][2];
				if(one == weapon && two != 0x0)
				{
					weight[2][0] = 0;
					weight[1][1]=0;
					weight[0][2]=0;
				}
				else if(one == weapon && three != 0x0)
				{
					weight[2][0] = 0;
					weight[1][1]=0;
					weight[0][2]=0;
				}
				else if(two == weapon && one != 0x0)
				{
					weight[2][0] = 0;
					weight[1][1]=0;
					weight[0][2]=0;
				}
				else if(two == weapon && three != 0x0)
				{
					weight[2][0] = 0;
					weight[1][1]=0;
					weight[0][2]=0;
				}
				else if(three == weapon && one != 0x0)
				{
					weight[2][0] = 0;
					weight[1][1]=0;
					weight[0][2]=0;
				}
				else if(three == weapon && two != 0x0)
				{
					weight[2][0] = 0;
					weight[1][1]=0;
					weight[0][2]=0;
				}
				else if( one > 0x0)
				{
					weight[1][1]++;
					weight[0][2]++;
				}
				else if(two > 0x0)
				{
					weight[2][0]++;
					weight[0][2]++;
				}
				else if(three > 0x0)
				{
					weight[2][0]++;
					weight[1][1]++;
				}
			}

			checkHappened=true;
		}

		//If the cell is occupied, then the weight is set to null.
		//IF THIS GIVES US PROBLEMS, CHANGE TO '-1'
		for(int x=0;x<3;x++)
		{
			for(int y=0;y<3;y++)
			{
				if(occupancy[x][y]>0x0)
				{
					weight[x][y] = -1;
				}
			}
		}
		return weight;
	}

	private TTTSpacePosition Hardest(int[][] weight)
	{
		int test=0;//shows the current highest priority number
		ArrayList<TTTSpacePosition> highestPriorArrayList = new ArrayList<TTTSpacePosition>(); //for saving ties in priority in several locs
		for(int x=0;x<3;x++)
		{
			for(int y=0;y<3;y++)
			{
				if(weight[x][y]!= -1)
				{
					//first runthrough of the loop to initialize all the values
					if(x == 0 && y == 0)
					{
						test=weight[x][y];
						TTTSpacePosition loc = new TTTSpacePosition(x,y);
						highestPriorArrayList.add(loc);
					}
					else
					{
						//if the weight of the current spot is higher then the previous highest,
						//then reset all the values for the new spot
						if(weight[x][y]>test)
						{
							test=weight[x][y];
							TTTSpacePosition loc = new TTTSpacePosition(x,y);
							highestPriorArrayList.clear();
							highestPriorArrayList.add(loc);
						}
						//if the weight of the current spot is equal to the previous highest weight.
						//then add the new spot to the collection of the arraylist
						else if(weight[x][y]==test)
						{
							TTTSpacePosition loc = new TTTSpacePosition(x,y);
							highestPriorArrayList.add(loc);
						}
					}
				}
			}
		}
		if(test==0)
		{
			algorithm_Is_Irrelevant=true;
			return highestPriorArrayList.get(0);
		}
		if(highestPriorArrayList.size() == -1)
			return dumbAI(occupancy);
		else
		{
			//if the arraylist has more then one value, then multiple spots are equal in priority to be played,
			//so the AI will pick one at random to play		
			int choice=ran.nextInt(highestPriorArrayList.size());
			System.out.println("this is the random value: "+choice);
			return highestPriorArrayList.get(choice);
		}
	}

	private TTTSpacePosition Hard(int[][] weight)
	{
		int highestPrior=0;//shows the current highest priority number	
		ArrayList<TTTSpacePosition> highestPriorArrayList = new ArrayList<TTTSpacePosition>(); //for saving ties in priority in several locs
		for(int x=0;x<3;x++)
		{
			for(int y=0;y<3;y++)
			{

				if(weight[x][y]!= (Integer) null)
				{
					//first runthrough of the loop to initialize all the values
					if(x == 0 && y == 0)
					{
						highestPrior=weight[x][y];
						TTTSpacePosition loc = new TTTSpacePosition(x,y);
						highestPriorArrayList.add(loc);
					}
					else
					{
						//if the weight of the current spot is higher then the previous highest,
						//then reset all the values for the new spot
						if(weight[x][y]>highestPrior)
						{
							highestPrior=weight[x][y];
							TTTSpacePosition loc = new TTTSpacePosition(x,y);
							highestPriorArrayList.clear();
							highestPriorArrayList.add(loc);
						}
						//if the weight of the current spot is equal to the previous highest weight.
						//then add the new spot to the collection of the arraylist
						else if(weight[x][y]==highestPrior)
						{
							TTTSpacePosition loc = new TTTSpacePosition(x,y);
							highestPriorArrayList.add(loc);
						}
					}
				}
			}
		}
		if(highestPrior==0)
		{
			algorithm_Is_Irrelevant=true;
			return highestPriorArrayList.get(0);
		}
		int secondHighestPrior=highestPrior-1;//shows the current second highest priority number	
		for(int x=0;x<3;x++)
		{
			for(int y=0;y<3;y++)
			{
				if(weight[x][y]!= (Integer) null)
				{
					if(weight[x][y]==secondHighestPrior)
					{
						TTTSpacePosition loc = new TTTSpacePosition(x,y);
						highestPriorArrayList.add(loc);
					}
				}
			}
		}
		//if the arraylist has more then one value, then multiple spots are equal in priority to be played,
		//so the AI will pick one at random to play		
		int choice=ran.nextInt(highestPriorArrayList.size());
		return highestPriorArrayList.get(choice);

	}

	private TTTSpacePosition Easiest(int[][] weight)
	{
		int test=10;//shows the current lowest priority number
		ArrayList<TTTSpacePosition> lowestPriorArrayList = new ArrayList<TTTSpacePosition>(); //for saving ties in priority in several locs
		for(int x=0;x<3;x++)
		{
			for(int y=0;y<3;y++)
			{
				if(weight[x][y]!= -1)
				{
					//first runthrough of the loop to initialize all the values
					if(x == 0 && y == 0)
					{
						TTTSpacePosition loc = new TTTSpacePosition(x,y);
						lowestPriorArrayList.add(loc);
					}
					else
					{
						//if the weight of the current spot is lower then the previous lowest,
						//then reset all the values for the new spot
						if(weight[x][y] < test)
						{
							test=weight[x][y];
							TTTSpacePosition loc = new TTTSpacePosition(x,y);
							lowestPriorArrayList.clear();
							lowestPriorArrayList.add(loc);
						}
						//if the weight of the current spot is equal to the previous lowest weight.
						//then add the new spot to the collection of the arraylist
						else if(weight[x][y]==test)
						{
							TTTSpacePosition loc = new TTTSpacePosition(x,y);
							lowestPriorArrayList.add(loc);
						}
					}
				}
			}
		}
		//if the arraylist has more then one value, then multiple spots are equal in priority to be played,
		//so the AI will pick one at random to play	
		if(test==0)
		{
			algorithm_Is_Irrelevant=true;
			return lowestPriorArrayList.get(0);
		}
		if(lowestPriorArrayList.size() == -1)
			return dumbAI(occupancy);
		if(lowestPriorArrayList.size() == 1)
			return lowestPriorArrayList.get(0);
		else
		{
			//if the arraylist has more then one value, then multiple spots are equal in priority to be played,
			//so the AI will pick one at random to play		
			int choice=ran.nextInt(lowestPriorArrayList.size());
			return lowestPriorArrayList.get(choice);
		}

	}

	private TTTSpacePosition dumbAI(int[][] occupancy2) {
		ArrayList<TTTSpacePosition> emptySpots = new ArrayList<TTTSpacePosition>();
		
		for(int x=0;x<3;x++)
		{
			for(int y=0;y<3;y++)
			{
				//Adds any space that is unoccupied to the array list emptySpots
				if(occupancy2[x][y] == 0x0)
				{
					TTTSpacePosition check = new TTTSpacePosition(x,y);
					emptySpots.add(check);
				}
			}
		}
		/**
		 * What if there are NO empty cells available? -Harley F.
		 * loc.positionX and loc.positionY return the int value of 3.
		 * these are impossible parameters for our 3x3 grid. -Harley F. 
		 */

		//Random choice out of the empty spots available
		if(emptySpots.size()<=1)
		{
			return emptySpots.get(0);
		}
		else
		{
			int choice=ran.nextInt(emptySpots.size());
			return emptySpots.get(choice);
		}
	}

	private TTTSpacePosition checkBlock(int[][] occupancy, int getPlayerWeapon) {

		TTTSpacePosition loc = new TTTSpacePosition(3,3);

		for(int k = 0; k<3; k++) //vertical check
		{
			int one=occupancy[0][k];
			int two=occupancy[1][k];
			int three=occupancy[2][k];
			if(one==getPlayerWeapon&&
					two==getPlayerWeapon&&
					three==0x0)
			{
				//place marker on (2,k) to block
				loc = new TTTSpacePosition(2,k);
				return loc;
			}
			else if(one==getPlayerWeapon&&
					three==getPlayerWeapon&&
					two==0x0)
			{
				//place marker on (1,k) to block
				loc = new TTTSpacePosition(1,k);
				return loc;
			}
			else if(two==getPlayerWeapon&&
					three==getPlayerWeapon&&
					one==0x0)
			{
				//place marker on (0,k) to block
				loc = new TTTSpacePosition(0,k);
				return loc;
			}

		}
		for(int k = 0;k<3; k++) //Horizontal check each cell
		{
			int one=occupancy[k][0];
			int two=occupancy[k][1];
			int three=occupancy[k][2];
			if(one==getPlayerWeapon&&
					two==getPlayerWeapon&&
					three==0x0)
			{
				//place marker on (k,2) to block
				loc = new TTTSpacePosition(k,2);
				return loc;
			}
			else if(one==getPlayerWeapon&&
					three==getPlayerWeapon&&
					two==0x0)
			{
				//place marker on (k,1) to block
				loc = new TTTSpacePosition(k,1);
				return loc;
			}
			else if(two==getPlayerWeapon&&
					three==getPlayerWeapon&&
					one==0x0)
			{
				//place marker on (k,0) to block
				loc = new TTTSpacePosition(k,0);
				return loc;
			}


		}
		for(int k = 0;k < 2; k++ ) //Diag. check
		{
			int one, two, three;
			if(!checkHappened)
			{
				one=occupancy[0][0];
				two=occupancy[1][1];
				three=occupancy[2][2];
				if(one==getPlayerWeapon&&
						two==getPlayerWeapon&&
						three==0x0)
				{
					//place marker on three to block
					loc = new TTTSpacePosition(2,2);
					return loc;
				}
				else if(one==getPlayerWeapon&&
						three==getPlayerWeapon&&
						two==0x0)
				{
					//place marker on two to block
					loc = new TTTSpacePosition(1,1);
					return loc;
				}
				else if(two==getPlayerWeapon&&
						three==getPlayerWeapon&&
						one==0x0)
				{
					//place marker on one to block
					loc = new TTTSpacePosition(0,0);
					return loc;
				}
			}
			else
			{
				one=occupancy[2][0];
				two=occupancy[1][1];
				three=occupancy[0][2];
				if(one==getPlayerWeapon&&
						two==getPlayerWeapon&&
						three==0x0)
				{
					//place marker on three to block
					loc = new TTTSpacePosition(0,2);
					return loc;
				}
				else if(one==getPlayerWeapon&&
						three==getPlayerWeapon&&
						two==0x0)
				{
					//place marker on two to block
					loc = new TTTSpacePosition(1,1);
					return loc;
				}
				else if(two==getPlayerWeapon&&
						three==getPlayerWeapon&&
						one==0x0)
				{
					//place marker on one to block
					loc = new TTTSpacePosition(2,0);
					return loc;
				}
			}
			checkHappened=true;
		}
		return loc;
	}

	private TTTSpacePosition checkWin( int[][] occupancy, int getPlayerWeapon) {
		// TODO Auto-generated method stub

		TTTSpacePosition loc = new TTTSpacePosition(3,3);

		for(int k = 0; k<3; k++) //vertical check
		{
			int one=occupancy[0][k];
			int two=occupancy[1][k];
			int three=occupancy[2][k];
			if(one==getPlayerWeapon&&
					two==getPlayerWeapon&&
					three==0x0)
			{
				//place marker on (2,k) to win
				loc = new TTTSpacePosition(2,k);
				return loc;
			}
			else if(one==getPlayerWeapon&&
					three==getPlayerWeapon&&
					two==0x0)
			{
				//place marker on (1,k) to win
				loc = new TTTSpacePosition(1,k);
				return loc;
			}
			else if(two==getPlayerWeapon&&
					three==getPlayerWeapon&&
					one==0x0)
			{
				//place marker on (0,k) to win
				loc = new TTTSpacePosition(0,k);
				return loc;
			}

		}
		for(int k = 0;k<3; k++) //Horizontal check each cell
		{
			int one=occupancy[k][0];
			int two=occupancy[k][1];
			int three=occupancy[k][2];
			if(one==getPlayerWeapon&&
					two==getPlayerWeapon&&
					three==0x0)
			{
				//place marker on (k,2) to win
				loc = new TTTSpacePosition(k,2);
				return loc;
			}
			else if(one==getPlayerWeapon&&
					three==getPlayerWeapon&&
					two==0x0)
			{
				//place marker on (k,1) to win
				loc = new TTTSpacePosition(k,1);
				return loc;
			}
			else if(two==getPlayerWeapon&&
					three==getPlayerWeapon&&
					one==0x0)
			{
				//place marker on (k,0) to win
				loc = new TTTSpacePosition(k,0);
				return loc;
			}


		}
		for(int k = 0;k < 2; k++ ) //Diag. check
		{
			int one, two, three;
			if(!checkHappened)
			{
				one=occupancy[0][0];
				two=occupancy[1][1];
				three=occupancy[2][2];
				if(one==getPlayerWeapon&&
						two==getPlayerWeapon&&
						three==0x0)
				{
					//place marker on three to win
					loc = new TTTSpacePosition(2,2);
					return loc;
				}
				else if(two==getPlayerWeapon&&
						three==getPlayerWeapon&&
						one==0x0)
				{
					//place marker on one to win
					loc = new TTTSpacePosition(0,0);
					return loc;
				}
			}
			else
			{
				one=occupancy[2][0];
				two=occupancy[1][1];
				three=occupancy[0][2];
				if(one==getPlayerWeapon&&
						two==getPlayerWeapon&&
						three==0x0)
				{
					//place marker on three to win
					loc = new TTTSpacePosition(0,2);
					return loc;
				}
				else if(two==getPlayerWeapon&&
						three==getPlayerWeapon&&
						one==0x0)
				{
					//place marker on one to win
					loc = new TTTSpacePosition(2,0);
					return loc;
				}
			}
			if(one==getPlayerWeapon&&
					three==getPlayerWeapon&&
					two==0x0)
			{
				//place marker on two to win
				loc = new TTTSpacePosition(1,1);
				return loc;
			}

			checkHappened=true;
		}
		return loc;
	}

	public boolean isBoardEmpty()
	{
		for (int n = 0; n < 3; n++)
			for (int j = 0; j < 3; j++)
				if (TicTacToeSinglePlayerActivity.grid[n][j].getOccupancy() != 0x0)
					return false;
		return true;
	}
}