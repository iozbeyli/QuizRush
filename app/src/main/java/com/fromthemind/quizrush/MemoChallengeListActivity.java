package com.fromthemind.quizrush;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Game.GameType;


/**
 * Created by MEHMET on 18.05.2017.
 */
public class MemoChallengeListActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_challenge_list);
    }

    protected void goMemo(Challenge ch){
        try {
            GameController.loadGame(GameType.MEMO,ch);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this,MemoActivity.class);
        startActivity(intent);
    }
}