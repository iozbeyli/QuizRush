package com.fromthemind.quizrush.Loader;

import android.support.annotation.IntegerRes;

import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Game.MemoGame;
import com.fromthemind.quizrush.Question.MemoBoard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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


    public static void loadGame() {
        MemoBoard board = GameController.getMemoBoard();
        int size = board.getBoardSize();
        int[] targets = new int[size];
        Random rand = new Random();
        int[][] flags = new int [size][size];
        ArrayList<Integer> positions = new ArrayList<Integer>();
        ArrayList<Integer> flagsGeneral = new ArrayList<Integer>();

        for(int i=0;i<size*size;i++) {
            positions.add(i);
        }
        for(int i=1;i<=226;i++){
            flagsGeneral.add(i);
        }
        Collections.shuffle(flagsGeneral);
        for(int i=0;i<size;i++){
            targets[i]=flagsGeneral.get(i);
            placeFlag(positions,flags,flagsGeneral.get(i));
            placeFlag(positions,flags,flagsGeneral.get(i));
        }
        for(int i=size;i<((size*size)-(2*size));i++){
            placeFlag(positions,flags,flagsGeneral.get(i));
        }
        board.setFlags(flags);
        board.setTargets(targets);
        GameController.setMemoBoard(board);

    }

    private static void placeFlag(ArrayList<Integer> positions,int[][] flags,int flag){
        Random rand = new Random();
        int randomIndex = rand.nextInt(positions.size());
        int x = randomIndex%flags.length;
        int y = randomIndex/flags.length;
        flags[x][y]=flag;
        positions.remove(randomIndex);
    }
}
