package com.fromthemind.quizrush;

/**
 * Created by Melih on 17.05.2017.
 */

public class MemoChallenge implements RushListItem{
    public String getChallenger() {
        return challenger;
    }

    public void setChallenger(String challenger) {
        this.challenger = challenger;
    }

    public String getChallengee() {
        return challengee;
    }

    public void setChallengee(String challengee) {
        this.challengee = challengee;
    }

    public int getScore_ee() {
        return score_ee;
    }

    public void setScore_ee(int score_ee) {
        this.score_ee = score_ee;
    }

    public int getScore_er() {
        return score_er;
    }

    public void setScore_er(int score_er) {
        this.score_er = score_er;
    }

    public String challenger = "";
    public String challengee = "";
    public int score_er = 0;
    public int score_ee = 0;

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
    public MemoChallenge(String challenger,int score_er,String challengee,int score_ee) {
        this.challenger = challenger;
        this.score_er=score_er;
        this.challengee=challengee;
        this.score_ee = score_ee;
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
}
