package com.fromthemind.quizrush;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.fromthemind.quizrush.dummy.DummyItem;

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
        FriendFragment fragment = FriendFragment.newInstance(this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_add_friend, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();


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
        EditText usernameEdit = (EditText)layout.findViewById(R.id.username_search_edit_text);
        EditText nameEdit = (EditText)layout.findViewById(R.id.name_search_edit_text);
        EditText surnameEdit = (EditText)layout.findViewById(R.id.surname_search_edit_text);
        EditText cityEdit = (EditText)layout.findViewById(R.id.city_search_edit_text);

    }

    @Override
    public void onListFragmentInteraction(RushListItem<DummyItem> item) {

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
