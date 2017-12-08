package retu.simplecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView display;
    Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero;
    Button btnPlus, btnMinus, btnMulti, btnDivide, btnPoint, btnEqual, btnBack, btnClear;

    double result = 0.0;
    boolean firstOperation = true;
    String operation = "";
    String scOperationPoint = "";
    String scOperationName = "";
    boolean lockOperator = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.textView_display);
        btnOne = findViewById(R.id.button_one);
        btnTwo = findViewById(R.id.button_two);
        btnThree = findViewById(R.id.button_three);
        btnFour = findViewById(R.id.button_four);
        btnFive = findViewById(R.id.button_five);
        btnSix = findViewById(R.id.button_six);
        btnSeven = findViewById(R.id.button_seven);
        btnEight = findViewById(R.id.button_eight);
        btnNine = findViewById(R.id.button_nine);
        btnZero = findViewById(R.id.button_zero);


        btnPlus = findViewById(R.id.button_plus);
        btnMinus = findViewById(R.id.button_minus);
        btnMulti = findViewById(R.id.button_multi);
        btnDivide = findViewById(R.id.button_divide);
        btnPoint = findViewById(R.id.button_point);
        btnEqual = findViewById(R.id.button_equal);
        btnClear = findViewById(R.id.button_clear);
        btnBack = findViewById(R.id.button_back);


        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);
        btnZero.setOnClickListener(this);


        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMulti.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnPoint.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnBack.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view == btnOne) {
            addToDisplay("1");
        } else if (view == btnTwo) {
            addToDisplay("2");
        } else if (view == btnThree) {
            addToDisplay("3");
        } else if (view == btnFour) {
            addToDisplay("4");
        } else if (view == btnFive) {
            addToDisplay("5");
        } else if (view == btnSix) {
            addToDisplay("6");
        } else if (view == btnSeven) {
            addToDisplay("7");
        } else if (view == btnEight) {
            addToDisplay("8");
        } else if (view == btnNine) {
            addToDisplay("9");
        } else if (view == btnZero) {
            addToDisplay("0");
        } else if (view == btnBack) {
            backClickAction();
        } else if (view == btnClear) {
            refresh();
        } else if (view == btnPlus) {
            operatorHandle("+");
        } else if (view == btnMinus) {
            operatorHandle("-");
        } else if (view == btnMulti) {
            operatorHandle("*");
        } else if (view == btnDivide) {
            operatorHandle("÷");
        } else if (view == btnPoint) {
            validationForPoint();
        } else if (view == btnEqual) {
            equalClickAction();
        }
    }

    private void backClickAction() {
        if (display.getText().length() > 0 && operatorHandleValidity()){
            String current = display.getText().toString();
            display.setText(method(current));
        } else {
            Toast.makeText(MainActivity.this, "Please Clear All", Toast.LENGTH_SHORT).show();
        }
    }
    public String method(String str) {
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public void validationForPoint(){
        String current = display.getText().toString();
        Pattern p = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Matcher m = p.matcher(current);

        String last = "";
        while (m.find()){
            last = m.group(1);
//            Toast.makeText(MainActivity.this, last, Toast.LENGTH_SHORT).show();
        }
        if (last.contains(".") || current.endsWith(".")) {

        } else {
            addToDisplay(".");
        }
    }

    public void equalClickAction(){
        completeOperation();
        display.setText(display.getText() + "\n" + String.valueOf(result));
        operation = "=";
    }

    public void addToDisplay(String value) {
        lockOperator = false;
        if (operation == "=") {
            refresh();
            display.setText(String.valueOf(result));
        } else if (display.getText() != "0" || value == ".") {
            display.setText(display.getText() + value);
        } else {
            display.setText(value);
        }
    }

    public void operatorHandle(String op)
    {
        if (operation == "=")
        {
            display.setText("");
            display.setText(String.valueOf(result) + op);
            operation = op;
        }
        else if (operatorHandleValidity())
        {
            if (firstOperation)
            {
                String current = display.getText().toString();
                double operand = Double.valueOf(current);
                result = result + operand;
                display.setText(display.getText() + op);
                firstOperation = false;
                operation = op;
            }
            else
            {
                completeOperation();
                operation = op;
                display.setText(display.getText() + op);
            }
        }
    }

    public void completeOperation()
    {
        String current = display.getText().toString().trim();
        Pattern p = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Matcher m = p.matcher(current);

        String last = "";

        while (m.find()){
            last = m.group(1);
//          Toast.makeText(MainActivity.this, last, Toast.LENGTH_SHORT).show();
        }
        double secondOperand = Double.valueOf(last);

        if (operation == "+")
        {
            result = result + secondOperand;
        }
        else if (operation == "-")
        {
            result = result - secondOperand;
        }
        else if (operation == "*")
        {
            result = result * secondOperand;
        }
        else
        {
            result = result / secondOperand;
        }
    }

    public Boolean operatorHandleValidity()
    {
        if (display.getText().toString().equals("0") || display.getText().toString().endsWith("+") || display.getText().toString().endsWith("-")
                || display.getText().toString().endsWith("*") || display.getText().toString().endsWith("÷") || lockOperator ) {
            return false;
        }
        return true;
    }

    private void refresh() {
        display.setText("0");
        result = 0.0;
        firstOperation = true;
        operation = "";
        scOperationName = "";
        scOperationPoint = "";
        lockOperator = false;
    }
}




