package com.fromthemind.quizrush;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Game.GameType;

/**
 * Created by Melih on 2.04.2017.
 */

public class MemoActivity extends Activity implements MemoQuestionFragment.MemoInterface{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
    }

    public void onBackPressed() {
        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadNextMemoLevel() {
        if(User.getInstance().getMemoLevel() == 7){
            showScore();
        }else{
            try {
                //GameController.loadGame(GameType.MEMO,User.getInstance().getMemoLevel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this,MemoActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void showScore() {
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }
}
