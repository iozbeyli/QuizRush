package com.fromthemind.quizrush;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Question.Question;
import com.fromthemind.quizrush.Question.QuizQuestion;

import static com.fromthemind.quizrush.Question.QuestionStatus.FALSE;
import static com.fromthemind.quizrush.Question.QuestionStatus.TIMEOUT;
import static com.fromthemind.quizrush.Question.QuestionStatus.TRUE;


/**
 * Created by MEHMET on 25.02.2017.
 */



public class QuizQuestionFragment extends Fragment implements View.OnClickListener {

    static interface QuizInterface{
        void showQuizSelection();
    }
    private static Question currentQuestion;
    private static Button[] optionsButtons;
    private static Chronometer cm;
    private int fColor;
    private int tColor;
    private int pColor;
    private int oColor;
    private int currentTime;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View layout = inflater.inflate(R.layout.fragment_quizquestion, container, false);
        Button button = null;
        for (int que=0; que<4; que++)
        {
            String buttonID = "optionButton"+que;
            int resID = getResources().getIdentifier(buttonID, "id", getActivity().getPackageName());
            button = (Button) layout.findViewById(resID);
            button.setOnClickListener(this);
        }
        return layout;
    }
    private void initColors(){
        fColor = ResourcesCompat.getColor(getResources(),R.color.colorFalse,null);
        tColor = ResourcesCompat.getColor(getResources(),R.color.colorTrue,null);
        pColor = ResourcesCompat.getColor(getResources(),R.color.colorPause,null);
        oColor = ResourcesCompat.getColor(getResources(),R.color.colorChronometer,null);
    }


    @Override
    public void onStart(){
        super.onStart();
        initColors();
        currentQuestion = GameController.getCurrentQuestion();
        currentTime=currentQuestion.getTime();
        setChronometer();
        setQuestionText();
        setOptionButtons();
    }

    @Override
    public void onPause(){
        super.onPause();
        cm = (Chronometer) getView().findViewById(R.id.chronometer);
        String sec = ((String) cm.getText());
        currentTime = Integer.parseInt(sec);
    }

    @Override
    public void onResume(){
        super.onResume();
        setChronometer();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("currentTime",currentTime);
    }

    /*@Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        currentTime = savedInstanceState.getInt("currentTime");
    }*/


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
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                QuizInterface qi = (QuizInterface) getActivity();
                qi.showQuizSelection();
            }

        },1000);


    }

    private void setQuestionText(){
        TextView questionText = (TextView) getView().findViewById(R.id.questionText);
        questionText.setText(((QuizQuestion)currentQuestion).getDefinition());
    }

    private void setOptionButtons(){
        optionsButtons = new Button[4];

        optionsButtons[0] = (Button) getView().findViewById(R.id.optionButton0);
        optionsButtons[0].setText(((QuizQuestion)currentQuestion).getOptions()[0]);

        optionsButtons[1]= (Button) getView().findViewById(R.id.optionButton1);
        optionsButtons[1].setText(((QuizQuestion)currentQuestion).getOptions()[1]);

        optionsButtons[2]= (Button) getView().findViewById(R.id.optionButton2);
        optionsButtons[2].setText(((QuizQuestion)currentQuestion).getOptions()[2]);

        optionsButtons[3]= (Button) getView().findViewById(R.id.optionButton3);
        optionsButtons[3].setText(((QuizQuestion)currentQuestion).getOptions()[3]);
    }

    private void setChronometer() {
        cm = (Chronometer) getView().findViewById(R.id.chronometer);
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

    @Override
    public void onClick(View view) {
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

        if(((QuizQuestion)currentQuestion).isAnswer(answer)){
            currentQuestion.setStatus(TRUE);
            User.getInstance().addScore(currentQuestion.getValue());
        } else {
            currentQuestion.setStatus(FALSE);
        }

        update(optionsButtons[answer], optionsButtons[((QuizQuestion)currentQuestion).getCorrectAnswer()]);
    }

    private class RushListener implements Chronometer.OnChronometerTickListener{

        @Override
        public void onChronometerTick(Chronometer chronometer) {
                String chronometerText = (String) chronometer.getText();
                String sec="";
                if(chronometerText.length()>=2){
                    sec = chronometerText.substring(chronometerText.length()-2);
                }else{
                    sec = chronometerText.substring(chronometerText.length()-1);
                }

                if(sec.contains("-")){
                    sec=sec.substring(sec.length()-1);
                }
                chronometer.setText(sec);

                if(Integer.parseInt(sec)==(currentQuestion.getTime()/3))
                    chronometer.setTextColor(oColor);

                if(Integer.parseInt(sec)==0){
                    currentQuestion.setStatus(TIMEOUT);
                    update(null, optionsButtons[((QuizQuestion)currentQuestion).getCorrectAnswer()]);

            }
        }
    }

}
