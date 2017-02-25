package com.fromthemind.quizrush;

import java.io.BufferedReader;
import java.util.Arrays;

/**
 * Created by Melih on 24.02.2017.
 */

public class GameController {
    private static GameController instance;
    private Category[] categories;
    public static GameController getInstance(){
        if(instance == null)
            instance = new GameController();

        return instance;
    }
    private GameController(){
        categories=new Category[3];
    }

    public void setCategory(Category cat,int index){
        categories[index]=cat;
    }

    @Override
    public String toString() {
        return "GameController{" +
                "categories=" + Arrays.toString(categories) +
                '}';
    }

    public Category getCategory(int index){
        return categories[index];
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
