package com.fromthemind.quizrush.Game;

import com.fromthemind.quizrush.Loader.QuizLoader;
import com.fromthemind.quizrush.Challenge;

/**
 * Created by Melih on 24.03.2017.
 */

public class QuizGame extends Game{

    protected Challenge challenge = null;

    protected QuizGame(){
        super(GameType.QUIZ);
    }

    protected QuizGame(Challenge challenge){
        super(GameType.QUIZ);
        this.challenge = challenge;
    }

    protected void load(){
        QuizLoader.getInstance().loadGame(this);
    }

}
