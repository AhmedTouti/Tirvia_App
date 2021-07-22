package com.me.trivia;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import controller.AppController;
import data.AnswerListAsyncResponse;
import data.QuestionBank;
import model.Question;
/**AL HAMDO LILAH**/


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btrue,bfalse;
    TextView count,questions,scoretxt;
    ImageButton next, prev;
int counts=0;
int size=0;
    List<Question> listQ;
    CardView card;
    int scorelast=0;
    int scoretoday=0;
    static final String ID_score="score";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count=findViewById(R.id.textView2);
        scoretxt=findViewById(R.id.scoretxt);

        questions=findViewById(R.id.qestions);

        card=findViewById(R.id.cardView2);
         btrue =findViewById(R.id.idtrue);
         bfalse=findViewById(R.id.idfalse);
         next=findViewById(R.id.next);
         prev=findViewById(R.id.prev);

        btrue.setOnClickListener(this);
        bfalse.setOnClickListener(this);
        next.setOnClickListener(this);
        prev.setOnClickListener(this);



       listQ= new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {

                size=questionArrayList.size();
                getScore();


                update();



                /** Toast.makeText(getApplicationContext(),questionArrayList.get(0).getAnswer().toString(),Toast.LENGTH_LONG).show();**/
            }
        });



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.idtrue:{
                if (listQ.get(counts).getAnswerTrue()){
                  Toast.makeText(getApplicationContext(),"True ",Toast.LENGTH_LONG).show();
                    fadeView();
                    scoretoday++;



                }else {
                    shakeAnimation();
                    Toast.makeText(getApplicationContext(),"Wronggg",Toast.LENGTH_LONG).show();
                    if (scoretoday<=0){
                        scoretoday=0;
                    }
                    scoretoday--;


                }


                break;
            }
            case R.id.idfalse:{
                if (!listQ.get(counts).getAnswerTrue()){
                    Toast.makeText(getApplicationContext(),"True ",Toast.LENGTH_LONG).show();
                    fadeView();
                    scoretoday++;


                }else {
                    shakeAnimation();
                    Toast.makeText(getApplicationContext(),"Wronggg",Toast.LENGTH_LONG).show();
                    if (scoretoday<=0){
                        scoretoday=0;
                    }
                    scoretoday--;

                }

                break;
            }
            case R.id.next:{
                counts =(counts +1)%listQ.size();
                update();


                break;
            }
            case R.id.prev:{
                counts--;
                if (counts<=0){
                    counts=0;
                }
                update();

                break;
            }

        }

    }

    private void update() {

        String Question=listQ.get(counts).getAnswer().toString();
              questions.setText(Question);
              count.setText(String.valueOf(counts)+"/"+String.valueOf(size));
              scoretxt.setText("**"+String.valueOf(scoretoday)+"**");

    }

    public void shakeAnimation() {
        update();
        Animation shake= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake_animation);
card.setAnimation(shake);
shake.setAnimationListener(new Animation.AnimationListener() {
    @Override
    public void onAnimationStart(Animation animation) {
        card.setCardBackgroundColor(Color.BLACK);
        questions.setTextColor(Color.RED);

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        card.setCardBackgroundColor(Color.WHITE);
        questions.setTextColor(Color.BLACK);



    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
});

    }

    public void fadeView() {
        update();

        AlphaAnimation al=new AlphaAnimation(1.0f,0.0f);
        al.setDuration(300);
        al.setRepeatCount(1);
        al.setRepeatMode(Animation.REVERSE);
        card.setAnimation(al);
        al.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                questions.setTextColor(Color.BLUE);

                card.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                questions.setTextColor(Color.BLACK);

                card.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }

    public void setScore() {
        SharedPreferences setData=getSharedPreferences(ID_score,MODE_PRIVATE);
        SharedPreferences.Editor editor=setData.edit();
        if (scoretoday>scorelast){
        editor.putInt("scores",scoretoday);
            editor.putInt("count",counts);
            editor.apply();
        }


    }


    public void getScore() {
        SharedPreferences getdata=getSharedPreferences(ID_score,MODE_PRIVATE);
        scorelast= getdata.getInt("scores",0);
        counts= getdata.getInt("count",0);


        scoretoday=scorelast;

    }

    @Override
    protected void onPause() {
        super.onPause();
        setScore();
        Toast.makeText(getApplicationContext(),"Enregistrer",Toast.LENGTH_LONG).show();
    }
}
