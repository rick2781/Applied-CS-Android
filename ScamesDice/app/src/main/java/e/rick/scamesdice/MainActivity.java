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

    Handler handler = new Handler();

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

    private void updateScoreDisplay() {

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

        updateScoreDisplay();
    }

    private void computerTurn() {

        isCpuTurn(true);

        enableButton(false);

        if (cpuTurn) {
            rollDice();
        }

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                if (turnCPUScore < 40 && cpuTurn) {

                    rollDice();
                }

                holdFunctionality();

                isCpuTurn(false);

                enableButton(true);
            }
        }, 1300);

        declareWinner();
    }

    private void rollDice() {

        declareWinner();

        int randomValueDice1 = random.nextInt(6);
        int randomValueDice2 = random.nextInt(6);

        final ImageView dice1Image = findViewById(R.id.dice1Image);
        final ImageView dice2Image = findViewById(R.id.dice2Image);

        int dice[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

        dice1Image.setImageResource(dice[randomValueDice1]);
        dice2Image.setImageResource(dice[randomValueDice2]);

        randomValueDice1 ++;
        randomValueDice2 ++;

        if (randomValueDice1 == 0 && randomValueDice2 == 0) {

            if (userTurn) {

                Toast.makeText(this, "You Rolled 1 Twice.\n You lost all your score.", Toast.LENGTH_SHORT).show();

                turnUserScore = 0;
                overallUserScore = 0;

                updateScoreDisplay();

                computerTurn();

            } else if (cpuTurn) {

                Toast.makeText(this, "CPU Rolled 1 twice.\n He lost all his score.", Toast.LENGTH_SHORT).show();

                turnCPUScore = 0;
                overallCPUScore = 0;

                updateScoreDisplay();

                isCpuTurn(false);
            }
        }

        if (randomValueDice1 == 1 || randomValueDice2 == 1) {

            addTurnScore(0);

            Log.d(TAG, "rollDice: dice == 1 executed");

            if (userTurn) {

                Toast.makeText(this, "User Rolled 1", Toast.LENGTH_SHORT).show();

                holdFunctionality();
                computerTurn();

            } else if (cpuTurn) {

                Toast.makeText(this, "CPU Rolled 1", Toast.LENGTH_SHORT).show();

                isCpuTurn(false);
            }

        } else if (randomValueDice1 == randomValueDice2) {

            addTurnScore(randomValueDice1 + randomValueDice2);

            enableButton(false);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    rollDice();
                    enableButton(true);
                }
            }, 1000);

        } else {

            Log.d(TAG, "rollDice: regular dice executed");

            addTurnScore(randomValueDice1 + randomValueDice2);

            Log.d(TAG, "rollDice value1: " + randomValueDice1 + "value 2: " + randomValueDice2);
        }
    }

    private void addTurnScore(int value) {

        Log.d(TAG, "addTurnScore: value " + value);

        if (userTurn) {

            if (value == 0) {

                turnUserScore = 0;

            } else {

                turnUserScore += value;

                Log.d(TAG, "addTurnScore: userScore: " + turnUserScore);
            }

        } else if (cpuTurn) {

            if (value == 0) {

                turnCPUScore = 0;

            } else {

                turnCPUScore += value;
            }
        }

        updateScoreDisplay();
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

        updateScoreDisplay();
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

