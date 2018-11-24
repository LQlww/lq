package com.example.test;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HorizontalActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);

        final TextView textView = (TextView) findViewById(R.id.DisplayHorizontal);
        final TextView resultTextView = (TextView) findViewById(R.id.ResultHorizontal);
        final Calculate calculate = new Calculate();

        Button btn0 = (Button)findViewById(R.id.ScienceNum0);
        Button btn1 = (Button)findViewById(R.id.ScienceNum1);
        Button btn2 = (Button)findViewById(R.id.ScienceNum2);
        Button btn3 = (Button)findViewById(R.id.ScienceNum3);
        Button btn4 = (Button)findViewById(R.id.ScienceNum4);
        Button btn5 = (Button)findViewById(R.id.ScienceNum5);
        Button btn6 = (Button)findViewById(R.id.ScienceNum6);
        Button btn7 = (Button)findViewById(R.id.ScienceNum7);
        Button btn8 = (Button)findViewById(R.id.ScienceNum8);
        Button btn9 = (Button)findViewById(R.id.ScienceNum9);
        Button btnPoint = (Button)findViewById(R.id.SciencePoint);
        Button btnAdd = (Button)findViewById(R.id.ScienceAdd);
        Button btnRed = (Button)findViewById(R.id.ScienceReduce);
        Button btnMul = (Button)findViewById(R.id.ScienceMultiplication);
        Button btnDiv = (Button)findViewById(R.id.ScienceDivision);
        Button btnClr = (Button)findViewById(R.id.ScienceClear);
        Button btnEqu = (Button)findViewById(R.id.ScienceEqual);
        Button btnBack = (Button)findViewById(R.id.ScienceBackSpace);
        Button btnTransfer = (Button)findViewById(R.id.transferCalculate);
        Button btnSin = (Button)findViewById(R.id.Sin);
        Button btnCos = (Button)findViewById(R.id.Cos);
        Button btnTan = (Button)findViewById(R.id.Tan);
        Button btnE = (Button)findViewById(R.id.E);
        Button btnLn = (Button)findViewById(R.id.Ln);
        Button btnLog = (Button)findViewById(R.id.Log);
        Button btnSquare = (Button)findViewById(R.id.Square);
        Button btnCube = (Button)findViewById(R.id.Cube);
        Button btnY = (Button)findViewById(R.id.NSquare);
        Button btnReciprocal = (Button)findViewById(R.id.Reciprocal);
        Button btnRoot = (Button)findViewById(R.id.Root);
        Button btnPi = (Button)findViewById(R.id.Pai);

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
                Intent intent = new Intent(HorizontalActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = textView.getText().toString();
                Double result = Double.parseDouble(temp);
                result = Math.sin(result);
                resultTextView.setText("sin"+temp+" = "+result.toString());
            }
        });
        btnCos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = textView.getText().toString();
                Double result = Double.parseDouble(temp);
                result = Math.cos(result);
                resultTextView.setText("cos"+temp+" = "+result.toString());
            }
        });
        btnTan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = textView.getText().toString();
                Double result = Double.parseDouble(temp);
                result = Math.tan(result);
                resultTextView.setText("tan"+temp+" = "+result.toString());
            }
        });
        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double result = Math.E;
                resultTextView.setText("e = "+result.toString());
            }
        });
        btnLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = textView.getText().toString();
                Double result = Double.parseDouble(temp);
                result = Math.log(result);
                resultTextView.setText("Ln"+temp+" = "+result.toString());
            }
        });
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = textView.getText().toString();
                Double result = Double.parseDouble(temp);
                result = Math.log10(result);
                resultTextView.setText("Log"+temp+" = "+result.toString());
            }
        });
        btnSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = textView.getText().toString();
                Double result = Double.parseDouble(temp);
                result = result*result;
                resultTextView.setText(result.toString());
            }
        });
        btnCube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = textView.getText().toString();
                Double result = Double.parseDouble(temp);
                result = result*result*result;
                resultTextView.setText(result.toString());
            }
        });
        btnReciprocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = textView.getText().toString();
                Double result = Double.parseDouble(temp);
                result = 1/result;
                resultTextView.setText(result.toString());
            }
        });
        btnRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = textView.getText().toString();
                Double result = Double.parseDouble(temp);
                result = Math.sqrt(result);
                resultTextView.setText(result.toString());
            }
        });
        btnPi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double result = Math.PI;
                resultTextView.setText(result.toString());
            }
        });
        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView.length()!=0){
                    textView.setText(textView.getText()+"^");
                }
            }
        });

    }



}
