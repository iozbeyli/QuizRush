package com.fromthemind.quizrush;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Melih on 24.02.2017.
 */

public class QuestionSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Button start = (Button) findViewById(R.id.start);
        start.setEnabled(false);
        start.setVisibility(View.INVISIBLE);
    }

    protected void onClickTopic1Button1(View view){
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

    protected void onClickTopic1Button2(View view){

    }

    protected void onClickStart(View view){
        Log.d("Start ","Clicked");
    }

}
