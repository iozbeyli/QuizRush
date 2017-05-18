package com.fromthemind.quizrush;

/**
 * Created by MEHMET on 17.05.2017.
 */

public class QuizChallenge extends Challenge implements RushListItem<QuizChallenge> {
    public QuizChallenge(String challenger, int score_er, String challengee, int score_ee, String key) {
        super(challenger, score_er, challengee, score_ee, key);
    }

    public QuizChallenge() {

    }
    @Override
    public String getVisibleContent() {
        return super.getVisibleContent();
    }

    @Override
    public QuizChallenge rushItem() {
        return this;
    }
}
