package e.rick.scamesdice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    int overallUserScore = 0;
    int turnUserScore = 0;

    int overallCPUScore = 0;
    int turnCPUScore = 0;

    boolean userTurn;
    boolean cpuTurn;

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userTurn = true;
        cpuTurn = false;

        rollDiceButton();
        resetButton();
        holdButton();
    }

    private void rollDiceButton() {

        Button rollButton = findViewById(R.id.rollButton);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userTurn) {

                    Log.d(TAG, "onClick: userturn");
                }

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

                resetFunctionality();
            }
        });
    }

    private void holdButton() {

        Button holdButton = findViewById(R.id.holdButton);

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holdFunctionality();

                if (userTurn) {

                    computerTurn();
                }
            }
        });
    }

    private void holdFunctionality() {

        if (userTurn) {

            overallUserScore = overallUserScore + turnUserScore;

            turnUserScore = 0;

        } else if (cpuTurn) {

            overallCPUScore = overallCPUScore + turnCPUScore;

            turnCPUScore = 0;
        }

        changeScoreDisplay();
    }

    private void computerTurn() {

        isCpuTurn(true);

        enableButton(false);

        if (cpuTurn) {
            rollDice();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                if (turnCPUScore < 20 && cpuTurn) {

                    rollDice();
                }

                holdFunctionality();

                isCpuTurn(false);

                enableButton(true);
            }
        }, 1300);
    }

    private void rollDice() {

        declareWinner();

        int randomValue = random.nextInt(6);

        final ImageView diceImage = findViewById(R.id.dice1Image);

        int dice[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

        diceImage.setImageResource(dice[randomValue]);

        randomValue++;

        Log.d(TAG, "rollDice: value - " + randomValue);

        if (randomValue == 1) {

            addTurnScore(0);

            if (userTurn) {

                Toast.makeText(this, "User Rolled 1", Toast.LENGTH_SHORT).show();

                holdFunctionality();
                computerTurn();

            } else if (cpuTurn) {

                Toast.makeText(this, "CPU Rolled 1", Toast.LENGTH_SHORT).show();

                isCpuTurn(false);
            }

        } else {

            addTurnScore(randomValue);
        }
    }

    private void addTurnScore(int value) {

        if (userTurn) {

            if (value == 0) {

                turnUserScore = 0;

            } else {

                turnUserScore += value;
            }

        } else if (cpuTurn) {

            if (value == 0) {

                turnCPUScore = 0;

            } else {

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

    private void isCpuTurn(boolean turn) {

        if (turn) {

            cpuTurn = true;
            userTurn = false;

        } else {

            cpuTurn = false;
            userTurn = true;
        }
    }

    private void resetFunctionality() {

        overallUserScore = 0;
        overallCPUScore = 0;
        turnUserScore = 0;
        turnCPUScore = 0;

        changeScoreDisplay();
    }

    private void declareWinner() {

        if (overallCPUScore >= 100) {

            Toast.makeText(this, "You lost!\n  CPU won!", Toast.LENGTH_SHORT).show();

            recreate();

        } else if (overallUserScore >= 100) {

            Toast.makeText(this, "Congratulations! \n You won!", Toast.LENGTH_SHORT).show();

            recreate();
        }
    }
}

