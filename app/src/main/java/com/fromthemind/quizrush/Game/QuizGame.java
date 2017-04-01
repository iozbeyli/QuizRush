package com.fromthemind.quizrush.Game;

import android.content.res.AssetManager;

import com.fromthemind.quizrush.Loader.QuizLoader;

/**
 * Created by Melih on 24.03.2017.
 */

public class QuizGame extends Game{

    protected QuizGame(){
        super(GameType.QUIZ);
    }

    protected void load(){
        QuizLoader.getInstance().loadGame(this);
    }

}
