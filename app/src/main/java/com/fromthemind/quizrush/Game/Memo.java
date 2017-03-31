package com.fromthemind.quizrush.Game;

import com.fromthemind.quizrush.Loader.MemoLoader;

/**
 * Created by Melih on 24.03.2017.
 */

public class Memo extends Game{
    protected Memo(){
        super(GameType.MEMO);
    }

    protected void load(){
        MemoLoader.getInstance().loadGame(this);
    }
}
