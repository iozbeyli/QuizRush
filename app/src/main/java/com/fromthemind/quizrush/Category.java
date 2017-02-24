package com.fromthemind.quizrush;

/**
 * Created by Melih on 24.02.2017.
 */

public class Category {
    private String topic;
    private Question[] questions;
    public Category(String topic){
        this.topic=topic;
        questions=new Question[5];
    }

    public void addQuestion(Question question,int index){
        questions[index]=question;
    }

    public void getQuestion(int index){
        return questions[index];
    }

    public void setTopic(String topic){
        this.topic=topic;
    }

    public String getTopic(){
        return topic;
    }
}
