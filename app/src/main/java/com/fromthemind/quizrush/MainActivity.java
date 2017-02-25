package com.fromthemind.quizrush;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Button start = (Button) findViewById(R.id.start);
        File fXmlFile = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("game.xml");
            ReadXMLFile.getInstance().read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GameController gc = GameController.getInstance();
        Log.d("Game", gc.toString());
    }

    protected void onClickNicknameSave(View view){
        EditText nickField = (EditText) findViewById(R.id.NicknameField);
        Button start = (Button) findViewById(R.id.start);
        String nickname = nickField.getText().toString();
        if(!nickname.isEmpty()){
            Log.d("Nickname ",nickname);
            start.setEnabled(true);
            start.setVisibility(View.VISIBLE);
            User.getInstance().setNickname(nickname);
        }

    }

    protected void onClickStart(View view){
        Log.d("Start ","Clicked");
        EditText usernameEditText = (EditText) findViewById(R.id.NicknameField);
        String nick = usernameEditText.getText().toString();
        if (nick.matches("")) {
            Toast.makeText(this, "You did not enter a nickname", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, QuestionSelectActivity.class);
        startActivity(intent);
    }


}
