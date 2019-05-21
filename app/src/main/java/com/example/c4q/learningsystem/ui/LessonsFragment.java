package com.example.c4q.learningsystem.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.c4q.learningsystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonsFragment extends Fragment {


    public LessonsFragment() {
        // Required empty public constructor
    }

   public static LessonsFragment newInstance() {

        Bundle args = new Bundle();
        LessonsFragment fragment = new LessonsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lessons, container, false);
    }



}
