package e.rick.scamesdice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int overallUserScore = 0;
    int turnUserScore = 0;

    int overallCPUScore = 0;
    int turnCPUScore = 0;

    boolean userTurn = true;
    boolean cpuTurn = false;

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollDiceButton();
        resetButton();
        holdButton();
    }

    private void rollDiceButton() {

        Button rollButton = findViewById(R.id.rollButton);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rollDice();
            }
        });
    }

    private void changeScoreDisplay() {

        TextView scoreDisplay = findViewById(R.id.score);

        String standardScore = "Your score: " + String.valueOf(overallUserScore) + " " + "Computer Score: " + String.valueOf(overallCPUScore);
        String turnScore = "Turn Score: ";

        scoreDisplay.setText(standardScore);

        if (userTurn) {

            scoreDisplay.setText(standardScore + " " + turnScore + String.valueOf(turnUserScore));

        } else if (cpuTurn) {

            scoreDisplay.setText(standardScore + " " + turnScore + String.valueOf(turnCPUScore));
        }
    }

    private void resetButton() {

        Button resetButton = findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                overallUserScore = 0;
                overallCPUScore = 0;
                turnUserScore = 0;
                turnCPUScore = 0;

                changeScoreDisplay();
            }
        });
    }

    private void holdButton() {

        Button holdButton = findViewById(R.id.holdButton);

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holdFunctionality();
            }
        });
    }

    private void holdFunctionality(){

        if (userTurn) {

            if (overallUserScore == 0) {

                overallUserScore = turnUserScore;

            } else {

                overallUserScore = overallUserScore + turnUserScore;
            }

            turnUserScore = 0;

            computerTurn();

        } else if (cpuTurn) {

            if (overallCPUScore == 0) {

                overallCPUScore = turnCPUScore;

            } else {

                overallCPUScore = overallCPUScore + turnCPUScore;
            }

            turnCPUScore = 0;
        }

        changeScoreDisplay();
    }

    private void computerTurn() {

        cpuTurn = true;
        userTurn = false;

        enableButton(false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                rollDice();
            }
        }, 1300);

        cpuTurn = false;
        userTurn = true;

        enableButton(true);
    }

    private void rollDice() {

        int randomValue = random.nextInt(6);

        final ImageView diceImage = findViewById(R.id.diceImage);

        int dice[] = { R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

        diceImage.setImageResource(dice[randomValue]);

        randomValue ++;

        if (randomValue == 1) {

            addTurnScore(0);

            if (userTurn) {

                computerTurn();

            } else if (cpuTurn) {

                cpuTurn = false;
                userTurn = true;
            }

        } else {

            addTurnScore(randomValue);
        }
    }

    private void addTurnScore(int value) {

        if (value == 0) {

            if (userTurn) {

                turnUserScore = 0;

            } else if (cpuTurn) {

                turnCPUScore = 0;
            }

        } else {

            if (userTurn) {

                turnUserScore += value;

            } else if (cpuTurn) {

                turnCPUScore += value;
            }
        }

        changeScoreDisplay();
    }

    private void enableButton(boolean enable) {

        Button rollButton = findViewById(R.id.rollButton);
        Button holdButton = findViewById(R.id.holdButton);
        Button resetButton = findViewById(R.id.resetButton);

        if (enable) {

            rollButton.setEnabled(true);
            holdButton.setEnabled(true);
            resetButton.setEnabled(true);
        } else {

            rollButton.setEnabled(false);
            holdButton.setEnabled(false);
            resetButton.setEnabled(false);
        }
    }
}

