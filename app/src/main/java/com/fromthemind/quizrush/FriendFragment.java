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
import android.widget.Button;
import android.widget.EditText;

import com.fromthemind.quizrush.dummy.DummyItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class FriendFragment extends Fragment {

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
    public FriendFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FriendFragment newInstance(int columnCount) {
        FriendFragment fragment = new FriendFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
    public static FriendFragment newInstance(ClickListener listener,String buttonText) {
        FriendFragment fragment = new FriendFragment();
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
        layout = inflater.inflate(R.layout.fragment_friend_list, container, false);

        updateLayout();
        Button addFriendButton = (Button) layout.findViewById(R.id.add_friend_button);
        if(buttonText!=null){
            addFriendButton.setText(buttonText);
        }
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener==null){
                    EditText addFriendTextEdit =(EditText) layout.findViewById(R.id.add_friend_edit_text);
                    Log.d("add Friend", addFriendTextEdit.getText().toString());

                    User.getInstance().getFriends().add(addFriendTextEdit.getText().toString());
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
                                updateLayout();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });
                }else{
                    EditText addFriendTextEdit =(EditText) layout.findViewById(R.id.add_friend_edit_text);
                    listener.onClickButton(addFriendTextEdit.getText().toString());
                }
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
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public void updateLayout(){
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.list);
        // Set the adapter
        if (recyclerView instanceof RecyclerView) {
            Context context = layout.getContext();

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            ArrayList<String> hm = User.getInstance().getFriends();
            ArrayList<RushListItem> list = new ArrayList<RushListItem>();
            for (String s: hm) {
                RushListItem r = new DummyItem(s);
                list.add(r);
            }


            recyclerView.setAdapter(new RushRecyclerViewAdapter(list, mListener));
        }
    }


}
