package com.fromthemind.quizrush;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Melih on 2.04.2017.
 */

public class QuizSelectionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizselection);
        QuizSelectFragment workoutDetailFragment = (QuizSelectFragment) getFragmentManager().findFragmentById(R.id.quiz_selection_fragment);
    }

}
