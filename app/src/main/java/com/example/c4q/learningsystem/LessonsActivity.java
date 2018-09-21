package com.example.c4q.learningsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.c4q.learningsystem.lesson_recycler_view.LessonAdapter;
import com.example.c4q.learningsystem.models.Lessons;
import com.example.c4q.learningsystem.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LessonsActivity extends AppCompatActivity {
    @BindView(R.id.new_lesson_bbtn)
    Button addLessonBttn;

    User user;

    RecyclerView lessonRV;
    List<Lessons> lessonsList;


    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        ButterKnife.bind(this);
        createNewLesson();


         lessonRV = findViewById(R.id.lessons_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        lessonRV.setLayoutManager(linearLayoutManager);

        lessonsList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
         databaseReference = firebaseDatabase.getReference("users").child("8VjZWjZIHdSwX6KBmqUVccxX16Z2").child("lessons");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userId = user.getUid();

//        NewLessonFragment lessonFragment = new NewLessonFragment();
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("lessons");

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is signed in
                } else {
                    //user is signed out
                }
            }
        };

//        lessonsList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void showData(DataSnapshot dataSnapshot) {

//        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//            lessonsList.add(ds.getValue(Lessons.class));
//        }
//
//        lessonsList.add(dataSnapshot.getValue(Lessons.class));
//        Log.d("size:", lessonsList.size()+"");

        for (DataSnapshot ds: dataSnapshot.getChildren()) {

            Lessons lessons = ds.getValue(Lessons.class);
            lessonsList.add(lessons);
        }
        Collections.reverse(lessonsList);
        LessonAdapter lessonAdapter = new LessonAdapter(lessonsList);
        lessonRV.setAdapter(lessonAdapter);

    }

    public void createNewLesson() {

        addLessonBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("button clicked", "opens fragment");

                Intent i = new Intent(LessonsActivity.this, LessonDetails.class);
                startActivity(i);

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

}