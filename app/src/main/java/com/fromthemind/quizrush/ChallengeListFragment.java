package com.fromthemind.quizrush;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fromthemind.quizrush.dummy.DummyItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChallengeListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private RushRecyclerViewAdapter.OnListFragmentInteractionListener mListener;
    private View layout;
    private ClickListener listener=null;
    private String buttonText=null;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChallengeListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ChallengeListFragment newInstance(int columnCount) {
        ChallengeListFragment fragment = new ChallengeListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
    public static ChallengeListFragment newInstance(ClickListener listener, String buttonText) {
        ChallengeListFragment fragment = new ChallengeListFragment();
        fragment.listener=listener;
        fragment.buttonText=buttonText;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User.updateInstance();
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_challenge_list, container, false);

        list_ee();
        list_er();

        return layout;
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

    public void list_ee(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query ee_query = database.getReference("challenges").orderByChild("challenge").equalTo(User.getInstance().getUsername());
        ee_query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot == null || snapshot.getValue() == null){
                    Log.wtf("username", "no record after friend add");
                }
                else {
                    RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.ee_list);

                    // Set the adapter
                    if (recyclerView instanceof RecyclerView) {
                        Context context = layout.getContext();

                        if (mColumnCount <= 1) {
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        } else {
                            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                        }

                        ArrayList<RushListItem> hm = new ArrayList<RushListItem>();
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            hm.add(postSnapshot.getValue(QuizChallenge.class));
                        }


                        recyclerView.setAdapter(new RushRecyclerViewAdapter(hm, mListener));
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void list_er(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query ee_query = database.getReference("challenges").orderByChild("challenger").equalTo(User.getInstance().getUsername());
        ee_query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot == null || snapshot.getValue() == null){
                    Log.wtf("username", "no record after friend add");
                }
                else {
                    RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.er_list);

                    // Set the adapter
                    if (recyclerView instanceof RecyclerView) {
                        Context context = layout.getContext();

                        if (mColumnCount <= 1) {
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        } else {
                            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                        }

                        ArrayList<RushListItem> hm = new ArrayList<RushListItem>();
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            hm.add(postSnapshot.getValue(QuizChallenge.class));
                        }


                        recyclerView.setAdapter(new RushRecyclerViewAdapter(hm, mListener));
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

}
