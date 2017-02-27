package com.fromthemind.quizrush;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Melih on 24.02.2017.
 */

public class QuestionSelectActivity extends Activity {

    private int fColor;
    private int tColor;
    private int pColor;
    private int oColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionselect);
        initColors();
        setTopicTexts();
    }

    private void setTopicTexts() {
        TextView t0 = (TextView) findViewById(R.id.topic0);
        t0.setText(GameController.getInstance().getCategory(0).getTopic());
        TextView t1 = (TextView) findViewById(R.id.topic1);
        t1.setText(GameController.getInstance().getCategory(1).getTopic());
        TextView t2 = (TextView) findViewById(R.id.topic2);
        t2.setText(GameController.getInstance().getCategory(2).getTopic());
    }

    private void initColors(){
        fColor = ResourcesCompat.getColor(getResources(),R.color.colorFalse,null);
        tColor = ResourcesCompat.getColor(getResources(),R.color.colorTrue,null);
        pColor = ResourcesCompat.getColor(getResources(),R.color.colorPause,null);
        oColor = ResourcesCompat.getColor(getResources(),R.color.colorChronometer,null);
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    protected void onResume(){
        super.onResume();
        setSelectionView();
    }

    private void setSelectionView(){
        Button button = null;
        GameController gm = GameController.getInstance();
        for(int cat=0; cat<3; cat++) {
            for (int que=0; que<5; que++)
            {
                String buttonID = "topic"+cat+"button"+que;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                button = ((Button) findViewById(resID));

                QuestionStatus status = gm.getCategory(cat).getQuestion(que).getStatus();
                updateButton(button, status);
            }
        }

    }

    private void updateButton(Button button, QuestionStatus status){
        int color = -1;
        switch (status){
            case TRUE:
                color = tColor;
                break;
            case FALSE:
                color = fColor;
                break;
            case TIMEOUT:
                color = fColor;
                break;
            case ONPAUSE:
                color = pColor;
                break;
        }

        if(color != -1){
            button.setClickable(false);
            button.setBackgroundColor(color);
        }
    }

    public void onClickQuestion(View view){
        int category = -1;
        int question = -1;
        switch (view.getId()){
            case R.id.topic0button0:
                category = 0;
                question = 0;
                break;
            case R.id.topic0button1:
                category = 0;
                question = 1;
                break;
            case R.id.topic0button2:
                category = 0;
                question = 2;
                break;
            case R.id.topic0button3:
                category = 0;
                question = 3;
                break;
            case R.id.topic0button4:
                category = 0;
                question = 4;
                break;
            case R.id.topic1button0:
                category = 1;
                question = 0;
                break;
            case R.id.topic1button1:
                category = 1;
                question = 1;
                break;
            case R.id.topic1button2:
                category = 1;
                question = 2;
                break;
            case R.id.topic1button3:
                category = 1;
                question = 3;
                break;
            case R.id.topic1button4:
                category = 1;
                question = 4;
                break;
            case R.id.topic2button0:
                category = 2;
                question = 0;
                break;
            case R.id.topic2button1:
                category = 2;
                question = 1;
                break;
            case R.id.topic2button2:
                category = 2;
                question = 2;
                break;
            case R.id.topic2button3:
                category = 2;
                question = 3;
                break;
            case R.id.topic2button4:
                category = 2;
                question = 4;
                break;
        }

        if(category == -1 || question == -1)
            return;

        GameController.getInstance().setCurrentQuestion(category,question);
        Intent intent = new Intent(this, QuestionActivity.class);
        startActivity(intent);
    }



}
