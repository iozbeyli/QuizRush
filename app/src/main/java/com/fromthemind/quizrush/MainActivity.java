package com.fromthemind.quizrush;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Game.GameType;

public class MainActivity extends Activity {

    private int backButtonCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backButtonCount = 0;
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    private void loadGame(GameType type) throws Exception {
        GameController.loadGame(type);
        boolean status = GameController.repOK();
        if(!status)
            Toast.makeText(this, "Game load failure!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Game loaded successfully!", Toast.LENGTH_SHORT).show();
    }
    /**
     * Back button listener.
     * Will close the application if the back button pressed twice.
     */
    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }


    public void onClickStart(View view){
        EditText usernameEditText = (EditText) findViewById(R.id.NicknameField);
        String nick = usernameEditText.getText().toString();
        if (nick.matches("")) {
            Toast.makeText(this, "You did not enter a nickname", Toast.LENGTH_SHORT).show();
            return;
        }
        User.deleteUser();
        User.getInstance().setNickname(nick);
        boolean status = User.getInstance().repOK();

        if(!status) {
            Toast.makeText(this, "User failure", Toast.LENGTH_SHORT).show();
            return;
        }
        //loadGame();
        //Intent intent = new Intent(this, QuizSelectActivity.class);
        //startActivity(intent);
    }


}
