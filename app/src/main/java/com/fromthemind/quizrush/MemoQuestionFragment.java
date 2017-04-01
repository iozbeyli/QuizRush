package com.fromthemind.quizrush;

import android.app.Fragment;

import android.media.Image;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Game.GameType;
import com.fromthemind.quizrush.Loader.GameLoader;

import java.util.ArrayList;


/**
 * Created by MEHMET on 1.04.2017.
 */

public class MemoQuestionFragment extends Fragment implements  View.OnClickListener{

        private ArrayList<Integer> lastSelectedIDs = new ArrayList<Integer>();
        private ArrayList<String> lastSelectedTags = new ArrayList<String>();

        private ArrayList<Integer> found=new ArrayList<Integer>();
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
        private View layout;
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            User.getInstance().resetLives();
            layout = inflater.inflate(R.layout.activity_memoquestion, container, false);
            LinearLayout targets = (LinearLayout) layout.findViewById(R.id.targetLayout);
            LinearLayout board = (LinearLayout) layout.findViewById(R.id.boardLayout);
            int size = GameController.getMemoBoard().getBoardSize();
            int[] targetFlags = GameController.getMemoBoard().getTargets();
            int[][] boardFlags = GameController.getMemoBoard().getFlags();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1f;
            params.setMargins(5,5,5,5);

            for (int i = 0; i < size ; i++) {
                ImageView iv = new ImageView(getActivity());
                String imageID = "flag_"+targetFlags[i];
                int resID = getResources().getIdentifier(imageID, "mipmap", getActivity().getPackageName());
                iv.setImageResource(resID);
                iv.setLayoutParams(params);
                iv.setTag("target"+targetFlags[i]);
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
                    iv.setImageResource(R.mipmap.secret);
                    iv.setId(boardFlags[j][i]);
                    iv.setTag("flag"+j+","+i);
                    iv.setLayoutParams(params);
                    iv.setOnClickListener(this);
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


    @Override
    public void onClick(View view) {
        int id =view.getId();
        int[] targets=GameController.getMemoBoard().getTargets();
        boolean contains=false;
        for (int i = 0; i < targets.length; i++) {
            if(targets[i]==id){
                contains=true;
                break;
            }
        }
        ImageView iv = (ImageView)view;
        String imageID = "flag_"+id;
        int resID = getResources().getIdentifier(imageID, "mipmap", getActivity().getPackageName());
        iv.setImageResource(resID);
        String currentTag = (String)view.getTag();

        if(lastSelectedIDs.size()==0){
            lastSelectedIDs.add(id);
            lastSelectedTags.add(currentTag);
        }else if(lastSelectedIDs.size()==1){
            if(lastSelectedIDs.get(0)==id&&!lastSelectedTags.get(0).equals(currentTag)){
                found.add(id);
                lastSelectedIDs.clear();
                lastSelectedTags.clear();
                User.getInstance().addScore(100);
                changeScoreText();
            }else if(lastSelectedIDs.get(0)==id&&lastSelectedTags.get(0).equals(currentTag)){
            }else{
                lastSelectedIDs.add(id);
                lastSelectedTags.add(currentTag);
                User.getInstance().loseLife();
                updateHearts();
            }
        }else if(lastSelectedIDs.size()==2){
            if(!lastSelectedTags.contains(currentTag)){
                ImageView lastImageView = (ImageView)layout.findViewWithTag(lastSelectedTags.get(0));
                lastImageView.setImageResource(R.mipmap.secret);
                lastImageView.setImageResource(R.mipmap.secret);
                lastImageView = (ImageView)layout.findViewWithTag(lastSelectedTags.get(1));
                lastImageView.setImageResource(R.mipmap.secret);
                lastImageView.setImageResource(R.mipmap.secret);
                lastSelectedIDs.clear();
                lastSelectedTags.clear();
                lastSelectedIDs.add(id);
                lastSelectedTags.add(currentTag);
            }
        }
    }

    public void changeScoreText(){
        TextView scoreText = (TextView)layout.findViewById(R.id.memoScoreText);
        scoreText.setText(""+User.getInstance().getScore());
    }

    public void updateHearts(){
        User user = User.getInstance();
        int lives = user.getLives();
        LinearLayout healthLayout = (LinearLayout)layout.findViewById(R.id.healthLayout);
        healthLayout.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < lives ; i++) {
            ImageView iv = new ImageView(getActivity());
            iv.setImageResource(R.drawable.heart);
            params.weight=1;
            iv.setLayoutParams(params);
            healthLayout.addView(iv);
        }
    }
}
