package com.fromthemind.quizrush;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Melih on 27.02.2017.
 */

public class ScoreActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        TextView scoreText = (TextView) findViewById(R.id.scoreText);
        TextView nicknameText = (TextView) findViewById(R.id.nickname);
        User user = User.getInstance();
        scoreText.setText(""+user.getScore());
    }

    public void onClickStart(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
