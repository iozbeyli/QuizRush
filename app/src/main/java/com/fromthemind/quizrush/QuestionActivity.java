package com.fromthemind.quizrush;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.res.ResourcesCompat;
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
    private int fColor;
    private int tColor;
    private int pColor;
    private int oColor;
    private int currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initColors();
        currentQuestion = GameController.getInstance().getCurrentQuestion();
        currentTime=currentQuestion.getTime();
        setChronometer();
        setQuestionText();
        setOptionButtons();
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

    @Override
    protected void onPause(){
        super.onPause();
        cm = (Chronometer) findViewById(R.id.chronometer);
        String sec = ((String) cm.getText());
        currentTime = Integer.parseInt(sec);
    }

    @Override
    protected void onResume(){
        super.onResume();
        setChronometer();

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("currentTime",currentTime);
        Log.d("Save Instance",""+currentTime);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        currentTime = savedInstanceState.getInt("currentTime");
        Log.d("Restore Instance","called");
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

        if(currentQuestion.isAnswer(answer)){
            currentQuestion.setStatus(TRUE);
            User.getInstance().addScore(currentQuestion.getValue());
        } else {
            currentQuestion.setStatus(FALSE);
        }

        update(optionsButtons[answer], optionsButtons[currentQuestion.getCorrectAnswer()]);
        Intent intent = new Intent(this,QuestionSelectActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(currentQuestion.getStatus() == QuestionStatus.ONSTART){
            currentQuestion.setStatus(QuestionStatus.ONPAUSE);
        }
    }

    public void update(Button selected, Button correct) {
        if(correct != null)
            correct.setBackgroundColor(tColor);

        switch (currentQuestion.getStatus()){
            case TRUE:
                selected.setBackgroundColor(tColor);
                cm.stop();
                setOptionsClickable(false);
                break;
            case FALSE:
                selected.setBackgroundColor(fColor);
                cm.stop();
                setOptionsClickable(false);
                break;
            case TIMEOUT:
                cm.stop();
                cm.setTextColor(fColor);
                setOptionsClickable(false);
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
        cm.setBase(SystemClock.elapsedRealtime() + (currentTime*1000));
        cm.setOnChronometerTickListener(new RushListener());
        cm.start();
    }

    private void setOptionsClickable(boolean bool){
        for (int i=0; i<optionsButtons.length; i++)
            optionsButtons[i].setClickable(bool);
    }

    private class RushListener implements Chronometer.OnChronometerTickListener{

        @Override
        public void onChronometerTick(Chronometer chronometer) {
                String chronometerText = (String) chronometer.getText();
                String sec = chronometerText.substring(chronometerText.length()-2);
                chronometer.setText(sec);
                if(Integer.parseInt(sec)==(currentQuestion.getTime()/3))
                    chronometer.setTextColor(oColor);

                if(Integer.parseInt(sec)==0){
                    currentQuestion.setStatus(TIMEOUT);
                    update(null, optionsButtons[currentQuestion.getCorrectAnswer()]);

            }
        }
    }

}
