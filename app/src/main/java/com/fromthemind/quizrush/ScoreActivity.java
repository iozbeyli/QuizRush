package com.fromthemind.quizrush;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.fromthemind.quizrush.R.id.username;

/**
 * Created by Melih on 27.02.2017.
 */

public class ScoreActivity extends Activity implements ClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        int score = User.getInstance().getScore();
        TextView scoreText = (TextView) findViewById(R.id.scoreText);
        scoreText.setText(""+score);

        TextView nicknameText = (TextView) findViewById(R.id.nickname);
        String nick = User.getInstance().getUsername();
        nicknameText.setText(nick);

    }

    public void onClickStart(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void onClickChallenge(View view){
        FriendFragment fragment = FriendFragment.newInstance(this,"Challenge");
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
    public void onClickButton() {

    }

    @Override
    public void onClickButton(String challengee, int score) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference challengesRef = database.getReference("challenges");
        String key = challengesRef.push().getKey();

        QuizChallenge post = new QuizChallenge(User.getInstance().getUsername(), score, challengee, -1);
        challengesRef.child(key).setValue(post);
        challengesRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                        //showProgress(false);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}
