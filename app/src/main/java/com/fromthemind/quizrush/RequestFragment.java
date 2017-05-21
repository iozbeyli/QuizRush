package com.fromthemind.quizrush;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Game.GameType;
import com.fromthemind.quizrush.dummy.DummyItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RequestFragment extends Fragment {

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
    public RequestFragment() {
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
    public static RequestFragment newInstance(ClickListener listener, String buttonText) {
        RequestFragment fragment = new RequestFragment();
        fragment.listener=listener;
        fragment.buttonText=buttonText;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_request, container, false);
        list_requests();
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

    public void list_requests(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query ee_query = database.getReference("friendRequests").orderByChild("requestee").equalTo(User.getInstance().getUsername());
        ee_query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange( DataSnapshot snapshot) {
                if (snapshot == null || snapshot.getValue() == null){
                    Log.wtf("username", "no record after friend add");
                }
                else {
                    RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.req_list);

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
                            hm.add(new DummyItem(postSnapshot.child("requester").getValue().toString()));

                        final String key = postSnapshot.getKey();
                        mListener = new RushRecyclerViewAdapter.OnListFragmentInteractionListener<String>() {
                            @Override
                            public void onListFragmentInteraction(final RushListItem<String> item) {
                                User.updateInstance();
                                User.getInstance().getFriends().add(item.getVisibleContent());
                                DatabaseReference ref = database.getReference("user");
                                String user = User.getInstance().getUsername();
                                ref.child(user).setValue(User.getInstance());
                                Log.d("clicked", "onListFragmentInteraction: "+user);

                                User.getUser(item.getVisibleContent()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot2) {
                                        if (snapshot2 == null || snapshot2.getValue() == null){Log.wtf("username", "error for friend listing");}
                                        else {
                                            User the = snapshot2.child(item.getVisibleContent()).getValue(User.class);
                                            the.getFriends().add(item.getVisibleContent());
                                            DatabaseReference ref = database.getReference("user");
                                            String user = the.getUsername();
                                            ref.child(user).setValue(the);
                                            Log.d("clicked", "onListFragmentInteraction: "+key);
                                            Query ref2 = database.getReference("friendRequests").child(key);
                                            ref2.getRef().removeValue();
                                            list_requests();

                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {}
                                });


                                Log.d("clicked", "onListFragmentInteraction: "+item.getVisibleContent());
                            }

                        };
                        recyclerView.setAdapter(new RushRecyclerViewAdapter(hm, mListener));
                        }
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }


}
