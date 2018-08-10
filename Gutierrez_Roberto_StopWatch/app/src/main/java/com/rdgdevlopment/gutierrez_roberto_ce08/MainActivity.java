/*
Roberto Gutierrez
JAVA 1-1709
MainActivity
 */
package com.rdgdevlopment.gutierrez_roberto_ce08;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


//MESSAGE FOR ANYONE REVIEWING THIS PROJECT
/*
 THIS PROJECT WAS BUILT AS PART OF A CLASS EXERCISE AND WAS DONE WITH MY OWN ORIGINAL CODE. IT DISPLAYS MY UNDERSTANDING OF THE APPLICATION
 LIFE CYCLE, AND HOW TO CREATE A AYSNC TASK.

 IF YOUR ARE REVIEWING THIS PROJECT IT IS BECAUSE YOU HAVE BEEN AUTHORIZED TO LOOK AT THIS AS AN EXAMPLE OF SOME OF MY JAVA PROGRAMMING KNOWLEDGE.

 THIS IS A SIMPLE STOP WATCH APPLICATION THAT TAKES IN THE USERS INPUTED TIME IN MINUTES AND SECONDS.

 THE APPLICATION WILL THEN COUNT DOWN UNTIL THE TIMER IS STOPPED OR RUNS OUT OF TIME.

 THIS PROJECT WAS TESTED ON A VIRTUAL DEVICE NEXUS 5X API 25 ON ANDROID STUDIO

 THANK YOU FOR YOUR TIME IN REVIEWING MY SKILL SET.

 */

public class MainActivity extends AppCompatActivity {

    //Create our reference variables to our UI components.
    private EditText theMinutesField;
    private EditText theSecondsField;
    private Button theStartButton;
    private Button theStopButton;
    private boolean numbersGoodToGo = false;
    private anAsyncThread backThread;
    private final Context activityIsContext = this;
    //Create my TAG
    private static  String infoTag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set our UI components
        theMinutesField = (EditText) findViewById(R.id.minutesET);
        theSecondsField = (EditText) findViewById(R.id.secondsET);
        theStartButton = (Button) findViewById(R.id.startButton);
        theStopButton = (Button) findViewById(R.id.stopButton);

        //Set the button Listeners
        theStartButton.setOnClickListener(startButtonPressed);
        theStopButton.setOnClickListener(stopClicked);
        infoTag = activityIsContext.getString(R.string.myTag);
    }//onCreate



    //METHODS---------------------------------------------------------------------------------------
    //Create On click events for our buttons
    private final View.OnClickListener startButtonPressed = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Create the task
            backThread = new anAsyncThread();
            //Check the number inputs
            int[] validInts = timeChecker(theMinutesField, theSecondsField);
            Log.v(infoTag,"minutes value is " + Integer.toString(validInts[0]) + " seconds value is " + Integer.toString(validInts[1]));
            //Create an instance of the asyncThread
            //If the numbers are signaled as ok then continue into the background thread. If thread was cancelled before, change to not cancelled.
            if (backThread.isCancelled()){
                backThread.cancel(false);
            }
            if (numbersGoodToGo) {
                    backThread.execute(validInts[1], validInts[0]);
            }
        }
    };//startButtonPressed

    //Create a On Click for the Stop button
    private final View.OnClickListener stopClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(backThread != null) {
                //Cancel the thread.
                backThread.cancel(true);
            }
        }
    };//stopClicked

    //Create a method that checks the users text to make sure it is valid.
    private int[] timeChecker(EditText userMinutes, EditText userSeconds) {
        String minutesString = userMinutes.getText().toString();
        String secondsString = userSeconds.getText().toString();
        //If fields are empty fill make values zero.
        if(minutesString.isEmpty()) {
            minutesString = getString(R.string.zeroString);
            theMinutesField.setText(minutesString);
        }
        if (secondsString.isEmpty()){
            secondsString = getString(R.string.zeroString);
            theSecondsField.setText(secondsString);
        }
        //These will be the int values of time. The array will hold the values.
        int minutesAsNumbers;
        int secondsAsNumbers;
        int[] tempArray = new int[2];
        //Begin checking values.
        if (Integer.parseInt(minutesString) == 0) {
            if(Integer.parseInt(secondsString) == 0){
                runInputToast();
                numbersGoodToGo = false;
            }else {
                //The minutes here are going to be 0 or not entered at all so we will automatically set it to 0.
                minutesString = "0";
                minutesAsNumbers = Integer.parseInt(minutesString);
                secondsAsNumbers = Integer.parseInt(secondsString);
                //Used to display any entry of seconds above or equal to 60 correctly.
                if (secondsAsNumbers >= 60){
                    minutesAsNumbers += 1;
                    secondsAsNumbers -= 60;
                    if(minutesAsNumbers > 99){
                        Toast.makeText(this, R.string.maxMinutesString, Toast.LENGTH_SHORT).show();
                        minutesAsNumbers = 99;
                    }
                }
                //Set the fields to the corrected numbers
                theMinutesField.setText(Integer.toString(minutesAsNumbers));
                theSecondsField.setText(Integer.toString(secondsAsNumbers));
                //Set our array to the numbers and tell the system that we are good to go.
                tempArray[0] = minutesAsNumbers;
                tempArray[1] = secondsAsNumbers;
                numbersGoodToGo = true;
                return tempArray;
            }
            //At this point we know that minutes are going to be larger than 0, We just have to prepare if seconds in empty or 0.
        }else if (Integer.parseInt(minutesString) > 0 && Integer.parseInt(secondsString) == 0){
            minutesAsNumbers = Integer.parseInt(minutesString);
            secondsAsNumbers = Integer.parseInt(getString(R.string.zeroString));
            theMinutesField.setText(Integer.toString(minutesAsNumbers));
            theSecondsField.setText(Integer.toString(secondsAsNumbers));
            tempArray[0] = minutesAsNumbers;
            tempArray[1] = secondsAsNumbers;
            numbersGoodToGo = true;
            return tempArray;
        }else{
            //Here we know that neither is zero se we can just assign the numbers.
            minutesAsNumbers = Integer.parseInt(minutesString);
            secondsAsNumbers = Integer.parseInt(secondsString);
            //Used to display any entry of seconds above or equal to 60 correctly.
            if (secondsAsNumbers >= 60){
                minutesAsNumbers += 1;
                secondsAsNumbers -= 60;
                if(minutesAsNumbers > 99){
                    Toast.makeText(this,getString(R.string.maxMinutesString), Toast.LENGTH_SHORT).show();
                    minutesAsNumbers = 99;
                }
            }
            theMinutesField.setText(Integer.toString(minutesAsNumbers));
            theSecondsField.setText(Integer.toString(secondsAsNumbers));
            tempArray[0] = minutesAsNumbers;
            tempArray[1] = secondsAsNumbers;
            numbersGoodToGo = true;
            return tempArray;
        }
        return tempArray;
    }//timeChecker


    //This is the Toast that will show when the user enters invalid entries
    private void runInputToast (){
        Toast.makeText(this, getString(R.string.invalidToastText), Toast.LENGTH_SHORT).show();
    }//runInputAlert

    //Create a function tha calculates the amount of time that has passed once the cancel button has been pushed
    private String calculateTimeElapsed (long start, long end) {
        long difference = end - start;
        int minutesInt;
        int rawSecondsInt;
        int theSecondsInt;
        Long divisionRawSeconds = difference/1000;
        rawSecondsInt = divisionRawSeconds.intValue();
        minutesInt = rawSecondsInt/60;
        theSecondsInt = rawSecondsInt%60;
        return "Minute(s): " + Integer.toString(minutesInt) + " second(s): " + Integer.toString(theSecondsInt);
    }//calculateTimeElapsed


    //This is the AsyncTask Implementation
    private class anAsyncThread extends AsyncTask<Integer, String, String> {
        private static final String infoTag = "TAG";
        //These variables will be used to update my UI
        int varForMin = 0;
        int varForSec= 0;
        long startTimeForCancel;
        //This used to perform things before the task starts. For example doing something on the UI before we start the long operation.
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(activityIsContext, R.string.timerstartingToastText,Toast.LENGTH_SHORT).show();
            startTimeForCancel = System.currentTimeMillis();
        }//onPreExecute

        //This performs the long running task and should NEVER  EVER touch the UI
        @Override
        protected String doInBackground(Integer... params) {
            long startTimeMillis = System.currentTimeMillis();
            final long sleepInterval = 500;
            //We can get our current time and use to figure out how much time is passing and then update our UI accordingly.
            //params[0] is seconds
            while (params[0] >= 0 && !this.isCancelled()) {
                try {
                    long differenceTimeMillis = System.currentTimeMillis() - startTimeMillis;
                    if (differenceTimeMillis >= 1000L) {
                        varForMin = params[1];
                        varForSec = params[0];
                        if (params[0] == 0 && params[1] != 0) {
                            params[1] -= 1;
                            varForMin = params[1];
                            params[0] = 59;
                            varForSec = params[0];
                            publishProgress(Integer.toString(params[0]));
                            publishProgress(Integer.toString(params[1]));
                            startTimeMillis = System.currentTimeMillis();
                        } else {
                            if (params[0] == 0 && params[1] == 0) {
                                varForMin = params[1];
                                varForSec = params[0];
                                return getString(R.string.finishedCounting);
                            }
                            params[0] -= 1;
                            varForSec = params[0];
                            publishProgress(Integer.toString(params[0]));
                            startTimeMillis = System.currentTimeMillis();
                        }
                    } else {
                        Thread.sleep(sleepInterval);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return getString(R.string.noCountingDone);
        }//doInBackground

        //This can be used to give the user feed back while the long running task is operating
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if(Integer.parseInt(values[0]) < 0){
                //Do something here to prevent putting anything less than 0.
                varForSec = 0;
            }else{
                theMinutesField.setText(Integer.toString(varForMin));
                theSecondsField.setText(Integer.toString(varForSec));
            }
        }//onProgressUpdate

        //This can be used to explain what to do or has been done when the task is cancelled
        @Override
        protected void onCancelled() {
            super.onCancelled();
            long stopTimeForCancel = System.currentTimeMillis();
            //Run the time elapsed calculator
            theSecondsField.setText(R.string.zeroString);
            theMinutesField.setText(R.string.zeroString);
            AlertDialog.Builder timerCancelledAlert = new AlertDialog.Builder(activityIsContext);
            timerCancelledAlert.setMessage("Time Elapsed: " + calculateTimeElapsed(startTimeForCancel, stopTimeForCancel));
            timerCancelledAlert.setPositiveButton(R.string.alertButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            timerCancelledAlert.show();
        }//onCancelled

        //This happens after the operation is finished running.
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Create an alert Dialog that displays
            AlertDialog.Builder timerFinishedAlert = new AlertDialog.Builder(activityIsContext);
            timerFinishedAlert.setTitle(R.string.alertTitleText);
            timerFinishedAlert.setPositiveButton(R.string.alertButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            timerFinishedAlert.show();
            Log.v(infoTag,s);
        }//onPostExecute
    }//anAsyncThread


}//MainActivity
