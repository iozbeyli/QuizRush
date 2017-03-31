package com.fromthemind.quizrush.Game;

import com.fromthemind.quizrush.Category.Category;
import com.fromthemind.quizrush.Question.Question;

import java.util.Arrays;

/**
 * Created by Melih on 24.03.2017.
 */

public class Game {
    private Category[] categories;
    private Question currentQuestion;
    private GameType type;

    protected Game(GameType type){
        this.type = type;
        categories=new Category[3];
    }

    protected GameType getType(){
        return type;
    }

    protected void setType(GameType type){
        this.type = type;
    }

    protected void setCategory(Category cat,int index){
        categories[index]=cat;
    }

    protected Category getCategory(int index){
        return categories[index];
    }

    protected void setCurrentQuestion(int category,int question){
        currentQuestion=getCategory(category).getQuestion(question);
    }

    protected Question getCurrentQuestion(){
        return currentQuestion;
    }

    protected Category[] getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "Game{" +
                "categories=" + Arrays.toString(categories) +
                '}';
    }

    public boolean repOK() {

        for (Category obj:categories) {
            if (obj == null)
                return false;

            if(!obj.repOK())
                return false;
        }
        return true;
    }

}
