package com.fromthemind.quizrush;

/**
 * Created by Melih on 17.05.2017.
 */

public class MemoChallenge extends Challenge implements RushListItem<MemoChallenge> {

    public int[] getTargetFlags() {
        return targetFlags;
    }

    public void setTargetFlags(int[] targetFlags) {
        this.targetFlags = targetFlags;
    }

    public int[] targetFlags;

    public int[][] getBoardFlags() {
        return boardFlags;
    }

    public void setBoardFlags(int[][] boardFlags) {
        this.boardFlags = boardFlags;
    }

    public int[][] boardFlags;
    public MemoChallenge(String challenger,int score_er,String challengee,int score_ee,int[] targetFlags,int[][] boardFlags) {
        super(challenger,score_er,challengee,score_ee);
        this.targetFlags =targetFlags;
        this.boardFlags=boardFlags;
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
