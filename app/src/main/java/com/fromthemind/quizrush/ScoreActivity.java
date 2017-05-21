package com.fromthemind.quizrush;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fromthemind.quizrush.AsyncCommunication.AsyncCommunicationTask;
import com.fromthemind.quizrush.AsyncCommunication.Communicator;
import com.fromthemind.quizrush.Game.Game;
import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Game.MemoGame;
import com.fromthemind.quizrush.dummy.DummyItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Melih on 27.02.2017.
 */

public class ScoreActivity extends Activity implements ClickListener,RushRecyclerViewAdapter.OnListFragmentInteractionListener<DummyItem>{
    private int score = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        score = User.getInstance().getScore();

        TextView scoreText = (TextView) findViewById(R.id.scoreText);
        scoreText.setText(""+score);

        TextView nicknameText = (TextView) findViewById(R.id.nickname);
        String nick = User.getInstance().getUsername();
        nicknameText.setText(nick);

        Game game = GameController.getGame();
        if(game.hasChallenge()){
            View view = findViewById(R.id.challengeButton);
            view.setVisibility(View.GONE);
            Challenge ch = game.getChallenge();
            int otherScore=-1;
            if(ch.getScore_ee() == -1) {
                ch.setScore_ee(score);
                otherScore = ch.getScore_er();
            }
            if(ch.getScore_er() == -1) {
                ch.setScore_er(score);
                otherScore = ch.getScore_ee();
            }
            TextView winLoseText = (TextView)findViewById(R.id.winOrLoseText);
            winLoseText.setVisibility(View.VISIBLE);
            if(score>otherScore){
                winLoseText.setText("YOU WIN");
            }else if(score==otherScore){
                winLoseText.setText("DRAW");
            }else{
                winLoseText.setText("YOU LOSE");
            }
            updateChallenges(null, 0, ch);

        }

    }

    public void onClickStart(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void onClickChallenge(View view){
        FriendFragment fragment = FriendFragment.newInstance(this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.challengeFragmentContainer, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        findViewById(R.id.challengeFragmentContainer).setVisibility(View.VISIBLE);
    }

    public void onBackPressed() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }



    @Override
    public void onClickButton(String challengee) {
        updateChallenges(challengee,score, null);
    }

    private void updateChallenges(String challengee, int score, Challenge challenge){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference challengesRef;
        final Challenge post;
        String key;
        if(GameController.getGame() instanceof MemoGame){
            challengesRef = database.getReference("memoChallenges");
            if(challenge == null){
                key = challengesRef.push().getKey();
                post = new MemoChallenge(User.getInstance().getUsername(), score, challengee, -1,GameController.getMemoBoard().getTargets(),GameController.getMemoBoard().getFlags(), key);
            }else {
                post = challenge;
                key = challenge.getKey();
            }
        }else{
            challengesRef = database.getReference("challenges");

            if(challenge == null) {
                key = challengesRef.push().getKey();
                post = new QuizChallenge(User.getInstance().getUsername(), score, challengee, -1, key);
            }else {
                post = challenge;
                key = challenge.getKey();
            }
        }

        challengesRef.child(key).setValue(post);
        challengesRef.addListenerForSingleValueEvent( new ValueEventListener() {
            public void setCh(Challenge ch) {
                this.ch = ch;
            }

            public Challenge ch;

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot == null || snapshot.getValue() == null){
                    Toast.makeText(ScoreActivity.this,
                            "No record found",
                            Toast.LENGTH_LONG).show();
                }
                else {Toast.makeText(ScoreActivity.this,
                        snapshot.getValue().toString(),
                        Toast.LENGTH_LONG).show();
                    JSONObject postData = new JSONObject();

                    try {
                        postData.put("sub", "New Challenge Received");
                        postData.put("receiver", post.getChallengee());
                        postData.put("text", post.getChallenger()+ " Challenges You");
                        postData.put("act", GameController.getGame().getClass().getName());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    AsyncCommunicationTask task = new AsyncCommunicationTask(null,postData, new Communicator() {
                        @Override
                        public void successfulExecute(JSONObject jsonObject) {
                            Log.d("Res", jsonObject.toString());
                        }

                        @Override
                        public void failedExecute() {

                        }
                    });
                    task.execute((Void)null);
                    //showProgress(false);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }


    @Override
    public void onListFragmentInteraction(RushListItem<DummyItem> item) {
        updateChallenges(item.getVisibleContent(),score, null);
    }
}
