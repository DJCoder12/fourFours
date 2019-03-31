package com.example.myapplication;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    protected CountDownTimer timer;
    private static final String TAG = "FourFours";
    protected TextView logoTW;
    protected long finalTime;
    Random rand;
    int n;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rand = new Random();
        n = rand.nextInt(50);
        TextView tv = (TextView) findViewById(R.id.Rand_num);
        String s = tv.getText().toString();
        s += " " + n;
        tv.setText(s);


        timer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                TextView time = (TextView) findViewById(R.id.Time);
                time.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                TextView time = (TextView) findViewById(R.id.Time);
                time.setText("Done!");
            }
        }.start();

    }

    protected void reset(View v)
    {
        TextView userInput = (TextView) findViewById(R.id.User_Input);
        TextView tv = (TextView) findViewById(R.id.Rand_num);
        TextView score = (TextView) findViewById(R.id.Score);
        Random rand = new Random();
        int n = rand.nextInt(50);
        tv.setText("Your number to generate is " + n);
        score.setText("Your score is: 0");
        userInput.setText("");
        timer.cancel();
        timer.start();

    }



    protected String addDecimal(View v) {
        TextView tv = (TextView) findViewById(R.id.User_Input);
        String s = tv.getText().toString();

        s += ".";
        tv.setText(s);
        return s;
    }



    protected String add4(View v) {
        TextView tv = (TextView) findViewById(R.id.User_Input);
        String s = tv.getText().toString();

        s += "4";
        tv.setText(s);
        return s;
    }

    protected String addSign(View v) {
        TextView tv = (TextView) findViewById(R.id.User_Input);
        String s = tv.getText().toString();
        if (s.length() == 7) {
            return s;
        }
        s += "+";
        tv.setText(s);
        return s;
    }

    protected String subtractSign(View v) {
        TextView tv = (TextView) findViewById(R.id.User_Input);
        String s = tv.getText().toString();
        if (s.length() == 0) {
            return s;
        }
        s += "-";
        tv.setText(s);
        return s;
    }

    protected String multiplySign(View v) {
        TextView tv = (TextView) findViewById(R.id.User_Input);
        String s = tv.getText().toString();
        s += "*";
        tv.setText(s);
        return s;
    }

    protected String divisionSign(View v) {
        TextView tv = (TextView) findViewById(R.id.User_Input);
        String s = tv.getText().toString();
        s += "/";
        tv.setText(s);
        return s;
    }

    protected String addExponentSign(View v) {
        TextView tv = (TextView) findViewById(R.id.User_Input);
        String s = tv.getText().toString();
        s += "^";
        tv.setText(s);
        return s;
    }

    protected String addOpenParen(View v) {
        TextView tv = (TextView) findViewById(R.id.User_Input);
        String s = tv.getText().toString();
        s += "(";
        tv.setText(s);
        return s;
    }

    protected String clear(View v) {
        TextView tv = (TextView) findViewById(R.id.User_Input);
        String b = "";
        tv.setText(b);
        return b;
    }

    protected String logs(View v) {
        TextView tv = (TextView) findViewById(R.id.User_Input);
        String b = tv.getText().toString(); ;
        int c = 0;
        b += "log";
        tv.setText(b);
        return b;
    }

    protected String sqrt(View v) {
        TextView tv = (TextView) findViewById(R.id.User_Input);
        String b = tv.getText().toString(); ;
        int c = 0;
        b += "sqrt";
        tv.setText(b);
        return b;
    }

    protected String addEndParen(View v) {
        TextView tv = (TextView) findViewById(R.id.User_Input);
        String s = tv.getText().toString();
        s += ")";
        tv.setText(s);
        return s;
    }

    protected void addComma(View v){
        TextView tv = (TextView) findViewById(R.id.User_Input);
        String s = tv.getText().toString();
        s += ",";
        tv.setText(s);
    }

    protected float add(float num, float num2) {
        float result = num + num2;
        return result;
    }

    protected float subtract(float num, float num2) {
        float result = num - num2;
        return result;
    }

    protected float multiply(float num, float num2) {
        float result = num * num2;
        return result;
    }

    protected float divide(float num, float num2) {
        float result = num / num2;
        return result;
    }


    protected Double exponentiation(Double num, Double num2) {
        Double result = Math.pow(num, num2);
        return result;
    }

    protected void checkNum(View v){
        TextView userInput = (TextView) findViewById(R.id.User_Input);
        ArrayList expression = new ArrayList();
        String s = userInput.getText().toString();
        for (int i = 0; i<s.length();i++)
        {
            expression.add(s.charAt(i));

        }

        Double result = calc(expression);
        if (result == n)
        {
            TextView score = (TextView) findViewById(R.id.Score);
            String playerScore = score.getText().toString();
            int oldScore = (int)(playerScore.charAt(score.length()-1));
            int newScore = oldScore + 1;
            score.setText("Your Score is " + newScore );

        }

    }

    protected Double calc(ArrayList terms){
        Double ans = 0.0;
        //if there's only one thing in the list it should be a number
        //return that
        if(terms.size() == 1){
            try {
                ans = new Double(terms.get(0).toString());
                return ans;
            }
            catch (NumberFormatException e){}
        }
        else {
            //deal with all the square roots and logs
            //then convert to postfix and calculate
            while (terms.contains("log") || terms.contains("sqrt")) {
                int idx = 0;
                int firstLog = terms.indexOf("log");
                int firstRoot = terms.indexOf("sqrt");
                if (firstLog == -1 || firstRoot == -1) {
                    idx = Math.max(firstLog, firstRoot);
                } else {
                    idx = Math.min(firstLog, firstRoot);
                }
                //idx is now the index of the first occurrence of sqrt or log
                //closeParen will be the index of the proper closing parenthesis for that term
                int closeParen = idx + 1;
                String term = terms.get(closeParen).toString();
                int parenCheck = 0;
                boolean lastFound = false;
                while (!lastFound) {
                    if (term.equals("(")) {
                        parenCheck += 1;
                        closeParen += 1;
                        term = terms.get(closeParen).toString();
                    } else if (parenCheck == 0) {
                        if (term.equals(")")) {
                            lastFound = true;
                        } else {
                            parenCheck -= 1;
                            closeParen += 1;
                            term = terms.get(closeParen).toString();
                        }
                    }
                }
                //split the array into three parts: before the log/root term, the log/root term, and after the log/root term
                ArrayList beforeTerm = new ArrayList();
                try {
                    beforeTerm = new ArrayList(terms.subList(0, idx));
                } catch (IllegalArgumentException e) {
                }
                ArrayList termPart = new ArrayList(terms.subList(idx + 2, closeParen));
                ArrayList afterTerm = new ArrayList();
                try {
                    afterTerm = new ArrayList(terms.subList(closeParen + 1, terms.size()));
                } catch (IllegalArgumentException e) {
                } catch (IndexOutOfBoundsException e) {
                }
                Double newTerm = 0.0;
                if (terms.get(idx).toString().equals("log")) {
                    newTerm = parseLogs(termPart);
                } else {
                    newTerm = parseRoot(termPart);
                }
                //now that the termPart was calculated, put the parts back together and make terms the new array
                beforeTerm.add(newTerm.toString());
                beforeTerm.addAll(afterTerm);
                terms = beforeTerm;
            }
        }
        //at this point terms should contain no logs or square roots
        //convert to postfix and evaluate
        ArrayList postTerms = shuntingYard.postfix(terms);
        ans = postfixEval.postEval(postTerms);

        return ans;
    }

    protected Double parseLogs(ArrayList terms){
        //take in the section of the whole term list that started with "log(" and ended with the matching end parenthesis
        //Should have a comma separating the base and the value
        //this function separates the base and the value, calculates those values, and return the log base b of n
        //might contain another log, so make sure the comma isn't in an unclosed parenthesis section
        int openParen = 0;
        Boolean commaFound = false;
        int i = 0;
        String term = terms.get(i).toString();
        while(!commaFound){
            if(term.equals("(")){
                openParen += 1;
                i += 1;
                term = terms.get(i).toString();
            }
            else if(term.equals(")")){
                openParen -= 1;
                i += 1;
                term = terms.get(i).toString();
            }
            else if(openParen == 0){
                if(term.equals(',')){
                    commaFound = true;
                }
            }
        }
        ArrayList base = new ArrayList(terms.subList(0, i));
        ArrayList num = new ArrayList(terms.subList(i+1, terms.size()));
        Double b = calc(base);
        Double n = calc(num);
        Double ans = Math.log(b)/Math.log(n);
        return ans;
    }

    protected Double parseRoot(ArrayList terms){
        Double ans = 0.0;
        ans = Math.sqrt(calc(terms));
        return ans;
    }

    public void HowTo(View v) {
        Intent intent = new Intent(this, HowTo.class);
        startActivity(intent);
    }
}
