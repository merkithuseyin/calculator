package com.merkithuseyin.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnDot, btnClear, btnDel, btnAdd, btnSub, btnMul, btnDiv, btnEqual;
    private TextView txtView;
    private Operator currentOperator = Operator.NONE;
    private double savedValue = 0;

    private enum Operator {
        NONE, ADD, SUB, MUL, DIV
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MapComponents();
        SetListeners();
    }

    private void MapComponents(){
        // Map components
        btn0 = findViewById(R.id.button_0);
        btn1 = findViewById(R.id.button_1);
        btn2 = findViewById(R.id.button_2);
        btn3 = findViewById(R.id.button_3);
        btn4 = findViewById(R.id.button_4);
        btn5 = findViewById(R.id.button_5);
        btn6 = findViewById(R.id.button_6);
        btn7 = findViewById(R.id.button_7);
        btn8 = findViewById(R.id.button_8);
        btn9 = findViewById(R.id.button_9);
        btnDot = findViewById(R.id.button_dot);
        btnClear = findViewById(R.id.button_clear);
        btnDel = findViewById(R.id.button_backspace);
        btnAdd = findViewById(R.id.button_plus);
        btnSub = findViewById(R.id.button_minus);
        btnMul = findViewById(R.id.button_multiply);
        btnDiv = findViewById(R.id.button_divide);
        btnEqual = findViewById(R.id.button_equals);
        txtView = findViewById(R.id.textView);
    }

    private void SetListeners(){
        btn0.setOnClickListener(v -> appendToInput("0"));
        btn1.setOnClickListener(v -> appendToInput("1"));
        btn2.setOnClickListener(v -> appendToInput("2"));
        btn3.setOnClickListener(v -> appendToInput("3"));
        btn4.setOnClickListener(v -> appendToInput("4"));
        btn5.setOnClickListener(v -> appendToInput("5"));
        btn6.setOnClickListener(v -> appendToInput("6"));
        btn7.setOnClickListener(v -> appendToInput("7"));
        btn8.setOnClickListener(v -> appendToInput("8"));
        btn9.setOnClickListener(v -> appendToInput("9"));
        btnDot.setOnClickListener(v -> appendToInput("."));

        btnClear.setOnClickListener(v -> clear());
        btnDel.setOnClickListener(v -> delete());

        btnAdd.setOnClickListener(v -> operate(Operator.ADD));
        btnSub.setOnClickListener(v -> operate(Operator.SUB));
        btnMul.setOnClickListener(v -> operate(Operator.MUL));
        btnDiv.setOnClickListener(v -> operate(Operator.DIV));

        btnEqual.setOnClickListener(v -> calculate());
    }

    private void appendToInput(String str){
        boolean isEmpty = txtView.getText().toString().equals("0");

        if(isEmpty){
            if (str.equals("0")) return;

            txtView.setText("");

            if (str.equals("."))
            {
                txtView.setText("0.");
                return;
            }
        }

        boolean hasDot = txtView.getText().toString().contains(".");
        if (str.equals(".") && hasDot) return;

        String oldText = txtView.getText().toString();
        String newText = oldText + str;
        txtView.setText(newText);
    }

    private void clear(){
        txtView.setText("0");
    }

    private void delete(){
        String oldText = txtView.getText().toString();
        String newText = oldText.substring(0, oldText.length() - 1);

        if(newText.isEmpty()){
            newText = "0";
        }

        txtView.setText(newText);
    }

    private void operate(Operator operator){
        if(currentOperator != Operator.NONE){
            calculate();
        }

        savedValue = Double.parseDouble(txtView.getText().toString());
        currentOperator = operator;
        txtView.setText("0");
    }

    private void calculate(){
        double newValue = Double.parseDouble(txtView.getText().toString());
        double result = 0;

        switch (currentOperator){
            case ADD:
                result = savedValue + newValue;
                break;
            case SUB:
                result = savedValue - newValue;
                break;
            case MUL:
                result = savedValue * newValue;
                break;
            case DIV:
                if (newValue == 0){
                    result = 0;
                    break;
                }
                result = savedValue / newValue;
                break;
        }

        txtView.setText(String.valueOf(result));
        currentOperator = Operator.NONE;
    }
}
