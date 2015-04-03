package com.deckerhead.tictactoe;

/**
 * TicTacToeActivity.java
 *
 * This class creates a simple Tic-Tac-Toe game.
 * In this version of the game, human player plays
 * against computers and humans in an android setting.
 * 
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TicTacToeActivity extends Activity { // Activity, the main part of the Android application.
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Setting the content to the main .xml file.
		setContentView(R.layout.main); 


		// Getting the Button1 object from the GUI.
		Button vComputer = (Button) findViewById(R.id.button1);
		vComputer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent launchSP = new Intent(v.getContext(), TicTacToeChooseYourWeaponActivity.class);
				launchSP.putExtra("GAME_MODE", "SINGLEPLAYER");
				startActivityForResult(launchSP, 0);
			}
		});

		// Getting the Button2 object from the GUI.
		Button vHuman = (Button) findViewById(R.id.button2);
		vHuman.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Intention to launch the Choose Your Weapon screen
				Intent launchCW = new Intent(v.getContext(), TicTacToeChooseYourWeaponActivity.class);
				launchCW.putExtra("GAME_MODE", "MULTIPLAYER");
				// Starting intent
				startActivityForResult(launchCW, 0);

			}
		});
	}
}