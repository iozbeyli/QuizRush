package com.fromthemind.quizrush;

import android.app.Activity;
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
        gc = GameController.getInstance();
        TextView t0 = (TextView) findViewById(R.id.topic1);
        t0.setText(gc.getCategory(0).getTopic());
        TextView t1 = (TextView) findViewById(R.id.topic2);
        t1.setText(gc.getCategory(1).getTopic());
        TextView t2 = (TextView) findViewById(R.id.topic3);
        t2.setText(gc.getCategory(2).getTopic());
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    protected void onClickQuestion(View view){
        switch (view.getId()){
            case R.id.topic1button1:

                break;
            case R.id.topic1button2:

                break;
            case R.id.topic1button3:

                break;
            case R.id.topic1button4:

                break;
            case R.id.topic1button5:

                break;
            case R.id.topic2button1:

                break;
            case R.id.topic2button2:

                break;
            case R.id.topic2button3:

                break;
            case R.id.topic2button4:

                break;
            case R.id.topic2button5:

                break;
            case R.id.topic3button1:

                break;
            case R.id.topic3button2:

                break;
            case R.id.topic3button3:

                break;
            case R.id.topic3button4:

                break;
            case R.id.topic3button5:

                break;
        }
    }



}
