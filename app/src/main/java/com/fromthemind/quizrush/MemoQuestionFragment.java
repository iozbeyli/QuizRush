package com.fromthemind.quizrush;

import android.app.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Game.GameType;
import com.fromthemind.quizrush.Loader.GameLoader;


/**
 * Created by MEHMET on 1.04.2017.
 */

public class MemoQuestionFragment extends Fragment {

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            try {
                GameController.loadGame(GameType.MEMO,4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View layout = inflater.inflate(R.layout.activity_memoquestion, container, false);
            LinearLayout targets = (LinearLayout) layout.findViewById(R.id.targetLayout);
            LinearLayout board = (LinearLayout) layout.findViewById(R.id.boardLayout);
            int size = GameController.getMemoBoard().getBoardSize();
            size = 10;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1f;
            params.setMargins(5,5,5,5);

            for (int i = 0; i < size ; i++) {
                ImageView iv = new ImageView(getActivity());
                iv.setImageResource(R.mipmap.flag_10);
                iv.setLayoutParams(params);
                targets.addView(iv);
            }
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,0);
            params2.weight = 1f;
            params2.setMargins(5,5,5,5);

            for (int i = 0; i < size ; i++) {
                LinearLayout ll = new LinearLayout(GameLoader.getContext());
                ll.setLayoutParams(params2);
                for (int j = 0; j < size ; j++) {
                    ImageView iv = new ImageView(getActivity());
                    iv.setImageResource(R.mipmap.flag_25);
                    iv.setLayoutParams(params);
                    ll.addView(iv);
                }
                board.addView(ll);
            }

            return layout;
        }



        @Override
        public void onStart(){
            super.onStart();

        }

        @Override
        public void onPause(){
            super.onPause();

        }

        @Override
        public void onResume(){
            super.onResume();

        }

        @Override
        public void onSaveInstanceState(Bundle savedInstanceState){

        }



}
