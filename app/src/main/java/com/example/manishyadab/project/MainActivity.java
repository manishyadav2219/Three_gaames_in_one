package com.example.manishyadab.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void highscores(View view){
        Intent intent = new Intent(MainActivity.this,Highscore.class);
        startActivity(intent);
    }
    public void tictactoe(View view){
        Intent intent = new Intent(MainActivity.this,TTTStart.class);
        startActivity(intent);
    }

    public void guesscelebrity(View view){
        Intent intent1 = new Intent(MainActivity.this, CelebrityGuess.class);
        startActivity(intent1);
    }
    public void mathsquiz(View view){
        Intent intent2 = new Intent(MainActivity.this, MathsQuiz.class);
        startActivity(intent2);
    }

}
