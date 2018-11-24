package com.example.test;

import java.util.Stack;

public class Calculate {

    private static double baseCalculate(double num1,char symbol,double num2){
        switch (symbol){
            case '+':
                return num1+num2;
            case '-':
                return num1-num2;
            case '*':
                return num1*num2;
            case '/':
                if(num2!=0){
                    return num1/num2;}
                else {
                    return 0;}
            default:
        return 0;
        }
    }
    public static int symbolPriority(char symbol,int flag){
        int priority = 0;
        if(symbol=='+'||symbol=='-')
            priority = 1+flag*3;
        if (symbol=='*'||symbol=='/')
            priority = 2+flag*3;
        if (symbol=='(')
            priority = 3+(flag-1)*3;
        if(symbol==')')
            priority = 0;
        return priority;
    }
    public static double stackCalculate(String statement){
        Stack<Character> symbolStack = new Stack<>();
        Stack<Double> numStack = new Stack<>();
        String temp = "";
        symbolStack.push('!');
        char chSymbol=0;
        double lastJudge = 0;
        int flag = 0;

        //为x的y次方写一个函数
        int tempDivision = 0;
        for (int j=0;j<statement.length();j++){
            char ch = statement.charAt(j);
            if(ch=='#'){
                tempDivision = j;
                String a = statement.substring(0,j);
                String b = statement.substring(j+1,statement.length());
                Double a1 = Double.parseDouble(a);
                Double b1 = Double.parseDouble(b);
                double result = 1;
                for (int i=0;i<b1;i++){
                    result = result * a1;
                }
                return result;
            }
        }

        //判断第一个字符是否为正负号，如果是正负号的话在前面加一个0防止栈出现问题
        if(statement.charAt(0)=='+'||statement.charAt(0)=='-'){
            statement = '0' + statement;
        }

        //char[] statmentChar = new char[statement.length()]; char的方法使用失败，对于多位数实现有困难
        //char数组中每位只能存一个单位数，不能存多位数

        //把字符串分解拆成两个栈并进行运算
        //完成：（）的运算
        for(int i=0;i<statement.length();i++){
            char ch = statement.charAt(i);
            if(ch=='+'||ch=='-'||ch=='*'||ch=='/'){
                chSymbol = ch;      //当一个符号被读进来的时候为了和下一个符号对比而记录自己的符号
                symbolStack.push(ch);
            }
            if(ch == '('){
                chSymbol = ch;
                symbolStack.push(ch);
                flag = flag + 1;
            }
            if (ch == ')'){
                //symbolStack.pop();
                while(symbolStack.peek()!='('){
                    double operand2 = numStack.pop();
                    double operand1 = numStack.pop();
                    char operate = symbolStack.pop();
                    double result = baseCalculate(operand1,operate,operand2);
                    numStack.push(result);
                }
                flag = flag - 1;
                symbolStack.pop();
            }
            if(ch=='0'||ch=='1'||ch=='2'||ch=='3'||ch=='4'||ch=='5'||ch=='6'||ch=='7'||ch=='8'||ch=='9'||ch=='.'){
                temp = temp + ch;
                int k = i+1;
                if(k<=statement.length()-1) {
                    char tempCh = statement.charAt(k);
                    if(tempCh=='+'||tempCh=='-'||tempCh=='*'||tempCh=='/'||tempCh==')'){
                        numStack.push(Double.parseDouble(temp));
                        temp = "";

                        if(symbolPriority(tempCh,flag)<=symbolPriority(chSymbol,flag)){
                            double operand1 = numStack.pop();
                            double operand2 = numStack.pop();
                            char operate = symbolStack.pop();
                            double tempResult = baseCalculate(operand2,operate,operand1);
                            numStack.push(tempResult);
                        }
                    }
                }else if(i==statement.length()-1) {

                    numStack.push(Double.parseDouble(temp));
                    temp = "";

                    double operand1 = numStack.pop();
                    double operand2 = numStack.pop();
                    char operate = symbolStack.pop();
                    double tempResult = baseCalculate(operand2,operate,operand1);
                    numStack.push(tempResult);
                }
            }
        }

        lastJudge = numStack.pop();
        while(!numStack.empty()) {
            double operand1 = numStack.pop();
            char operate = symbolStack.pop();
            double Result = baseCalculate(operand1,operate,lastJudge);
            numStack.push(Result);
            lastJudge = numStack.pop();
        }
        return lastJudge;

    }

}
