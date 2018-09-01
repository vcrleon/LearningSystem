package com.example.c4q.learningsystem;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LessonsActivity extends AppCompatActivity {
    @BindView(R.id.new_lesson_bbtn)
    Button addLessonBttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        ButterKnife.bind(this);
        createNewLesson();
    }

    public void createNewLesson(){

        addLessonBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("button clicked", "opens fragment");

                Intent i = new Intent(LessonsActivity.this, LessonDetails.class);
                startActivity(i);

            }
        });

    }



}