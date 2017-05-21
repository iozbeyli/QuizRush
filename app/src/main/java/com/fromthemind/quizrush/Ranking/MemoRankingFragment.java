package com.fromthemind.quizrush.Ranking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fromthemind.quizrush.R;
import com.fromthemind.quizrush.RushListItem;
import com.fromthemind.quizrush.RushRecyclerViewAdapter;
import com.fromthemind.quizrush.User;
import com.fromthemind.quizrush.dummy.DummyItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MemoRankingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MemoRankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemoRankingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View layout;
    private int mColumnCount=1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RushRecyclerViewAdapter.OnListFragmentInteractionListener mListener;

    public MemoRankingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MemoRankingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MemoRankingFragment newInstance(String param1, String param2) {
        MemoRankingFragment fragment = new MemoRankingFragment();
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

    public void listUsers(ArrayList<User> users){
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.memo_ranking_list);
        // Set the adapter
        if (recyclerView instanceof RecyclerView) {
            Context context = layout.getContext();

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            Collections.reverse(users);
            ArrayList<RushListItem> list = new ArrayList<RushListItem>();
            int count=0;
            for (User s: users) {
                count++;
                RushListItem r = new DummyItem(""+count+","+s.getUsername()+","+s.getHighestMemo());
                list.add(r);
            }

            recyclerView.setAdapter(new RushRecyclerViewAdapter(list, mListener));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout= inflater.inflate(R.layout.fragment_memo_ranking, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user");
        Query queryRef = userRef.orderByChild("highestMemo");
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot == null || snapshot.getValue() == null){
                    Toast.makeText(getActivity(),
                            "There is no such user, go and register! Or you have blank space in your username",
                            Toast.LENGTH_LONG).show();

                }
                else {

                    Log.d("users",snapshot.toString());
                    ArrayList<User> users = new ArrayList<User>();
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        User the = postSnapshot.getValue(User.class);
                        users.add(the);
                        Log.d("User",the.username);
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
        return layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
