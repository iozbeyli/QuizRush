package com.fromthemind.quizrush.Loader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.IntegerRes;
import android.util.Log;

import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Game.MemoGame;
import com.fromthemind.quizrush.GameDrawerActivity;
import com.fromthemind.quizrush.MemoChallenge;
import com.fromthemind.quizrush.Question.MemoBoard;
import com.fromthemind.quizrush.SQLite.RushDatabaseHelper;

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
        Collections.shuffle(positions);
        for(int i=1;i<=223;i++){
            flagsGeneral.add(i);
        }
        Collections.shuffle(flagsGeneral);
        int positionIndex=0;
        for(int i=0;i<size;i++){
            targets[i]=flagsGeneral.get(i);
            placeFlag(positions.get(positionIndex),flags,flagsGeneral.get(i));
            positionIndex++;
            placeFlag(positions.get(positionIndex),flags,flagsGeneral.get(i));
            positionIndex++;
        }
        for(int i=size;i<((size*size)-size);i++){
            placeFlag(positions.get(positionIndex),flags,flagsGeneral.get(i));
            positionIndex++;
        }

        board.setFlags(flags);
        board.setTargets(targets);
        GameController.setMemoBoard(board);

    }

    public static void loadGame(MemoChallenge memoChallenge) {
        MemoBoard board = GameController.getMemoBoard();
        int size = board.getBoardSize();
        int[] targets = new int[size];
        for(int i=0;i<size;i++){
            targets[i]=memoChallenge.targetFlags.get(i);
        }
        int[][] flags = new int [size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                flags[i][j]=memoChallenge.boardFlags.get(i).get(j);
            }
        }
        board.setFlags(flags);
        board.setTargets(targets);
        GameController.setMemoBoard(board);

    }


    private static void placeFlag(int position,int[][] flags,int flag){

        int x = position%flags.length;
        int y = position/flags.length;
        flags[x][y]=flag;
    }

    public static boolean loadGameOffline(Context applicationContext) {
        Log.d("MemoLoader","LoadingOffline");
        MemoBoard board = GameController.getMemoBoard();
        int size = board.getBoardSize();
        int[] targets = new int[size];
        int[][] flags = new int [size][size];
        ArrayList<Integer> positions = new ArrayList<Integer>();
        ArrayList<Integer> flagsGeneral = new ArrayList<Integer>();
        RushDatabaseHelper helper = new RushDatabaseHelper(applicationContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<Integer> offlineFlags=RushDatabaseHelper.retrieveOfflineFlags(db);
        for(int i=0;i<size*size;i++) {
            positions.add(i);
        }
        Collections.shuffle(positions);
        if(offlineFlags.size()<(size*size-size)){
            return false;
        }
        for(int i=0;i<offlineFlags.size();i++){
            flagsGeneral.add(offlineFlags.get(i));
        }
        Collections.shuffle(flagsGeneral);
        int positionIndex=0;
        for(int i=0;i<size;i++){
            targets[i]=flagsGeneral.get(i);
            placeFlag(positions.get(positionIndex),flags,flagsGeneral.get(i));
            positionIndex++;
            placeFlag(positions.get(positionIndex),flags,flagsGeneral.get(i));
            positionIndex++;
        }
        for(int i=size;i<((size*size)-size);i++){
            placeFlag(positions.get(positionIndex),flags,flagsGeneral.get(i));
            positionIndex++;
        }
        board.setFlags(flags);
        board.setTargets(targets);
        GameController.setMemoBoard(board);
        return true;
    }
}
