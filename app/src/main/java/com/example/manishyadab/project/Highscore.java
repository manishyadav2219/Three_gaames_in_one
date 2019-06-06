package com.example.manishyadab.project;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Highscore extends AppCompatActivity {
    TextView score;
    TextView cscore;
    int mscore=0 ;
    int gscore=0 ;
    dbhandler db11;
    dbhandler db21;
    Cursor res;
    Cursor res2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_scores);
        score = findViewById(R.id.mscore);
        cscore = findViewById(R.id.cscore);
        db11 = new dbhandler(this);
        res = db11.getAllData();

        /*try {
            mscore = MathsQuiz.getActivityInstance().getData();
            db11.updateData("1",Integer.toString(mscore));
        }catch (Exception e){
            e.printStackTrace();
        }
        if(res.moveToFirst()) {
            mscore =Integer.parseInt(res.getString(1));

        }else{
            mscore = 0;
        }*/
        try {
            res.moveToNext();
            mscore = Integer.parseInt(res.getString(1));

            res.moveToNext();
            gscore = Integer.parseInt(res.getString(1));
        }catch (Exception e){
            e.printStackTrace();
        }
        score.setText(Integer.toString(mscore));
        cscore.setText(Integer.toString(gscore));
    }
    public void fin(View v){
        finish();
    }
}
