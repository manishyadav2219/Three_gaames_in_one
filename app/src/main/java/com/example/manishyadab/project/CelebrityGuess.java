package com.example.manishyadab.project;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CelebrityGuess extends AppCompatActivity {

    ArrayList<String> celeburl = new ArrayList<String>();
    ArrayList<String> celebnames = new ArrayList<String>();
    ImageView image;
    int urlchosen = 0;
    String[] answers = new String[4];
    int correctanswer;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    int score = 0;
    int scoreg;
    int total = 0;
    TextView scoreview;
    dbhandler guessdb;
    Cursor res;
    Button Play;
    TextView timer;
    Boolean islayoutactive=true;
    TextView resultview;
    String id="2";
    static CelebrityGuess Instance1;
    TextView message;
    TextView finalscore;

    public class ImageDownlaoder extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try{
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream inputStream  = connection.getInputStream();
                Bitmap mymap = BitmapFactory.decodeStream(inputStream);
                return mymap;

            }catch (Exception e){
                e.printStackTrace();
                return null;

            }
        }
    }

    public class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;
            String result = "";

            try{
                url =  new URL(strings[0]);
                urlConnection  = (HttpURLConnection)url.openConnection();
                InputStream in  = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data;
                data = reader.read();
                while(data!=-1){
                    char c = (char)data;
                    result+=c;
                    data = reader.read();
                }
                return result;

            }catch(Exception e){
                e.printStackTrace();
                return "FAILED";
            }
        }
    }
    public  void chosenans(View view){
        if(islayoutactive) {
            if (view.getTag().toString().equals(Integer.toString(correctanswer))) {
                score++;
                Toast.makeText(getApplicationContext(), "Correct answer", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong! The answer is " + celebnames.get(urlchosen), Toast.LENGTH_SHORT).show();
            }
            total++;
            scoreview.setText(Integer.toString(score) + "/" + Integer.toString(total));
            nextQuestion();
        }
    }
    public void nextQuestion(){
        Random rand = new Random();
        urlchosen = rand.nextInt(celeburl.size());
        ImageDownlaoder myimage = new ImageDownlaoder();
        Bitmap map = null;
        try {
            map = myimage.execute(celeburl.get(urlchosen)).get();
            image.setImageBitmap(map);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        correctanswer = rand.nextInt(4);
        int wrongans;
        for (int i=0;i<4;i++){
            if(i==correctanswer){
                answers[i] = celebnames.get(urlchosen);
            }else{
                wrongans = rand.nextInt(celeburl.size());
                while(wrongans == urlchosen){
                    wrongans = rand.nextInt(celeburl.size());
                }
                answers[i] = celebnames.get(wrongans);
            }
        }
        button0.setText(answers[0]);
        button1.setText(answers[1]);
        button2.setText(answers[2]);
        button3.setText(answers[3]);
    }

    public void Playonceagain(View view){

        score = 0;
        total = 0;
        resultview.setText("");
        timer.setText("90s");
        nextQuestion();
        Play.setVisibility(View.INVISIBLE);
        image.setVisibility(View.VISIBLE);
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        message.setVisibility(View.INVISIBLE);
        finalscore.setVisibility(View.INVISIBLE);
        scoreview.setText("0/0");
        islayoutactive = true;

        CountDownTimer countDownTimer = new CountDownTimer(40000,1000) {
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
                image.setVisibility(View.INVISIBLE);
                button0.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.INVISIBLE);
                message.setVisibility(View.VISIBLE);
                finalscore.setVisibility(View.VISIBLE);
                finalscore.setText(Integer.toString(score));
                islayoutactive = false;
                if(score>scoreg){
                    scoreg = score;
                    guessdb.updateData(id,Integer.toString(scoreg));

                }
                System.out.println(scoreg);
            }
        }.start();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Instance1 = this;
        setContentView(R.layout.celebrity_guess);
        image = findViewById(R.id.imageView);
        getSupportActionBar().hide();
        DownloadTask task = new DownloadTask();
        String result =null;
        button0  = findViewById(R.id.button6);
        button1  = findViewById(R.id.button7);
        button2  = findViewById(R.id.button8);
        button3  = findViewById(R.id.button9);
        Play = findViewById(R.id.button10);
        scoreview  = findViewById(R.id.scoretextview);
        timer = findViewById(R.id.timertextview);
        resultview =findViewById(R.id.resulttextview);
        message = findViewById(R.id.message);
        finalscore  = findViewById(R.id.finalscore);

        try{
            result = task.execute("http://www.posh24.se/kandisar").get();



            String[] SplitResult = result.split("<div class=\"listedArticles\">");

            Pattern p  = Pattern.compile("img src=\"(.*?)\"");
            Matcher m = p.matcher(SplitResult[0]);

            while(m.find()){
                celeburl.add(m.group(1));
            }

            p  = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(SplitResult[0]);

            while(m.find()){
                celebnames.add(m.group(1));
            }
            Playonceagain(findViewById(R.id.resulttextview));
        }catch (Exception e){
            e.printStackTrace();
        }

        guessdb = new dbhandler(this);
        guessdb.insertData(id,Integer.toString(scoreg));
        res = guessdb.getAllData();
        res.moveToNext();
        res.moveToNext();
        scoreg = Integer.parseInt(res.getString(1));

    }
    public static CelebrityGuess getActiveInstance(){
        return Instance1;
    }
    public int getData(){
        return this.scoreg;
    }

}
