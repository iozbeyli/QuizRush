package com.fromthemind.quizrush;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.fromthemind.quizrush.AsyncCommunication.AsyncCommunicationTask;
import com.fromthemind.quizrush.AsyncCommunication.Communicator;
import com.fromthemind.quizrush.Loader.GameLoader;
import com.fromthemind.quizrush.dummy.DummyItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFriendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFriendFragment extends Fragment implements RushRecyclerViewAdapter.OnListFragmentInteractionListener<DummyItem>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int mColumnCount = 1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View layout;
    private View progressView;
    private FriendFragment friends;
    public AddFriendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFriendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFriendFragment newInstance(String param1, String param2) {
        AddFriendFragment fragment = new AddFriendFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_add_friend,container,false);
        friends = FriendFragment.newInstance(this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_add_friend, friends);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        progressView=layout.findViewById(R.id.search_progress);
        Spinner criteriaSpinner = (Spinner)layout.findViewById(R.id.search_criteria_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.search_criteria_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        criteriaSpinner.setAdapter(adapter);
        Button searchButton = (Button)layout.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForUsers();
            }
        });
        return layout;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void listUsers(ArrayList<String> users){
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.addFriendList);
        // Set the adapter
        if (recyclerView instanceof RecyclerView) {
            Context context = layout.getContext();

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            ArrayList<RushListItem> list = new ArrayList<RushListItem>();
            for (String s: users) {
                RushListItem r = new DummyItem(s);
                list.add(r);
            }

            recyclerView.setAdapter(new RushRecyclerViewAdapter(list, this));
        }
    }

    public void searchForUsers(){
        EditText searchEdit = (EditText)layout.findViewById(R.id.search_edit_text);
        Spinner criteriaSpinner = (Spinner)layout.findViewById(R.id.search_criteria_spinner);
        String currentCriteria = criteriaSpinner.getSelectedItem().toString().toLowerCase();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user");
        showProgress(true);
        if(searchEdit.getText().toString().isEmpty()){
            Log.d("username",criteriaSpinner.getSelectedItem().toString());
            ArrayList<String> empty = new ArrayList<String>();
            listUsers(empty);
        }else{
            Query queryRef = userRef.orderByChild(currentCriteria).equalTo(searchEdit.getText().toString());
            queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot == null || snapshot.getValue() == null){
                        Toast.makeText(getActivity(),
                                "There is no such user, go and register! Or you have blank space in your username",
                                Toast.LENGTH_LONG).show();
                        showProgress(false);
                    }
                    else {
                        showProgress(false);
                        Log.d("users",snapshot.toString());
                        ArrayList<String> users = new ArrayList<String>();
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            User the = postSnapshot.getValue(User.class);
                            users.add(the.username);
                        }
                        listUsers(users);
                        /*User the = snapshot.child(username).getValue(User.class);
                        Toast.makeText(LoginActivity.this,
                                "Your password is "+the.getPassword(),
                                Toast.LENGTH_LONG).show();
                        if(!the.getPassword().equals(password)){
                            mPasswordView.setError(getString(R.string.error_incorrect_password));
                            mPasswordView.requestFocus();
                        }else{
                            User.setInstance(the);
                            GameLoader.setContext(getApplicationContext());
                            SaveSharedPreference.setUser(LoginActivity.this, username, password);
                            Intent intent = new Intent(LoginActivity.this, GameDrawerActivity.class);
                            startActivity(intent);
                        }*/
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
        }
    }

    public void viewSearchResult(){

    }

    @Override
    public void onListFragmentInteraction(final RushListItem<DummyItem> item) {
        User.getInstance().getFriends().add(item.getVisibleContent());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user");
        userRef.child(User.getInstance().getUsername()).setValue(User.getInstance());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot == null || snapshot.getValue() == null){
                    Log.wtf("username", "no record after friend add");
                }
                else {

                    User.updateInstance();
                    friends.updateLayout();
                    Toast.makeText(getActivity(),"User added to friends",Toast.LENGTH_LONG);
                    JSONObject postData = new JSONObject();
                    String name = this.getClass().getName();
                    int index = name.indexOf('$');
                    try {
                        postData.put("sub", "New Follower");
                        postData.put("receiver", item.getVisibleContent());
                        postData.put("text", User.getInstance().getUsername()+ " Followed You");
                        postData.put("act", this.getClass().getName().substring(0,index));
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
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
