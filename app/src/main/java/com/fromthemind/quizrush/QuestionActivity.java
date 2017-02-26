package com.fromthemind.quizrush;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import static com.fromthemind.quizrush.QuestionStatus.FALSE;
import static com.fromthemind.quizrush.QuestionStatus.TIMEOUT;
import static com.fromthemind.quizrush.QuestionStatus.TRUE;


/**
 * Created by MEHMET on 25.02.2017.
 */



public class QuestionActivity extends Activity {
    private static Question currentQuestion;
    private static Button[] optionsButtons;
    private static Chronometer cm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        currentQuestion = GameController.getInstance().getCurrentQuestion();
        setChronometer();
        setQuestionText();
        setOptionButtons();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }


    public void onClickAnswer(View view){
        int answer = -1;

        switch (view.getId()){
            case R.id.optionButton0:
                answer = 0;
                break;
            case R.id.optionButton1:
                answer = 1;
                break;
            case R.id.optionButton2:
                answer = 2;
                break;
            case R.id.optionButton3:
                answer = 3;
                break;
        }

        if(answer == -1 )
            return;

        if(currentQuestion.isAnswer(answer))
            currentQuestion.setStatus(TRUE);
        else
            currentQuestion.setStatus(FALSE);

        update(optionsButtons[answer], optionsButtons[currentQuestion.getCorrectAnswer()]);

    }

    public void update(Button selected, Button correct) {
        if(correct != null)
            correct.setBackgroundColor(Color.parseColor("#27AE60"));

        switch (currentQuestion.getStatus()){
            case TRUE:
                selected.setBackgroundColor(Color.parseColor("#27AE60"));
                cm.stop();
                setOptionsClickable(false);
                break;
            case FALSE:
                selected.setBackgroundColor(Color.parseColor("#c0392b"));
                cm.stop();
                setOptionsClickable(false);
                break;
            case TIMEOUT:
                cm.stop();
                cm.setTextColor(Color.parseColor("#c0392b"));
                setOptionsClickable(false);
                break;

            case ONPAUSE:
                break;
            case ONSTART:
                break;
        }

    }

    private void setQuestionText(){
        TextView questionText = (TextView) findViewById(R.id.questionText);
        questionText.setText(currentQuestion.getDefinition());
    }

    private void setOptionButtons(){
        optionsButtons = new Button[4];

        optionsButtons[0] = (Button) findViewById(R.id.optionButton0);
        optionsButtons[0].setText(currentQuestion.getOptions()[0]);

        optionsButtons[1]= (Button) findViewById(R.id.optionButton1);
        optionsButtons[1].setText(currentQuestion.getOptions()[1]);

        optionsButtons[2]= (Button) findViewById(R.id.optionButton2);
        optionsButtons[2].setText(currentQuestion.getOptions()[2]);

        optionsButtons[3]= (Button) findViewById(R.id.optionButton3);
        optionsButtons[3].setText(currentQuestion.getOptions()[3]);
    }

    private void setChronometer() {
        cm = (Chronometer) findViewById(R.id.chronometer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.setCountDown(true);
        }
        final int time = currentQuestion.getTime();
        cm.setBase(SystemClock.elapsedRealtime() + (time*1000));
        cm.setOnChronometerTickListener(new RushListener());
        cm.start();
    }

    private void setOptionsClickable(boolean bool){
        findViewById(R.id.optionButton0).setClickable(bool);
        findViewById(R.id.optionButton1).setClickable(bool);
        findViewById(R.id.optionButton2).setClickable(bool);
        findViewById(R.id.optionButton3).setClickable(bool);
    }

    private class RushListener implements Chronometer.OnChronometerTickListener{

        @Override
        public void onChronometerTick(Chronometer chronometer) {
                String sec = ((String) chronometer.getText()).substring(3);
                chronometer.setText(sec);
                if(Integer.parseInt(sec)==(currentQuestion.getTime()/3))
                    chronometer.setTextColor(Color.parseColor("#e67e22"));

                if(Integer.parseInt(sec)==0){
                    currentQuestion.setStatus(TIMEOUT);
                    update(null, optionsButtons[currentQuestion.getCorrectAnswer()]);

            }
        }
    }

}
