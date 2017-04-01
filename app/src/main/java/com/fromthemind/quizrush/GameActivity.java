package com.fromthemind.quizrush;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Game.GameType;

/**
 * Created by MEHMET on 31.03.2017.
 */

public class GameActivity extends Activity implements QuizSelectFragment.Listener, GameSelectFragment.Listener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    public void onBackPressed() {
        GameController.deleteGame();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void showScore(){
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

    @Override
    public void questionSelect() {
        Log.d("item", "clicdsaked");
        View fragmentContainer = findViewById(R.id.fragment_container);
        if(fragmentContainer != null){
            QuizQuestionFragment fragment = new QuizQuestionFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

        }else{
        }
    }

    @Override
    public void itemClicked(long id) {
        Log.d("item", "clicked");
        View fragmentContainer = findViewById(R.id.fragment_container);
        Fragment fragment;
        if(fragmentContainer != null){
            if(id == 0){
                try {
                    GameController.loadGame(GameType.QUIZ);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fragment = new QuizSelectFragment();

            }else{
                try {
                    GameController.loadGame(GameType.MEMO,4);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fragment = new MemoQuestionFragment();
            }
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }else{
            if(id == 0){
                try {
                    GameController.loadGame(GameType.QUIZ);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(this,QuizSelectionActivity.class);
                startActivity(intent);

            }else{
                try {
                    GameController.loadGame(GameType.MEMO,4);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
