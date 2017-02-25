package com.fromthemind.quizrush;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Melih on 24.02.2017.
 */

public class QuestionSelectActivity extends Activity {
    private static GameController gc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionselect);
        setTopicTexts();
    }

    private void setTopicTexts() {
        gc = GameController.getInstance();
        TextView t0 = (TextView) findViewById(R.id.topic0);
        t0.setText(gc.getCategory(0).getTopic());
        TextView t1 = (TextView) findViewById(R.id.topic1);
        t1.setText(gc.getCategory(1).getTopic());
        TextView t2 = (TextView) findViewById(R.id.topic2);
        t2.setText(gc.getCategory(2).getTopic());
    }

    @Override
    protected void onStart(){
        super.onStart();

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

        Intent intent = new Intent(this, QuestionActivity.class);
        startActivity(intent);
    }



}
