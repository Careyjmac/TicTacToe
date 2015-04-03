package com.deckerhead.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class TicTacToeChooseYourWeaponActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cw);
		
		String arrayadapter[] = new String[5];
		arrayadapter[0] = "Insane";
		arrayadapter[1] = "Pretty Damn Hard";
		arrayadapter[2] = "I Can Handle This";
		arrayadapter[3] = "No Skill";
		arrayadapter[4] = "Wuss";
		
		final Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);
		SpinnerAdapter arrayadapt = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayadapter);
		mySpinner.setAdapter(arrayadapt);
		
		if (getIntent().getStringExtra("GAME_MODE").equalsIgnoreCase("MULTIPLAYER"))
		{
			mySpinner.setVisibility(View.INVISIBLE);
		}
		
		ImageButton xbutton = (ImageButton) findViewById(R.id.imageButton10);
		xbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent playerx = new Intent(v.getContext(), getIntent().getStringExtra("GAME_MODE").equalsIgnoreCase("SINGLEPLAYER") ? TicTacToeSinglePlayerActivity.class : TicTacToeMultiPlayerActivity.class);
				playerx.putExtra("PlayerOneWeapon", TTTSpace.WEAPON_X);
				playerx.putExtra("PlayerTwoWeapon", TTTSpace.WEAPON_O);
				if (getIntent().getStringExtra("GAME_MODE").equalsIgnoreCase("SINGLEPLAYER"))
				{
					playerx.putExtra("DIFFICULTY", mySpinner.getSelectedItemPosition());
				}
				startActivityForResult(playerx, 0);
				finish();
			}
		});
		
		ImageButton obutton = (ImageButton) findViewById(R.id.imageButton11);
		obutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent playero = new Intent(v.getContext(), getIntent().getStringExtra("GAME_MODE").equalsIgnoreCase("SINGLEPLAYER") ? TicTacToeSinglePlayerActivity.class : TicTacToeMultiPlayerActivity.class);
				playero.putExtra("PlayerOneWeapon", TTTSpace.WEAPON_O);
				playero.putExtra("PlayerTwoWeapon", TTTSpace.WEAPON_X);
				if (getIntent().getStringExtra("GAME_MODE").equalsIgnoreCase("SINGLEPLAYER"))
				{
					playero.putExtra("DIFFICULTY", mySpinner.getSelectedItemPosition());
				}
				startActivityForResult(playero, 0);
				finish();
			}
		});
	}

}
