package com.fromthemind.quizrush;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Button start = (Button) findViewById(R.id.start);
        start.setEnabled(false);
        start.setVisibility(View.INVISIBLE);
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
        }

    }

    protected void onClickStart(View view){
        Log.d("Start ","Clicked");
    }


}
