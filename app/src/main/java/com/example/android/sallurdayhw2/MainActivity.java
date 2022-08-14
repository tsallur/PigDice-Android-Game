package com.example.android.sallurdayhw2;
/*
Thomas Sallurday
CPSC 4150-Plaue
C17123785
2-17-2021
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    /**
     * @pre none
     * @post contentView = R.activity_main
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     *
     * @pre contentView = R.layout.activity_main
     * @post contentView = R.layout.instructions
     */
    public void DisplayInstructions(View view) {
        setContentView(R.layout.instructions); //display instructions layout page
    }

    /**
     *
     * @pre contentView = R.layout.instructions
     * @post contentView = R.layout.activity_main
     */
    public void returnToHomePage(View view) {
        setContentView(R.layout.activity_main); //changes layout from instructions to activity_main
    }

    /**
     * @pre contentView = R.layout.activity_main
     * @post contentView = R.layout.get_num_to_win
     */
    public void startGame(View view) {
            Intent intent = new Intent(this, PigDice.class);
            startActivity(intent); //starts new activity centered around pig dice gameplay
    }
}