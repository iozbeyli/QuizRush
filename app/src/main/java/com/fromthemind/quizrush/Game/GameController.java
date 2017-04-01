package com.fromthemind.quizrush.Game;

import com.fromthemind.quizrush.Category.Category;
import com.fromthemind.quizrush.Question.MemoBoard;
import com.fromthemind.quizrush.Question.Question;

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
                game = new QuizGame();
                ((QuizGame)game).load();
                break;

            case MEMO:
                game = new MemoGame(4);
                ((MemoGame)game).load();
                break;

            default:
                throw new Exception("Unknown Game Type");
        }
    }

    public static void loadGame(GameType type, int size) throws Exception {
        switch (type){
            case QUIZ:
                game = new QuizGame();
                ((QuizGame)game).load();
                break;

            case MEMO:
                game = new MemoGame(size);
                ((MemoGame)game).load();
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

    public static Question getCurrentQuestion(){
        return game.getCurrentQuestion();
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

    public static MemoBoard getMemoBoard() {
        if(game instanceof MemoGame)
            return ((MemoGame)game).getBoard();

        return null;
    }

    public static void setMemoBoard(MemoBoard mb) {
        if(game instanceof MemoGame)
            ((MemoGame)game).setBoard(mb);
    }
}
