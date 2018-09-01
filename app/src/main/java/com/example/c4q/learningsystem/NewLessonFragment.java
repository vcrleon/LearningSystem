package com.example.c4q.learningsystem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewLessonFragment extends Fragment {
    @BindView(R.id.title_input)
    EditText lessonTitle;
    @BindView(R.id.date_input)
    EditText lessonDate;
    @BindView(R.id.time_input)
    EditText lessonTime;
    @BindView(R.id.lecture_pic_bttn)
    ImageButton lecturePicBttn;
    @BindView(R.id.hw_pic_bttn)
    ImageButton hwPicBttn;
    @BindView(R.id.create_lesson_bttn)
    Button createLessonBttn;

    private View rootview;


    public NewLessonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_new_lesson, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }



}
