//Roberto Gutierrez
//JAVA1-1709
//MainActivity.java
package com.rdgdevlopment.gutierrezroberto_ce02;

import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

//MESSAGE FOR ANYONE REVIEWING THIS PROJECT
/*
 THIS PROJECT WAS BUILT AS PART OF A CLASS EXERCISE AND WAS DONE WITH MY OWN ORIGINAL CODE.

 IF YOUR ARE REVIEWING THIS PROJECT IT IS BECAUSE YOU HAVE BEEN AUTHORIZED TO LOOK AT THIS AS AN EXAMPLE OF SOME OF MY JAVA PROGRAMMING KNOWLEDGE.

 THIS IS A 4 DIGIT GUESSING GAME THAT TELLS YOU IF YOUR GUESS IS HOT, COLD, OR CORRECT THROUGH THE COLOR OF YOUR INPUT.

 WHEN ALL 4 DIGITS ARE GUESSED CORRECTLY A MESSAGE WILL APPEAR INFORMING YOU OF WINNING AND WILL ASK IF YOU WOULD LIKE TO PLAY AGAIN.

 THIS PROJECT WAS TESTED ON A VIRTUAL DEVICE NEXUS 5X API 25 ON ANDROID STUDIO

 THANK YOU FOR YOUR TIME IN REVIEWING MY SKILL SET.
 */

public class MainActivity extends AppCompatActivity {

    //Create an array to hold fields of the EditText
    EditText[] myEts;
    //Create an array to hold the random numbers generated
    int[] myInts;
    //Create the random class holder
    Random myRand;
    //Create a tag for loggin to log cat
    private static final String TAG = "MyActivity";
    //Create a int to represent how many tries the user has left.
    int numberOfTries;
    //Create an array that will hold the correct answers
    int[] matchingAnswers;
    //Create an emptyArray to reset the game
    int[] emptyInt;
    String[] emptyStrings;
    //Create a boolean to use for checking the users status
    Boolean youWon;
    //Create a static variable that will be used in several ways including filling arrays
   static final int myFour = 4;
    //Create a place holder for the random range
    static final int my10Range = 10;
    //Set up the guessListener and call the onClick method
    View.OnClickListener guessListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            checkAnswer(myEts);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Add a click listener to the button
        Button submitGuess = (Button) findViewById(R.id.button_submit);
        submitGuess.setOnClickListener(guessListener);
        //Prepare the youWon boolean by setting it equal to false
        youWon = false;
        //Prepare the array by allocating 4 EditText spots
        myEts = new EditText[myFour];
        myEts[0] = (EditText) findViewById(R.id.entry1);
        myEts[1] = (EditText) findViewById(R.id.entry2);
        myEts[2] = (EditText) findViewById(R.id.entry3);
        myEts[3] = (EditText) findViewById(R.id.entry4);

        //Initialize the array that will hold the random numbers
        myInts = new int[myFour];
        //Initialize the matching numbers array
        matchingAnswers = new int[myFour];
        //initialize your random generator
        myRand = new Random();
        //Show how many tries are left
        numberOfTries = myFour;
        //initialize the empty strings with 4 spaces
        emptyInt = new int[myFour];
        emptyStrings = new String [myFour];
        //Tell the user how many tries they start with
        Toast.makeText(getApplicationContext(), this.getString(R.string.triesLeft) + Integer.toString(numberOfTries), Toast.LENGTH_LONG).show();
    }


    //Create a loop that will check the users answers against the random number
    private void checkAnswer(final EditText[] array) {
        //Call the fillRandomInts function to create the random integer key
        fillRandomInts(myInts);
        //loop through the textViews and make sure they are not empty. If so, inform the user
        for (EditText textView : array) {
            Log.v(TAG, "Text from user = " + textView.getText().toString());
            String theViewsValue = textView.getText().toString();
            if (theViewsValue.isEmpty()){
                Toast.makeText(getApplicationContext(), R.string.emptyFieldToast, Toast.LENGTH_SHORT).show();
                return;
            }
        }

        //Run a loop through each of the arrays and compare the answers and act accordingly
        for (int index=0; index<myFour;index++){
            //Make sure you have both integers for comparison
            int answerKey = myInts[index];
            int userInput = Integer.parseInt(array[index].getText().toString());
            //Change the color of the text according to the comparison and insert the number into the matching collection if they match.
            if (answerKey == userInput){
                array[index].setEnabled(false);
                array[index].setTextColor(ContextCompat.getColor(this, R.color.correctAnswer));
                matchingAnswers[index] = answerKey;
                if (Arrays.equals(matchingAnswers, myInts) && numberOfTries >= 1){
                    youWon = true;
                    showWinningAlert();
                }
            }else if (answerKey > userInput){
                array[index].setTextColor(ContextCompat.getColor(this, R.color.tooLow));
            }else if (answerKey < userInput){
                array[index].setTextColor(ContextCompat.getColor(this, R.color.tooHigh));
            }
        }
        //Every time the check answer is pushed inform the user of how many tries he/she has left.
        if (numberOfTries != 0){
            numberOfTries--;
            Toast.makeText(getApplicationContext(), this.getString(R.string.triesLeft) + Integer.toString(numberOfTries), Toast.LENGTH_SHORT).show();

        }else if (!youWon && numberOfTries == 0){
            //This is the alert that will appear when the user runs out of tries
           showLosingAlert();
        }
    }

    //METHODS---------------------------------------------------------------------------------<<<<<
    //Create a index to fill the my Ints array
    int intArrayIndex = 0;
    //Create a method that fills in th erandom numbers for us and call it when the button is pressed.
    private void fillRandomInts (int[] intArray) {
        while (intArrayIndex < intArray.length) {
            //Create a random number; Populate the array with the number
            int aRandomNumber = myRand.nextInt(my10Range);
            Log.v(TAG, "The Correct number =" + aRandomNumber);
            intArray[intArrayIndex] = aRandomNumber;
            intArrayIndex++;
        }
    }

    private void showLosingAlert (){
        AlertDialog.Builder lostAlert = new AlertDialog.Builder(MainActivity.this);
        lostAlert.setTitle(R.string.LostAlert);
        lostAlert.setCancelable(false);
        lostAlert.setPositiveButton(R.string.playAgain, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Reset all the values for when the user plays again
                        enableAllFields(myEts);
                        numberOfTries = myFour;
                        myInts = emptyInt;
                        matchingAnswers = emptyInt;
                        intArrayIndex = 0;
                        youWon = false;
                        //Loop through all the edit text views and set them blank
                        for (EditText textView : myEts){
                            textView.setText("");
                        }
            }
        });
        lostAlert.show();
    }

    private void showWinningAlert (){
        //Insert the alert that lets the user know that they won.
        AlertDialog.Builder wonAlert = new AlertDialog.Builder(this);
        wonAlert.setCancelable(false);
        wonAlert.setTitle(R.string.youWon);
        wonAlert.setPositiveButton(R.string.playAgain, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Reset all the values for when the user plays again
                for (EditText textView : myEts){
                    textView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                }
                enableAllFields(myEts);
                youWon = false;
                numberOfTries = myFour;
                myInts = emptyInt;
                matchingAnswers = new int [myFour];
                intArrayIndex = 0;
                //Loop through all the edit text views and set them blank
                for (EditText textView : myEts){
                    textView.setText("");
                }
            }
        });
        wonAlert.show();
    }

    //Create a method to enable the textfields that were disabled when correct
    private void enableAllFields(EditText[] array){
        for (EditText textView : array){
            textView.setEnabled(true);
        }
    }

}
