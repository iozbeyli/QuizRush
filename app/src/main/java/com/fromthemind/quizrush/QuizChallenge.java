package com.fromthemind.quizrush;

/**
 * Created by MEHMET on 5.05.2017.
 */

public class QuizChallenge {
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
    public QuizChallenge() {
    }
}
