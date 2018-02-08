package e.rick.scamesdice;

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

    int overllCPUScore = 0;
    int turnCPUScore = 0;

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

        final ImageView diceImage = findViewById(R.id.diceImage);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rollDiceUser();

                changeScoreDisplay(turnUserScore);
            }
        });
    }

    private void changeScoreDisplay(int currentTurnScore) {

        TextView scoreDisplay = findViewById(R.id.score);

        String standardScore = "Your score: " + String.valueOf(overallUserScore) + " " + "Computer Score: " + String.valueOf(overllCPUScore);
        String turnScore = "Turn Score: ";

        scoreDisplay.setText(standardScore);

        if (currentTurnScore != 0) {

            scoreDisplay.setText(standardScore + " " + turnScore + String.valueOf(currentTurnScore));
        }
    }

    private void resetButton() {

        Button resetButton = findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                overallUserScore = 0;
                overllCPUScore = 0;
                turnUserScore = 0;
                turnCPUScore = 0;

                changeScoreDisplay(0);
            }
        });
    }

    private void holdButton() {

        Button holdButton = findViewById(R.id.holdButton);

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (overallUserScore == 0) {

                    overallUserScore = turnUserScore;
                } else {

                    overallUserScore = overallUserScore + turnUserScore;
                }

                turnUserScore = 0;

                changeScoreDisplay(turnUserScore);
            }
        });
    }

    private boolean computerTurn() {

        while (turnCPUScore < 20) {

            rollDiceComputer();
        }

        if (overllCPUScore == 0) {

            overllCPUScore = turnCPUScore;

        } else {

            overllCPUScore = overllCPUScore + turnCPUScore;
        }

        turnCPUScore = 0;

        changeScoreDisplay(turnCPUScore);

        return false;
    }

    private void rollDiceUser() {

        Integer[] possibleValue = {1, 2, 3, 4, 5, 6};

        int randomValue = random.nextInt(possibleValue.length);

        final ImageView diceImage = findViewById(R.id.diceImage);

        switch (possibleValue[randomValue]) {

            case 1:
                diceImage.setImageResource(R.drawable.dice1);
                turnUserScore = 0;

                computerTurn();
                break;

            case 2:
                diceImage.setImageResource(R.drawable.dice2);
                turnUserScore = turnUserScore + 2;
                break;

            case 3:
                diceImage.setImageResource(R.drawable.dice3);
                turnUserScore = turnUserScore + 3;
                break;

            case 4:
                diceImage.setImageResource(R.drawable.dice4);
                turnUserScore = turnUserScore + 4;
                break;

            case 5:
                diceImage.setImageResource(R.drawable.dice5);
                turnUserScore = turnUserScore + 5;
                break;

            case 6:
                diceImage.setImageResource(R.drawable.dice6);
                turnUserScore = turnUserScore + 6;
                break;
        }
    }

    private void rollDiceComputer() {

        Integer[] possibleValue = {1, 2, 3, 4, 5, 6};

        int randomValue = random.nextInt(possibleValue.length);

        final ImageView diceImage = findViewById(R.id.diceImage);

        switch (possibleValue[randomValue]) {

            case 1:
                diceImage.setImageResource(R.drawable.dice1);
                turnCPUScore = 0;
                break;

            case 2:
                diceImage.setImageResource(R.drawable.dice2);
                turnCPUScore = turnCPUScore + 2;
                break;

            case 3:
                diceImage.setImageResource(R.drawable.dice3);
                turnCPUScore = turnCPUScore + 3;
                break;

            case 4:
                diceImage.setImageResource(R.drawable.dice4);
                turnCPUScore = turnCPUScore + 4;
                break;

            case 5:
                diceImage.setImageResource(R.drawable.dice5);
                turnCPUScore = turnCPUScore + 5;
                break;

            case 6:
                diceImage.setImageResource(R.drawable.dice6);
                turnCPUScore = turnCPUScore + 6;
                break;
        }
    }
}
