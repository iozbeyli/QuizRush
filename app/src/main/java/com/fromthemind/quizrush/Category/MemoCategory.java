package com.fromthemind.quizrush.Category;

/**
 * Created by Melih on 24.03.2017.
 */

public class MemoCategory extends Category {

    int boardSize;

    public MemoCategory(String name,int boardSize){
        super(name,1);
        this.boardSize=boardSize;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }




}
