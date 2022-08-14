package com.example.android.sallurdayhw2;
/*
Thomas Sallurday
CPSC 4150-Plaue
2-17-2021
C17123785
 */
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

/**Constraints:
 * diceValue >= 1 && diceValue <= 6
 * playerScore >= 0
 * computerScore >= 0
 * int round > 0
 * roundPoints > 0
 * numTowWin > 0
 */



public class PigDice extends AppCompatActivity {
    CountDownTimer countDownTimer;
    private int gamesComputerWon;
    private int gamesPlayerWon;
    private int playerScore; //keeps track of player score
    private int computerScore; //keeps track of computer score
    private int diceValue; //holds a value between 1 and 6
    int playerTurns;
    int computerTurns;
    private String onlyPlayerName;
    private boolean playerTurn; //true if it is currently the player's turn
    private boolean computerCondition; // if the computer rolls a 1, this flips to false
    private ImageView dice;
    private int round;
    private TextView alertScreen;
    private Button rollDieButton;
    private Button endTurnButton;
    private int roundPoints;
    private EditText textNumToWin;
    private EditText eplayerName;
    private TextView roundDisplay;
    private TextView displayRoundPoints;
    private TextView displayComputerName;
    private TextView displayPlayerName;
    private TextView displayNumToWin;
    private String playerName;
    private TextView displayComputerGamesWon;
    private TextView displayPlayerGamesWon;
    private int numToWin; //score required to win the game

    /**
     * @pre none
     * @param savedInstanceState bundle for this activity
     * @post playerScore = 0,round = 1, computerScore = 0, numToWin = 0, roundPoints = 0,
     * playerTurns = 0, computerTurns = 0, diceValue = 0, gamesComputerWon = 0, gamesPlayerWon = 0
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_num_to_win); //set layout to setup screen
        playerScore = 0;
        round = 1;
        computerScore = 0;
        numToWin = 0;
        roundPoints = 0;
        playerTurns = 0;
        computerTurns = 0;
        diceValue = 0;
        gamesComputerWon = 0;
        gamesPlayerWon = 0;
    }

    /**
     * @pre numToWin > 0 && R.id.NameToGoBy != NULL
     * @post playerTurn == false || true
     * eplayerName = R.id.NameToGoBy
     * playerName = eplayerName.getText()
     * computerCondition = true
     * onlyPlayerName = string
     * contentView = R.layout.pig_dice_layout
     * computerCondition = true
     * dice = R.id.diceImg
     * alertScreen = R.id.alert_screen
     * alertScreen.view = gone;
     * rollDieButton = R.id.Roll_Die_Button
     * endTurnButton = R.id.End_Turn
     * roundDisplay = R.id.RoundInfo
     * endTurnButton.view = gone
     * displayRoundPoints = R.id.RoundPoints
     * displayPlayerName = R.id.displayPlayerName
     * displayComputerName = (R.id.displayComputerName
     * displayNumToWin = R.id.TargetScore
     * displayComputerGamesWon = R.id.computerGamesWon
     * displayPlayerGamesWon = R.id.playerGamesWon
     * displayComputerGamesWon.View = GONE
     * displayPlayerGamesWon.View = GONE
     * iff(!playerTurn):
     *      rollDieButton.View = gone
     */
    public void changeToGameScreen(View view) {
        Random random = new Random();
        int number = random.nextInt(100);
        if(number % 2 == 0){ // determines who goes first
            playerTurn = true;
        }
        else{
            playerTurn = false;
        }
        String roundInfo = "placeholder";
        String computerName = "Banker:\n\n" + computerScore;
        textNumToWin = (EditText)findViewById(R.id.NumToWin);
        if(textNumToWin.getText().toString().equals("")){
            textNumToWin.setText(R.string.one_hundred); //prevents game from crashing
        }
        String string = textNumToWin.getText().toString();
        numToWin = Integer.parseInt(string);
        String winInfo = "Score Needed to Win:\n" + numToWin;
        eplayerName = (EditText)findViewById(R.id.NameToGoBy);
        if(eplayerName.getText().toString().equals("")){
            eplayerName.setText(R.string.player); //prevents the game from crashing
        }
        playerName = eplayerName.getText().toString();
        onlyPlayerName = eplayerName.getText().toString(); //just for holding the player's name

        setContentView(R.layout.pig_dice_layout);
        displayComputerGamesWon = (TextView) findViewById(R.id.computerGamesWon);
        displayPlayerGamesWon = (TextView) findViewById(R.id.playerGamesWon);
        displayComputerGamesWon.setVisibility(View.GONE); //only show after the player wants to play again
        displayPlayerGamesWon.setVisibility(View.GONE); //only show after the player wants to play again
        computerCondition = true;
        dice = findViewById(R.id.diceImg);
        dice.setVisibility(View.VISIBLE);
        alertScreen = (TextView) findViewById(R.id.alert_screen);
        alertScreen.setVisibility(View.GONE);
        rollDieButton = (Button) findViewById(R.id.Roll_Die_Button);
        endTurnButton = (Button) findViewById(R.id.End_Turn);
        roundDisplay = (TextView)findViewById(R.id.RoundInfo);
        endTurnButton.setVisibility(View.GONE); // you must roll first before you can end your turn
        displayRoundPoints = (TextView)findViewById(R.id.RoundPoints);
        displayPlayerName = (TextView)findViewById(R.id.displayPlayerName);
        displayComputerName = (TextView)findViewById(R.id.displayComputerName);
        displayNumToWin = (TextView)findViewById(R.id.TargetScore);
        String zero = "0";
        String roundPointsStr = "Round Points:\n" + roundPoints;
        numToWin = Integer.parseInt(string);
        if(playerTurn){
            roundInfo = "Round 1 - " + playerName + "'s Turn";
        }
        else{
            roundInfo = "Round 1 - Banker's Turn";
        }
        roundDisplay.setText(roundInfo);
        playerName = playerName +":\n\n" + playerScore;
        displayPlayerName.setText(playerName);
        displayComputerName.setText(computerName);
        displayNumToWin.setText(winInfo);
        displayRoundPoints.setText(roundPointsStr);
        if(!playerTurn){
            rollDieButton.setVisibility(View.GONE);
            computerTurn(view);
        }
    }

    /**
     * @pre contentView = pig_dice_layout
     * @post roundPoints += diceValue
     * iff diceValue == 1:
     *  dice = R.drawable.dice_1
     *  iff diceValue == 2
     *        dice = R.drawable.dice_2
     *  iff diceValue == 3
     *        dice = R.drawable.dice_3
     *  iff diceValue == 4
     *        dice = R.drawable.dice_4
     *  iff diceValue == 5
     *      *  dice = R.drawable.dice_5
     *  iff diceValue == 6
     *      *  dice = R.drawable.dice_6
     *
     *  if(diceValue > 1):
     *      roundPoints += diceValue
     *      computerCondition = false
     *  else:
     *      roundPoints = 0
     *
     */
    public void rollDieFunc(View view) {
        //add code for random int between 1 and 6
        if(playerTurn) {
            endTurnButton.setVisibility(View.VISIBLE); // player now has option to end turn
            rollDieButton.setText(R.string.roll_again); // different string for roll dice button
        }

        if(diceValue > 1) { //diceView will have been determined by rollDiceAnimation
            roundPoints += diceValue;
            String roundPointsStr = "Round Points:\n" + roundPoints;
            displayRoundPoints.setText(roundPointsStr);
            if(diceValue == 2){
                dice.setImageResource(R.drawable.dice_2);
            }
            else if(diceValue == 3){
                dice.setImageResource(R.drawable.dice_3);
            }
            else if(diceValue == 4){
                dice.setImageResource(R.drawable.dice_4);
            }
            else if(diceValue == 5){
                dice.setImageResource(R.drawable.dice_5);
            }
            else{
                dice.setImageResource(R.drawable.dice_6);
            }
            computerCondition = true;
        }
        else{ //triggered if a 1 is rolled
            roundPoints = 0;
            dice.setImageResource(R.drawable.dice_1);
            new CountDownTimer(1000, 500) {
                public void onFinish() {
                    alertScreen.setVisibility(View.INVISIBLE); //remove alert screen
                    endTurn(view);
                }
                public void onTick(long millisUntilFinished) {
                    alertScreen.setText(R.string.One_Rolled);
                    alertScreen.setVisibility(View.VISIBLE);
                }
                // millisUntilFinished    The amount of time until finished.
            }.start();
            computerCondition = false; //set this to false to break the computer loop

        }
    }

    /**
     * @pre contentView = pig_dice_layout
     * @post
     * contentView = #contentView
     * roundPoints = 0
     * iff(playerTurn && diceValue != 1):
     *      playerTurn = false
     *      playerScore = #playerScore + roundPoints
     *      endTurnButton.GONE
     *      rollDieButton.GONE
     *      playerTurns = #playerTurns + 1
     * elif(!playerTurn && diceValue != 1):
     *      computerScore = #computerScore + roundPoints
     *      playerTurn = true
     *      computerTurns = #computerTurns + 1
     */
    public void endTurn(View view) {
        endTurnButton.setVisibility(View.GONE);
        rollDieButton.setText(R.string.roll_die);
        String string;
        boolean condition = false;
        if(playerTurn) {
            playerScore += roundPoints;
            string = onlyPlayerName + ":\n\n" + playerScore;
            displayPlayerName.setText(string);
            playerTurn = false;
            playerTurns++;
            resetRoundPoints();
            changeRoundInfo();
            endTurnButton.setVisibility(View.GONE);
            rollDieButton.setVisibility(View.GONE);
            condition = checkForWin();
            if(condition){
                return; //game is over
            }
            else {
                computerTurn(view); // it is now the computer's turn
            }
        }
        else{
            computerScore += roundPoints;
            string = "Banker:\n\n" + computerScore;
            displayComputerName.setText(string);
            playerTurn = true;
            computerTurns++;
            resetRoundPoints();
            changeRoundInfo();
            rollDieButton.setVisibility(View.VISIBLE);
            checkForWin();
        }
    }

    /**
     * @pre contentView = R.layout.pig_dice_layout
     * @return true if computerScore || playerScore >= numToWin
     * @post:
     * iff(computerScore >= numToWin):
     *      contentView = R.layout.win_screen
     *      winView.message = "The Banker Wins!!"
     *      gamesComputerWon = #gamesComputerWon + 1
     *  elif(playerScore >= numToWIn):
     *      contentView = R.layout.win_screen
     *      winView.message = "<playername> Wins!!"
     *      gamesPlayerWon = #gamesPlayerWon + 1
     *  else:
     *      do nothing
     *  contentView = #contentView
     */
    public boolean checkForWin(){
        String winMessage;
        if(computerScore >= numToWin){
            winMessage = "The Banker wins!!!";
            gamesComputerWon++;
            setContentView(R.layout.win_screen);
            TextView winView = findViewById(R.id.winText);
            winView.setText(winMessage);
            return true;
            //add code to reset the game
        }
        else if (playerScore >= numToWin){
            winMessage = onlyPlayerName + " wins!!!";
            gamesPlayerWon++;
            setContentView(R.layout.win_screen);
            TextView winView = findViewById(R.id.winText);
            winView.setText(winMessage);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @pre contentView = R.layout.pig_dice_layout
     * @post roundPoints = 0
     * displayRoundPoints.text = roundPoints
     * contentView = R.layout.pig_dice_layout
     */
    public void resetRoundPoints(){
        roundPoints = 0;
        String roundPointsStr = "Round Points:\n" + roundPoints;
        displayRoundPoints.setText(roundPointsStr);
    }

    /**
     * @pre contentView = win_screen
     * @post
     *   contentView = R.layout.pig_dice_layout
     *   computerScore  = 0
     *   playerScore = 0
     *   computerTurns = 0
     *   playerTurns = 0
     *   diceValue = 0
     *   round = 1
     *   dice = R.id.diceImg
     *   dice = View.VISIBLE
     *   rollDieButton = R.id.Roll_Die_Button
     *   roundDisplay = R.id.RoundInfo
     *   endTurnButton = R.id.End_Turn
     *   alertScreen = R.id.alert_screen
     *   alertScreen.View = GONE
     *   endTurnButton.View = GONE
     *   displayRoundPoints = R.id.RoundPoints
     *   displayPlayerName = R.id.displayPlayerName
     *   displayComputerName = R.id.displayComputerName
     *   displayNumToWin = R.id.TargetScore
     *   roundDisplay.text = roundInfo
     *   playerName = onlyPlayerName +":\n\n" + 0
     *   displayPlayerName.text(playerName)
     *   displayComputerName.text(computerName)
     *   displayComputerGamesWon = R.id.computerGamesWon
     *   displayPlayerGamesWon = R.id.playerGamesWon
     *   displayComputerGamesWon.View = GONE
     *   displayPlayerGamesWon.View = GONE
     *   iff(!playerTurn):
     *         rollDieButton.View = GONE
     */
    public void newGame(View view){ //when the content view gets changed I need to reassign the view objects
        computerScore  = 0;
        playerScore = 0;
        computerTurns = 0;
        playerTurns = 0;
        diceValue = 0;
        computerCondition = true;
        resetRoundPoints();
        Random random = new Random();
        round = 1;
        int number = random.nextInt(100);
        if(number % 2 == 0){
            playerTurn = true;
        }
        else{
            playerTurn = false;
        }
        String computerName = "Banker:\n\n" + computerScore;
        String winInfo = "Score Needed to Win:\n" + numToWin;

        setContentView(R.layout.pig_dice_layout);

        displayComputerGamesWon = (TextView) findViewById(R.id.computerGamesWon);
        displayPlayerGamesWon = (TextView) findViewById(R.id.playerGamesWon);
        String string = "Computer's Games Won:\n" + gamesComputerWon;
        displayComputerGamesWon.setText(string);
        string = onlyPlayerName + "'s Games Won:\n" + gamesPlayerWon;
        displayPlayerGamesWon.setText(string);
        dice = findViewById(R.id.diceImg);
        dice.setVisibility(View.VISIBLE);
        rollDieButton = (Button) findViewById(R.id.Roll_Die_Button);
        roundDisplay = (TextView)findViewById(R.id.RoundInfo);
        endTurnButton = (Button) findViewById(R.id.End_Turn);
        alertScreen = (TextView) findViewById(R.id.alert_screen);
        alertScreen.setVisibility(View.GONE);
        endTurnButton.setVisibility(View.GONE); // you must roll first before you can end your turn
        displayRoundPoints = (TextView)findViewById(R.id.RoundPoints);
        displayPlayerName = (TextView)findViewById(R.id.displayPlayerName);
        displayComputerName = (TextView)findViewById(R.id.displayComputerName);
        displayNumToWin = (TextView)findViewById(R.id.TargetScore);
        String zero = "0";
        String roundPointsStr = "Round Points:\n" + roundPoints;
        String roundInfo;
        if(playerTurn){
            roundInfo = "Round 1 - " + onlyPlayerName + "'s Turn";
        }
        else{
            roundInfo = "Round 1 - Banker's Turn";
        }
        roundDisplay.setText(roundInfo);
        playerName = onlyPlayerName +":\n\n" + playerScore;
        displayPlayerName.setText(playerName);
        displayComputerName.setText(computerName);
        displayNumToWin.setText(winInfo);
        displayRoundPoints.setText(roundPointsStr);
        if(!playerTurn){
            rollDieButton.setVisibility(View.GONE);
            computerTurn(view);
        }
    }

    /**
     * @pre contentView = R.layout.pig_dice_layout
     * @post
     * iff(playerTurn == computerTurn && playerTurn):
     *      #round++;
     *      roundDisplay.text = "Round + str(round)" + " - <playername>'s Turn"
     *elif(playerTurn == computerTurn && !playerTurn):
     *      #round++
     *      roundDisplay.text = "Round + str(round)" + " - Banker's Turn"
     * elif(playerTurn != computerTurn && playerTurn):
     *      roundDisplay.text = "Round + str(round)" + " - <playerName>'s Turn"
     * else:
     *      roundDisplay.text = "Round + str(round)" + " - Banker's Turn"
     * contentView = R.layout.pig_dice_layout
     */
    public void changeRoundInfo(){
        String string;
        String stringTurn;
        if(playerTurn){
            stringTurn = onlyPlayerName + "'s Turn";
        }
        else{
            stringTurn  = "Banker's Turn";
        }
        if(playerTurns == computerTurns){
            round++;
            string = "Round " + round + " - " + stringTurn;
            roundDisplay.setText(string);
        }
        else{
            string = "Round " + round + " - " + stringTurn;
            roundDisplay.setText(string);
        }
    }

    /**
     * @pre playerTurn == false
     * contentView = R.layout.pig_dice_layout
     * @post playerTurn == true
     * contentView = R.layout.pig_dice_layout
     */
    public void computerTurn(View view){
        computerCondition = true;

        countDownTimer = new CountDownTimer(9000, 3000) {
            public void onFinish(){
                if(diceValue != 1 && roundPoints >= 15){ //fixed bug where if the computer's last roll was a 1, would skip player's turn
                    endTurn(view);
                }
                else if(diceValue != 1 && roundPoints < 15){
                    countDownTimer.start(); //to few points for a round, restart timer
                }
            }
            public void onTick(long millisUntilFinished) {
                if (!computerCondition){
                    countDownTimer.cancel(); //you rolled a 1
                }
                else if(roundPoints >= 18 || roundPoints + computerScore >= numToWin){
                    endTurn(view); //you have plenty of points or you have enough points to win
                    countDownTimer.cancel();
                }
                else{
                    rollDieAnimation(view);
                }
            }
                // millisUntilFinished    The amount of time until finished.
        }.start();
    }

    /**
     * @pre contentView = win_screen
     * @post contentView = activity_main
     */
    public void quitGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * @pre contentView = R.layout.pig_dice_layout
     * @post
     * diceValue > 0 && diceValue < 7
     * dice.setImage(R.drawable.?) == diceValue
     * contentView = #contentView
     */
    public void rollDieAnimation(View view) {
        Random random = new Random();
        new CountDownTimer(2000, 200) {
            public void onFinish() {
                rollDieFunc(view);
            }
            public void onTick(long millisUntilFinished) {
                diceValue = random.nextInt(6);
                diceValue++;
                switch (diceValue){
                    case 1:
                        dice.setImageResource(R.drawable.dice_1);
                        break;
                    case 2:
                        dice.setImageResource(R.drawable.dice_2);
                        break;
                    case 3:
                        dice.setImageResource(R.drawable.dice_3);
                        break;
                    case 4:
                        dice.setImageResource(R.drawable.dice_4);
                        break;
                    case 5:
                        dice.setImageResource(R.drawable.dice_5);
                        break;
                    case 6:
                        dice.setImageResource(R.drawable.dice_6);
                        break;
                }
            }
            // millisUntilFinished    The amount of time until finished.
        }.start();
    }
}
