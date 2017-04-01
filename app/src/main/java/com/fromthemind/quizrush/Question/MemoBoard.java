package com.fromthemind.quizrush.Question;

/**
 * Created by Melih on 24.03.2017.
 */

public class MemoBoard {

    private int[] targets;
    private int[][] flags;
    private final int boardSize;

    public MemoBoard(int boardSize){
        targets = new int[boardSize];
        flags = new int[boardSize][boardSize];
        this.boardSize = boardSize;
    }

    public void setTargets(int[] targets){
        this.targets = targets;
    }

    public void setFlags(int[][] flags){
        this.flags = flags;
    }

    public int[] getTargets(){
        return targets;
    }

    public int[][] getFlags(){
        return flags;
    }

    public int getBoardSize(){
        return boardSize;
    }


}
