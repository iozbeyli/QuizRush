package com.fromthemind.quizrush.Game;

import com.fromthemind.quizrush.Category.Category;

/**
 * Created by Melih on 24.02.2017.
 */

public class GameController {
    private static GameController instance;
    private static Game game;

    public static boolean isGameLoaded(){
        if(instance == null || !instance.repOK())
            return false;
        else
            return true;
    }

    public static void deleteGame(){
        game = null;
    }

    public static Game getGame() { return game;}

    public static void loadGame(GameType type) throws Exception {
        switch (type){
            case QUIZ:
                game = new Quiz();
                break;

            case MEMO:
                game = new Memo();
                break;

            default:
                throw new Exception("Unknown Game Type");
        }
    }

    public static Category[] getCategories(){
        return game.getCategories();
    }

    public static void setCategory(Category cat, int index){
        game.setCategory(cat, index);
    }

    public static Category getCategory(int index){
        return game.getCategory(index);
    }

    public static void setCurrentQuestion(int category, int question){
        game.setCurrentQuestion(category,question);
    }

    @Override
    public String toString() {
        return "GameController{" +
                "game=" + game.toString() +
                '}';
    }

    public static boolean repOK() {
        if (game == null)
        return false;

        if(!game.repOK())
            return false;

        return true;
    }

}
