package com.fromthemind.quizrush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Created by MEHMET on 17.05.2017.
 */
public class ChallengeListActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_list);
    }

    protected void goQuiz(){
        Intent intent = new Intent(this,QuizSelectionActivity.class);
        startActivity(intent);
    }
}
