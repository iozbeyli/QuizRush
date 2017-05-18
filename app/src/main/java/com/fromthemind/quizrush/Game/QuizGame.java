package com.fromthemind.quizrush.Game;

import com.fromthemind.quizrush.Challenge;
import com.fromthemind.quizrush.Loader.QuizLoader;
import com.fromthemind.quizrush.QuizChallenge;

/**
 * Created by Melih on 24.03.2017.
 */

public class QuizGame extends Game implements Challengable<Challenge>{

    protected QuizChallenge challenge = null;

    protected QuizGame(){
        super(GameType.QUIZ);
    }

    protected QuizGame(QuizChallenge challenge){
        super(GameType.QUIZ);
        this.challenge = challenge;
    }

    protected void load(){
        QuizLoader.getInstance().loadGame(this);
    }

    @Override
    public boolean hasChallenge() {
        return challenge!=null;
    }

    @Override
    public QuizChallenge getChallenge() {
        return challenge;
    }
}
