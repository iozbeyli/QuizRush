package com.fromthemind.quizrush;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fromthemind.quizrush.Game.Game;
import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Question.QuestionStatus;

/**
 * Created by Melih on 2.04.2017.
 */

public class QuizSelectionActivity extends Activity implements QuizSelectFragment.Listener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizselection);
    }

    @Override
    public void showScore() {
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

    @Override
    public void questionSelect() {
        Log.d("item", "clicdsaked");
        Intent intent = new Intent(this, QuizQuestionActivity.class);
        startActivity(intent);
    }


}
