package com.fromthemind.quizrush;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MemoSelectFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MemoSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemoSelectFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MemoSelectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MemoSelectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MemoSelectFragment newInstance(String param1, String param2) {
        MemoSelectFragment fragment = new MemoSelectFragment();
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
        View layout =inflater.inflate(R.layout.fragment_memo_select, container, false);
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.X4Button:
                        onClickMemoSelection(4);
                        break;
                    case R.id.X5Button:
                        onClickMemoSelection(5);
                        break;
                    case R.id.X6Button:
                        onClickMemoSelection(6);
                        break;
                    default:
                        break;
                }
            }
        };
        Button X4Button = (Button)layout.findViewById(R.id.X4Button);
        X4Button.setOnClickListener(buttonClickListener);
        Button X5Button = (Button)layout.findViewById(R.id.X5Button);
        X5Button.setOnClickListener(buttonClickListener);
        Button X6Button = (Button)layout.findViewById(R.id.X6Button);
        X6Button.setOnClickListener(buttonClickListener);
        return layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    public void onClickMemoSelection(int boardSize){
        MemoSelector memoSelector = (MemoSelector) getActivity();
        memoSelector.goToMemoGame(boardSize);
    }
}
