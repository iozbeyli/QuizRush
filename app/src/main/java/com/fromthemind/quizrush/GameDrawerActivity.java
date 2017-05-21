package com.fromthemind.quizrush;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Game.GameType;
import com.fromthemind.quizrush.Question.QuestionStatus;
import com.fromthemind.quizrush.Ranking.RankingFragment;

import org.w3c.dom.Text;

public class GameDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, QuizSelectFragment.Listener, GameSelectFragment.Listener, MemoQuestionFragment.MemoInterface, QuizQuestionFragment.QuizInterface,MemoSelector  {

    private boolean inQuestion = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView usernameText= (TextView)navigationView.getHeaderView(0).findViewById(R.id.drawer_username);
        usernameText.setText(User.getInstance().username);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String op = extras.getString("op");
            if(op != null && !op.equals("")){
                Fragment fragment = null;
                switch (op){
                    case "memo":
                        fragment = new MemoChallengeListFragment();
                        break;
                    case "quiz":
                        fragment = new ChallengeListFragment();
                        break;
                    case "friend":
                        fragment = new AddFriendFragment();
                        break;
                    default:
                        fragment = null;
                        break;
                }
                if(fragment != null){
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_game_activity, fragment);
                    ft.addToBackStack(null);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }else{
                    Log.wtf("Extra of gamedrawer intent", "Not working");
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(!inQuestion){
                GameController.deleteGame();
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
            }else{
                if(GameController.getCurrentQuestion().getStatus() == QuestionStatus.ONSTART){
                    GameController.getCurrentQuestion().setStatus(QuestionStatus.PASS);
                }
                showQuizSelection();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        Fragment fragment;
        if (id == R.id.nav_quiz) {
            // Handle the camera action
            try {
                GameController.loadGame(GameType.QUIZ, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            fragment = new QuizSelectFragment();
        } else if (id == R.id.nav_memo) {
            fragment = new MemoSelectFragment();
        } else if (id == R.id.nav_friend) {
            fragment = new AddFriendFragment();
        } else if (id == R.id.nav_memo_challenge) {
            fragment = new MemoChallengeListFragment();
        } else if(id == R.id.logout_challenge) {
            SaveSharedPreference.setUser(GameDrawerActivity.this, "", "");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            fragment = null;
        }else if(id == R.id.nav_edit_profile){
            fragment = new EditProfileFragment();
        }else if(id == R.id.nav_ranking){
            fragment = new RankingFragment();
        }else if(id==R.id.nav_friend_requests){
            fragment = new RequestFragment();
        }else{
            fragment = new ChallengeListFragment();
        }
        if(id!=R.id.logout_challenge){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame_game_activity, fragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }



        return true;
    }

    @Override
    public void goToMemoGame(int boardSize) {
        boolean loadingSuccesful=true;
        try {
            if(isNetworkAvailable()){
                GameController.loadGame(GameType.MEMO,boardSize);
            }else{
                Log.d("GameDrawerActivity","No internet");
                loadingSuccesful=GameController.loadGameOffline(GameType.MEMO,boardSize,getApplicationContext());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        if(loadingSuccesful){
            Fragment fragment;
            fragment = new MemoQuestionFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame_game_activity, fragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }else{
            Toast.makeText(this,"Not enough flags to play offline",Toast.LENGTH_LONG);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    protected void goQuiz(){
        Fragment fragment = new QuizSelectFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_game_activity, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public void itemClicked(long id) {

    }

    @Override
    public void loadNextMemoLevel() {
        if(User.getInstance().getMemoLevel() == 7){
            showScore();
            inQuestion=false;
        }else{
            itemClicked(1);
        }
    }

    @Override
    public void showScore() {
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

    @Override
    public void questionSelect() {
        Log.d("item", "clicdsaked");

        QuizQuestionFragment fragment = new QuizQuestionFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_game_activity, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        inQuestion=true;

    }

    @Override
    public void showQuizSelection() {
        Fragment fragment = new QuizSelectFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_game_activity, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    protected void goMemo(Challenge ch){
        try {
            GameController.loadGame(GameType.MEMO,ch);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Intent intent = new Intent(this,MemoActivity.class);
        startActivity(intent);*/
        Fragment fragment = new MemoQuestionFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_game_activity, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}
