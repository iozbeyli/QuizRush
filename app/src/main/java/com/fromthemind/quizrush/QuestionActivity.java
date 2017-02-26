package com.fromthemind.quizrush;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by MEHMET on 25.02.2017.
 */



public class QuestionActivity extends Activity {
    private static GameController gc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        gc=GameController.getInstance();
        setQuestionText();
        setOptionTexts();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    protected void setQuestionText(){
        TextView questionText = (TextView) findViewById(R.id.questionText);
        questionText.setText(gc.getCurrentQuestion().getDefinition());
    }

    protected void setOptionTexts(){
        Button option0 = (Button) findViewById(R.id.optionButton0);
        option0.setText(gc.getCurrentQuestion().getOptions()[0]);
        Button option1 = (Button) findViewById(R.id.optionButton1);
        option1.setText(gc.getCurrentQuestion().getOptions()[1]);
        Button option2 = (Button) findViewById(R.id.optionButton2);
        option2.setText(gc.getCurrentQuestion().getOptions()[2]);
        Button option3 = (Button) findViewById(R.id.optionButton3);
        option3.setText(gc.getCurrentQuestion().getOptions()[3]);
    }



}
