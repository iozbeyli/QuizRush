package com.fromthemind.quizrush;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by MEHMET on 18.05.2017.
 */
public class MemoChallengeListActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_challenge_list);
    }

    protected void goMemo(){
        Intent intent = new Intent(this,MemoQuestionFragment.class);
        startActivity(intent);
    }
}