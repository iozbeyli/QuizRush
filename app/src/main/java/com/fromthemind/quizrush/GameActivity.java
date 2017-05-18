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
import com.fromthemind.quizrush.Question.QuestionStatus;

/**
 * Created by MEHMET on 31.03.2017.
 */

public class GameActivity extends Activity implements QuizSelectFragment.Listener, GameSelectFragment.Listener, MemoQuestionFragment.MemoInterface, QuizQuestionFragment.QuizInterface,MemoSelector {
    private boolean inQuestion = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    public void onBackPressed() {
        if(!inQuestion){
            GameController.deleteGame();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }else{
            if(GameController.getCurrentQuestion().getStatus() == QuestionStatus.ONSTART){
                GameController.getCurrentQuestion().setStatus(QuestionStatus.PASS);
            }
            showQuizSelection();
        }
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
            inQuestion=true;
        }
    }

    @Override
    public void itemClicked(long id) {
        inQuestion=false;
        Log.d("item", "clicked");
        View fragmentContainer = findViewById(R.id.fragment_container);
        Fragment fragment;
        if(fragmentContainer != null){
            if(id == 0){
                try {
                    GameController.loadGame(GameType.QUIZ, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fragment = new QuizSelectFragment();

            }else if(id==1){
                fragment = new MemoSelectFragment();
            }else{
                fragment = new FriendFragment();
            }
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }else{
            if(id == 0){
                try {
                    GameController.loadGame(GameType.QUIZ, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(this,QuizSelectionActivity.class);
                startActivity(intent);
                //Intent intent = new Intent(this,ScoreActivity.class);
                //startActivity(intent);

            }else if(id==1){
                Intent intent = new Intent(this,MemoSelectActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(this,MemoChallengeListActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void loadNextMemoLevel() {
        if(User.getInstance().getMemoLevel() == 7){
            showScore();
            inQuestion=false;
        }else{
            itemClicked(1);
        }
    }

    @Override
    public void showQuizSelection() {
        Fragment fragment = new QuizSelectFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }

    @Override
    public void goToMemoGame(int boardSize) {
        View fragmentContainer = findViewById(R.id.fragment_container);
        try {
            GameController.loadGame(GameType.MEMO,boardSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(fragmentContainer != null){
            Fragment fragment;
            fragment = new MemoQuestionFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }else{
            Intent intent = new Intent(this,MemoActivity.class);
            startActivity(intent);
        }

    }
}
