package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;


public class MainActivity extends Activity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView textView = (TextView) findViewById(R.id.Display);
        final TextView resultTextView = (TextView) findViewById(R.id.Result);
        final Calculate calculate = new Calculate();

        Button btn0 = (Button)findViewById(R.id.Num0);
        Button btn1 = (Button)findViewById(R.id.Num1);
        Button btn2 = (Button)findViewById(R.id.Num2);
        Button btn3 = (Button)findViewById(R.id.Num3);
        Button btn4 = (Button)findViewById(R.id.Num4);
        Button btn5 = (Button)findViewById(R.id.Num5);
        Button btn6 = (Button)findViewById(R.id.Num6);
        Button btn7 = (Button)findViewById(R.id.Num7);
        Button btn8 = (Button)findViewById(R.id.Num8);
        Button btn9 = (Button)findViewById(R.id.Num9);
        Button btnPoint = (Button)findViewById(R.id.Point);
        Button btnAdd = (Button)findViewById(R.id.Add);
        Button btnRed = (Button)findViewById(R.id.Reduce);
        Button btnMul = (Button)findViewById(R.id.Multiplication);
        Button btnDiv = (Button)findViewById(R.id.Division);
        Button btnClr = (Button)findViewById(R.id.Clear);
        Button btnEqu = (Button)findViewById(R.id.Equal);
        Button btnBack = (Button)findViewById(R.id.Backspace);
        Button btnTransfer = (Button)findViewById(R.id.transfer);
        Button btnBracketLeft = (Button)findViewById(R.id.bracketLeft);
        Button btnBracketRight = (Button)findViewById(R.id.bracketRight);

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(textView.getText()+"0");
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(textView.getText()+"1");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(textView.getText()+"2");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(textView.getText()+"3");
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(textView.getText()+"4");
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(textView.getText()+"5");
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(textView.getText()+"6");
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(textView.getText()+"7");
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(textView.getText()+"8");
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(textView.getText()+"9");
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(textView.getText()+"+");
            }
        });
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(textView.getText()+"-");
            }
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView.length()!=0){
                    textView.setText(textView.getText()+"*");
                }

            }
        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView.length()!=0){
                    textView.setText(textView.getText()+"/");
                }
            }
        });
        btnPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(textView.getText()+".");
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = textView.getText().toString();
                String tempReplace = temp.substring(0,temp.length()-1);
                textView.setText(tempReplace);

            }
        });
        btnClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                resultTextView.setText("");
            }
        });
        btnEqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = textView.getText().toString();

                Double result = calculate.stackCalculate(temp);
                resultTextView.setText(result.toString());
            }
        });
        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("startAnotherActivity");
                startActivity(intent);
            }
        });
        btnBracketLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(textView.getText()+"(");
            }
        });
        btnBracketRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(textView.getText()+")");
            }
        });

    }


}
