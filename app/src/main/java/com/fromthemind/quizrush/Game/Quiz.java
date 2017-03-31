package com.fromthemind.quizrush.Game;

import android.content.res.AssetManager;

import com.fromthemind.quizrush.Loader.QuizLoader;

/**
 * Created by Melih on 24.03.2017.
 */

public class Quiz extends Game{

    protected Quiz(){
        super(GameType.QUIZ);
        QuizLoader.getInstance().loadGame(this);
    }

}
