package com.fromthemind.quizrush;

import android.app.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Loader.GameLoader;
import com.fromthemind.quizrush.SQLite.RushDatabaseHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by MEHMET on 1.04.2017.
 */

public class MemoQuestionFragment extends Fragment implements  View.OnClickListener{

    static interface MemoInterface{
        void loadNextMemoLevel();
        void showScore();
    }
        private int currentSeconds = 0;
        private int clickedSeconds = -1;

        private ArrayList<Integer> lastSelectedIDs = new ArrayList<>();
        private ArrayList<String> lastSelectedTags = new ArrayList<>();
        private ArrayList<ImageView> boardImages = new ArrayList<>();
        private ArrayList<Integer> found=new ArrayList<>();
        private ArrayList<String> foundTags = new ArrayList<>();
        private LinearLayout.LayoutParams horizontalParams = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
        private LinearLayout.LayoutParams verticalParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,0);
        private Bitmap[][] boardFlagsBitmaps;
        private Bitmap secret=null;
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
            questionTime();
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
            savedInstanceState.putStringArrayList("foundTags",foundTags);
        }
        @Override
        public void onActivityCreated(Bundle savedInstanceState){
         super.onActivityCreated(savedInstanceState);
            if(savedInstanceState!=null){
                foundTags = savedInstanceState.getStringArrayList("foundTags");
                for (int i = 0; i < boardImages.size() ; i++) {
                    //boardImages.get(i).setImageResource(R.mipmap.secret);
                    drawFlag(boardImages.get(i),"secret",-1,-1);
                }

                for (int i = 0; i < foundTags.size(); i++) {
                    final ImageView iv = (ImageView) layout.findViewWithTag(foundTags.get(i));
                    int id = iv.getId();
                    drawFlag(iv,"flat_"+id,-1,-1);
                }

            }
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

        //drawFlag(iv,id);
        //int resID = getResources().getIdentifier(imageID, "mipmap", getActivity().getPackageName());
        //iv.setImageResource(resID);
        String currentTag = (String)view.getTag();
        int j = Integer.parseInt(currentTag.substring(4,5));
        int i = Integer.parseInt(currentTag.substring(6,7));
        Log.wtf("Indices",""+j+","+i);
        iv.setImageBitmap(boardFlagsBitmaps[j][i]);
        if(foundTags.contains(currentTag))
            return;

        if(lastSelectedIDs.size()==0){
            lastSelectedIDs.add(id);
            lastSelectedTags.add(currentTag);
            clickedSeconds = currentSeconds;
        }else if(lastSelectedIDs.size()==1){
            if(lastSelectedIDs.get(0)==id&&!lastSelectedTags.get(0).equals(currentTag)){
                lastSelectedIDs.clear();
                User.getInstance().addScore(100);
                changeScoreText();
                found.add(id);
                foundTags.add(currentTag);
                foundTags.add(lastSelectedTags.get(0));
                lastSelectedTags.clear();
                if(found.size() == GameController.getMemoBoard().getBoardSize()){
                    User.getInstance().levelUpMemo();
                    /*MemoInterface activity = (MemoInterface) getActivity();
                    activity.loadNextMemoLevel();*/
                    MemoInterface activity = (MemoInterface) getActivity();
                    activity.showScore();
                }
                clickedSeconds=-1;
            }else if(lastSelectedIDs.get(0)==id&&lastSelectedTags.get(0).equals(currentTag)){
            }else{
                lastSelectedIDs.add(id);
                lastSelectedTags.add(currentTag);
                User.getInstance().loseLife();
                updateHearts();
                clickedSeconds=-1;
            }
        }else if(lastSelectedIDs.size()==2){
            if(!lastSelectedTags.contains(currentTag)){
                if(!foundTags.contains(lastSelectedTags.get(0)) || !foundTags.contains(lastSelectedTags.get(1))) {
                    ImageView lastImageView = (ImageView) layout.findViewWithTag(lastSelectedTags.get(0));
                   // lastImageView.setImageResource(R.mipmap.secret);
                   // lastImageView.setImageResource(R.mipmap.secret);
                    drawFlag(lastImageView,"secret",-1,-1);
                    drawFlag(lastImageView,"secret",-1,-1);
                    lastImageView = (ImageView) layout.findViewWithTag(lastSelectedTags.get(1));
                   // lastImageView.setImageResource(R.mipmap.secret);
                   // lastImageView.setImageResource(R.mipmap.secret);
                    drawFlag(lastImageView,"secret",-1,-1);
                    drawFlag(lastImageView,"secret",-1,-1);
                    lastSelectedIDs.clear();
                    lastSelectedTags.clear();
                    lastSelectedIDs.add(id);
                    lastSelectedTags.add(currentTag);
                }
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
            //boardImages.get(i).setImageResource(R.mipmap.secret);
            drawFlag(boardImages.get(i),"secret",-1,-1);
        }
    }

    public void initializeAllFlags(){
        LinearLayout board = (LinearLayout) layout.findViewById(R.id.boardLayout);
        int size = GameController.getMemoBoard().getBoardSize();
        int[][] boardFlags = GameController.getMemoBoard().getFlags();
        boardFlagsBitmaps = new Bitmap[size][size];
        verticalParams.weight = 1f;
        verticalParams.setMargins(5,5,5,5);

        horizontalParams.weight = 1f;
        horizontalParams.setMargins(5,5,5,5);
        for (int i = 0; i < size ; i++) {
            LinearLayout ll = new LinearLayout(GameLoader.getContext());
            ll.setLayoutParams(verticalParams);
            for (int j = 0; j < size ; j++) {
                final ImageView iv = new ImageView(getActivity());
                boardImages.add(iv);
                //String imageID = "flag_"+boardFlags[j][i];
                drawFlag(iv,"flag_"+boardFlags[j][i],j,i);

                //int resID = getResources().getIdentifier(imageID, "mipmap", getActivity().getPackageName());
                //iv.setImageResource(resID);
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
            drawFlag(iv,"flag_"+targetFlags[i],-1,-1);
            iv.setLayoutParams(horizontalParams);
            iv.setTag("target"+targetFlags[i]);
            targets.addView(iv);
        }
    }

    public void questionTime(){
        final Handler handler =  new  Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                currentSeconds++;
                if(clickedSeconds!=-1){
                    if(currentSeconds-clickedSeconds>=5){
                        ImageView lastImageView;
                        if(!foundTags.contains(lastSelectedTags.get(0))){
                            lastImageView = (ImageView)layout.findViewWithTag(lastSelectedTags.get(0));
                            //lastImageView.setImageResource(R.mipmap.secret);
                            //lastImageView.setImageResource(R.mipmap.secret);
                            drawFlag(lastImageView,"secret",-1,-1);
                            drawFlag(lastImageView,"secret",-1,-1);
                        }
                        if(lastSelectedTags.size()==2 && !foundTags.contains(lastSelectedTags.get(1))){
                            lastImageView = (ImageView)layout.findViewWithTag(lastSelectedTags.get(1));
                            //lastImageView.setImageResource(R.mipmap.secret);
                            //lastImageView.setImageResource(R.mipmap.secret);
                            drawFlag(lastImageView,"secret",-1,-1);
                            drawFlag(lastImageView,"secret",-1,-1);
                        }
                        User.getInstance().loseLife();
                        updateHearts();
                        lastSelectedIDs.clear();
                        lastSelectedTags.clear();
                        clickedSeconds=-1;
                    }
                }

                handler.postDelayed(this, 1000);
            }
        });
    }
    public void drawFlag(final ImageView iv,final String imageName,final int indexI,final int indexJ){

        RushDatabaseHelper helper = new RushDatabaseHelper(getActivity());
        SQLiteDatabase db = helper.getReadableDatabase();
        byte[] imageArray = RushDatabaseHelper.retrieveFlag(db,imageName);
        db.close();
        if(imageName.equals("secret")&&secret!=null){
            iv.setImageBitmap(secret);
        }else if(imageArray!=null){
            Log.d("SQLLite Draw","giriyor");
            Bitmap temp = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(imageArray,0,imageArray.length), 250,250, false);
            iv.setImageBitmap(temp);
            if(indexI!=-1&&indexJ!=-1){
                boardFlagsBitmaps[indexI][indexJ]=temp;
            }
            if(imageName.equals("secret")){
                secret=temp;
            }
           // iv.setImageBitmap(BitmapFactory.decodeByteArray(imageArray,0,imageArray.length));
        }else{
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://quiz-6b08b.appspot.com/flags/");

// Create a reference with an initial file path and name
            StorageReference pathReference = storageRef.child("/ic_launcher.png");
            File localFile = null;
            try {
                localFile = File.createTempFile("images", "jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }

            final File finalLocalFile = localFile;
            storageRef.child(imageName+".png").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    RushDatabaseHelper rushDatabaseHelper = new RushDatabaseHelper(getActivity());

                    SQLiteDatabase database = rushDatabaseHelper.getWritableDatabase();
                    RushDatabaseHelper.insertFlag(database,1,imageName,bytes);
                    database.close();

                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    Bitmap temp =Bitmap.createScaledBitmap(bmp, 250,250, false);
                    iv.setImageBitmap(temp);
                    if(indexI!=-1&&indexJ!=-1){
                        boardFlagsBitmaps[indexI][indexJ]=temp;
                    }
                    if(imageName.equals("secret")){
                        secret=temp;
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }
        //int resID = getResources().getIdentifier(imageID, "mipmap", getActivity().getPackageName());
        //iv.setImageResource(resID);
    }
}
