package com.fromthemind.quizrush.Category;

import com.fromthemind.quizrush.Question.MemoQuestion;
import com.fromthemind.quizrush.Question.Question;
import com.fromthemind.quizrush.Question.QuizQuestion;

import java.util.Arrays;

/**
 * Created by Melih on 24.03.2017.
 */

public class MemoCategory extends Category {

    private final MemoQuestion[] questions;
    int boardSize;

    private MemoCategory(String label, MemoQuestion[] questions){
        super(label, questions);
        this.questions = questions;
    }

    public MemoCategory(String label){
        this(label, new MemoQuestion[1]);
    }

    @Override
    public String toString() {
        return "Category{" +
                "\nlabel='" + getLabel() + '\'' +
                ",\nquestions=" + Arrays.toString(questions) +
                "}\n";
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }




}
