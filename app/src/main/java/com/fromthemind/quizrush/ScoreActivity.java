package com.fromthemind.quizrush;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Melih on 27.02.2017.
 */

public class ScoreActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        int score = User.getInstance().getScore();
        TextView scoreText = (TextView) findViewById(R.id.scoreText);
        scoreText.setText(""+score);

        TextView nicknameText = (TextView) findViewById(R.id.nickname);
        String nick = User.getInstance().getNickname();
        nicknameText.setText(nick);

    }

    public void onClickStart(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
