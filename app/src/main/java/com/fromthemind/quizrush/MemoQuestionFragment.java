package com.fromthemind.quizrush;

import android.app.Fragment;

import android.os.Bundle;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Loader.GameLoader;

import java.util.ArrayList;


/**
 * Created by MEHMET on 1.04.2017.
 */

public class MemoQuestionFragment extends Fragment implements  View.OnClickListener{

    static interface MemoInterface{
        void loadNextMemoLevel();
        void showScore();
    }

        private ArrayList<Integer> lastSelectedIDs = new ArrayList<>();
        private ArrayList<String> lastSelectedTags = new ArrayList<>();
        private ArrayList<ImageView> boardImages = new ArrayList<>();
        private ArrayList<Integer> found=new ArrayList<>();
        private LinearLayout.LayoutParams horizontalParams = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
        private LinearLayout.LayoutParams verticalParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,0);

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
        private View layout;
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            User.getInstance().resetLives();
            layout = inflater.inflate(R.layout.fragment_memoquestion, container, false);

            initializeTargetFlags();
            initializeAllFlags();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideFlags();
            }

            },5000);


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
                lastSelectedIDs.clear();
                lastSelectedTags.clear();
                User.getInstance().addScore(100);
                changeScoreText();
                found.add(id);
                if(found.size() == GameController.getMemoBoard().getBoardSize()){
                    User.getInstance().levelUpMemo();
                    MemoInterface activity = (MemoInterface) getActivity();
                    activity.loadNextMemoLevel();
                }

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
        if(lives == 0){
            MemoInterface activity = (MemoInterface) getActivity();
            activity.showScore();
        }else{
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

    public void hideFlags(){
        for (int i = 0; i < boardImages.size(); i++) {
            boardImages.get(i).setImageResource(R.mipmap.secret);
        }
    }

    public void initializeAllFlags(){
        LinearLayout board = (LinearLayout) layout.findViewById(R.id.boardLayout);
        int size = GameController.getMemoBoard().getBoardSize();
        int[][] boardFlags = GameController.getMemoBoard().getFlags();

        verticalParams.weight = 1f;
        verticalParams.setMargins(5,5,5,5);

        horizontalParams.weight = 1f;
        horizontalParams.setMargins(5,5,5,5);
        for (int i = 0; i < size ; i++) {
            LinearLayout ll = new LinearLayout(GameLoader.getContext());
            ll.setLayoutParams(verticalParams);
            for (int j = 0; j < size ; j++) {
                ImageView iv = new ImageView(getActivity());
                boardImages.add(iv);
                String imageID = "flag_"+boardFlags[j][i];
                int resID = getResources().getIdentifier(imageID, "mipmap", getActivity().getPackageName());
                iv.setImageResource(resID);
                iv.setId(boardFlags[j][i]);
                iv.setTag("flag"+j+","+i);
                iv.setLayoutParams(horizontalParams);
                iv.setOnClickListener(this);
                ll.addView(iv);
            }
            board.addView(ll);
        }
    }

    public void initializeTargetFlags(){
        LinearLayout targets = (LinearLayout) layout.findViewById(R.id.targetLayout);
        int size = GameController.getMemoBoard().getBoardSize();
        int[] targetFlags = GameController.getMemoBoard().getTargets();
        horizontalParams.weight = 1f;
        horizontalParams.setMargins(5,5,5,5);
        for (int i = 0; i < size ; i++) {
            ImageView iv = new ImageView(getActivity());
            String imageID = "flag_"+targetFlags[i];
            int resID = getResources().getIdentifier(imageID, "mipmap", getActivity().getPackageName());
            iv.setImageResource(resID);
            iv.setLayoutParams(horizontalParams);
            iv.setTag("target"+targetFlags[i]);
            targets.addView(iv);
        }
    }
}
