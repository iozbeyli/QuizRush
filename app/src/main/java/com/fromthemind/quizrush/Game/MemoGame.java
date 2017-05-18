package com.fromthemind.quizrush.Game;

import com.fromthemind.quizrush.Challenge;
import com.fromthemind.quizrush.Loader.MemoLoader;
import com.fromthemind.quizrush.MemoChallenge;
import com.fromthemind.quizrush.Question.MemoBoard;

/**
 * Created by Melih on 24.03.2017.
 */

public class MemoGame extends Game implements Challengable<Challenge>{
    private MemoBoard board;
    private MemoChallenge challenge;
    protected MemoGame(int boardSize, MemoChallenge challenge){
        super(GameType.MEMO);
        board = new MemoBoard(boardSize);
        this.challenge = challenge;
    }

    protected void load(){
        MemoLoader.loadGame();
    }

    protected void setBoard(MemoBoard board){
        this.board = board;
    }

    protected MemoBoard getBoard(){
        return board;
    }

    @Override
    public boolean hasChallenge() {
        return challenge != null;
    }

    @Override
    public MemoChallenge getChallenge() {
        return challenge;
    }
}
