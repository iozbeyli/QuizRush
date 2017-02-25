package com.fromthemind.quizrush;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
        super.onStart();
        AssetManager am = getApplicationContext().getAssets();
        GameLoader.getInstance().loadGame("game.xml", am);
        boolean status = GameController.getInstance().repOK();
        if(!status)
            Toast.makeText(this, "Game load failure!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Game loaded successfully!", Toast.LENGTH_SHORT).show();
    }


    public void onClickStart(View view){
        EditText usernameEditText = (EditText) findViewById(R.id.NicknameField);
        String nick = usernameEditText.getText().toString();
        if (nick.matches("")) {
            Toast.makeText(this, "You did not enter a nickname", Toast.LENGTH_SHORT).show();
            return;
        }

        User.getInstance().setNickname(nick);
        boolean status = User.getInstance().repOK();

        if(!status)
            Toast.makeText(this, "User failure", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, QuestionSelectActivity.class);
        startActivity(intent);
    }


}
