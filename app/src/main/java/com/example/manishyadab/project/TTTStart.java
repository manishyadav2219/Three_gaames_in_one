package com.example.manishyadab.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TTTStart extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tictactoe_start);

    }

    public void single_play(View view){
        Intent intent = new Intent(this, TTTMain.class);
        intent.putExtra("Mode", 0);
        startActivity(intent);
    }

    public void multi_play(View view){
        Intent intent = new Intent(this, TTTMain.class);
        intent.putExtra("Mode", 1);
        startActivity(intent);
    }
    public void fin(View v){
        finish();
    }
}

