package com.fromthemind.quizrush;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by MEHMET on 31.03.2017.
 */

public class GameSelectFragment extends ListFragment {
    static interface Listener{
        void itemClicked(long id);
    }

    private Listener listener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        String[] gameLabels = {"Quiz Rush", "Memoli"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                gameLabels
        );
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onAttach(Context context){
        super.onAttach(context);
        Listener act;
        if (context instanceof Listener){
            act=(Listener) context;
            this.listener = act;
        }
    }

    public void onListItemClick(ListView lv, View view, int position, long id){
        if(listener != null)
            listener.itemClicked(id);
    }
}
