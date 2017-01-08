package com.example.user1.rdcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    /**
     * when user input first value
     */
    private final static int FIRST_VALUE = 1;

    /**
     * when user input second value
     */
    private final static int SECOND_VALUE = 2;

    private final static int RESULT_VALUE = 3;

    private final static String ACTION_PLUS = "actionPlus";

    private final static String ACTION_MINUS = "actionMinus";

    private final static String ACTION_MULTIPLE = "actionMultiple";

    private final static String ACTION_DIVISION = "actionDivision";

    private final static String ACTION_PER_CENT = "actionPerCent";

    /**
     * first number before action
     */
    private String mFirstValue;

    /**
     * second number after action
     */
    private String mSecondValue;

    /**
     * current number, which input now
     */
    private String mCurrentValue;

    /**
     * show which value input now
     */
    private int mWhichValueNow;

    /**
     * textView for first value
     */
    private TextView mTopTextView;

    /**
     * textView for second value
     */
    private TextView mBottomTextView;

    /**
     * count of digits in one row (for max 15 digits)
     */
    private int mCountOfDigitsRow;

    private String mAction;

    private String mDisplayAction;

    private String mResultString;

    private boolean mIsDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirstValue = "";
        mSecondValue = "";
        mCurrentValue = "";
        mWhichValueNow = FIRST_VALUE;
        mTopTextView = (TextView) findViewById(R.id.firstValueTV);
        mBottomTextView = (TextView) findViewById(R.id.secondValueTV);
        mCountOfDigitsRow = 0;
        mIsDot = false;
    }

    public void notDigitClick(View view) {
        String buttonValue = ((Button)view).getText().toString();
        switch (buttonValue) {
            case "AC" :
                setAllToZero();
                break;
            case "--" :
                backspace();
                displayDigits();
                break;
            case "%" :
                if (mWhichValueNow == SECOND_VALUE && mCountOfDigitsRow > 0) {
                    if (mAction == ACTION_MULTIPLE) {
                        mSecondValue = mCurrentValue;
                        mWhichValueNow = RESULT_VALUE;
                        mResultString = MyCalculator.makePerCent(mFirstValue, mSecondValue);
                        displayDigits();
                    }
                    else {
                        Toast.makeText(this, "Wrong action. Require *", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "Less digits", Toast.LENGTH_SHORT).show();
                }
                displayDigits();
                break;
            case "/" :
                mAction = ACTION_DIVISION;
                actionsForAction(buttonValue);
                displayDigits();
                break;
            case "*" :
                mAction = ACTION_MULTIPLE;
                actionsForAction(buttonValue);
                displayDigits();
                break;
            case "-" :
                mAction = ACTION_MINUS;
                actionsForAction(buttonValue);
                displayDigits();
                break;
            case "+" :
                mAction = ACTION_PLUS;
                actionsForAction(buttonValue);
                displayDigits();
                break;
            case "=" :
                if (mWhichValueNow == SECOND_VALUE && mCountOfDigitsRow > 0) {
                    mSecondValue = mCurrentValue;
                    mWhichValueNow = RESULT_VALUE;
                    switch (mAction) {
                        case ACTION_PLUS:
                            mResultString = MyCalculator.makeSum(mFirstValue, mSecondValue);
                            break;
                        case ACTION_MINUS:
                            mResultString = MyCalculator.makeMinus(mFirstValue, mSecondValue);
                            break;
                        case ACTION_MULTIPLE:
                            mResultString = MyCalculator.makeMultiple(mFirstValue, mSecondValue);
                            break;
                        case ACTION_DIVISION:
                            mResultString = MyCalculator.makeDivision(mFirstValue, mSecondValue);
                            if (mResultString.equals(MyCalculator.ERROR_DIV_BY_ZERO)) {
                                mResultString = "";
                                Toast.makeText(this, MyCalculator.ERROR_DIV_BY_ZERO,
                                        Toast.LENGTH_LONG).show();
                            }
                            break;
                    }
                    displayDigits();
                }
                else {
                    Toast.makeText(this, "Less digits", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    public void digitClick(View view) {
        if (mCountOfDigitsRow < 15) {
            String digit = ((Button)view).getText().toString();
            if (digit.equals(".")) {
                if (!mIsDot) {
                    mIsDot = true;
                }
                else {
                    Toast.makeText(this, "More than 1 dot in number", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            mCurrentValue += digit;
            mCountOfDigitsRow++;
            displayDigits();
        }
        else {
            Toast.makeText(this, "Max digit = 15", Toast.LENGTH_SHORT).show();
        }

    }

    public void displayDigits() {
        switch (mWhichValueNow) {
            case FIRST_VALUE:
                mTopTextView.setText("");
                mBottomTextView.setText(mCurrentValue);
                break;
            case SECOND_VALUE:
                mTopTextView.setText(mFirstValue + mDisplayAction);
                mBottomTextView.setText(mCurrentValue);
                break;
            case RESULT_VALUE:
                mTopTextView.setText("");
                mBottomTextView.setText(mResultString);
                setAfterResult();
                break;
        }
    }

    private void setAfterResult() {
        mCurrentValue = mResultString;
        mCountOfDigitsRow = mCurrentValue.length();
        mAction = "";
        mDisplayAction = "";
        mWhichValueNow = FIRST_VALUE;
    }

    /**
     * call when click on AC
     */
    private void setAllToZero() {
        mFirstValue = "";
        mSecondValue = "";
        mCurrentValue = "";
        mDisplayAction = "";
        mAction = "";
        mCountOfDigitsRow = 0;
        mWhichValueNow = FIRST_VALUE;
        mTopTextView.setText("");
        mBottomTextView.setText("");
        mIsDot = false;
    }

    /**
     * call when click on backspace
     */
    private void backspace() {
        if (mWhichValueNow == FIRST_VALUE) {
            if (mCountOfDigitsRow > 0) {
                mCountOfDigitsRow--;
                checkDot();
                mCurrentValue = cutLastCharFromStr(mCurrentValue);
            }
            else {
                Toast.makeText(this, "No more digits", Toast.LENGTH_SHORT).show();
            }
        }
        else if (mWhichValueNow == SECOND_VALUE) {
            if (mCountOfDigitsRow > 0) {
                mCountOfDigitsRow--;
                checkDot();
                mCurrentValue = cutLastCharFromStr(mCurrentValue);
            }
            else {
                if (mAction == ACTION_PLUS || mAction == ACTION_MINUS
                        || mAction == ACTION_MULTIPLE || mAction == ACTION_DIVISION) {
                    mWhichValueNow = FIRST_VALUE;
                    mAction = "";
                    mDisplayAction = "";
                    mCurrentValue = mFirstValue;
                    mCountOfDigitsRow = mCurrentValue.length();
                    mFirstValue = "";
                }
                else {
                    mCurrentValue = cutLastCharFromStr(mCurrentValue);
                    mCountOfDigitsRow--;
                    checkDot();
                }
            }
        }
    }

    private String cutLastCharFromStr(String string) {
        return string.substring(0, string.length()-1);
    }

    private void actionsForAction(String buttonValue) {
        mDisplayAction = buttonValue;
        mFirstValue = mCurrentValue;
        mCurrentValue = "";
        mCountOfDigitsRow = 0;
        mWhichValueNow = SECOND_VALUE;
        mIsDot = false;
    }

    private void checkDot() {
        if (mCurrentValue.charAt(mCurrentValue.length()-1) == '.') {
            mIsDot = false;
        }
    }
}
