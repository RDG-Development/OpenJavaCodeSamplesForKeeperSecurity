//Roberto Gutierrez
//Java1 - 1709
//GutierrezRoberto_CE01.Java

package com.rdgdevlopment.gutierrezroberto_ce01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Objects;


//MESSAGE FOR ANYONE REVIEWING THIS PROJECT
/*
 THIS PROJECT WAS BUILT AS PART OF A CLASS EXERCISE AND WAS DONE WITH MY OWN ORIGINAL CODE.

 IF YOUR ARE REVIEWING THIS PROJECT IT IS BECAUSE YOU HAVE BEEN AUTHORIZED TO LOOK AT THIS AS AN EXAMPLE OF SOME OF MY JAVA PROGRAMMING KNOWLEDGE.

 THIS IS A CALCULATOR APPLICATION THAT CAN PERFORM SOME SIMPLE CALCULATIONS. THE CODE IS COMPLETELY ORIGINAL AND IS ONLY A PEAK AT WHAT I CAN DO.

 THIS PROJECT WAS TESTED ON A VIRTUAL DEVICE NEXUS 5X API 25 ON ANDROID STUDIO

 THANK YOU FOR YOUR TIME IN REVIEWING MY SKILL SET.

 */

public class GutierrezRoberto_CE01 extends AppCompatActivity {

    //Set Up--------------------------------------------------------------------------------------->>
    //Create a variable that will hold the textViews data and the text view itself
    private TextView numberInputTextView;
    private static  Integer initialNumber = null;
    private static Integer numAfterOperator = null;
    private static Integer calculatedNumber = null;
    //Create a mode tracker for the operator pushed function to check button to check
    private static String lastOperatorPushed = "";
    //Create a tag for logging to log cat
    private static final String TAG = "MyActivity";




    //Set Up---------------------------------------------------------------------------------------<<
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gutierrez_roberto__ce01);
        //Assign the buttons and text view
        numberInputTextView = (TextView) findViewById(R.id.myNumberTextView);
        Button[] myButtons = {(Button) findViewById(R.id.button0), (Button) findViewById(R.id.button1),
                (Button) findViewById(R.id.button2), (Button) findViewById(R.id.button3),
                (Button) findViewById(R.id.button4), (Button) findViewById(R.id.button5),
                (Button) findViewById(R.id.button6), (Button) findViewById(R.id.button7),
                (Button) findViewById(R.id.button8), (Button) findViewById(R.id.button9),
                (Button) findViewById(R.id.additionButton), (Button) findViewById(R.id.subtractionButton),
                (Button) findViewById(R.id.multiplication), (Button) findViewById(R.id.division),
                (Button) findViewById(R.id.equalsButton), (Button) findViewById(R.id.clearButton)};

        //Run a loop that assigns the onClickListener to all buttons
        for (Button button:myButtons) {
            button.setOnClickListener(numberPushed);
        }
    }

    //METHODS-------------------------------------------------------------------------------------->
    //Create an on click listener that is used for all the number buttons
    private final View.OnClickListener numberPushed = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int theViewId = view.getId();
            buttonPressChecker(theViewId);
        }
    };


    //Create a method that adds a users number inputs into one string and displays it.
    private void createNumberString (String userInput){
        //current value
        String currentValue = numberInputTextView.getText().toString();
        String newValue = currentValue.concat(userInput);
        numberInputTextView.setText(newValue);
    }//END createNumberString

    //Create a method that checks which button was pressed and acts accordingly.
    private void buttonPressChecker (int buttonID) {
        //If the text is NaN reset it to  blank text.
        if (Objects.equals(numberInputTextView.getText().toString(), getString(R.string.divide_multiplyBy0))){
            numberInputTextView.setText("");
        }
        //This switch checks what was pressed and performs the actions accordingly.
        switch (buttonID) {
            case R.id.button0:
                createNumberString(Integer.toString(0));
                break;
            case R.id.button1:
                createNumberString(Integer.toString(1));
                break;
            case R.id.button2:
                createNumberString(Integer.toString(2));
                break;
            case R.id.button3:
                createNumberString(Integer.toString(3));
                break;
            case R.id.button4:
                createNumberString(Integer.toString(4));
                break;
            case R.id.button5:
                createNumberString(Integer.toString(5));
                break;
            case R.id.button6:
                createNumberString(Integer.toString(6));
                break;
            case R.id.button7:
                createNumberString(Integer.toString(7));
                break;
            case R.id.button8:
                createNumberString(Integer.toString(8));
                break;
            case R.id.button9:
                createNumberString(Integer.toString(9));
                break;
            case R.id.additionButton:
                if (Objects.equals(numberInputTextView.getText().toString(), getString(R.string.divide_multiplyBy0))){
                    numberInputTextView.setText("");
                }
                if (initialNumber == null && !numberInputTextView.getText().toString().isEmpty()){
                    initialNumber = Integer.parseInt(numberInputTextView.getText().toString());
                    numberInputTextView.setText("");
                } else if(initialNumber != null && !numberInputTextView.getText().toString().isEmpty()){
                    operatorPushed(lastOperatorPushed,initialNumber);
                }else{
                    Toast.makeText(this,"INVALID ENTRY, Enter a Number",Toast.LENGTH_SHORT).show();

                }
                lastOperatorPushed = getString(R.string.ADDITION_MODE);
                break;
            case R.id.subtractionButton:
                if (initialNumber == null && !numberInputTextView.getText().toString().isEmpty()){
                    initialNumber = Integer.parseInt(numberInputTextView.getText().toString());
                    numberInputTextView.setText("");
                } else if(initialNumber != null && !numberInputTextView.getText().toString().isEmpty()){
                    operatorPushed(lastOperatorPushed,initialNumber);
                }else{
                    Toast.makeText(this,"INVALID ENTRY, Enter a Number",Toast.LENGTH_SHORT).show();

                }
                lastOperatorPushed = getString(R.string.SUBTRACTION_MODE);
                break;
            case R.id.multiplication:
                if (initialNumber == null && !numberInputTextView.getText().toString().isEmpty()){
                    initialNumber = Integer.parseInt(numberInputTextView.getText().toString());
                    numberInputTextView.setText("");
                } else if(initialNumber != null && !numberInputTextView.getText().toString().isEmpty()){
                    operatorPushed(lastOperatorPushed,initialNumber);
                }else{
                    Toast.makeText(this,"INVALID ENTRY, Enter a Number",Toast.LENGTH_SHORT).show();
                }
                lastOperatorPushed = getString(R.string.MULTIPLICATION_MODE);
                break;
            case R.id.division:
                if (initialNumber == null && !numberInputTextView.getText().toString().isEmpty() ){
                    initialNumber = Integer.parseInt(numberInputTextView.getText().toString());
                    numberInputTextView.setText("");
                } else if(initialNumber != null && !numberInputTextView.getText().toString().isEmpty()){
                    operatorPushed(lastOperatorPushed,initialNumber);
                }else{
                    Toast.makeText(this,"INVALID ENTRY, Enter a Number",Toast.LENGTH_SHORT).show();
                }
                lastOperatorPushed = getString(R.string.DIVISION_MODE);
                break;
            case R.id.equalsButton:
                if (Objects.equals(numberInputTextView.getText().toString(), getString(R.string.divide_multiplyBy0))){
                    numberInputTextView.setText("");
                }
                if (calculatedNumber != null && !numberInputTextView.getText().toString().isEmpty()) {
                    operatorPushed(lastOperatorPushed, initialNumber);
                    if (calculatedNumber == null) {
                        break;
                    }
                    numberInputTextView.setText(Integer.toString(calculatedNumber));
                    initialNumber = null;
                    calculatedNumber = null;
                    lastOperatorPushed = "";
                }else if (calculatedNumber == null && initialNumber != null && !numberInputTextView.getText().toString().isEmpty()){
                    operatorPushed(lastOperatorPushed, initialNumber);
                    if (calculatedNumber == null) {
                        break;
                    }
                    numberInputTextView.setText(Integer.toString(calculatedNumber));
                    calculatedNumber = null;
                    initialNumber = null;
                    lastOperatorPushed = "";
                }else{
                    Toast.makeText(this,"INVALID ENTRY,Begin A Calculation",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.clearButton:
                resetCalculator();
                break;
            default:
                Log.v(TAG, "Something went horribly wrong");
                break;
                }
    }//END numberPressChecker

    //This function performs all the calculations according to what mode was pushed last. It is called whenever an operator or the equals button is pushed.
    private void operatorPushed(String operator, Integer firstNum){
        if (Objects.equals(operator, getString(R.string.ADDITION_MODE))){
            numAfterOperator = Integer.parseInt(numberInputTextView.getText().toString());
            numberInputTextView.setText("");
            Log.v(TAG, Integer.toString(initialNumber) + " + " + Integer.toString(numAfterOperator));
            calculatedNumber = firstNum + numAfterOperator;
            if(calculatedNumber > 9999){
                calculatedNumber = 9999;
                Toast.makeText(this,"Number Too Large To Display",Toast.LENGTH_SHORT).show();
            }
            initialNumber = calculatedNumber;
            Log.v(TAG, "= " + Integer.toString(calculatedNumber));

        }else if (Objects.equals(operator, getString(R.string.SUBTRACTION_MODE))){
            numAfterOperator = Integer.parseInt(numberInputTextView.getText().toString());
            numberInputTextView.setText("");
            Log.v(TAG, Integer.toString(initialNumber) + " - " + Integer.toString(numAfterOperator));
            calculatedNumber = firstNum - numAfterOperator;
            if(calculatedNumber > 9999){
                calculatedNumber = 9999;
                Toast.makeText(this,"Number Too Large To Display",Toast.LENGTH_SHORT).show();
            }
            initialNumber = calculatedNumber;
            Log.v(TAG, "= " + Integer.toString(calculatedNumber));

        }else if (Objects.equals(operator, getString(R.string.MULTIPLICATION_MODE))){
            numAfterOperator = Integer.parseInt(numberInputTextView.getText().toString());
            numberInputTextView.setText("");
            Log.v(TAG, Integer.toString(initialNumber) + " * " + Integer.toString(numAfterOperator));
            calculatedNumber = firstNum * numAfterOperator;
            if(calculatedNumber > 9999){
                calculatedNumber = 9999;
                Toast.makeText(this,"Number Too Large To Display",Toast.LENGTH_SHORT).show();
            }
            initialNumber = calculatedNumber;
            Log.v(TAG, "= " + Integer.toString(calculatedNumber));

        }else if (Objects.equals(operator, getString(R.string.DIVISION_MODE))){
            numAfterOperator = Integer.parseInt(numberInputTextView.getText().toString());
            if(numAfterOperator != 0 && initialNumber != 0) {
                numberInputTextView.setText("");
                Log.v(TAG, Integer.toString(initialNumber) + " / " + Integer.toString(numAfterOperator));
                calculatedNumber = firstNum / numAfterOperator;
                if(calculatedNumber > 9999){
                    calculatedNumber = 9999;
                    Toast.makeText(this,"Number Too Large To Display",Toast.LENGTH_SHORT).show();
                }
                initialNumber = calculatedNumber;
                Log.v(TAG, "= " + Integer.toString(calculatedNumber));
            }else{
                resetCalculator();
                Toast.makeText(this,"Divide By Number Other Than Zero",Toast.LENGTH_SHORT).show();
                numberInputTextView.setText(getString(R.string.divide_multiplyBy0));
            }
        }
    }//END operatorPushed

    private void resetCalculator(){
        initialNumber = null;
        numAfterOperator = null;
        calculatedNumber = null;
        lastOperatorPushed = "";
        numberInputTextView.setText("");
    }//END resetCalculator

}//END

