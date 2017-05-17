package com.fromthemind.quizrush;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Melih on 17.05.2017.
 */

public class MemoChallenge extends Challenge implements RushListItem<MemoChallenge> {

    public ArrayList<Integer> getTargetFlags() {
        return targetFlags;
    }

    public void setTargetFlags(ArrayList<Integer> targetFlags) {
        this.targetFlags = targetFlags;
    }

    public ArrayList<Integer> targetFlags = new ArrayList<Integer> ();

    public ArrayList<ArrayList<Integer>> getBoardFlags() {
        return boardFlags;
    }

    public void setBoardFlags(ArrayList<ArrayList<Integer>> boardFlags) {
        this.boardFlags = boardFlags;
    }

    public ArrayList<ArrayList<Integer>> boardFlags = new ArrayList<ArrayList<Integer>>();
    public MemoChallenge(String challenger,int score_er,String challengee,int score_ee,int[] targetFlags,int[][] boardFlags) {
        super(challenger,score_er,challengee,score_ee);

        for (int i = 0; i < boardFlags.length; i++) {
            this.targetFlags.add(targetFlags[i]);
        }
        for (int i = 0; i < boardFlags.length; i++) {
            ArrayList<Integer> row = new ArrayList<Integer>();
            for (int j = 0; j < boardFlags[0].length; j++) {
                row.add(boardFlags[i][j]);
            }
            this.boardFlags.add(row);
        }
    }

    public MemoChallenge() {

    }
    @Override
    public String getVisibleContent() {
        return "Challenger: "+challenger
                +"\nChallengee: "+challengee
                +"\nscore_er: " +score_er
                +"\nscore_ee: "+score_ee;
    }

    @Override
    public MemoChallenge rushItem() {
        return null;
    }
}
