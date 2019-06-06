package com.example.manishyadab.project;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.sql.StatementEvent;

public class MathsQuiz extends AppCompatActivity {

    static MathsQuiz INSTANCE;

    private static DecimalFormat df2 = new DecimalFormat(".##");
    dbhandler db;
    Cursor res;
    int scoreh ;
    Button gobutton;
    TextView question ;
    TextView timer;
    TextView scoreview;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button Play;
    Boolean layoutactive;
    TextView resultview;
    RelativeLayout layout;
    RelativeLayout befquiz;
    int index;
    ArrayList<String> answers = new ArrayList<String>();
    ArrayList<String> symbols;
    int answer;
    double Answer;
    String correctanswer;
    int score =0;
    int total =0;
    String id = "1";


    public void go(View view){
        getSupportActionBar().hide();
        befquiz.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
        Playagain(findViewById(R.id.resulttextview));
    }
    public void generatequestion(){
        Random rand = new Random();
        int a = rand.nextInt(50);
        int b = rand.nextInt(50)+1;
        int c = rand.nextInt(50)+1;
        int op = rand.nextInt(symbols.size());
        String operation = symbols.get(op);;
        if(op<3) {
            question.setText(Integer.toString(a) + " "+operation+" " + Integer.toString(b));
        }else{
            question.setText(Integer.toString(a) + " "+operation.charAt(0)+" " + Integer.toString(b)+ " "+operation.charAt(1)+" " + Integer.toString(c));

        }

        switch (operation){
            case ("+"):
                answer = a+b;
                correctanswer = Integer.toString(answer);
                break;
            case("*"):
                answer = a*b;
                correctanswer = Integer.toString(answer);
                break;
            case("/"):
                Answer = (double)a/b;
                correctanswer = df2.format(Answer);
                break;
            case("++"):
                answer = a+b+c;
                correctanswer = Integer.toString(answer);
                break;
            case("+*"):
                answer = a+b*c;
                correctanswer = Integer.toString(answer);
                break;
            case("+/"):
                double d =(double)b/c;
                Answer = ((double)a)+d;
                correctanswer = df2.format(Answer);
                break;
            case("*/"):
                double d1 =(double)b/c;
                Answer = ((double)a)*d1;
                correctanswer = df2.format(Answer);
                break;
            case ("//"):
                double d2 = (double)b/c;
                Answer = (double)a/d2;
                correctanswer = df2.format(Answer);
                break;
        }
        answers.clear();
        index = rand.nextInt(4);
        if(op<2||op==3||op==4) {
            for (int i = 0; i < 4; i++) {
                if (i == index) {
                    answers.add(correctanswer);

                } else {

                    int min;
                    int max;
                    if(Integer.parseInt(correctanswer)<30){
                        min = 0;
                        max = min+30;
                    }else{
                        min = Integer.parseInt(correctanswer)-30;
                        max = Integer.parseInt(correctanswer)+30;
                    }

                    int next = rand.nextInt(max-min+1)+min;
                    while (answers.contains(next) || next == Integer.parseInt(correctanswer)) {
                        next = rand.nextInt(100);
                    }
                    answers.add(Integer.toString(next));
                }

            }
        }else if (op==6||op==2){
            for (int i = 0; i < 4; i++) {
                if (i == index) {
                    answers.add(correctanswer);

                } else {
                    double s = 2.0;
                    double min ;
                    double max ;
                    if(Double.parseDouble(correctanswer)<s){
                        min = 0;
                        max = min+s;
                    }else{
                        min = Double.parseDouble(correctanswer)-s;
                        max = Double.parseDouble(correctanswer)+s;
                    }
                    Double next = (Math.random()*((max-min)+1))+min;
                    while (answers.contains(df2.format(next)) || next == Double.parseDouble(correctanswer)) {
                        next = (Math.random()*((max-min)+1))+min;
                    }
                    answers.add(df2.format(next));
                }

            }
        }
        else{
            for (int i = 0; i < 4; i++) {
                if (i == index) {
                    answers.add(correctanswer);

                } else {
                    double s = 20.0;
                    double min ;
                    double max ;
                    if(Double.parseDouble(correctanswer)<s){
                        min = 0;
                        max = min+s;
                    }else{
                        min = Double.parseDouble(correctanswer)-s;
                        max = Double.parseDouble(correctanswer)+s;
                    }
                    Double next = (Math.random()*((max-min)+1))+min;

                    while (answers.contains(df2.format(next)) || next == Double.parseDouble(correctanswer)) {
                        next = (Math.random()*((max-min)+1))+min;
                    }
                    answers.add(df2.format(next));
                }

            }
        }
        button0.setText(answers.get(0));
        button1.setText(answers.get(1));
        button2.setText(answers.get(2));
        button3.setText(answers.get(3));

    }

    public void Playagain(View view){

        score = 0;
        total = 0;
        resultview.setText("");
        timer.setText("30s");
        generatequestion();
        Play.setVisibility(View.INVISIBLE);
        scoreview.setText("0/0");
        layoutactive = true;

        CountDownTimer countDownTimer = new CountDownTimer(30100,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
                timer.setText("0s");
                resultview.setTextColor(getResources().getColor(R.color.colorAccent));
                resultview.setText("Done");
                Play.setVisibility(View.VISIBLE);
                layoutactive = false;
                if(score>scoreh){
                    scoreh = score;
                    db.updateData(id,Integer.toString(scoreh));
                    System.out.println("highscore :"+scoreh);
                }

            }
        }.start();


    }

    public void chooseanswer(View view) {

        if (layoutactive) {
            if (Integer.toString(index).equals(view.getTag().toString())) {
                resultview.setTextColor(getResources().getColor(android.R.color.white));
                resultview.setText("Correct");
                score++;
            } else {
                resultview.setTextColor(getResources().getColor(R.color.colorAccent));
                resultview.setText("Wrong");
            }
            total++;
            scoreview.setText(Integer.toString(score) + "/" + Integer.toString(total));

            generatequestion();
        }
    }
    public void fin(View v){

        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = this;

        setContentView(R.layout.maths_quiz);
        gobutton = findViewById(R.id.gobutton);
        question = findViewById(R.id.questiontextview);
        timer = findViewById(R.id.timertextview);
        scoreview = findViewById(R.id.scoretextview);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        Play = findViewById(R.id.button4);
        resultview = findViewById(R.id.resulttextview);
        layout = findViewById(R.id.layout1);
        befquiz=findViewById(R.id.quiz_before);
        symbols = new ArrayList<String>();

        symbols.add("+");
        symbols.add("*");
        symbols.add("/");
        symbols.add("++");
        symbols.add("+*");
        symbols.add("+/");
        symbols.add("//");
        symbols.add("*/");

        db = new dbhandler(this);
        try {
            db.insertData(id, Integer.toString(scoreh));
        }catch (Exception e){
            e.printStackTrace();
        }
        res = db.getAllData();


        res.moveToNext();
        scoreh = Integer.parseInt(res.getString(1));
    }

    public static MathsQuiz getActivityInstance(){
        return INSTANCE;
    }
    public int getData(){
        return this.scoreh;
    }
}
