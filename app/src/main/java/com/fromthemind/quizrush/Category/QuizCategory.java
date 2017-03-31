package com.fromthemind.quizrush.Category;

import com.fromthemind.quizrush.Question.Question;
import com.fromthemind.quizrush.Question.QuizQuestion;

import java.util.Arrays;

/**
 * Created by Melih on 24.03.2017.
 */
public class QuizCategory extends Category {
    private final QuizQuestion[] questions;

    private QuizCategory(String topic, QuizQuestion[] questions){
        super(topic, questions);
        this.questions = questions;
    }

    public QuizCategory(String topic){
        this(topic, new QuizQuestion[5]);
    }

    @Override
    public String toString() {
        return "Category{" +
                "\nlabel='" + getLabel() + '\'' +
                ",\nquestions=" + Arrays.toString(questions) +
                "}\n";
    }
}
