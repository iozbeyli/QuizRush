package com.fromthemind.quizrush.Loader;

import com.fromthemind.quizrush.Game.Game;

/**
 * Created by MEHMET on 31.03.2017.
 */

public class MemoLoader extends GameLoader {

    private static MemoLoader instance;

    private MemoLoader() {super();}

    /**
     * @return Instance of Class
     */
    public static MemoLoader getInstance() {
        if (instance == null) {
            instance = new MemoLoader();
        }
        return instance;
    }


    public static void loadGame(Game game) {

    }
}
