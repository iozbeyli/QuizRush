package com.fromthemind.quizrush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Game.GameType;

public class MemoSelectActivity extends AppCompatActivity implements MemoSelector{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_select);
    }

    @Override
    public void goToMemoGame(int boardSize) {
        try {
            GameController.loadGame(GameType.MEMO,boardSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this,MemoActivity.class);
        startActivity(intent);
    }
}
