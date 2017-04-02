package com.fromthemind.quizrush;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Question.QuestionStatus;

/**
 * Created by MEHMET on 2.04.2017.
 */

public class QuizQuestionActivity extends Activity implements QuizQuestionFragment.QuizInterface{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizquestion);
    }

    @Override
    public void showQuizSelection() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(GameController.getCurrentQuestion().getStatus() == QuestionStatus.ONSTART){
            GameController.getCurrentQuestion().setStatus(QuestionStatus.PASS);
        }
    }
}
