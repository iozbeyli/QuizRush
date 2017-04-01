package com.fromthemind.quizrush.Game;

import com.fromthemind.quizrush.Loader.MemoLoader;
import com.fromthemind.quizrush.Question.MemoBoard;

/**
 * Created by Melih on 24.03.2017.
 */

public class MemoGame extends Game{
    private MemoBoard board;
    protected MemoGame(int boardSize){
        super(GameType.MEMO);
        board = new MemoBoard(boardSize);
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
}
