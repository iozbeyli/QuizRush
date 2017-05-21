package com.fromthemind.quizrush.Ranking;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fromthemind.quizrush.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RankingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankingFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    View layout;
    public RankingFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RankingFragment newInstance() {
        RankingFragment fragment = new RankingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout= inflater.inflate(R.layout.fragment_ranking, container, false);
        Fragment quizRankings = new QuizRankingFragment();
        FragmentTransaction ft = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ft = getChildFragmentManager().beginTransaction();
        }else{
            ft = getFragmentManager().beginTransaction();
        }
        ft.replace(R.id.quiz_ranking_frame, quizRankings);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        Fragment memoRankings = new MemoRankingFragment();
        FragmentTransaction ftMemo = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ftMemo = getChildFragmentManager().beginTransaction();
        }else{
            ftMemo = getFragmentManager().beginTransaction();
        }
        ftMemo.replace(R.id.memo_ranking_frame, memoRankings);
        ftMemo.addToBackStack(null);
        ftMemo.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ftMemo.commit();

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
}
