package com.deckerhead.tictactoe;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TicTacToeSinglePlayerActivity extends Activity {

	/*
	 * Static values for the players.
	 * Player one is always a human.
	 * Player two is always a computer.
	 */
	public final static boolean PLAYER_ONE = true;
	public final static boolean PLAYER_TWO = false;

	TTTAI TTTMaster;

	// Player names.
	private String playerOneName = "Player 1";
	private String playerTwoName = "Computer";

	// Player scores.
	private int playerOneScore = 0;
	private int playerTwoScore = 0;

	// Who's turn it is.
	private static boolean turn = PLAYER_ONE;

	// The player weapons.
	private static int playerOneWeapon;
	private static int playerTwoWeapon;

	// The grid lock.
	public boolean gridNotLocked = true;

	// The reset lock.
	public boolean resetLocked = true;

	// The current step.
	private static int step = 1;

	private int DIFFICULTY;

	/*
	 * Player name methods.
	 */
	public void setPlayerOneName(String name) {
		/*
		 * Sets the first player's name.
		 */
		playerOneName = name;
	}
	public void setPlayerTwoName(String name) {
		/*
		 * Sets the second player's name.
		 */
		playerTwoName = name;
	}
	public String getPlayerOneName() {
		/*
		 * Gets the first player's name.
		 */
		return playerOneName;
	}
	public String getPlayerTwoName() {
		/*
		 * Gets the second player's name.
		 */
		return playerTwoName;
	}

	/*
	 * Player score methods.
	 */
	public int getPlayerOneScore() {
		/*
		 * Returns the first player's score.
		 */
		return playerOneScore;
	}
	public int getPlayerTwoScore() {
		/*
		 * Returns the second player's score.
		 */
		return playerTwoScore;
	}
	public int incrementPlayerOneScore() {
		/*
		 * Increments player one's score, then returns the new score.
		 */
		playerOneScore++;
		return playerOneScore;
	}
	public int incrementPlayerTwoScore() {
		/*
		 * Increments player two's score, then returns the new score.
		 */
		playerTwoScore++;
		return playerTwoScore;
	}

	/*
	 * Player weapon methods.
	 */
	public void setPlayerWeapons(int p1Wep, int p2Wep) {
		/*
		 * Sets the player weapons.
		 */
		playerOneWeapon = p1Wep;
		playerTwoWeapon = p2Wep;
	}
	public static int getPlayerOneWeapon() {
		/*
		 * Gets the first player's weapon.
		 */
		return playerOneWeapon;
	}
	public static int getPlayerTwoWeapon() {
		/*
		 * Gets the second player's weapon.
		 */
		return playerTwoWeapon;
	}

	public static int getStep() {
		/*
		 * Returns the current step.
		 */
		return step;
	}

	/*
	 * The grid.
	 * NOTE: See the TTTSpace class for
	 * more information.
	 */
	public static TTTSpace[][] grid = {
		{
			new TTTSpace(0, 0),
			new TTTSpace(0, 1),
			new TTTSpace(0, 2)
		},
		{
			new TTTSpace(1, 0),
			new TTTSpace(1, 1),
			new TTTSpace(1, 2)
		},
		{
			new TTTSpace(2, 0),
			new TTTSpace(2, 1),
			new TTTSpace(2, 2)
		}
	};

	// The grid locations.
	public static ImageButton[][] locations = new ImageButton[3][3];

	// The turn indicators.
	ImageView turnIndicator;
	TextView turnIndicatorText;

	// The score text.
	TextView playerOneScoreText;
	TextView playerTwoScoreText;

	// The score images.
	ImageView playerOneImage;
	ImageView playerTwoImage;

	public static boolean getTurn() {
		// Returns the current turn.
		return turn;
	}

	public static void setTurn(boolean theTurn) {
		// Sets the current turn.
		turn = theTurn;
	}

	public boolean isWinner(int weapon) {
		/*
		 * Checks if the specified weapon is a winner.
		 */
		int consec = 0;
		// Variable to store consecutive values.

		// Checking columns.
		for (int n = 0; n < 3; n++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (grid[n][j].getOccupancy() == weapon)
				{
					consec++;
				}
			}
			if (consec == 3) {
				return true;
			}
			else {
				consec = 0;
			}
		}

		// Checking rows.
		for (int n = 0; n < 3; n++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (grid[j][n].getOccupancy() == weapon)
				{
					consec++;
				}
			}
			if (consec == 3) {
				return true;
			}
			else {
				consec = 0;
			}
		}

		// Checking first diagonal.
		for (int n = 0; n < 3; n++) {
			if (grid[n][n].getOccupancy() == weapon)
			{
				consec++;
			}
		}
		if (consec == 3) {
			return true;
		}
		else {
			consec = 0;
		}

		// Checking second diagonal.
		for (int n = 0, j = 2; n < 3; n++, j--) {
			if (grid[n][j].getOccupancy() == weapon)
			{
				consec++;
			}
		}
		if (consec == 3) {
			return true;
		}
		else {
			consec = 0;
		}
		return false;
	}

	public boolean isBoardFull()
	{
		/*
		 * Checks to see if the board is full.
		 */
		for (int n = 0; n < 3; n++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (grid[n][j].getOccupancy() == 0)
				{
					return false;
				}
			}
		}

		return true;
	}
	public boolean checkBoardAndUpdateAll() {

		// Updates the turn indicators.
		if (getTurn() == PLAYER_ONE) {
			if (getPlayerOneWeapon() == TTTSpace.WEAPON_X) {
				turnIndicator.setImageResource(R.drawable.x);
			}
			else {
				turnIndicator.setImageResource(R.drawable.o);
			}
			turnIndicatorText.setText(getPlayerOneName());
		}
		else {
			if (getPlayerTwoWeapon() == TTTSpace.WEAPON_X) {
				turnIndicator.setImageResource(R.drawable.x);
			}
			else {
				turnIndicator.setImageResource(R.drawable.o);
			}
			turnIndicatorText.setText(getPlayerTwoName());
		}


		// Check if game won and display winner banner.
		if (isWinner(getPlayerOneWeapon())) {
			gridNotLocked = false;
			incrementPlayerOneScore();
			if (getPlayerOneWeapon() == TTTSpace.WEAPON_X) {
				playerOneScoreText.setText(": " + getPlayerOneScore());
				playerTwoScoreText.setText(": " + getPlayerTwoScore());
				LayoutInflater inflater = getLayoutInflater();
				View view = inflater.inflate(R.layout.customtoastx,
						(ViewGroup) findViewById(R.id.relativeLayout1));

				Toast toast = new Toast(view.getContext());
				toast.setView(view);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			else {
				playerOneScoreText.setText(": " + getPlayerOneScore());
				playerTwoScoreText.setText(": " + getPlayerTwoScore());
				LayoutInflater inflater = getLayoutInflater();
				View view = inflater.inflate(R.layout.customtoasto,
						(ViewGroup) findViewById(R.id.relativeLayout1));

				Toast toast = new Toast(view.getContext());
				toast.setView(view);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			return false;
		}

		else if (isWinner(getPlayerTwoWeapon())) {
			gridNotLocked = false;
			incrementPlayerTwoScore();
			if (getPlayerTwoWeapon() == TTTSpace.WEAPON_X) {
				playerOneScoreText.setText(": " + getPlayerOneScore());
				playerTwoScoreText.setText(": " + getPlayerTwoScore());
				LayoutInflater inflater = getLayoutInflater();
				View view = inflater.inflate(R.layout.customtoastx,
						(ViewGroup) findViewById(R.id.relativeLayout1));

				Toast toast = new Toast(view.getContext());
				toast.setView(view);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			else {
				playerOneScoreText.setText(": " + getPlayerOneScore());
				playerTwoScoreText.setText(": " + getPlayerTwoScore());
				LayoutInflater inflater = getLayoutInflater();
				View view = inflater.inflate(R.layout.customtoasto,
						(ViewGroup) findViewById(R.id.relativeLayout1));

				Toast toast = new Toast(view.getContext());
				toast.setView(view);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			return false;
		}

		else if (isBoardFull())
		{
			gridNotLocked = false;
			LayoutInflater inflater = getLayoutInflater();
			View view = inflater.inflate(R.layout.customtoastcat,
					(ViewGroup) findViewById(R.id.relativeLayout1));

			Toast toast = new Toast(view.getContext());
			toast.setView(view);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();

			return false;
		}

		else
		{
			//			TTTMaster.makeMoveSP(DIFFICULTY);
			//			checkBoardAndUpdateAll();
		}

		if (!gridNotLocked) {
			turnIndicator.setImageResource(R.drawable.arrow);
			turnIndicatorText.setText(" ");
			resetLocked = false;
		}
		return true;
	}
	// TODO change the reset method when setting up the themes.

	public void resetGrid() {
		for (int n = 0; n < 3; n++) {
			for (int j = 0; j < 3; j++) {
				grid[n][j].setOccupancy(TTTSpace.UNOCCUPIED);
				locations[n][j].setImageResource(R.drawable.blank);
			}
		}

		gridNotLocked = true;
		checkBoardAndUpdateAll();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mp);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// Gets and sets the weapons from the extras.
		setPlayerWeapons(getIntent().getExtras().getInt("PlayerOneWeapon"), getIntent().getExtras().getInt("PlayerTwoWeapon"));

		TTTMaster = new TTTAI(getIntent().getExtras().getInt("PlayerTwoWeapon"));
		DIFFICULTY = getIntent().getExtras().getInt("DIFFICULTY");
		// Sets the initial player turn.
		if (getPlayerOneWeapon() == TTTSpace.WEAPON_X) {
			setTurn(PLAYER_ONE);
		}
		else {
			setTurn(PLAYER_TWO);
		}

		// Sets (and sets up) the turn indicator.
		turnIndicator = (ImageView) findViewById(R.id.imageView9);
		turnIndicatorText = (TextView) findViewById(R.id.textView1);

		turnIndicator.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!resetLocked) {
					resetGrid();
					resetLocked = true;
				}

			}
		});

		if (getTurn() == PLAYER_ONE) {
			if (getPlayerOneWeapon() == TTTSpace.WEAPON_X) {
				turnIndicator.setImageResource(R.drawable.x);
			}
			else {
				turnIndicator.setImageResource(R.drawable.o);
			}
			turnIndicatorText.setText(getPlayerOneName());
		}
		else {
			if (getPlayerTwoWeapon() == TTTSpace.WEAPON_X) {
				turnIndicator.setImageResource(R.drawable.x);
			}
			else {
				turnIndicator.setImageResource(R.drawable.o);
			}
			turnIndicatorText.setText(getPlayerTwoName());
		}

		// Sets up the point system. 
		playerOneImage = (ImageView) findViewById(R.id.imageView10);
		playerTwoImage = (ImageView) findViewById(R.id.imageView11);
		if (getPlayerOneWeapon() == TTTSpace.WEAPON_X)
		{
			playerOneImage.setImageResource(R.drawable.x);
			playerTwoImage.setImageResource(R.drawable.o);
		}
		else
		{
			playerOneImage.setImageResource(R.drawable.o);
			playerTwoImage.setImageResource(R.drawable.x);
		}
		playerOneScoreText = (TextView) findViewById(R.id.textView2);
		playerTwoScoreText = (TextView) findViewById(R.id.textView3);
		if (getPlayerOneWeapon() == TTTSpace.WEAPON_X) {
			playerOneScoreText.setText(": " + getPlayerOneScore());
			playerTwoScoreText.setText(": " + getPlayerTwoScore());
		}
		else {
			playerOneScoreText.setText(": " + getPlayerTwoScore());
			playerTwoScoreText.setText(": " + getPlayerOneScore());
		}

		// First space, pos 0, 0
		locations[0][0] = (ImageButton) findViewById(R.id.imageButton1);
		locations[0][0].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Check that there is nothing in the location.
				if (grid[0][0].getOccupancy() == 0 && gridNotLocked) {
					// Figure out who's turn it is.
					if (getTurn() == PLAYER_ONE) {
						// The case of player one being X.
						if (getPlayerOneWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[0][0].setImageResource(R.drawable.x);

							// Set the spot to X.
							grid[0][0].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case of player one being O.
						else {
							// Change spot to an O.
							locations[0][0].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[0][0].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// If it is player two's turn.
					else {
						// The case that player two is playing X.
						if (getPlayerTwoWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[0][0].setImageResource(R.drawable.x);
							// Set the spot to X.
							grid[0][0].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case that player two is playing O.
						else {
							// Change spot to an O.
							locations[0][0].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[0][0].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// Finally, change the turn and refresh.
					setTurn(!getTurn());
					if (checkBoardAndUpdateAll())
					{
						TTTMaster.makeMoveSP(DIFFICULTY);
						checkBoardAndUpdateAll();
					}

				}
			}
		});

		// Second space, pos 0, 1
		locations[0][1] = (ImageButton) findViewById(R.id.imageButton2);
		locations[0][1].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Check that there is nothing in the location.
				if (grid[0][1].getOccupancy() == 0 && gridNotLocked) {
					// Figure out who's turn it is.
					if (getTurn() == PLAYER_ONE) {
						// The case of player one being X.
						if (getPlayerOneWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[0][1].setImageResource(R.drawable.x);

							// Set the spot to X.
							grid[0][1].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case of player one being O.
						else {
							// Change spot to an O.
							locations[0][1].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[0][1].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// If it is player two's turn.
					else {
						// The case that player two is playing X.
						if (getPlayerTwoWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[0][1].setImageResource(R.drawable.x);
							// Set the spot to X.
							grid[0][1].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case that player two is playing O.
						else {
							// Change spot to an O.
							locations[0][1].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[0][1].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// Finally, change the turn and refresh.
					setTurn(!getTurn());
					if (checkBoardAndUpdateAll())
					{
						TTTMaster.makeMoveSP(DIFFICULTY);
						checkBoardAndUpdateAll();
					}
				}
			}
		});

		// Third space, pos 0, 0
		locations[0][2] = (ImageButton) findViewById(R.id.imageButton3);
		locations[0][2].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Check that there is nothing in the location.
				if (grid[0][2].getOccupancy() == 0 && gridNotLocked) {
					// Figure out who's turn it is.
					if (getTurn() == PLAYER_ONE) {
						// The case of player one being X.
						if (getPlayerOneWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[0][2].setImageResource(R.drawable.x);

							// Set the spot to X.
							grid[0][2].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case of player one being O.
						else {
							// Change spot to an O.
							locations[0][2].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[0][2].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// If it is player two's turn.
					else {
						// The case that player two is playing X.
						if (getPlayerTwoWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[0][2].setImageResource(R.drawable.x);
							// Set the spot to X.
							grid[0][2].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case that player two is playing O.
						else {
							// Change spot to an O.
							locations[0][2].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[0][2].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// Finally, change the turn and refresh.
					setTurn(!getTurn());
					if (checkBoardAndUpdateAll())
					{
						TTTMaster.makeMoveSP(DIFFICULTY);
						checkBoardAndUpdateAll();
					}
				}
			}
		});

		// Fourth space, pos 1, 0
		locations[1][0] = (ImageButton) findViewById(R.id.imageButton4);
		locations[1][0].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Check that there is nothing in the location.
				if (grid[1][0].getOccupancy() == 0 && gridNotLocked) {
					// Figure out who's turn it is.
					if (getTurn() == PLAYER_ONE) {
						// The case of player one being X.
						if (getPlayerOneWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[1][0].setImageResource(R.drawable.x);

							// Set the spot to X.
							grid[1][0].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case of player one being O.
						else {
							// Change spot to an O.
							locations[1][0].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[1][0].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// If it is player two's turn.
					else {
						// The case that player two is playing X.
						if (getPlayerTwoWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[1][0].setImageResource(R.drawable.x);
							// Set the spot to X.
							grid[1][0].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case that player two is playing O.
						else {
							// Change spot to an O.
							locations[1][0].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[1][0].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// Finally, change the turn and refresh.
					setTurn(!getTurn());
					if (checkBoardAndUpdateAll())
					{
						TTTMaster.makeMoveSP(DIFFICULTY);
						checkBoardAndUpdateAll();
					}
				}
			}
		});

		// Fifth space, pos 1, 1
		locations[1][1] = (ImageButton) findViewById(R.id.imageButton5);
		locations[1][1].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Check that there is nothing in the location.
				if (grid[1][1].getOccupancy() == 0 && gridNotLocked) {
					// Figure out who's turn it is.
					if (getTurn() == PLAYER_ONE) {
						// The case of player one being X.
						if (getPlayerOneWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[1][1].setImageResource(R.drawable.x);

							// Set the spot to X.
							grid[1][1].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case of player one being O.
						else {
							// Change spot to an O.
							locations[1][1].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[1][1].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// If it is player two's turn.
					else {
						// The case that player two is playing X.
						if (getPlayerTwoWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[1][1].setImageResource(R.drawable.x);
							// Set the spot to X.
							grid[1][1].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case that player two is playing O.
						else {
							// Change spot to an O.
							locations[1][1].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[1][1].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// Finally, change the turn and refresh.
					setTurn(!getTurn());
					if (checkBoardAndUpdateAll())
					{
						TTTMaster.makeMoveSP(DIFFICULTY);
						checkBoardAndUpdateAll();
					}
				}
			}
		});

		// Sixth space, pos 0, 0
		locations[1][2] = (ImageButton) findViewById(R.id.imageButton6);
		locations[1][2].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Check that there is nothing in the location.
				if (grid[1][2].getOccupancy() == 0 && gridNotLocked) {
					// Figure out who's turn it is.
					if (getTurn() == PLAYER_ONE) {
						// The case of player one being X.
						if (getPlayerOneWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[1][2].setImageResource(R.drawable.x);

							// Set the spot to X.
							grid[1][2].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case of player one being O.
						else {
							// Change spot to an O.
							locations[1][2].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[1][2].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// If it is player two's turn.
					else {
						// The case that player two is playing X.
						if (getPlayerTwoWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[1][2].setImageResource(R.drawable.x);
							// Set the spot to X.
							grid[1][2].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case that player two is playing O.
						else {
							// Change spot to an O.
							locations[1][2].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[1][2].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// Finally, change the turn and refresh.
					setTurn(!getTurn());
					if (checkBoardAndUpdateAll())
					{
						TTTMaster.makeMoveSP(DIFFICULTY);
						checkBoardAndUpdateAll();
					}
				}
			}
		});

		// Seventh space, pos 2, 0
		locations[2][0] = (ImageButton) findViewById(R.id.imageButton7);
		locations[2][0].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Check that there is nothing in the location.
				if (grid[2][0].getOccupancy() == 0 && gridNotLocked) {
					// Figure out who's turn it is.
					if (getTurn() == PLAYER_ONE) {
						// The case of player one being X.
						if (getPlayerOneWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[2][0].setImageResource(R.drawable.x);

							// Set the spot to X.
							grid[2][0].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case of player one being O.
						else {
							// Change spot to an O.
							locations[2][0].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[2][0].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// If it is player two's turn.
					else {
						// The case that player two is playing X.
						if (getPlayerTwoWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[2][0].setImageResource(R.drawable.x);
							// Set the spot to X.
							grid[2][0].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case that player two is playing O.
						else {
							// Change spot to an O.
							locations[2][0].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[2][0].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// Finally, change the turn and refresh.
					setTurn(!getTurn());
					if (checkBoardAndUpdateAll())
					{
						TTTMaster.makeMoveSP(DIFFICULTY);
						checkBoardAndUpdateAll();
					}
				}
			}
		});

		// Eigth space, pos 2, 1
		locations[2][1] = (ImageButton) findViewById(R.id.imageButton8);
		locations[2][1].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Check that there is nothing in the location.
				if (grid[2][1].getOccupancy() == 0 && gridNotLocked) {
					// Figure out who's turn it is.
					if (getTurn() == PLAYER_ONE) {
						// The case of player one being X.
						if (getPlayerOneWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[2][1].setImageResource(R.drawable.x);

							// Set the spot to X.
							grid[2][1].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case of player one being O.
						else {
							// Change spot to an O.
							locations[2][1].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[2][1].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// If it is player two's turn.
					else {
						// The case that player two is playing X.
						if (getPlayerTwoWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[2][1].setImageResource(R.drawable.x);
							// Set the spot to X.
							grid[2][1].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case that player two is playing O.
						else {
							// Change spot to an O.
							locations[2][1].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[2][1].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// Finally, change the turn and refresh.
					setTurn(!getTurn());
					if (checkBoardAndUpdateAll())
					{
						TTTMaster.makeMoveSP(DIFFICULTY);
						checkBoardAndUpdateAll();
					}
				}
			}
		});

		// Ninth space, pos 2, 2
		locations[2][2] = (ImageButton) findViewById(R.id.imageButton9);
		locations[2][2].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Check that there is nothing in the location.
				if (grid[2][2].getOccupancy() == 0 && gridNotLocked) {
					// Figure out who's turn it is.
					if (getTurn() == PLAYER_ONE) {
						// The case of player one being X.
						if (getPlayerOneWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[2][2].setImageResource(R.drawable.x);

							// Set the spot to X.
							grid[2][2].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case of player one being O.
						else {
							// Change spot to an O.
							locations[2][2].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[2][2].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// If it is player two's turn.
					else {
						// The case that player two is playing X.
						if (getPlayerTwoWeapon() == TTTSpace.WEAPON_X) {
							// Change spot to an X.
							locations[2][2].setImageResource(R.drawable.x);
							// Set the spot to X.
							grid[2][2].setOccupancy(TTTSpace.WEAPON_X);
						}
						// The case that player two is playing O.
						else {
							// Change spot to an O.
							locations[2][2].setImageResource(R.drawable.o);
							// Set the spot to O.
							grid[2][2].setOccupancy(TTTSpace.WEAPON_O);
						}
					}
					// Finally, change the turn and refresh.
					setTurn(!getTurn());
					if (checkBoardAndUpdateAll())
					{
						TTTMaster.makeMoveSP(DIFFICULTY);
						checkBoardAndUpdateAll();
					} 
				}
			}
		});
		if (getTurn() == PLAYER_TWO)
		{
			TTTMaster.makeMoveSP(DIFFICULTY);
			checkBoardAndUpdateAll();
		}
	}



}
